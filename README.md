# Obsidian

A modular framework for designing advanced experiences for Minecraft servers.

<p align="center">
  <img width="300" height="300" src="https://static.wikia.nocookie.net/minecraft_gamepedia/images/9/99/Obsidian_JE3_BE2.png/revision/latest?cb=20200124042057">
</p>

---

Obsidian is designed with the following goals in mind:

- to improve and expand upon the core Minecraft server's codebase
- to provide well-defined, thread-safe, and extensible APIs that wrap around the game's complex behaviour
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

## APIs

### `obsidian.entity`

An API wrapping around client-sided entity rendering, providing a simple foundation for server software utilizing
entities.

```java
GameClient client; // the target client
Position position; // where we want the entity

// configure the renderer
PlayerOptions options = PlayerOptions
        .builder(position)
        .name("Cool NPC!")
        .build();

// construct the renderer and show to client
PlayerRenderer.of(options)
        .show(client);
```

### `obsidian.npc`

An API providing state management and interaction handling for non-player characters, using `obsidian.entity` renderers.

```java
// create our 'default state'
NPCState state = NPCState.builder(position,world)
        .renderer(someRenderer)
        // look at nearby players within 5 blocks
        .withBehaviour(LookAtBehaviour.of(5))
        .build();

// create the NPC using our state, show to client
SimpleNPC.builder("cool-npc")
        .renderer()
        .defaultState(state)
        .build()
        .addClient(client);
```

TODO: interaction handling

## Modules

### `obsidian-core`

> Obsidian's core API module defines interfaces that represent various actors of
> a multiplayer game, such as the client, the server, and any other
> related concepts that are used throughout the other modules.

Read [Getting Started with Obsidian](#) to learn more.

### `obsidian-paper`

> Platform implementation of Obsidian's APIs on [PaperMC](#).

Read [Using Obsidian with Paper](#) to learn more.