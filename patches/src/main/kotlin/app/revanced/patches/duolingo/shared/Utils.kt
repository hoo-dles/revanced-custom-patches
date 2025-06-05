package app.revanced.patches.duolingo.shared

import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.patch.PatchException
import app.revanced.util.getReference
import app.revanced.util.indexOfFirstInstructionOrThrow
import app.revanced.util.toStringMethod
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.ClassDef
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference

internal object Utils {
    // Gets field from toString() method with the following format:
    //  toString() { return "[Class]([field1]=" + this.a + "[field2]=" + this.b + ... + ")"; }
    internal fun ClassDef.fieldFromToString(subStr: String): FieldReference {
        val toString = this.toStringMethod()
        val strIndex = toString.indexOfFirstInstructionOrThrow() {
            this.opcode == Opcode.CONST_STRING &&
                    getReference<StringReference>()?.string?.contains(subStr) ?: false
        }
        // The iget-xxx should always be after const-string and invoke-virtual (StringBuilder.append())
        val field = toString.getInstruction<ReferenceInstruction>(strIndex + 2).getReference<FieldReference>()
        return field ?: throw PatchException("Could not find field: $subStr")
    }
}