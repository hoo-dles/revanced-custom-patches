package app.revanced.patches.teuida.misc.extension

import app.revanced.patches.teuida.misc.extension.hooks.startActivityInitHook
import app.revanced.patches.shared.misc.extension.sharedExtensionPatch

val sharedExtensionsPatch = sharedExtensionPatch(startActivityInitHook)
