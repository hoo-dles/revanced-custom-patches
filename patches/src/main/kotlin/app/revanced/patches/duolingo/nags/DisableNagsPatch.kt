package app.revanced.patches.duolingo.nags

import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.getReference
import app.revanced.util.indexOfFirstInstruction
import app.revanced.util.indexOfFirstInstructionReversed
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction10t
import com.android.tools.smali.dexlib2.builder.instruction.BuilderInstruction21t
import com.android.tools.smali.dexlib2.iface.instruction.formats.Instruction35c
import com.android.tools.smali.dexlib2.iface.reference.MethodReference


@Suppress("unused")
val DisableNagsPatch = bytecodePatch(
    name = "Disable practice reminder nag",
) {
    compatibleWith("com.duolingo"("6.33.2"))

    execute {
        homeFragmentStateFingerprint.method.apply {
            val visibleType = visibleFingerprint.classDef.type
            val visibleInitIndex = this.indexOfFirstInstruction {
                this.opcode == Opcode.INVOKE_DIRECT &&
                        (this as Instruction35c).getReference<MethodReference>()?.definingClass == visibleType
            }

            val branchIndex = this.indexOfFirstInstructionReversed(visibleInitIndex, Opcode.IF_EQZ)
            val branchLabel = this.getInstruction<BuilderInstruction21t>(branchIndex).target

            this.replaceInstruction(branchIndex, BuilderInstruction10t(Opcode.GOTO, branchLabel))
        }
    }
}