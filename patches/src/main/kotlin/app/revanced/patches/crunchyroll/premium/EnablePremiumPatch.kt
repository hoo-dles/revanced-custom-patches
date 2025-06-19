package app.revanced.patches.crunchyroll.premium

import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.returnEarly

@Suppress("unused")
val enablePremiumPatch = bytecodePatch(
    name = "Enable Premium"
) {
    compatibleWith("com.crunchyroll.crunchyroid")

    execute {
        hasPremiumBenefitFingerprint.method.returnEarly(true)
    }
}