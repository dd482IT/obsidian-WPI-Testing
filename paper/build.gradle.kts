plugins {
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev")
    id("com.github.johnrengelman.shadow")
    id("org.checkerframework")
}

dependencies {
    paperDevBundle("1.19.1-R0.1-SNAPSHOT")
    compileOnly(libs.cloud.core)
    compileOnly(libs.cloud.paper)
    compileOnly(libs.corn.minecraft.paper) {
        exclude(group="io.papermc.paper", module="paper-api")
    }
}