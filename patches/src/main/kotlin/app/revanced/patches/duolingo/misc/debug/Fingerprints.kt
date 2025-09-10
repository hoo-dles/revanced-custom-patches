package app.revanced.patches.duolingo.misc.debug

import app.revanced.patcher.fingerprint
import com.android.tools.smali.dexlib2.Opcode

internal val buildTargetFieldFingerprint = fingerprint {
    strings("BUILD_TARGET", "debug", "release")
    opcodes(Opcode.IGET_OBJECT, Opcode.IGET_BOOLEAN, Opcode.IF_EQZ)
}

