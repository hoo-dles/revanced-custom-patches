package app.revanced.patches.duolingo.premium

import app.revanced.patcher.fingerprint

// Matches LoggedInState.toString()
internal val loggedInStateFingerprint = fingerprint {
    strings("LoggedIn(user=", ")")
}

// Matches User.toString()
internal val userFingerprint = fingerprint {
    strings("User(adsConfig=", ", id=", ", betaStatus=")
}

internal val userIsPaidFieldUsageFingerprint = fingerprint {
    strings("user", "subscriptionsReady")
    parameters("Ljava/lang/Object;", "Ljava/lang/Object;")
    returns("Ljava/lang/Object;")
}