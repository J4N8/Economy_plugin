package com.j4n8.economy;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CommandMoneySet implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 3){
            return false;
        }

        if (args[0].equalsIgnoreCase("set")){
            String cmd = "UPDATE players SET money = " + args[2] + " WHERE uuid = '" + Bukkit.getPlayerUniqueId(args[1]) + "';";
            try {
                Connection con = Database.getConnection();
                Statement st = con.createStatement();
                st.executeUpdate(cmd);
            } catch (SQLException e) {
                sender.sendMessage("Error setting money!");
                return false;
            }
            return true;
        }
        return false;
    }
}
