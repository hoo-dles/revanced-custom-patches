package app.revanced.patches.webster.premium

import app.revanced.patcher.fingerprint

internal const val SUBSCRIPTION_CLASS = "Lcom/merriamwebster/dictionary/bean/MWSubscription;"

internal val getSubscriptionFingerprint = fingerprint {
    strings("PREF_CURRENT_SUBSCRIPTION")
    returns(SUBSCRIPTION_CLASS)
}
