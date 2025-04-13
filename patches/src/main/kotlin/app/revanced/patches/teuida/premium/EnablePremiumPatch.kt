package app.revanced.patches.teuida.premium

import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.patcher.patch.bytecodePatch

@Suppress("unused")
val enablePremiumPatch = bytecodePatch(
    name = "Enable Premium",
    description = "Enables premium paid subscription.",
) {
    compatibleWith("net.teuida.teuida")

    execute {
        premiumGetterFingerprint.method.replaceInstruction(
            0,
            "sget-object v0, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;"
        )
    }
}