package app.revanced.patches.duolingo.premium

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.instructions
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.*
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.ClassDef
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference

@Suppress("unused")
val enablePremiumPatch = bytecodePatch(
    name = "Enable Super",
) {
    compatibleWith("com.duolingo")

    execute {
        val hasPlusField = userFingerprint.classDef.fieldFromToString("hasPlus")
        val subscriberLevelField = userFingerprint.classDef.fieldFromToString("subscriberLevel")

        // This field is calculated in constructor, but not serialized. So we have to find its
        // name and set it late.
        val isPaidField = userIsPaidFieldUsageFingerprint.method.let {
            val isPaidIndex = it.indexOfFirstInstructionOrThrow(Opcode.IGET_BOOLEAN)
            it.getInstruction<ReferenceInstruction>(isPaidIndex).getReference<FieldReference>()!!
        }

        // Remove final keyword on fields we want to patch
        val fields = setOf(hasPlusField, subscriberLevelField, isPaidField)
        fields.forEach { userFingerprint.classDef.fieldByName(it.name).removeFlag(AccessFlags.FINAL) }

        // For patching user properties, we target the User object that is passed in to the
        // constructor of the LoggedIn class. This way we don't affect all instances of users
        // (eg. viewing a friend's profile).
        loggedInStateFingerprint.classDef.constructor.apply {
            val userType = userFingerprint.classDef.type
            val patchIndex = this.instructions.count() - 1
            // Single-parameter method means User will always be in p1.
            // Inserting right before method return, so we can clobber existing registers.
            this.addInstructions(
                patchIndex, """
                    const/4 v0, 0x1
                    iput-boolean v0, p1, $userType->${isPaidField.name}:Z
                    iput-boolean v0, p1, $userType->${hasPlusField.name}:Z
                    sget-object v0, ${subscriberLevelField.type}->PREMIUM:${subscriberLevelField.type}
                    iput-object v0, p1, $userType->${subscriberLevelField.name}:${subscriberLevelField.type}
                """.trimIndent()
            )
        }
    }
}

// Gets field from toString() method with the following format:
//  toString() { return "[Class]([field1]=" + this.a + "[field2]=" + this.b + ... + ")"; }
private fun ClassDef.fieldFromToString(subStr: String): FieldReference {
    val toString = this.toStringMethod
    val strIndex = toString.indexOfFirstInstructionOrThrow() {
        this.opcode == Opcode.CONST_STRING &&
                getReference<StringReference>()?.string?.contains(subStr) ?: false
    }
    // The iget-xxx should always be after const-string and invoke-virtual (StringBuilder.append())
    val field = toString.getInstruction<ReferenceInstruction>(strIndex + 2).getReference<FieldReference>()
    return field ?: throw PatchException("Could not find field: $subStr")
}