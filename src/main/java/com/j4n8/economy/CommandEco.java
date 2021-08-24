package com.j4n8.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandEco implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 3){
            return false;
        }

        Player player = Bukkit.getPlayer(args[1]);
        int amount = Integer.parseInt(args[2]);

        if (args[0].equalsIgnoreCase("set")){
            return Database.setPlayerBalance(player, amount);
        }

        else if (args[0].equalsIgnoreCase("add")){
            return Database.increasePlayerBalance(player, amount);
        }
        return false;
    }
}
