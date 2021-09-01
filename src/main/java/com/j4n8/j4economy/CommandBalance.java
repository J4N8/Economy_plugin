package com.j4n8.j4economy;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandBalance implements CommandExecutor {
    private final Plugin plugin;

    public CommandBalance(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Economy economy = J4Economy.getEconomy();
        Player player;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                return false;
            }
            player = (Player) sender;
            try {
                sender.sendMessage("Your balance is: " + ChatColor.GOLD + economy.format(economy.getBalance(player)));
                return true;
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Error retrieving balance!");
                return false;
            }
        } else {
            player = Bukkit.getPlayer(args[0]);
            try {
                sender.sendMessage(ChatColor.RED + player.getName() + ChatColor.RESET + "'s balance is: " + ChatColor.GOLD + economy.format(economy.getBalance(player)));
                return true;
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + "Error retrieving balance!");
                return false;
            }
        }
    }
}
