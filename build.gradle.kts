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

    repositories {
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://jitpack.io")
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://repo.broccol.ai/releases")
    }
}

dependencies {
}