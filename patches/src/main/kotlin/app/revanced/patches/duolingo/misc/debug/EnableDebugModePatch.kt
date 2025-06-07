package app.revanced.patches.duolingo.misc.debug

import app.revanced.patcher.extensions.InstructionExtensions.getInstruction
import app.revanced.patcher.patch.PatchException
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.util.addInstructionsToEnd
import app.revanced.util.constructor
import app.revanced.util.getReference
import com.android.tools.smali.dexlib2.iface.reference.FieldReference

@Suppress("Unused")
val EnableDebugModePatch = bytecodePatch(
    name = "Enable debug mode",
    description = "Enables hidden debug menu in settings.",
    use = false
) {
    compatibleWith("com.duolingo")

    execute {
        // Obfuscated class and name, but essentially: BuildConfigProvider.isDebug
        val isDebugFieldRef = getCanOpenDebugMenuFingerprint.method
            .getInstruction(getCanOpenDebugMenuFingerprint.patternMatch!!.startIndex)
            .getReference<FieldReference>()
            ?: throw PatchException("Could not find isDebug field reference")

        val buildConfigProviderClass = classBy { it.type == isDebugFieldRef.definingClass }!!.mutableClass
        buildConfigProviderClass.constructor().addInstructionsToEnd(
            """
                const/4 v0, 0x1
                iput-boolean v0, p0, ${buildConfigProviderClass.type}->${isDebugFieldRef.name}:Z
            """.trimIndent()
        )
    }
}