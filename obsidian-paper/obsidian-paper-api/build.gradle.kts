plugins {
    id("io.papermc.paperweight.userdev") apply (true)
    id("java-library") apply (true)
}

dependencies {
    paperDevBundle("1.19.2-R0.1-SNAPSHOT")
    api(projects.obsidianCoreApi)
    api("com.comphenix.protocol:ProtocolLib:4.8.0")
}
