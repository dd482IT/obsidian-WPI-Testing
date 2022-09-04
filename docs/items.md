# Introduction

bedrock's item system has been designed with a few goals in mind:

- to create a declarative, builder-style API to create custom item types
- to improve the data saving experience of item data saving/loading
- to give all items a uniquely addressable ID
- to abstract away the annoying bits of Minecraft's item and inventory API

## Item Types

An **item type** is the data structure that is used to create item instances.

An item type contains:

- the item's name
- the item's description
- item event handlers
- any extra components that may comprise the item

When creating an item instance, data from the item type is used to construct the user-facing Bukkit ItemStack.

## Item Instances

When the item system is enabled, every item on the server is given

## Events

`ItemInteractEvent`

- Fired when 