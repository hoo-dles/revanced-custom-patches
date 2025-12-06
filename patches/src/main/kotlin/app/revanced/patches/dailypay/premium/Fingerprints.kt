package app.revanced.patches.dailypay.premium

import app.revanced.patcher.fingerprint
import com.android.tools.smali.dexlib2.Opcode

val widgetConfigOnCreateFingerprint = fingerprint {
    opcodes(
        Opcode.MOVE_RESULT_OBJECT,
        Opcode.INVOKE_VIRTUAL,
        Opcode.MOVE_RESULT,
        Opcode.IF_EQZ
    )
    custom { method, classDef ->
        classDef.endsWith("AppWidgetConfigureActivity;") && method.name == "onCreate"
    }
}