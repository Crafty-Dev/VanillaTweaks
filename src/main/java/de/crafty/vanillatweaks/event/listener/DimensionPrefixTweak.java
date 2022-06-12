package de.crafty.vanillatweaks.event.listener;

import de.crafty.vanillatweaks.VanillaTweaks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class DimensionPrefixTweak implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (!VanillaTweaks.get().dimensionPrefix())
            return;


        String color;
        switch (player.getWorld().getEnvironment()) {
            case NORMAL -> color = "\u00a7a";
            case NETHER -> color = "\u00a74";
            case THE_END -> color = "\u00a79";
            default -> color = "\u00a70";
        }

        player.setPlayerListName(color + "+" + "\u00a7f" + player.getDisplayName());

    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onDimensionChange(PlayerChangedWorldEvent event) {

        Player player = event.getPlayer();


        if (!VanillaTweaks.get().dimensionPrefix())
            return;

        String color;
        switch (player.getWorld().getEnvironment()) {
            case NORMAL -> color = "\u00a7a";
            case NETHER -> color = "\u00a74";
            case THE_END -> color = "\u00a79";
            default -> color = "\u00a70";
        }

        player.setPlayerListName(color + "+" + "\u00a7f" + player.getDisplayName());

    }

}
