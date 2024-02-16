architectury {
    common(libs.versions.enabled.platforms.get().split(","))
}

loom {

}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${libs.versions.fabric.loader.version.get()}")
    modApi("dev.architectury:architectury:${libs.versions.architectury.version.get()}")
    modCompileOnly(libs.create.fabric)
}