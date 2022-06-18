package de.crafty.vanillatweaks.command;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CMD_playtime implements TabExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if(!(sender instanceof Player player))
            return false;

        if(!cmd.getName().equalsIgnoreCase("playtime"))
            return false;

        if(args.length == 0)
            player.sendMessage("\u00a77Playtime: \u00a7a" + this.getPlaytimeString(player.getStatistic(Statistic.TOTAL_WORLD_TIME)));

        if(args.length == 1){
            for(OfflinePlayer p : Bukkit.getOfflinePlayers()){
                if(p == null || p.getName() == null)
                    continue;
                
                if(p.getName().equalsIgnoreCase(args[0]))
                    player.sendMessage("\u00a76" + p.getName() + "\u00a77's Playtime: \u00a7a" + this.getPlaytimeString(p.getStatistic(Statistic.TOTAL_WORLD_TIME)));
            }
            
        }
        
        return true;
    }


    private String getPlaytimeString(long time){

        long minutes = time / 20 / 60;
        long hours = minutes / 60;

        if(minutes < 60)
            return minutes + " Minutes";

        long minutesLeft = minutes - hours * 60;

        float percentage = minutesLeft / 60.0F;
        return hours + "." + Math.round(percentage * 100) + " \u00a77Hours";

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> list = new ArrayList<>();

        if(!(sender instanceof Player player))
            return list;

        if(!cmd.getName().equalsIgnoreCase("playtime"))
            return list;

        if(args.length == 1){
            List<String> playerNames = new ArrayList<>();
            for(OfflinePlayer p : Bukkit.getOfflinePlayers())
                playerNames.add(p.getName());

            list.addAll(playerNames.stream().filter(name -> name.toUpperCase().startsWith(args[0].toUpperCase())).toList());
        }


        return list;
    }
}
