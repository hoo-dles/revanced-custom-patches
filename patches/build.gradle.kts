group = "app.revanced"

patches {
    about {
        name = "ReVanced Custom Patches"
        description = "Random ReVanced patches for niche apps"
        source = "git@github.com:hoo-dles/revanced-custom-patches.git"
        author = "hoodles"
        contact = "contact@revanced.app"
        website = "https://revanced.app"
        license = "GNU General Public License v3.0"
    }
}

dependencies {
    compileOnly(project(":patches:stub"))
}

kotlin {
    compilerOptions {
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
}