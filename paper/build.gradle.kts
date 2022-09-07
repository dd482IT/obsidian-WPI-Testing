plugins {
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev")
    id("com.github.johnrengelman.shadow")
    id("org.checkerframework")
}

repositories {
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
    api(projects.bedrockCore)
    api(libs.cloud.core)
    api(libs.cloud.paper)
    compileOnly(libs.items.core)
    compileOnly(libs.items.paper.core)
    compileOnly(libs.protocollib)
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    reobfJar {
        outputJar.set(rootProject.layout.buildDirectory.file("libs/${project.name}.jar"))
    }
}