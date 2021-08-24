package com.j4n8.economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Economy extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        PluginManager pm = getServer().getPluginManager();
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        //Database connection setup
        Database.mysqlSetup(config.getString("host"), config.getInt("port"), config.getString("database"),
                config.getString("username"), config.getString("password"));

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
