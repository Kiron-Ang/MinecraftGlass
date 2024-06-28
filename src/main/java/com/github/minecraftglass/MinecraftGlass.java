// Ensure that this class belongs in a NAMED package!
package com.github.minecraftglass;

// Reference classes that we need for this plugin
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import java.util.Set;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.Command;
import org.bukkit.Material;
import java.util.List;
import java.util.ArrayList;

// All plugins need to extend JavaPlugin
// Implement CommandExecutor for the /glass command
public class MinecraftGlass extends JavaPlugin
implements CommandExecutor {
  // Run this code when the server enables this plugin 
  @Override
  public void onEnable() {
    // Save the default config.yml file that comes with this plugin
    // but do NOT replace it if it already exists (false)
    saveResource("config.yml", false);
    // Register an event
    Bukkit.getPluginManager().registerEvents(new AirReplaceGlass(this), this);
    // Add an in-game command called "glass" that will use this
    // class to figure out what to do
    this.getCommand("glass").setExecutor(this);
  }

  // Run this code when "/glass" is executed
  @Override
  public boolean onCommand(CommandSender s, Command c, String l,
                           String[] a) {
    // Test to make sure that two arguments were given
    if (a.length != 2) {
      s.sendMessage("Please try again: /glass <PLAYER> <ITEM>");
      return true;
    }
    // Initialize an empty list since ItemMeta lores are stored in a list
    List<String> loreList = new ArrayList<String>();
    // Only run the rest of this code if the sender is a player
    if (s instanceof Player) {
      // Get the arguments passed to the command
      String playerName = a[0];
      String glassName = a[1];
      // Use playerName to get the Player object
      Player player = Bukkit.getServer().getPlayer(playerName);
      // Initialize a set based off of the "keys" in the config.yml file
      // This basically gets all of the words indented one level below the
      // "glass:" section in the config.yml file
      Set<String> glass = getConfig().getConfigurationSection("glass"
                           ).getKeys(false);
      // For every single glass mentioned in the config, run this code
      for (String glas : glass) {
        // Ensure the glassName matches an item in the config
        if (!glas.equals(glassName)) {continue;}
        // Initialize a new ItemStack with just the material specified
        // in the config. This will be further modified
        // TODO: There may be a more efficient way to organize all this
        ItemStack glassStack = new ItemStack(Material.matchMaterial(
                               getConfig().getString("glass." + glas + 
                               ".material")));
        // Get the ItemMeta associated with the above ItemStack
        // This only has the default values but those can be modified
        ItemMeta glassMeta = glassStack.getItemMeta();
        // Set the custom name specified in config.yml in field "name:"
        glassMeta.setDisplayName(getConfig().getString("glass." + glas +
          ".name"));
        // Ensure that the list initialized earlier is empty
        // I use the same list every single time, so I need to clear it
        loreList.clear();
        // The lore should just be ONE line. Add the lore in field "lore:"
        // TODO: Consider adding an easy way to add multiple lines of lore
        loreList.add(getConfig().getString("glass." + glas + ".lore"));
        // Set lore based off of the list initialized before.
        glassMeta.setLore(loreList);
        // Add a hidden enchantment if "glow" is "true"
        if (getConfig().getString("glass." + glas + ".glow").equals("true")) {
          // Add enchantment glint!
          glassMeta.setEnchantmentGlintOverride(true);
        }
        // Now take the entire ItemMeta instance and add it back
        glassStack.setItemMeta(glassMeta);
        // Give the player than ran "/glass" the item!
        player.getInventory().addItem(glassStack);

      }
    }
    // I need to return "true" or else the server will return an error
    return true;
  }
}