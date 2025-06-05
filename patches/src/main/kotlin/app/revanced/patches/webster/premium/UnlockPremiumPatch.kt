package app.revanced.patches.webster.premium

import app.revanced.patcher.extensions.InstructionExtensions.addInstructions
import app.revanced.patcher.patch.bytecodePatch

@Suppress("unused")
val UnlockPremiumPatch = bytecodePatch(
    name = "Unlock Premium"
) {
    compatibleWith("com.merriamwebster")

    execute {
        getSubscriptionFingerprint.method.addInstructions(
            0,
            """
                new-instance v0, Ljava/util/Date;
                const-wide v1, 0x7fffffffffffffffL
                invoke-direct {v0, v1, v2}, Ljava/util/Date;-><init>(J)V
                new-instance v1, $SUBSCRIPTION_CLASS    
                const-string v2, ""
                const/4 v3, 0x1
                invoke-direct {v1, v2, v0, v2, v3}, $SUBSCRIPTION_CLASS-><init>(Ljava/lang/String;Ljava/util/Date;Ljava/lang/String;Z)V
                return-object v1
                move-result-object v0
                return-object v0
            """.trimIndent()
        )
    }
}
