package com.ftlz.spigot.antiafk;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new AFKListener(this), this);
    }
}
