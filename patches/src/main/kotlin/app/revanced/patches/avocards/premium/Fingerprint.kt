package app.revanced.patches.avocards.premium

import app.revanced.patcher.fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal val getPremiumUserFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "getPremium" && classDef.type == "Lcom/avocards/data/entity/UserEntity;"
    }
}

internal val userEntityCtorFingerprint = fingerprint {
    accessFlags(AccessFlags.PUBLIC, AccessFlags.CONSTRUCTOR)
    custom { _, classDef ->
        classDef.type == "Lcom/avocards/data/entity/UserEntity;"
    }
}

internal val getPremiumBaseFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "getPremium" && classDef.type == "Lcom/avocards/data/entity/BaseEntity;"
    }
}

internal val isPremiumFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "isPremium" && classDef.type == "Lcom/avocards/data/manager/UserManager;"
    }
}