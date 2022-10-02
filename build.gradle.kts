plugins {
    java
    `maven-publish`
    id("com.github.johnrengelman.shadow") apply false
    id("org.checkerframework") apply false
    id("io.papermc.paperweight.userdev") apply (false)
}

group = "cafe.navy.obsidian"
version = "1.0.0"

allprojects {
    group = "cafe.navy.obsidian"
    version = "1.0.0"
}

subprojects {
    group = "cafe.navy.obsidian"
    version = "1.0.0"

    apply(plugin = "java")
    apply(plugin = "org.checkerframework")
    apply(plugin = "maven-publish")

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://jitpack.io")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.broccol.ai/releases")
        maven("https://repo.dmulloy2.net/repository/public/")
    }

    configure<JavaPluginExtension> {
        java {
            toolchain.languageVersion.set(JavaLanguageVersion.of(18))
        }
    }

    publishing {
        // create maven publication using java artifacts
        publications {
            create<MavenPublication>(project.name) {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                from(components["java"])
            }
        }
    }
}