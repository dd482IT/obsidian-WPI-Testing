plugins {
    `java-library`
    `maven-publish`
    id("io.papermc.paperweight.userdev")
    id("com.github.johnrengelman.shadow")
    id("org.checkerframework")
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
    implementation(libs.cloud.core)
    implementation(libs.cloud.paper)
    implementation(libs.corn.minecraft.paper) {
        exclude(group="io.papermc.paper", module="paper-api")
    }
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    reobfJar {
        outputJar.set(rootProject.layout.buildDirectory.file("libs/${project.name}.jar"))
    }
}