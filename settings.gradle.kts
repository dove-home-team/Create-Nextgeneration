pluginManagement {
    fun RepositoryHandler.mavens(vararg mavens: String) {
        mavens.forEach { maven { url = uri(it) } }
    }
    repositories {
        mavens(
                "https://maven.fabricmc.net/",
                "https://maven.architectury.dev/",
                "https://maven.minecraftforge.net/",
        )
        gradlePluginPortal()
    }
}

include("common", "fabric", "forge")
