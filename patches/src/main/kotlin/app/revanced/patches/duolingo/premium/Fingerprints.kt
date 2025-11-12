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

// Some method that has to do with subscription trials
internal val userIsPaidFieldUsageFingerprint = fingerprint {
    parameters("L", "L")
    returns("Z")
    strings("user", "onboardingState")
}

// Some method that has to do with checking if MAX is enabled
internal val userHasGoldFieldUsageFingerprint = fingerprint {
    parameters("L", "L", "L")
    returns("L")
    strings(
        "maxFeaturesEnabled",
        "isEmaEnabledInCourse",
        "user"
    )
}