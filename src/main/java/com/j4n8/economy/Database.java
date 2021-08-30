package com.j4n8.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.sql.*;

public class Database {
    private static String host, database, username, password, databaseType;
    private static int port;
    private static Plugin plugin;

    public static void mysqlSetup(String host, int port, String database, String username, String password){
        try{
            if (plugin.getConfig().getString("database-type").equals("mysql")){
                DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", username, password);
                Database.databaseType = "postgresql";
            }
            else if (plugin.getConfig().getString("database-type").equals("postgre")){
                DriverManager.getConnection("jdbc:postgresql://" + host + ":" + port + "/" + database + "", username, password);
                Database.databaseType = "mysql";
            }
            else {
                plugin.getLogger().severe(ChatColor.DARK_RED + "INVALID DATABASE TYPE!!!!");
                plugin.getPluginLoader().disablePlugin(plugin);
            }

            Database.host = host;
            Database.port = port;
            Database.database = database;
            Database.username = username;
            Database.password = password;
            Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL CONNECTED");
        }
        catch (SQLException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "MYSQL CONNECTION FAILED!");
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", username, password);
        }
        catch (SQLException e){
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + e.getMessage());
        }
        return connection;
    }

    public static java.lang.Integer getPlayerBalance(Player player){
        String cmd = "SELECT money FROM players WHERE uuid = '" + player.getUniqueId() + "';";
        int money = 0;
        try {
            Connection con = Database.getConnection();
            Statement st = con.createStatement();
            ResultSet set = st.executeQuery(cmd);

            while (set.next()) {
                money = set.getInt(1);
            }
            return money;
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean increasePlayerBalance(Player player, int amount){
        int money = Database.getPlayerBalance(player);
        money += amount;
        String cmd = "UPDATE players SET money = " + money + " WHERE uuid = '" + player.getUniqueId() + "';";
        try {
            Connection con = Database.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(cmd);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean setPlayerBalance(Player player, int newBalance){
        String cmd = "UPDATE players SET money = " + newBalance + " WHERE uuid = '" + player.getUniqueId() + "';";
        try {
            Connection con = Database.getConnection();
            Statement st = con.createStatement();
            st.executeUpdate(cmd);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
