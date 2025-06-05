package app.revanced.patches.duolingo.nags

import app.revanced.patcher.fingerprint

internal val homeFragmentStateFingerprint = fingerprint {
    strings("homeState", "currentScreen", "userLoadingState")
}

internal val visibleFingerprint = fingerprint {
    strings("Visible(backgroundColor=", ", buttonFallbackText=")
}