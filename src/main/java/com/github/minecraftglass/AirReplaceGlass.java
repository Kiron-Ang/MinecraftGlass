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

public class AirReplaceGlass implements Listener {
  @EventHandler
  public void onPlayerInteractEvent(PlayerInteractEvent event) {
    try {
    ItemStack itemStack = event.getItem();
    material = itemStack.getType().toString().strip();
    name = itemMeta.getDisplayName().toString().strip();
    lore = itemMeta.getLore().toString().strip();
    } catch (Exception exception) {
      return;
    }
    try {
      glass_material = getConfig().getString("glass.hammer.material"
                         ).strip();
      glass_name = getConfig().getString("glass.hammer.name"
                     ).strip();
      glass_lore = "[" + getConfig().getString("glass.hammer.lore"
                     ).strip() + "]";
    } catch (Exception exception) {
      return;
    }
    if (material.equals(glass_material) && name.equals(glass_name) && 
        lore.equals(glass_lore)) {
      if (event.getClickedBlock().getType().toString().contains("GLASS")) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        Location location = block.getLocation();
        player.getWorld().getBlockAt(location.getBlockX(), location.getBlockY(),
          location.getBlockZ()).setType(Material.AIR);
      }
    }
  }
}