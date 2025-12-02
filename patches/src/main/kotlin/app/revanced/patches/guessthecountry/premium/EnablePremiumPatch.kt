package app.revanced.patches.guessthecountry.premium

import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.returnEarly

@Suppress("unused")
val EnablePremiumPatch = bytecodePatch(
    name = "Enable Premium"
) {
    compatibleWith("com.qbis.guessthecountry")

    execute {
        isProductInCacheFingerprint.method.returnEarly(true)
    }
}