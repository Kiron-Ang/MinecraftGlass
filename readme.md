# MinecraftGlass

Use Java to create a plugin for Paper Minecraft servers.

Current Plugin Version: Version 1

---

MinecraftGlass Version 1

- /glass reload : Reloads the config.yml file
- /glass [PLAYER_NAME] [ITEM]: Gives a player an item from the config. Currently, the only item is the Glass Hammer.
- The Glass Hammer breaks any block with "glass" in its name instantly when you right-click or left-click on the block. The default is a stick named "Glass Hammer" with a little bit of lore, but you can change those settings in the config.yml file. Setting "drop: true" returns the broken block to the player's inventory, while setting "glow: true" adds an enchantment glint to the item.
