package com.j4n8.j4economy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Set;

public class ShopCategoryGUI implements Listener {
    private final Plugin plugin;
    private final Inventory categoryGUI;
    public ShopCategoryGUI(Plugin plugin) {
        this.plugin = plugin;
        categoryGUI = Bukkit.createInventory(new ShopGUICategoriesHolder(), 9, "Shop");

        // Put the items into the inventory
        initializeItems();
    }
    // You can call this whenever you want to put the items in
    public void initializeItems() {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("shop");
        Set<String> categories = section.getKeys(false);
        for (String category : categories){
            categoryGUI.addItem(createGuiItem(category, Material.getMaterial(section.getString(category + ".display_item")), 1));
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
        ent.openInventory(categoryGUI);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!(e.getInventory().getHolder() instanceof ShopGUICategoriesHolder)){
            return;
        }
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();
        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;
        final Player p = (Player) e.getWhoClicked();

        String category = clickedItem.getItemMeta().getDisplayName();
        e.getInventory().close();
        ShopGUI shopGUI = new ShopGUI(plugin);
        shopGUI.openInventory(p, category);
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof ShopGUICategoriesHolder) {
            e.setCancelled(true);
        }
    }
}