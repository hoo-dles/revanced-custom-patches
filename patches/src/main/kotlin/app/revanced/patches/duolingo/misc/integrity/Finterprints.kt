package app.revanced.patches.duolingo.misc.integrity

import app.revanced.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

val loginStateFingerprint = fingerprint {
    returns("V")
    accessFlags(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR)
    strings(
        "password",
        "distinctId",
        "signal"
    )
}

val basicLoginFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    parameters("Ljava/lang/String;", "Ljava/lang/String;")
    strings("password", "distinctId")
}