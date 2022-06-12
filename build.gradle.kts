plugins {
    id("java")
}

group = "de.crafty"
version = "1.3"

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    /*
     As Spigot-API depends on the Bungeecord ChatComponent-API,
    we need to add the Sonatype OSS repository, as Gradle,
    in comparison to maven, doesn't want to understand the ~/.m2
    directory unless added using mavenLocal(). Maven usually just gets
    it from there, as most people have run the BuildTools at least once.
    This is therefore not needed if you're using the full Spigot/CraftBukkit,
    or if you're using the Bukkit API.
    */
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    mavenLocal() // This is needed for CraftBukkit and Spigot.
    mavenCentral()
}

dependencies {
    // Pick only one of these and read the comment in the repositories block.
    //compileOnly("org.spigotmc:spigot-api:1.18.2-R0.1-SNAPSHOT") // The Spigot API with no shadowing. Requires the OSS repo.
    compileOnly("org.spigotmc:spigot:1.18.2-R0.1-SNAPSHOT") // The full Spigot server with no shadowing. Requires mavenLocal.
}

tasks {
    jar {
        destinationDirectory.set(file("C:\\Users\\Lius\\Desktop\\1.18.2 Server\\plugins"))
    }

    processResources {

        filesMatching("plugin.yml"){
            expand("version" to project.version)
        }
    }
}