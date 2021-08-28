package com.j4n8.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandBalance implements CommandExecutor {
    private final Plugin plugin;

    public CommandBalance(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String money_sign = plugin.getConfig().getString("money_sign");
        if (args.length == 0){
            if (!(sender instanceof Player)){
                return false;
            }
            else{
                Player player = (Player) sender;
                try {
                    sender.sendMessage("Your balance is: " + Database.getPlayerBalance(player).toString() + money_sign);
                    return true;
                }
                catch (Exception e){
                    sender.sendMessage("Error retrieving balance!");
                    return false;
                }
            }
        }
        else {
            Player player = Bukkit.getPlayer(args[0]);
            sender.sendMessage(args[0] + "'s balance is: " + Database.getPlayerBalance(player) + money_sign);
            return true;
        }
    }
}
