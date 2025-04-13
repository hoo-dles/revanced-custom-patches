package app.revanced.patches.teuida.misc.gms

import app.revanced.patcher.patch.Option
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patches.shared.misc.gms.gmsCoreSupportPatch
import app.revanced.patches.teuida.misc.gms.Constants.REVANCED_TEUIDA_PACKAGE_NAME
import app.revanced.patches.teuida.misc.gms.Constants.TEUIDA_PACKAGE_NAME
import app.revanced.patches.shared.misc.gms.gmsCoreSupportResourcePatch
import app.revanced.patches.teuida.misc.extension.sharedExtensionsPatch
import app.revanced.util.returnEarly

@Suppress("unused")
val gmsCoreSupportPatch = gmsCoreSupportPatch(
    fromPackageName = TEUIDA_PACKAGE_NAME,
    toPackageName = REVANCED_TEUIDA_PACKAGE_NAME,
    mainActivityOnCreateFingerprint = mainActivityOnCreateFingerprint,
    extensionPatch = sharedExtensionsPatch,
    gmsCoreSupportResourcePatchFactory = ::gmsCoreSupportResourcePatch,
) {
    compatibleWith(TEUIDA_PACKAGE_NAME)

    dependsOn(
        bytecodePatch {
            execute {
                isGooglePlayServicesAvailableFingerprint.method.returnEarly()
            }
        }
    )
}

private fun gmsCoreSupportResourcePatch(
    gmsCoreVendorGroupIdOption: Option<String>,
) = gmsCoreSupportResourcePatch(
    fromPackageName = TEUIDA_PACKAGE_NAME,
    toPackageName = REVANCED_TEUIDA_PACKAGE_NAME,
    gmsCoreVendorGroupIdOption = gmsCoreVendorGroupIdOption,
    spoofedPackageSignature = "67bd96b0fff3989af9ed068ec9bd9bbe7583b03b",
)