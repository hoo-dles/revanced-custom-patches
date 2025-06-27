package app.revanced.patches.guessthecountry.premium

import app.revanced.patcher.fingerprint

internal val isProductInCacheFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "isIapProductInFileCache" && classDef.endsWith("AppActivity;")
    }
}