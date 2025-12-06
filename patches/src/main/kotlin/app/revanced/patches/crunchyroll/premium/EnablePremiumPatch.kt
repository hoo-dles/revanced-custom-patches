package app.revanced.patches.crunchyroll.premium

import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.returnEarly

@Suppress("unused")
val enablePremiumPatch = bytecodePatch(
    name = "Enable Premium",
    description = "Enables client-side only premium features. Most functionality is absent as it is verified on the server."
) {
    compatibleWith("com.crunchyroll.crunchyroid")

    execute {
        hasPremiumBenefitFingerprint.method.returnEarly(true)
    }
}