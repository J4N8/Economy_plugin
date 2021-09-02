package com.j4n8.j4economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class ShopGUI implements Listener {
    private final Plugin plugin;
    private final Inventory shopGUI;
    public ShopGUI(Plugin plugin) {
        this.plugin = plugin;
        shopGUI = Bukkit.createInventory(new ShopGUIHolder(), 54, "Shop");
    }

    // You can call this whenever you want to put the items in
    public void initializeItems(String category) {
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("shop."+category);
        Set<String> items = section.getKeys(false);
        for (String item : items){
            if (!item.equals("display_item")){
                try{
                    shopGUI.addItem(createGuiItem(Material.getMaterial(item), section.getInt(item+".amount"), "Price: "+section.getInt(item+".price")));
                }
                catch (Exception e){
                    //Skip the item
                    plugin.getLogger().info(ChatColor.YELLOW + "Skipping invalid item in shop config: '" + item + "'!");
                }
            }
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
    public void openInventory(final HumanEntity ent, final String category) {
        initializeItems(category);
        ent.openInventory(shopGUI);
    }

    // Check for clicks on items
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        Economy economy = J4Economy.getEconomy();
        if (!(e.getInventory().getHolder() instanceof ShopGUIHolder)){
            return;
        }
        e.setCancelled(true);

        final ItemStack clickedItem = e.getCurrentItem();
        // verify current item is not null
        if (clickedItem == null || clickedItem.getType().isAir()) return;
        final Player p = (Player) e.getWhoClicked();
        Inventory inv = p.getInventory();
        int price = Integer.parseInt(clickedItem.getLore().get(0).split("Price: ")[1]);

        if (e.isLeftClick()){ //Buy items
            //Must have empty inventory slot and enough money
            if (p.getInventory().firstEmpty() != -1 && economy.has(p, price)){
                economy.withdrawPlayer(p, price);
                ItemStack item = clickedItem.clone();
                item.setLore(null);
                inv.setItem(inv.firstEmpty(), item);
            }
        }
        else if (e.isRightClick()){ //Sell items
            if (inv.contains(clickedItem.getType(), clickedItem.getAmount())){
                economy.depositPlayer(p, price*0.5);
                ItemStack item = clickedItem.clone();
                item.setLore(null);
                inv.removeItemAnySlot(item);
            }
        }

        else {
            //TODO: Make separate message for not enough money and full inventory.
            p.sendMessage(ChatColor.RED + "You can't buy that right now!");
        }
    }

    @EventHandler
    public void onInventoryClick(final InventoryDragEvent e) {
        if (e.getInventory().getHolder() instanceof ShopGUIHolder) {
            e.setCancelled(true);
        }
    }
}