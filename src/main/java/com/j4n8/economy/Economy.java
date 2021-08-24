package com.j4n8.economy;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Economy extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginManager pm = getServer().getPluginManager();
        Database.onEnable();
        //Events
        pm.registerEvents(new SetDefaultBalanceOnPlayerJoin(), this);
        //Commands
        this.getCommand("eco").setExecutor(new CommandEco());
        this.getCommand("balance").setExecutor(new CommandBalance());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
