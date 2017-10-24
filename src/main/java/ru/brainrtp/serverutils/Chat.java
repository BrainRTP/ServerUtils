package ru.brainrtp.serverutils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {

    private static Main plugin;
    private String[] prefixSuffix;

    public Chat(final Main instance) {
        Chat.plugin = instance;
    }

    @EventHandler
    private void onPlayerChatting(final AsyncPlayerChatEvent e) {

        if (e == null) return;

        String m = e.getMessage();
        Player player = e.getPlayer();
        int dlina = e.getMessage().length()-1;

        if (player.hasPermission("moneycat")) {
            prefixSuffix = Settings.moneycat.split("<name>");
        }
        else if (player.hasPermission("admin")) {
            prefixSuffix = Settings.adminChat.split("<name>");
        }
        else if (player.hasPermission("sponsor")) {
            prefixSuffix = Settings.sponsorChat.split("<name>");
        }
        else if (player.hasPermission("elite")) {
            prefixSuffix = Settings.eliteChat.split("<name>");
        }
        else if (player.hasPermission("legend")) {
            prefixSuffix = Settings.legendChat.split("<name>");
        }
        else if (player.hasPermission("premium")) {
            prefixSuffix = Settings.premiumChat.split("<name>");
        }
        else if (player.hasPermission("vip")) {
            prefixSuffix = Settings.vipChat.split("<name>");
        }
        else {
            prefixSuffix = Settings.defaultChat.split("<name>");
        }

        if (dlina == 0) e.setFormat(format(m, player));

        if (dlina != 0 && m.charAt(0) == '.' && m.charAt(1) != '.') {
            e.setFormat(format(m.replaceFirst(".", ""), player));
            return;
        }

        if (dlina != 0 && !String.valueOf(m.charAt(dlina)).matches("[a-zA-Z]*[.|?|!|-|+|,|(|)|>|<|;|:|_|^|D]+")) {
            m += ".";
            e.setMessage(m);
        }

        if (dlina != 0 && Character.isLowerCase(m.charAt(0))){
            m = m.replaceFirst(String.valueOf(m.charAt(0)), String.valueOf(Character.toUpperCase(m.charAt(0))));
            e.setMessage(m);
        }
        if (player.hasPermission("admin")) {
            e.setFormat(ChatColor.translateAlternateColorCodes('&', format(e.getMessage(), e.getPlayer())));
        } else {
            e.setFormat(format(e.getMessage(), e.getPlayer()));
        }
    }
    public String format(String args, Player player) {
        if (prefixSuffix[0].length() == 2 || prefixSuffix[0].length() == 0) {
            System.out.println(prefixSuffix[0]);
            return "§7"
                    + player.getName()
                    + ((prefixSuffix.length > 1) ? prefixSuffix[1] : "") + "§f: "
                    + prefixSuffix[1]
                    + args;
        } else {
            return prefixSuffix[0].replace(" ", "") +
                    ((prefixSuffix[0].split(" ").length == 2) ? prefixSuffix[0].split(" ")[1] : "§7")
                    + " "
                    + player.getName()
                    + ((prefixSuffix.length > 1) ? prefixSuffix[1] : "") + "§f: "
                    + prefixSuffix[1]
                    + args;
        }
    }
}
