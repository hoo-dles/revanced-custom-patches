package app.revanced.patches.eggbun.premium

import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.returnEarly

@Suppress("unused")
val enablePremiumPatch = bytecodePatch(
    name = "Enable Premium"
) {
    compatibleWith("kr.eggbun.eggconvo")

    execute {
        isLifetimePremiumFingerprint.method.returnEarly(true)
        getExpiredFingerprint.method.returnEarly(false)
        getLockedLessonRefFingerprint.method.returnEarly(false)
        getLockedLessonDetailsFingerprint.method.returnEarly(false)
    }
}