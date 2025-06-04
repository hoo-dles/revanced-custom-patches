package app.revanced.patches.duolingo.music

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.extensions.InstructionExtensions.instructions
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patches.duolingo.shared.Utils.fieldFromToString
import app.revanced.util.constructor

@Suppress("unused")
val UnlockFullSongsPatch = bytecodePatch(
    name = "Unlock licensed songs",
    description = "Allows playing the full version of licensed music instead of the 30-second preview."
) {
    compatibleWith("com.duolingo")

    execute {
        musicCourseFingerprint.classDef.apply {
            val accessField = this.fieldFromToString("licensedMusicAccess")
            val constructor = this.constructor()
            constructor.addInstructions(
                constructor.instructions.count() - 1,
                """
                    sget-object v0, Lcom/duolingo/data/home/music/LicensedMusicAccess;->FULL:Lcom/duolingo/data/home/music/LicensedMusicAccess;
                    iput-object v0, p0, ${this.type}->${accessField.name}:${accessField.type}
                """.trimIndent()
            )
        }
    }
}