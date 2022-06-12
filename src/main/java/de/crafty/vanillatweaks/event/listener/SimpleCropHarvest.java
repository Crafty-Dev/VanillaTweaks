package de.crafty.vanillatweaks.event.listener;

import de.crafty.vanillatweaks.VanillaTweaks;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.material.NetherWarts;

import java.util.Collection;
import java.util.List;

public class SimpleCropHarvest implements Listener {


    @EventHandler
    public void onCropHarvest(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        World world = player.getWorld();

        if (!VanillaTweaks.get().simpleCropHarvest())
            return;

        if (block == null)
            return;

        if (event.getHand() != EquipmentSlot.HAND)
            return;

        Crops crops = null;
        NetherWarts warts = null;

        if (block.getState().getData() instanceof NetherWarts w)
            warts = w;
        if (block.getState().getData() instanceof Crops c)
            crops = c;


        if(warts != null && warts.getState() != NetherWartsState.RIPE)
            return;

        if(crops != null && crops.getState() != CropState.RIPE)
            return;


        if(crops == null && warts == null)
            return;

        Collection<ItemStack> drops = block.getDrops(player.getItemInUse());
        ItemStack toRemove = null;

        for (ItemStack itemStack : drops) {
            if (itemStack.getType() == this.getSeedOfCrop(block.getType()))
                itemStack.setAmount(itemStack.getAmount() - 1);


        }

        drops.stream().filter(itemStack -> itemStack.getAmount() != 0).forEach(itemStack -> world.dropItemNaturally(block.getLocation(), itemStack));

        if(crops != null){
            crops.setState(CropState.SEEDED);
            world.setBlockData(block.getLocation(), crops.getItemType().createBlockData());
        }

        if(warts != null){
            warts.setState(NetherWartsState.SEEDED);
            world.setBlockData(block.getLocation(), warts.getItemType().createBlockData());
        }

        world.playSound(block.getLocation(), Sound.BLOCK_CROP_BREAK, 1.0F, 1.0F);
    }


    private Material getSeedOfCrop(Material mat) {

        switch (mat) {
            case WHEAT -> {
                return Material.WHEAT_SEEDS;
            }
            case POTATOES -> {
                return Material.POTATO;
            }
            case BEETROOTS -> {
                return Material.BEETROOT_SEEDS;
            }
            case CARROTS -> {
                return Material.CARROT;
            }
            default -> {
                return mat;
            }
        }
    }
}
