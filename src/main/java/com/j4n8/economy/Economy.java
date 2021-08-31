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

        //Events
        pm.registerEvents(new SetDefaultBalanceOnPlayerJoin(this), this);
        pm.registerEvents(new ShopGUI(this), this);

        //Commands
        this.getCommand("eco").setExecutor(new CommandEco(this));
        this.getCommand("balance").setExecutor(new CommandBalance(this));
        this.getCommand("shop").setExecutor(new CommandShop(this));

        //Database connection setup
        Database.mysqlSetup(config.getString("host"), config.getInt("port"), config.getString("database"),
                config.getString("username"), config.getString("password"), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
