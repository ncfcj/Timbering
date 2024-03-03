package me.nilt0.timbering.configuration;

import me.nilt0.timbering.plugin.ConfigurationFileValues;
import me.nilt0.timbering.plugin.Timbering;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class TimberingConfiguration {
    private final Timbering main;
    private YamlConfiguration yamlConfiguration;
    private File configurationFile;

    public TimberingConfiguration(Timbering main){
        this.main = main;

        try{
            this.loadConfigurationFile();
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    public void reloadConfigurationFile(){
        try{
            this.yamlConfiguration.load(this.configurationFile);
        }catch(IOException | InvalidConfigurationException exception){
            exception.printStackTrace();
        }

        ConfigurationFileValues.TreeCuttingDefaultRadius = this.yamlConfiguration.getDouble("Timbering.TreeCuttingDefaultRadius");
    }


    private void loadConfigurationFile() throws IOException {
        File mainDataFolderPath = this.main.getDataFolder();
        this.configurationFile = new File(mainDataFolderPath, "//pluginConfiguration.yml");

        if (!mainDataFolderPath.exists())
            this.main.getDataFolder().mkdir();

        if (!this.configurationFile.exists())
            this.configurationFile.createNewFile();

        this.yamlConfiguration = YamlConfiguration.loadConfiguration(this.configurationFile);
        this.yamlConfiguration.addDefault("Config.TreeCuttingDefaultRadius", 3);
        this.yamlConfiguration.options().copyDefaults();
        this.yamlConfiguration.save(this.configurationFile);

        // Populate Configuration Class
        ConfigurationFileValues.TreeCuttingDefaultRadius = this.yamlConfiguration.getDouble("Config.TreeCuttingDefaultRadius");
    }
}
