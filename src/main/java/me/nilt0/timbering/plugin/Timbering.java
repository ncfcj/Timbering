package me.nilt0.timbering;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Timbering extends JavaPlugin {
    private final java.util.logging.Logger Logger = Bukkit.getLogger();
    private final String PluginName = this.getName();




    @Override
    public void onDisable() {
        Logger.info(ChatColor.RED + "Disabled " + PluginName);
    }

    @Override
    public void onEnable() {
        Logger.info(ChatColor.GREEN + "Enabled" + PluginName);
    }
}
