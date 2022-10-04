plugins {
    id("io.papermc.paperweight.userdev") apply (true)
    id("com.github.johnrengelman.shadow") version ("7.1.2") apply (true)
    id("java-library")
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.8.0")
    implementation(projects.obsidianCore)
    implementation("cloud.commandframework:cloud-paper:1.7.1")
    implementation("me.lucko:helper:5.6.13")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
}