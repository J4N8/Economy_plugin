package com.j4n8.economy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ShopGUI implements Listener {
    private final Inventory shopGUI;
    public ShopGUI() {
        shopGUI = Bukkit.createInventory(null, 9, "Shop");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        shopGUI.addItem(createGuiItem(Material.DIAMOND_SWORD, 1, "Example Sword", "§aFirst line of the lore", "§bSecond line of the lore"));
        shopGUI.addItem(createGuiItem(Material.IRON_HELMET, 1, "§bExample Helmet", "§aFirst line of the lore", "§bSecond line of the lore"));
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final Material material, final int amount, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, amount);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);
        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    // You can open the inventory with this
    public void openInventory(final HumanEntity ent) {
        ent.openInventory(shopGUI);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!e.getInventory().getClass().equals(shopGUI.getClass())){
            return;
        }

        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();

        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        final Player p = (Player) e.getWhoClicked();

        // Using slots click is the best option for your inventory click's
        p.sendMessage("You clicked at slot " + e.getRawSlot());
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().getClass().equals(shopGUI.getClass())) {
            e.setCancelled(true);
        }
    }
}
