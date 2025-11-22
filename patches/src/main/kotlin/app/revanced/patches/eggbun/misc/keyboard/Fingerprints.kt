package app.revanced.patches.eggbun.misc.keyboard

import app.revanced.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

val krKeyboardCtorFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR)
    custom { _, classDef -> classDef.endsWith("keyboard/KoreanKeyboardDelegateImpl;") }
}