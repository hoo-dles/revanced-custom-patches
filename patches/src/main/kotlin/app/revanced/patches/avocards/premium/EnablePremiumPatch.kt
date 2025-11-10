package app.revanced.patches.avocards.premium

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.instructions
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.returnEarly

@Suppress("unused")
val enablePremiumPatch = bytecodePatch(
    name = "Enable Premium"
) {
    compatibleWith("com.avocards")

    execute {
        getPremiumUserFingerprint.method.returnEarly(true)
        getPremiumBaseFingerprint.method.replaceInstruction(
            0,
            "sget-object v0, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;"
        )
        isPremiumFingerprint.method.returnEarly(true)

        userEntityCtorFingerprint.method.apply {
            this.addInstructions(
                this.instructions.count() - 1, """
                const/4 v1, 0x1
                iput-boolean v1, v0, Lcom/avocards/data/entity/UserEntity;->premium:Z
            """.trimIndent()
            )
        }
    }
}