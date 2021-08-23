package com.j4n8.economy;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SetDefaultBalanceOnPlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        String cmd = "SELECT COUNT(id) FROM players WHERE uuid = '" + player.getUniqueId() + "';";
        int count = 0;
        try {
            Connection con = Database.getConnection();
            Statement st = con.createStatement();
            ResultSet set = st.executeQuery(cmd);

            while (set.next()) {
                count = set.getInt(1);
            }
        }
        catch (SQLException e) {
            return;
        }

        if (count == 0){
            String cmd2 = "INSERT INTO players (uuid, money) VALUES ('" + player.getUniqueId() + "', '5000');";
            try{
                Connection con = Database.getConnection();
                Statement st = con.createStatement();
                st.executeUpdate(cmd2);
            }
            catch (SQLException e) {
                return;
            }
        }
    }
}
