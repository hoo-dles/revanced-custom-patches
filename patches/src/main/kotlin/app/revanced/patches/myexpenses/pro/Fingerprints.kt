package app.revanced.patches.myexpenses.pro

import app.revanced.patcher.fingerprint

internal val setLicenseStatusFingerprint = fingerprint {
    parameters("Lorg/totschnig/myexpenses/util/licence/LicenceStatus;")
    strings("null", "Licence")
}

internal val getValidUntilFingerprint = fingerprint {
    returns("J")
    strings("licence_valid_until")
}