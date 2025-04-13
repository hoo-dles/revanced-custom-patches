package app.revanced.patches.teuida.premium

import app.revanced.patcher.extensions.InstructionExtensions.instructions
import app.revanced.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference

internal val premiumGetterFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.FINAL)
    returns("Ljava/lang/Boolean;")
    custom { method, classDef ->
        classDef.type == "Lnet/teuida/teuida/modelKt/MeData;" &&
                method.instructions.any { instr ->
                    instr.opcode == Opcode.IGET_OBJECT &&
                            ((instr as ReferenceInstruction).reference as FieldReference).name == "premium"
                }
    }
}
