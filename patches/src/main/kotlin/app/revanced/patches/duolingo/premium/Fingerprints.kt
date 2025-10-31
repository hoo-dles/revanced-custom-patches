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

// Some class that has to do with subscription trials
internal val userIsPaidFieldUsageFingerprint = fingerprint {
    parameters("L", "L")
    returns("Z")
    strings("user", "onboardingState")
}