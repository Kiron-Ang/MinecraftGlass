package com.github.minecraftglass;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.Material;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.bukkit.entity.HumanEntity;

public class MinecraftGlass extends JavaPlugin {
  public void onEnable() {
    saveResource("config.yml", false);
    Bukkit.getPluginManager().registerEvents(new AirReplaceGlass(this), this);
    this.getCommand("glass").setExecutor(this);
  }

  public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
    if (a.length == 0) {
      s.sendMessage("Please try again: /glass <PLAYER> <ITEM> OR /glass reload");
      return true;
    }
    if (a[0].equals("reload")) {
      reloadConfig();
      s.sendMessage("Reloaded MinecraftGlass config.yml");
      return true;
    }
    if (a.length != 2) {
      s.sendMessage("Please try again: /glass <PLAYER> <ITEM> OR /glass reload");
      return true;
    }
    String playerName = a[0];
    String glassName = a[1];
    Player player = Bukkit.getServer().getPlayer(playerName);
    Set<String> glass = getConfig().getConfigurationSection("glass").getKeys(false);
    for (String glas : glass) {
      if (!glas.equals(glassName)) {
        continue;
      }
      ItemStack glassStack = new ItemStack(Material.matchMaterial(getConfig().getString("glass." + glas + ".material")));
      ItemMeta glassMeta = glassStack.getItemMeta();
      glassMeta.setDisplayName(getConfig().getString("glass." + glas + ".name"));
      List<String> loreList = new ArrayList<String>();
      loreList.add(getConfig().getString("glass." + glas + ".lore"));
      glassMeta.setLore(loreList);
      if (getConfig().getString("glass." + glas + ".glow").equals("true")) {
        glassMeta.setEnchantmentGlintOverride(true);
      }
      glassStack.setItemMeta(glassMeta);
      player.getInventory().addItem(glassStack);
      s.sendMessage("Added the item to the player's inventory!");
    }
    return true;
  }

  public List<String> onTabComplete(CommandSender s, Command c, String l, String[] a) {
    if (a.length == 2) {
      Set<String> glass = getConfig().getConfigurationSection("glass").getKeys(false);
      return new ArrayList<>(glass);
    }
    return new ArrayList<>(Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()));
  }
}