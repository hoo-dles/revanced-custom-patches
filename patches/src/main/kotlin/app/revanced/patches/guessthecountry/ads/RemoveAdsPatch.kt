package app.revanced.patches.guessthecountry.ads

import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.returnEarly

@Suppress("unused")
val RemoveAdsPatch = bytecodePatch(
    name = "Remove Ads"
) {
    compatibleWith("com.qbis.guessthecountry")

    execute {
        areAdsRemovedFingerprint.method.returnEarly(true)
    }
}