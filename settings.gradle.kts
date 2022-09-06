rootProject.name = "bedrock"

projects("core", "paper", "plugin")

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

/**
 * Renames a set of project IDs.
 *
 * @param names the list of projects to rename
 */
fun projects(vararg names: String) {
    include(*names)

    names.forEach {
        project(":$it").name = "bedrock-$it"
    }
}
