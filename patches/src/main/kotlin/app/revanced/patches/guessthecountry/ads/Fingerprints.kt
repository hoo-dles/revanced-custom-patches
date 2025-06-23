package app.revanced.patches.guessthecountry.ads

import app.revanced.patcher.fingerprint

internal val areAdsRemovedFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "areAdsRemoved" && classDef.endsWith("AdsHelper;")
    }
}