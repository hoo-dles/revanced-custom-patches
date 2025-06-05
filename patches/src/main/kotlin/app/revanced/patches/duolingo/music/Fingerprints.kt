package app.revanced.patches.duolingo.music

import app.revanced.patcher.fingerprint

// Matches toString()
internal val musicCourseFingerprint = fingerprint {
    strings("Music(courseSummary=", ", activePathSectionId=")
}