package app.revanced.patches.crunchyroll.premium

import app.revanced.patcher.fingerprint

internal val hasPremiumBenefitFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "hasPremiumBenefit" && classDef.endsWith("BenefitKt;")
    }
}