package app.revanced.patches.cake.plus

import app.revanced.patcher.patch.rawResourcePatch
import app.revanced.patches.shared.misc.hex.HexPatchBuilder
import app.revanced.patches.shared.misc.hex.hexPatch

@Suppress("unused")
val enablePlusPatch = rawResourcePatch(
    name = "Enable Plus",
    description = "Enable Plus membership (not all features are available)."
) {
    compatibleWith("me.mycake"("6.3.4"))

    dependsOn(hexPatch(block = fun HexPatchBuilder.() {
        val hermesFile = "assets/index.android.bundle"
        val replacements = mapOf(
            // We only need to replace 2 bytes (2 method sizes), but this section has very low entropy to match against.
            "31 00 11 4A 2C DC 7C 06 00 02 00 02 0C 23 3A 02 1F 80 0E 00 2C DC 7C 1A 01 01 00 02 2B 23 3A 02 1C 00 11 4A 2C DC 7C 06 00 02 00 02 47 23 3A 02 1F 80 0E 00 2C DC 7C 1A 01 01 00 02 2B 23 3A 02 1C 00 11 4A 2C DC 7C 06 00 02 00 02 66 23 3A 02 1F 80 0E 00 2C DC 7C 1A 01 01 00 02 85 23 3A 02 0F 00 11 4A 2C DC 7C 02 00 01 00 02 94 23 3A 02 1F 80 0E 00 2C DC 7C 1A 01 01 00 02 2B 23 3A 02 1C 00 11 4A 2C DC 7C 06 00 02 00 02 B3 23 3A 02 1F 80 0E 00 2C DC 7C 1A 01 01 00 02 2B 23 3A 02 1C 00 11 4A 2C DC 7C 06 00 02 00 02 D2 23 3A 02 1F 80 0E 00 2C DC 7C 1A 01 01 00 02 F1 23 3A 02 2C 00 11 4A 2C DC 7C 06 00 03 00 02 1D 24 3A 02 1F" to "04 00 11 4A 2C DC 7C 06 00 02 00 02 0C 23 3A 02 1F 80 0E 00 2C DC 7C 1A 01 01 00 02 2B 23 3A 02 1C 00 11 4A 2C DC 7C 06 00 02 00 02 47 23 3A 02 1F 80 0E 00 2C DC 7C 1A 01 01 00 02 2B 23 3A 02 1C 00 11 4A 2C DC 7C 06 00 02 00 02 66 23 3A 02 1F 80 0E 00 2C DC 7C 1A 01 01 00 02 85 23 3A 02 0F 00 11 4A 2C DC 7C 02 00 01 00 02 94 23 3A 02 1F 80 0E 00 2C DC 7C 1A 01 01 00 02 2B 23 3A 02 1C 00 11 4A 2C DC 7C 06 00 02 00 02 B3 23 3A 02 1F 80 0E 00 2C DC 7C 1A 01 01 00 02 2B 23 3A 02 1C 00 11 4A 2C DC 7C 06 00 02 00 02 D2 23 3A 02 1F 80 0E 00 2C DC 7C 1A 01 01 00 02 F1 23 3A 02 2C 00 11 4A 2C DC 7C 06 00 03 00 02 1D 24 3A 02 04",
            // Replace last 4 bytes with opcodes for "return true"
            "33 1D 54 00 02 03 01 00 5C 00 29 00 00 2E" to "33 1D 54 00 02 03 01 00 5C 00 78 00 5c 00",
            "01 00 37 00 01 03 61 B0 5C 00 32 00 7C 03" to "01 00 37 00 01 03 61 B0 5C 00 78 00 5c 00"
        )

        replacements.map { (orig, repl) -> orig asPatternTo repl inFile hermesFile }
    }))
}