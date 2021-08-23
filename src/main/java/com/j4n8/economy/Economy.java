package com.j4n8.economy;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Economy extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new SetDefaultBalanceOnPlayerJoin(), this);
        this.getCommand("eco").setExecutor(new CommandMoneySet());
        Database.onEnable();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
