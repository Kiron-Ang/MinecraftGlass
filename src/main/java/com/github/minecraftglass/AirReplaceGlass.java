package com.github.minecraftglass;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.github.minecraftglass.MinecraftGlass;

public class AirReplaceGlass implements Listener {

  MinecraftGlass plugin = null;

  public AirReplaceGlass(MinecraftGlass minecraftGlass) {
    plugin = minecraftGlass;
  } 

  @EventHandler
  public void onPlayerInteractEvent(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    player.sendMessage("1");
    String material;
    String name;
    String lore;
    String glass_material;
    String glass_name;
    String glass_lore;
    try {
    player.sendMessage("2");
    ItemStack itemStack = event.getItem();
    ItemMeta itemMeta = itemStack.getItemMeta();
    material = itemStack.getType().toString().strip();
    name = itemMeta.getDisplayName().toString().strip();
    lore = itemMeta.getLore().toString().strip();
    player.sendMessage(material);
    player.sendMessage(name);
    player.sendMessage(lore);
    } catch (Exception exception) {
      return;
    }
    try {
    player.sendMessage("3");
    glass_material = plugin.getConfig().getString("glass.hammer.material"
                         ).strip();
    player.sendMessage(glass_material);
    glass_name = plugin.getConfig().getString("glass.hammer.name"
                     ).strip();
    player.sendMessage(glass_name);
    glass_lore = "[" + plugin.getConfig().getString("glass.hammer.lore"
                     ).strip() + "]";
    player.sendMessage(glass_lore);
    } catch (Exception exception) {
      return;
    }
    if (material.equals(glass_material) && name.equals(glass_name) && 
        lore.equals(glass_lore)) {
      player.sendMessage("4");
      if (event.getClickedBlock().getType().toString().contains("GLASS")) {
      player.sendMessage("5");
        Block block = event.getClickedBlock();
        Location location = block.getLocation();
        player.getWorld().getBlockAt(location.getBlockX(), location.getBlockY(),
          location.getBlockZ()).setType(Material.AIR);
      }
    }
  }
}