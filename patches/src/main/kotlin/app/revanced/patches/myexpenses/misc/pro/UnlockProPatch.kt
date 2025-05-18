package app.revanced.patches.myexpenses.misc.pro

import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.returnEarly

@Suppress("unused")
val unlockProPatch = bytecodePatch(
    name = "Unlock Pro",
) {
    compatibleWith("org.totschnig.myexpenses"("3.9.8"))

    execute {
        // Make setter value (first param) always LicenseStatus.PROFESSIONAL.
        setLicenseStatusFingerprint.method.addInstruction(
            0,
            "sget-object p1, Lorg/totschnig/myexpenses/util/licence/LicenceStatus;->PROFESSIONAL:Lorg/totschnig/myexpenses/util/licence/LicenceStatus;"
        )

        // No expiration license date is set to 0.
        getValidUntilFingerprint.method.returnEarly(0L)
    }
}
