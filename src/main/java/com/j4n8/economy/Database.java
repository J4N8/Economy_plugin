package com.j4n8.economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;
    public static String host, database, username, password;
    public static int port;

    public static void onEnable() {
        mysqlSetup();
    }

    public static void mysqlSetup(){
        host = "localhost";
        port = 3306;
        database = "economy";
        username = "root";
        password = "";

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
}
