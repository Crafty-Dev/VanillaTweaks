package de.crafty.vanillatweaks.command;

import de.crafty.vanillatweaks.VanillaTweaks;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class CMD_invsee implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player))
            return false;

        if (!cmd.getName().equalsIgnoreCase("invsee"))
            return false;

        if (args.length != 1)
            return false;

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (args[0].equalsIgnoreCase(player.getName())) {
                player.sendMessage(VanillaTweaks.PREFIX + "Why do you wanna look in your own inv? Dumb?");
                return true;
            }

            if (!p.getName().equalsIgnoreCase(args[0]))
                continue;

            Inventory inv = Bukkit.createInventory(null, InventoryType.PLAYER, "\u00a78" + p.getName() + "'s Inventory");
            inv.setContents(p.getInventory().getContents());
            VanillaTweaks.OPEN_INVS.add(inv);
            player.openInventory(inv);
            return true;
        }
        player.sendMessage(VanillaTweaks.PREFIX + "\u00a73Could not find player");
        return true;
    }
}
