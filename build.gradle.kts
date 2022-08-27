plugins {
    java
    `maven-publish`
    id("io.papermc.paperweight.userdev") apply false
    id("com.github.johnrengelman.shadow") apply false
    id("org.checkerframework") apply false
}

group = "cafe.navy.bedrock"
version = "1.0.0"

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.checkerframework")
    apply(plugin = "maven-publish")

    group = "cafe.navy.bedrock"
    version = "1.0.0"

    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://jitpack.io")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.broccol.ai/releases")
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