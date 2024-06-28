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
    String material;
    String name;
    String lore;
    String glass_material;
    String glass_name;
    String glass_lore;
    try {
    ItemStack itemStack = event.getItem();
    ItemMeta itemMeta = itemStack.getItemMeta();
    material = itemStack.getType().toString().strip();
    name = itemMeta.getDisplayName().toString().strip();
    lore = itemMeta.getLore().toString().strip();
    } catch (Exception exception) {
      return;
    }
    try {
    player.sendMessage("3");
    glass_material = plugin.getConfig().getString("glass.hammer.material"
                         ).strip();
    glass_name = plugin.getConfig().getString("glass.hammer.name"
                     ).strip();
    glass_lore = "[" + plugin.getConfig().getString("glass.hammer.lore"
                     ).strip() + "]";
    } catch (Exception exception) {
      return;
    }
    if (material.equals(glass_material) && name.equals(glass_name) && 
        lore.equals(glass_lore)) {
      if (event.getClickedBlock().getType().toString().contains("GLASS")) {
        Block block = event.getClickedBlock();
        Location location = block.getLocation();
        player.getWorld().getBlockAt(location.getBlockX(), location.getBlockY(),
          location.getBlockZ()).setType(Material.AIR);
      }
    }
  }
}