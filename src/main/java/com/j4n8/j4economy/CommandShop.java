package com.j4n8.j4economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandShop implements CommandExecutor {
    private final Plugin plugin;

    public CommandShop(Plugin plugin){
        this.plugin = plugin;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("This command can only be executed by players!");
            return false;
        }
        Player player = (Player) sender;
        ShopCategoryGUI shopGUI = new ShopCategoryGUI(plugin);
        shopGUI.openInventory(player);
        return true;
    }
}
