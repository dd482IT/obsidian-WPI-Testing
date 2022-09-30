# Obsidian

A modular framework for designing advanced experiences for Minecraft servers.

![Obsidian](https://static.wikia.nocookie.net/minecraft_gamepedia/images/9/99/Obsidian_JE3_BE2.png/revision/latest?cb=20200124042057)

---

Obsidian is designed with the following goals in mind:

- to improve and expand upon the core Minecraft server's codebase
- to provide well-defined, extensible APIs that wrap around the game's complex behaviour
- to utilize modern Java's best practices, maintaining a balance between
  concise code and powerful capabilities
- to support a wide-variety of use-cases, from simple minigames to complex server networks

GIven all this, the main point of Obsidian's existence is to allow developers to
cleanly express complex interactions between the Minecraft game server and client.

For example, without employing the use of a pre-existing library, creating performant custom entities can be a multi-day
long hassle.
Obsidian aims to be that library, scaling with the developer's needs as
  
Each Obsidian module is intended to be drop-in compatible with any existing Minecraft server. Depending on the use-case,
developers may choose to base a gamemode entirely off of Obsidian, or utilize only a selection
of Obsidian's modules and integrate with pre-existing software.

Obsidian aims to interact as little (or as much) with the world as necessary, considering all unexpected behaviour as
a bug worthy of fixing.

## Modules

### **Obsidian**

> The core of Obsidian: a set of utility classes and interfaces that represent the range
> of various 'actors' in a game world.

Read [Getting Started with Obsidian](#) to learn more.

### **Users**

> APIs behind users that can send and receive a variety of media, along
> with an extensible user data system.

### **Instancing**

> Instancing of game world objects.

### **Entities**

> A framework for designing custom in-game entities, using a variety
> of rendering methods and interaction handling capabilities.

### **Items**

> A custom item (and item type) API.