package app.revanced.patches.duolingo.misc.integrity

import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.indexOfFirstInstruction
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction21c
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction35c

@Suppress("unused")
val disableLoginIntegrityPatch = bytecodePatch(
    name = "Disable Login Integrity",
    description = "Removes Play Integrity device attestation from login request."
) {
    compatibleWith("com.duolingo")

    execute {

        val emptySignalRef = basicLoginFingerprint.method.let {
            it.getInstruction<Instruction21c>(
                it.indexOfFirstInstruction(Opcode.SGET_OBJECT)
            ).reference
        }

        loginStateFingerprint.method.apply {
            val signalNullCheckIndex =
                this.indexOfFirstInstruction(loginStateFingerprint.stringMatches!!.last().index, Opcode.INVOKE_STATIC)
            val signalParamNullCheckInstr = this.getInstruction<Instruction35c>(signalNullCheckIndex)
            val signalParamReg = this.getInstruction<Instruction35c>(signalNullCheckIndex).registerC

            this.addInstruction(
                0, "sget-object v$signalParamReg, $emptySignalRef"
            )
        }
    }
}