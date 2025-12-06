package app.revanced.patches.dailypay.premium

import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction21t

internal val premiumWidgetPatch = bytecodePatch {
    execute {
        widgetConfigOnCreateFingerprint.apply {
            val ifIndex = patternMatch!!.endIndex
            val ifRegister = method.getInstruction<Instruction21t>(ifIndex).registerA
            method.addInstruction(
                ifIndex,
                "const/4 v$ifRegister, 0x0")
        }
    }
}