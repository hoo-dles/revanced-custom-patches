package app.revanced.patches.duolingo.premium

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.instructions
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patcher.patch.stringOption
import app.revanced.patches.duolingo.shared.Utils.fieldFromToString
import app.revanced.util.*
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference

enum class PremiumVariant {
    SUPER,
    MAX
}

@Suppress("unused")
val enablePremiumPatch = bytecodePatch(
    name = "Enable Premium",
) {
    compatibleWith("com.duolingo")

    val premiumVariant by stringOption(
        key = "premiumVariant",
        default = PremiumVariant.SUPER.name,
        values = mapOf(
            "Duolingo Super" to PremiumVariant.SUPER.name,
            "Duolingo MAX" to PremiumVariant.MAX.name
        ),
        title = "Type",
        description = "Choose which type of premium Duolingo subscription to enable.",
        required = true,
    )

    execute {
        val optionIsMax = enumValueOf<PremiumVariant>(premiumVariant!!) == PremiumVariant.MAX

        val hasPlusField = userFingerprint.classDef.fieldFromToString("hasPlus")
        val subscriberLevelField = userFingerprint.classDef.fieldFromToString("subscriberLevel")
        val subscriberLevel = if (optionIsMax) "GOLD" else "PREMIUM"

        // These fields are calculated in constructor, but not serialized. So we have to find their
        // name and set it late.
        val isPaidField = userIsPaidFieldUsageFingerprint.method.let {
            val isPaidIndex = it.indexOfFirstInstructionOrThrow(Opcode.IGET_BOOLEAN)
            it.getInstruction<ReferenceInstruction>(isPaidIndex).getReference<FieldReference>()!!
        }
        val hasGoldField = userHasGoldFieldUsageFingerprint.method.let {
            val hasGoldIndex = it.indexOfFirstInstructionOrThrow(Opcode.IGET_BOOLEAN)
            it.getInstruction<ReferenceInstruction>(hasGoldIndex).getReference<FieldReference>()!!
        }

        // Remove final keyword on fields we want to patch.
        val fields = mutableSetOf(hasPlusField, subscriberLevelField, isPaidField, hasGoldField)
        fields.forEach { userFingerprint.classDef.fieldByName(it.name).removeFlag(AccessFlags.FINAL) }

        // For patching user properties, we target the User object that is passed in to the
        // constructor of the LoggedIn class. This way we don't affect all instances of users
        // (eg. viewing a friend's profile).
        loggedInStateFingerprint.classDef.constructor().apply {
            val userType = userFingerprint.classDef.type
            val patchIndex = this.instructions.count() - 1

            val instrSb = StringBuilder()
            instrSb.appendLine(
                """
                const/4 v0, 0x1
                iput-boolean v0, p1, $userType->${isPaidField.name}:Z
                iput-boolean v0, p1, $userType->${hasPlusField.name}:Z
                """.trimIndent()
            );

            if (optionIsMax) {
                instrSb.appendLine(
                    "iput-boolean v0, p1, $userType->${hasGoldField.name}:Z"
                )
            }

            instrSb.appendLine(
                """
                sget-object v0, ${subscriberLevelField.type}->$subscriberLevel:${subscriberLevelField.type}
                iput-object v0, p1, $userType->${subscriberLevelField.name}:${subscriberLevelField.type}
                """.trimIndent()
            )

            // Single-parameter method means User will always be in p1.
            // Inserting right before method return, so we can clobber existing registers.
            this.addInstructions(
                patchIndex, instrSb.toString()
            )
        }
    }
}