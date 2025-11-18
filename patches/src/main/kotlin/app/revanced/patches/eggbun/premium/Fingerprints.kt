package app.revanced.patches.eggbun.premium

import app.revanced.patcher.fingerprint

const val ACCOUNT_CLASS = "Lcom/eggbun/chat2learn/primer/model/Account;"

internal val isLifetimePremiumFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "isLifetimePremium" && classDef.type == ACCOUNT_CLASS
    }
}

internal val getExpiredFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "getExpired" && classDef.type == ACCOUNT_CLASS
    }
}

internal val getLockedLessonRefFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "getLocked" && classDef.type == "Lcom/eggbun/chat2learn/primer/model/ContentsRef\$LessonRef;"
    }
}

internal val getLockedLessonDetailsFingerprint = fingerprint {
    custom { method, classDef ->
        method.name == "getLocked" && classDef.type == "Lcom/eggbun/chat2learn/primer/network/dto/LessonDetailsState;"
    }
}