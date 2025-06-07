package app.revanced.patches.duolingo.misc.debug

import app.revanced.patcher.fingerprint
import com.android.tools.smali.dexlib2.Opcode

internal val getCanOpenDebugMenuFingerprint = fingerprint {
    opcodes(Opcode.IGET_BOOLEAN)
    strings("adminAccountExists", "isContractorWithDebugMenuAccess")
}
