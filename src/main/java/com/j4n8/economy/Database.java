package com.j4n8.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.sql.*;

public class Database {
    private static Connection connection;
    public static String host, database, username, password;
    public static int port;

    public static void mysqlSetup(String host, int port, String database, String username, String password){
        try{
            if(getConnection() != null){
                    return;
                }
                setConnection(DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", username, password));
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL CONNECTED");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "", username, password);
        }
        catch (SQLException e){
            return null;
        }
    }

    public static void setConnection(Connection connection) {
        Database.connection = connection;
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
