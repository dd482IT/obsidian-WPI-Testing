plugins {
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow")
    id("org.checkerframework")
}

repositories {
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    api(libs.adventure.api)
}