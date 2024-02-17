import net.fabricmc.loom.api.LoomGradleExtensionAPI
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    `java-library`
    alias(libs.plugins.architectury.plugin)
    alias(libs.plugins.dev.architectury.loom).apply(false)
    alias(libs.plugins.shadow).apply(false)
    `maven-publish`

}

architectury {
    minecraft = libs.versions.minecraft.version.get()
}

var lib: LibrariesForLibs = libs
var lib__ = extensions.getByType<VersionCatalogsExtension>().named("libs")

subprojects {
    apply {
        plugin(lib.plugins.dev.architectury.loom.get().pluginId)
        plugin(lib.plugins.shadow.get().pluginId)
        plugin(lib.plugins.maven.publish.get().pluginId)
    }

    version = lib.versions.mod.version.get()
    base.archivesName = "creative-next-generation"


    val loom = project.extensions.getByName<LoomGradleExtensionAPI>("loom")
    loom.silentMojangMappingsLicense()

    dependencies {
        "minecraft"("com.mojang:minecraft:${lib.versions.minecraft.version.get()}")
        @Suppress("UnstableApiUsage")
        "mappings"(loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-${lib.versions.minecraft.version.get()}:${lib.versions.parchment.get()}@zip")
        })
    }

    publishing {
        publications.create<MavenPublication>(project.name + "Maven") {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
        repositories {
            mavenLocal()
        }
    }
}

allprojects {
    apply(plugin = "java-library")
    apply(plugin = lib.plugins.architectury.plugin.get().pluginId)


    repositories {
        maven { url = uri("https://jitpack.io/") } // Mixin Extras, Fabric ASM
        maven { url = uri("https://api.modrinth.com/maven") }
        maven { url = uri("https://maven.jamieswhiteshirt.com/libs-release") } // Reach Entity Attributes
        maven { url = uri("https://cursemaven.com") } // Forge Config API Port
        maven { url = uri("https://maven.cafeteria.dev/releases") } // Fake Player API
        maven { url = uri("https://maven.terraformersmc.com/releases/") }
        maven {
            url = uri("https://maven.parchmentmc.org")
            name = "parchment mc"
        }
        maven {
            // location of the maven that hosts JEI files before January 2023
            name = "Progwml6's maven"
            url = uri("https://dvs1.progwml6.com/files/maven/")
        }
        maven {
            // location of the maven that hosts JEI files since January 2023
            name = "Jared's maven"
            url = uri("https://maven.blamejared.com/")
        }
        maven {
            // location of a maven mirror for JEI files, as a fallback
            name = "ModMaven"
            url = uri("https://modmaven.dev")
        }
        maven { url = uri("https://mvn.devos.one/snapshots/") } // create fabric
        maven { url = uri("https://maven.theillusivec4.top/") } // Curios
        maven { // Create Forge and Registrate Forge
            url = uri("https://maven.tterrag.com/")
            content {
                includeGroup("com.tterrag.registrate")
                includeGroup("com.simibubi.create")
            }
        }
        maven {
            // Shedaniel's maven (Architectury API)
            url = uri("https://maven.architectury.dev")
            content {
                includeGroupByRegex("me.shedaniel.*")
                includeGroup("dev.architectury")
                includeGroup("me.shedaniel")
            }
        }

        maven {
            // saps.dev Maven (KubeJS and Rhino)
            url = uri("https://maven.saps.dev/releases")
            content {
                includeGroup("dev.latvian.mods")
            }
        }
    }

    base.archivesName.set(project.name.split("-")[0])

    group = lib.versions.maven.group.get()

    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    java {
        withSourcesJar()
    }
}

