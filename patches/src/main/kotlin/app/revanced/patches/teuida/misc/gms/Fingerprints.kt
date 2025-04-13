package app.revanced.patches.teuida.misc.gms

import app.revanced.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal val mainActivityOnCreateFingerprint = fingerprint {
    returns("V")
    parameters("Landroid/os/Bundle;")
    custom { method, classDef ->
        method.name == "onCreate" && classDef.endsWith("/MainActivity;")
    }
}

internal val isGooglePlayServicesAvailableFingerprint = fingerprint {
    returns("I")
    accessFlags(AccessFlags.PUBLIC, AccessFlags.STATIC)
    parameters("Landroid/content/Context;","I")
    custom { method, classDef ->
        method.name == "isGooglePlayServicesAvailable" && classDef.endsWith("/GooglePlayServicesUtilLight;")
    }
}