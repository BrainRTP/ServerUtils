package ru.brainrtp.serverutils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

class Config {
    private static FileConfiguration config;

    static FileConfiguration getConfig() {return config;}

    static void createFiles(JavaPlugin plugin) {

        File configf = new File(getDataFolder(), "config.yml");

        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            log("§f[§cServerUtils§f] > " + "config.yml не найден, создаю новый!");
            Main.getPlugin(Main.class).saveResource("config.yml", false);
        }
        config = new YamlConfiguration();
        try {
            config.load(configf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private static File getDataFolder() {return Main.getPlugin(Main.class).getDataFolder();}

    private static void log(String args){
        Bukkit.getConsoleSender().sendMessage(args);
    }
}
