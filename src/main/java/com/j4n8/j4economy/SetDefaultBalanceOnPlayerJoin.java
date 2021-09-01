package com.j4n8.j4economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class SetDefaultBalanceOnPlayerJoin implements Listener {
    private final Plugin plugin;

    public SetDefaultBalanceOnPlayerJoin(Plugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Economy economy = J4Economy.getEconomy();

        if (economy.hasAccount(player)){
            return;
        }
        else {
            economy.createPlayerAccount(player);
            EconomyResponse r = economy.depositPlayer(player, plugin.getConfig().getDouble("default_balance"));
        }
    }
}
