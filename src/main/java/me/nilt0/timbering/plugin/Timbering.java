package me.nilt0.timbering.plugin;

import me.nilt0.timbering.configuration.TimberingConfiguration;
import me.nilt0.timbering.listener.BlockBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Timbering extends JavaPlugin {
    private final String PluginName = this.getName();
    public TimberingConfiguration configuration;


    @Override
    public void onDisable() {
        Bukkit.getLogger().info(PluginName + " is Disabled");
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("["+ PluginName + "]" + " is Enabled");
        new BlockBreakListener(this);
        new Executor(this);
        this.configuration = new TimberingConfiguration(this);
    }
}
