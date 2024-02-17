import net.fabricmc.loom.api.ForgeExtensionAPI

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    accessWidenerPath.set(project(":common").loom.accessWidenerPath)
    forge {
        convertAccessWideners.set(true)
        extraAccessWideners.add(loom.accessWidenerPath.get().asFile.name)
    }
}

val common: Configuration by configurations.creating
val shadowCommon: Configuration by configurations.creating
val developmentForge: Configuration by configurations.getting

configurations {
    compileClasspath.configure { extendsFrom(common) }
    runtimeClasspath.configure { extendsFrom(common) }
    developmentForge.extendsFrom(common)
}

dependencies {
    forge("net.minecraftforge:forge:${libs.versions.minecraft.version.get()}-${libs.versions.forge.version.get()}")
    modApi("dev.architectury:architectury-forge:${libs.versions.architectury.version.get()}")
    modImplementation(libs.kubejs.forge)
    implementation(libs.gson.overwrite)
    forgeRuntimeLibrary(libs.gson.overwrite)
    include(libs.gson.overwrite)
    common(project(":common", "namedElements")) {
        isTransitive = false
    }
    shadowCommon(project(":common", "transformProductionFabric")) {
        isTransitive = false
    }
    modImplementation(libs.flywheel)
    modImplementation(libs.registrate)
    modImplementation(libs.versions.create.forge.get()) {
        isTransitive = false
    }
}

tasks.processResources {
    val mapOf: Map<String, String> = mapOf(
            "version" to version.toString(),
            "forge_version" to libs.versions.forge.version.get().split(".")[0], // only specify major version of forge
            "minecraft_version" to libs.versions.minecraft.version.get(),
            "create_version" to libs.versions.create.forge.get().split(":")[2].split("-")[0] // cut off build number
    )
    mapOf.forEach {
        inputs.property(it.key, it.value)
    }
    filesMatching("META-INF/mods.toml") {
        expand(mapOf)
    }
}

loom {
    forge {
        mixinConfig(
                "create-next-generation.mixins.json"
        )
    }
}

tasks.shadowJar {
    exclude("fabric.mod.json")
    exclude("architectury.common.json")
    configurations = listOf(shadowCommon)
    archiveClassifier.set("dev-shadow")
}

tasks.remapJar {
    inputFile.set(tasks.shadowJar.get().archiveFile)
    dependsOn(tasks.shadowJar.get())
    archiveClassifier.set(null as String?)
}

tasks.jar {
    archiveClassifier.set("dev")
}

tasks.sourcesJar {
    var commonSources = project(":common").tasks.sourcesJar
    dependsOn(commonSources.get())
            from (commonSources.get().archiveFile.map { zipTree(it) })
}

components.getByName("java") {
    this as AdhocComponentWithVariants
    withVariantsFromConfiguration(project.configurations.getByName("shadowRuntimeElements")) {
        skip()
    }
}
