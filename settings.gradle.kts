rootProject.name = "obsidian"

setupCoreModule("api")
setupCoreModule("entities")
setupCoreModule("items")

setupPaperModule("api")
setupPaperModule("entities")
setupPaperModule("items")
setupPaperModule("plugin")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.stellardrift.ca/repository/snapshots/")
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

plugins {
    id("ca.stellardrift.polyglot-version-catalogs") version "5.0.0-SNAPSHOT"
}

fun setupCoreModule(name: String) =
    setupSubproject("obsidian-core-$name", file("obsidian-core/obsidian-core-$name"))

fun setupPaperModule(name: String) =
    setupSubproject("obsidian-paper-$name", file("obsidian-paper/obsidian-paper-$name"))

fun setupSubproject(name: String, projectDirectory: File) = setupSubproject(name) {
    projectDir = projectDirectory
}

inline fun setupSubproject(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}