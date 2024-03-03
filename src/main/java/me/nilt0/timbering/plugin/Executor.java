package me.nilt0.timbering.plugin;

import me.nilt0.timbering.commands.ReloadConfigurationFile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Executor implements CommandExecutor {
    private final Timbering main;

    public Executor(Timbering main){
        this.main = main;
        main.getCommand("trcf").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("trcf"))
            return new ReloadConfigurationFile().execute(commandSender, args, this.main);

        return false;
    }
}
