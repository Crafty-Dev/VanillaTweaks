package de.crafty.vanillatweaks.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_ping implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player player))
            return false;

        if(cmd.getName().equalsIgnoreCase("ping"))
            player.sendMessage("\u00a76Ping\u00a77: \u00a7a" + player.getPing() + "\u00a77ms");

        return true;
    }
}
