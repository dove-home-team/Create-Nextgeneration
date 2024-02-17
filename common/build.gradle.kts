

architectury {
    common(libs.versions.enabled.platforms.get().split(","))
    injectInjectables = false
}

loom {
    mixin {
        defaultRefmapName.set("mixins.create-next-generation.refmap.json")
    }
    accessWidenerPath.set(file("src/main/resources/create-next-generation.accesswidener"))
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${libs.versions.fabric.loader.version.get()}")
    modApi("dev.architectury:architectury:${libs.versions.architectury.version.get()}")
    compileOnly(libs.gson.overwrite)
    modCompileOnly(libs.create.fabric)
    modCompileOnly(libs.kubejs.fabric)
}
