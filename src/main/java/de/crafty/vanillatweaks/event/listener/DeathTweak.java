package de.crafty.vanillatweaks.event.listener;

import de.crafty.vanillatweaks.VanillaTweaks;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class DeathTweak implements Listener {


    @EventHandler
    public void onDeath$0(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Location loc = player.getLocation();

        if (!VanillaTweaks.get().deathCoords())
            return;


        String dimension;
        switch (loc.getWorld().getEnvironment()) {
            case NORMAL -> dimension = "\u00a7aOverworld";
            case NETHER -> dimension = "\u00a74Nether";
            case THE_END -> dimension = "\u00a79The End";
            default -> dimension = "\u00a77Somewhere";
        }

        StringBuilder deathCoordBuilder = new StringBuilder();
        deathCoordBuilder.append("\u00a70\u00a7kAA\u00a76R\u00a77.\u00a76I\u00a77.\u00a76P\u00a70\u00a7kAA\n");
        deathCoordBuilder.append("\u00a77You died at:\n");
        deathCoordBuilder.append("\u00a77X: \u00a7a").append(loc.getBlockX()).append("\n");
        deathCoordBuilder.append("\u00a77Y: \u00a7a").append(loc.getBlockY()).append("\n");
        deathCoordBuilder.append("\u00a77Z: \u00a7a").append(loc.getBlockZ()).append("\n");
        deathCoordBuilder.append("\u00a77Dimension: \u00a7a").append(dimension).append("\n");

        Bukkit.getScheduler().scheduleSyncDelayedTask(VanillaTweaks.get(), () -> player.sendMessage(deathCoordBuilder.toString()));

    }


    @EventHandler
    public void onDeath$1(PlayerDeathEvent event) {

        Player player = event.getEntity();

        event.setDeathMessage(VanillaTweaks.get().deathMsgColor() + event.getDeathMessage().replaceAll(player.getName(), VanillaTweaks.get().deathMsgPlayerName().replaceAll("%PLAYER%", player.getName() + VanillaTweaks.get().deathMsgColor())));

    }

    @EventHandler
    public void onDeath$2(PlayerDeathEvent event) {

        Player player = event.getEntity();
        World world = player.getWorld();
        Location loc = player.getLocation();

        if (!VanillaTweaks.get().deathChests())
            return;

        if(loc.getWorld() == null)
            return;

        if(loc.getWorld().getEnvironment() == World.Environment.THE_END && loc.getBlockY() < 0)
            loc.setY(0);

        ItemStack[] inv = player.getInventory().getContents();
        List<ItemStack> pureInv = new ArrayList<>();

        //Item merging
        for (int i = 0; i < inv.length; i++) {
            if (inv[i] != null)
                pureInv.add(inv[i]);
        }

        List<ItemStack> mergedInv = new ArrayList<>();
        for (ItemStack stack : pureInv) {
            for (ItemStack mergable : mergedInv) {
                if (mergable.getType() == stack.getType() && mergable.getAmount() != mergable.getMaxStackSize()) {
                    int free = mergable.getMaxStackSize() - mergable.getAmount();
                    mergable.setAmount(mergable.getAmount() + stack.getAmount());
                    if (mergable.getAmount() > mergable.getMaxStackSize())
                        mergable.setAmount(mergable.getMaxStackSize());

                    stack.setAmount(stack.getAmount() - free);

                }
            }
            if (stack.getAmount() > 0)
                mergedInv.add(stack);
        }

        if (mergedInv.size() == 0)
            return;

        event.getDrops().clear();


        Bukkit.getScheduler().scheduleSyncDelayedTask(VanillaTweaks.get(), () -> {
            world.setBlockData(loc, Material.CHEST.createBlockData());
            Chest chest = (Chest) world.getBlockAt(loc).getState();

            chest.setCustomName("\u00a78" + player.getName() + "'s DeathChest");
            chest.getPersistentDataContainer().set(new NamespacedKey(VanillaTweaks.get(), "DeathChest"), PersistentDataType.STRING, player.getUniqueId().toString());
            chest.update();

            for (int i = 0; i < mergedInv.size() && i < 27; i++) {
                chest.getBlockInventory().setItem(i, mergedInv.get(i));
            }

            if (mergedInv.size() > 27) {
                Location loc1 = loc.add(0, 1, 0);

                if (!world.getBlockAt(loc1).isLiquid() && !world.getBlockAt(loc1).isEmpty()) {
                    for (int i = 27; i < mergedInv.size(); i++) {
                        world.dropItemNaturally(loc, mergedInv.get(i));
                    }
                    return;
                }

                world.setBlockData(loc1, Material.CHEST.createBlockData());

                Chest chest1 = (Chest) world.getBlockAt(loc1).getState();
                chest1.setCustomName("\u00a78" + player.getName() + "'s DeathChest");
                chest1.getPersistentDataContainer().set(new NamespacedKey(VanillaTweaks.get(), "DeathChest"), PersistentDataType.STRING, player.getUniqueId().toString());
                chest1.update();

                for (int i = 27; i < mergedInv.size(); i++) {
                    chest1.getBlockInventory().setItem(i - 27, mergedInv.get(i));
                }
            }


        });
    }


    @EventHandler
    public void onDeathChestBreak(BlockBreakEvent event) {

        Player player = event.getPlayer();
        World world = player.getWorld();
        Block block = event.getBlock();

        if (!(event.getBlock().getState() instanceof Chest chest))
            return;


        if (chest.getPersistentDataContainer().get(new NamespacedKey(VanillaTweaks.get(), "DeathChest"), PersistentDataType.STRING) == null)
            return;

        event.setDropItems(false);


    }

    @EventHandler
    public void onDeathChestLoot(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        World world = player.getWorld();
        Location loc = event.getInventory().getLocation();

        if (loc == null)
            return;

        if (!(world.getBlockAt(loc).getState() instanceof Chest chest))
            return;

        if (chest.getPersistentDataContainer().get(new NamespacedKey(VanillaTweaks.get(), "DeathChest"), PersistentDataType.STRING) == null)
            return;


        Bukkit.getScheduler().scheduleSyncDelayedTask(VanillaTweaks.get(), () -> {
            if (event.getInventory().isEmpty()) {
                world.setBlockData(loc, Material.AIR.createBlockData());
                world.playSound(loc, Sound.BLOCK_CHEST_CLOSE, 1.0F, 1.0F);
                player.closeInventory();
            }
        });

    }
}
