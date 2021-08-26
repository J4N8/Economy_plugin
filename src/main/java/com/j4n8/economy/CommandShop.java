package com.j4n8.economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandShop implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("This command can only be executed by players!");
            return false;
        }
        Player player = (Player) sender;
        ShopGUI shopGUI = new ShopGUI();
        shopGUI.openInventory(player);
        return true;
    }
}
