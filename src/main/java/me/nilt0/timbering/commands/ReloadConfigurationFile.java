package me.nilt0.timbering.commands;

import me.nilt0.timbering.plugin.Timbering;
import org.bukkit.command.CommandSender;

public class ReloadConfigurationFile {

    public boolean execute(CommandSender sender, String[] args, Timbering main){
        boolean senderHasPermission = sender.hasPermission("Timbering.ReloadConfigurationFile");
        boolean isSenderOP = sender.isOp();

        if (!senderHasPermission && !isSenderOP){
            sender.sendMessage("[Timbering] - You don't have permission to use this command.");
            return false;
        }

        if (senderHasPermission && isSenderOP){
            main.configuration.reloadConfigurationFile();
            sender.sendMessage("[Timbering] - Configuration file has been reloaded successfully. --Timbering");
            return true;
        }

        if (args.length != 0)
            sender.sendMessage("[Timbering] - Command usage is /trcf");

        return false;
    }
}
