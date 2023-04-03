plugins {
    id("java")
}

val steamPath = "F:/Steam/steamapps"
group = "org.example"
version = "0.1"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

dependencies {
    compileOnly(files("$steamPath/common/SlayTheSpire/desktop-1.0.jar"))
    workshop("1605833019", "BaseMod")
    workshop("1605060445", "ModTheSpire")
    workshop("1609158507", "StSLib")
    workshop("2554005913", "[STS]Lazy Man's Kits")
}

fun DependencyHandler.workshop(id: String, name: String) {
    compileOnly(files("$steamPath/workshop/content/646570/$id/$name.jar"))
}

tasks.register<Copy>("install") {
    group = "shadowverseMod"
    dependsOn(tasks.build)
    from("$buildDir/libs")
    into("$steamPath/common/SlayTheSpire/mods")
    rename { "shadowverseMod.jar" }
}

