package de.crafty.vanillatweaks.event.listener;

import de.crafty.vanillatweaks.VanillaTweaks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class PlayerNameTweak implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();


        HashMap<String, String> names = VanillaTweaks.get().customPlayerNames();
        if(names.containsKey(player.getName()))
            player.setDisplayName(names.get(player.getName()).replaceAll("%PLAYER%", player.getName()));

        HashMap<String, String> listNames = VanillaTweaks.get().customPlayerListNames();
        if(listNames.containsKey(player.getName()))
            player.setPlayerListName(listNames.get(player.getName()).replaceAll("%PLAYER%", player.getName()));


    }

}
