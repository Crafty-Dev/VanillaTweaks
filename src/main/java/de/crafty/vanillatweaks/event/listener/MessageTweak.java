package de.crafty.vanillatweaks.event.listener;

import de.crafty.vanillatweaks.VanillaTweaks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MessageTweak implements Listener {


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        event.setFormat(VanillaTweaks.get().message().replaceAll("%PLAYER%", player.getDisplayName()).replaceAll("%MESSAGE%", event.getMessage()));

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        event.setJoinMessage(VanillaTweaks.get().joinMessage().replaceAll("%PLAYER%", player.getDisplayName()));

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){

        Player player = event.getPlayer();
        event.setQuitMessage(VanillaTweaks.get().quitMessage().replaceAll("%PLAYER%", player.getDisplayName()));

    }
}
