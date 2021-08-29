package com.j4n8.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
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
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ShopGUI implements Listener {
    private final Plugin plugin;
    private final Inventory shopGUI;
    public ShopGUI(Plugin plugin) {
        this.plugin = plugin;
        shopGUI = Bukkit.createInventory(null, 54, "Shop");

        // Put the items into the inventory
        initializeItems();
    }

    // You can call this whenever you want to put the items in
    public void initializeItems() {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("shop");
        Set<String> categories = section.getKeys(false);
        for (String item : categories){
            shopGUI.addItem(createGuiItem(Material.getMaterial(item), config.getInt("shop."+item+".amount"), "Price: "+config.getInt("shop."+item+".price")));
        }
    }

    // Nice little method to create a gui item with a custom name, and description
    protected ItemStack createGuiItem(final String name, final Material material, final int amount, final String... lore) {
        final ItemStack item = new ItemStack(material, amount);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);
        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }
    protected ItemStack createGuiItem(final Material material, final int amount, final String... lore) {
        final ItemStack item = new ItemStack(material, amount);
        final ItemMeta meta = item.getItemMeta();

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
        Inventory inv = p.getInventory();

        int price = Integer.parseInt(clickedItem.getLore().get(0).split("Price: ")[1]);
        if (p.getInventory().firstEmpty() != -1 && Database.getPlayerBalance(p) >= price){
            Database.increasePlayerBalance(p, price*-1);
            ItemStack item = clickedItem.clone();
            item.setLore(null);
            inv.setItem(inv.firstEmpty(), item);
        }
        else {
            //TODO: Make separate message for not enough money and full inventory.
            p.sendMessage(ChatColor.RED + "You can't buy that right now!");
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().getClass().equals(shopGUI.getClass())) {
            e.setCancelled(true);
        }
    }
}