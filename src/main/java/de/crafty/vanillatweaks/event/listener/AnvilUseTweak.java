package de.crafty.vanillatweaks.event.listener;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.crafty.vanillatweaks.VanillaTweaks;
import net.minecraft.world.item.Item;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AnvilUseTweak implements Listener {


    @EventHandler
    public void onAnvilUse$0(PrepareAnvilEvent event) {

        if(!VanillaTweaks.get().anvilUses())
            return;

        this.updateRepairCost(event.getInventory().getItem(0));
        this.updateRepairCost(event.getInventory().getItem(1));
        this.updateRepairCost(event.getResult());


        for (ItemStack stack : event.getView().getBottomInventory()) {
            this.applyAnvilUses(stack);
        }

    }

    @EventHandler
    public void onAnvilUse$2(InventoryClickEvent event) {

        if(!VanillaTweaks.get().anvilUses())
            return;

        if(event.getView().getTopInventory().getType() != InventoryType.ANVIL)
            return;

        for (ItemStack stack : event.getWhoClicked().getInventory()) {
            this.applyAnvilUses(stack);
        }

    }

    @EventHandler
    public void onAnvilUse$3(InventoryOpenEvent event) {

        if(!VanillaTweaks.get().anvilUses())
            return;

        if (event.getView().getTopInventory().getType() != InventoryType.ANVIL)
            return;


        for (ItemStack stack : event.getInventory()) {
            this.applyAnvilUses(stack);
        }

        for (ItemStack stack : event.getPlayer().getInventory()) {
            this.applyAnvilUses(stack);
        }
    }

    @EventHandler
    public void onAnvilUse$1(InventoryCloseEvent event) {

        if (event.getView().getTopInventory().getType() != InventoryType.ANVIL)
            return;


        for (ItemStack stack : event.getInventory()) {
            this.removeAnvilUses(stack);
        }

        for (ItemStack stack : event.getPlayer().getInventory()) {
            this.removeAnvilUses(stack);
        }
    }


    private void applyAnvilUses(ItemStack stack) {
        if (stack == null || !stack.hasItemMeta())
            return;


        if (this.containsRepairCostLore(stack))
            return;

        ItemMeta meta = stack.getItemMeta();

        JsonObject json = JsonParser.parseString(meta.getAsString()).getAsJsonObject();
        if (!json.has("RepairCost"))
            return;

        int repairCost = json.get("RepairCost").getAsInt();
        int anvilUses = (int) (Math.log(repairCost + 1) / Math.log(2));

        List<String> lore = meta.hasLore() ? stack.getItemMeta().getLore() : new ArrayList<>();
        lore.add("\u00a78\u00a7oAnvil Uses: \u00a76" + anvilUses);
        meta.setLore(lore);

        stack.setItemMeta(meta);
    }

    private void removeAnvilUses(ItemStack stack) {
        if (stack == null || !stack.hasItemMeta())
            return;

        ItemMeta meta = stack.getItemMeta();

        JsonObject json = JsonParser.parseString(meta.getAsString()).getAsJsonObject();
        if (!json.has("RepairCost"))
            return;

        List<String> lore = meta.hasLore() ? stack.getItemMeta().getLore() : new ArrayList<>();
        int toRemove = -1;
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).startsWith("\u00a78\u00a7oAnvil Uses:"))
                toRemove = i;
        }
        if (toRemove >= 0)
            lore.remove(toRemove);

        meta.setLore(lore);

        stack.setItemMeta(meta);
    }


    private boolean containsRepairCostLore(ItemStack stack) {
        if (stack == null || !stack.hasItemMeta())
            return false;

        if (stack.getItemMeta().getLore() == null)
            return false;

        for (String s : stack.getItemMeta().getLore()) {
            if (s.startsWith("\u00a78\u00a7oAnvil Uses:"))
                return true;
        }

        return false;
    }

    private void updateRepairCost(ItemStack stack){
        this.removeAnvilUses(stack);
        this.applyAnvilUses(stack);
    }
}
