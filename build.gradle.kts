plugins {
    id("java-library")
    id("net.neoforged.moddev") version "2.0.141"
}

fun prop(name: String): String = property(name) as String

group = prop("mod.group")
version = prop("mod.version")
base.archivesName = prop("mod.id")

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
    withSourcesJar()
}

neoForge {
    version = prop("neoforge.version")

    runs {
        register("client") {
            client()
            gameDirectory = file("runs/client")
        }
        register("server") {
            server()
            gameDirectory = file("runs/server")
        }
    }

    mods {
        register(prop("mod.id")) {
            sourceSet(sourceSets.main.get())
        }
    }
}

repositories {
    mavenCentral()

    exclusiveContent {
        forRepository { maven("https://cursemaven.com") { name = "CurseMaven" } }
        filter { includeGroup("curse.maven") }
    }

    exclusiveContent {
        forRepository { maven("https://maven.theillusivec4.top/") { name = "Curios" } }
        filter { includeGroup("top.theillusivec4.curios") }
    }
}

dependencies {
    // The two base mods this addon extends. Both are required at runtime and are not bundled.
    implementation("curse.maven:ntm-continuum-1719328:${prop("hbm.file")}") { isTransitive = false }
    implementation("curse.maven:golem-core-1719361:${prop("golemcore.file")}") { isTransitive = false }

    // Curios: the API jar is enough to compile against; the full jar is only for dev runs.
    compileOnly("top.theillusivec4.curios:curios-neoforge:${prop("curios.version")}:api")
    runtimeOnly("top.theillusivec4.curios:curios-neoforge:${prop("curios.version")}")

    compileOnly("org.spongepowered:mixin:0.8.5")
}

tasks.processResources {
    val props = mapOf(
        "mod_id" to prop("mod.id"),
        "mod_name" to prop("mod.name"),
        "mod_version" to prop("mod.version"),
        "mod_author" to prop("mod.author"),
        "mod_license" to prop("mod.license"),
        "minecraft_range" to prop("minecraft.range"),
        "neoforge_version" to prop("neoforge.version"),
        "golemcore_version" to prop("golemcore.version"),
        "hbm_version" to prop("hbm.version"),
        "curios_version" to prop("curios.version"),
    )
    inputs.properties(props)
    filesMatching(listOf("META-INF/neoforge.mods.toml", "*.mixins.json")) {
        expand(props)
    }
}

tasks.jar {
    manifest {
        attributes(
            "Implementation-Title" to prop("mod.name"),
            "Implementation-Version" to prop("mod.version"),
        )
    }
}
