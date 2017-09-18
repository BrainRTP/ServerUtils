package ru.brainrtp.serverutils;

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

        if (dlina == 0) return;

        if (dlina != 0 && m.charAt(0) == '.' && m.charAt(1) != '.') {
            e.setMessage(m.replaceFirst(".", ""));
        }

        if (!String.valueOf(m.charAt(dlina)).matches("[a-zA-Z]*[.|?|!|-|+|,|(|)|>|<|;|:|_|^|D]+")) {
            m += ".";
            e.setMessage(m);
        }

        if (dlina != 0 && Character.isLowerCase(m.charAt(0))){
            m = m.replaceFirst(String.valueOf(m.charAt(0)), String.valueOf(Character.toUpperCase(m.charAt(0))));
            e.setMessage(m);
        }

        if (player.hasPermission("hyneo.admin")) {
            prefixSuffix = Main.admin_chat.split("<name>");
        } else {
            prefixSuffix = Main.default_chat.split("<name>");
        }
        e.setFormat("[" + prefixSuffix[0].replace(" ", "") + "§f] " +
                ((prefixSuffix[0].split(" ").length == 2) ?  prefixSuffix[0].split(" ")[1] : "§7")
                + e.getPlayer().getName()
                + ((prefixSuffix.length > 1) ? prefixSuffix[1] : "") + " §f> " + e.getMessage());
    }
}
