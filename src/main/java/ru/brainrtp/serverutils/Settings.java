package ru.brainrtp.serverutils;

import java.util.ArrayList;
import java.util.List;

/**
 * Создано 18.09.17
 */

public class Settings {

    static final int maxRepeat;
    static final int minCooldown;
    static final int maxHeight;
    static final int maxAmmount;
    static final int cd;
    private static Main plugin = Main.instance;
    static final String prefix;
    static final String kbMess;
    static final String glow;
    static final String spawnLocation;
    static final String adminChat;
    static final String moneycat;
    static final String defaultChat;
    static final String vipChat;
    static final String premiumChat;
    static final String legendChat;
    static final String eliteChat;
    static final String sponsorChat;
    static final String flyMess;
    static List<String> motd = new ArrayList<>();
    static final boolean orthography;

    static {
        maxRepeat = plugin.getConfig().getInt("FireWorks.maxRepeat");
        minCooldown = plugin.getConfig().getInt("FireWorks.maxCooldown");
        maxHeight = plugin.getConfig().getInt("FireWorks.maxHeight");
        maxAmmount = plugin.getConfig().getInt("FireWorks.maxAmmount");
        cd = plugin.getConfig().getInt("FireWorks.cooldownTime");
        prefix = plugin.getConfig().getString("prefix").replace("&", "§");
        orthography = plugin.getConfig().getBoolean("orthography");
        kbMess = plugin.getConfig().getString("knockBackMess").replace("&", "§");
        flyMess = plugin.getConfig().getString("flyMess").replace("&", "§");
        glow = plugin.getConfig().getString("glow").replace("&", "§");
        spawnLocation = plugin.getConfig().getString("spawnLocation");
        adminChat = plugin.getConfig().getString("Chat.admin").replace('&', '§');
        defaultChat = plugin.getConfig().getString("Chat.default").replace('&', '§');
        vipChat = plugin.getConfig().getString("Chat.vip").replace('&', '§');
        premiumChat = plugin.getConfig().getString("Chat.premium").replace('&', '§');
        legendChat = plugin.getConfig().getString("Chat.legend").replace('&', '§');
        eliteChat = plugin.getConfig().getString("Chat.elite").replace('&', '§');
        sponsorChat = plugin.getConfig().getString("Chat.sponsor").replace('&', '§');
        moneycat = plugin.getConfig().getString("Chat.moneycat").replace('&', '§');
        motd = (List<String>) plugin.getConfig().getList("Motd");
    }
}
