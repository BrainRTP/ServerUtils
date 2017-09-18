package ru.brainrtp.serverutils;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin implements Listener, PluginMessageListener {

    private static HashMap<String, Long> cooldowns = new HashMap<>();
    static List<Player> kb_users = new ArrayList<Player>();
    private Integer maxRepeat;
    private Integer minCooldown;
    private Integer maxHeight;
    private Integer maxAmmount;
    private Integer cd;
    private Main main;
    private String prefix;
    private String kb_mess;
    private String fly_mess;
    private String glow;
    static String spawn_location;
    static Boolean build = false;
    static String admin_chat;
    static String default_chat;

    @Override
    public void onEnable() {

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        this.saveDefaultConfig();
        maxRepeat = this.getConfig().getInt("FireWorks.maxRepeat");
        minCooldown = this.getConfig().getInt("FireWorks.maxCooldown");
        maxHeight = this.getConfig().getInt("FireWorks.maxHeight");
        maxAmmount = this.getConfig().getInt("FireWorks.maxAmmount");
        cd = this.getConfig().getInt("FireWorks.cooldownTime");
        prefix = this.getConfig().getString("prefix").replace("&", "§");
        Boolean orthography = this.getConfig().getBoolean("orthography");
        kb_mess = this.getConfig().getString("knockback_mess").replace("&", "§");
        fly_mess = this.getConfig().getString("fly_mess").replace("&", "§");
        glow = this.getConfig().getString("glow").replace("&", "§");
        spawn_location = this.getConfig().getString("spawn_location");
        admin_chat = this.getConfig().getString("Chat.admin").replace('&', '§');
        default_chat = this.getConfig().getString("Chat.default").replace('&', '§');
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents((Listener) new EventListener(this), (Plugin)this);
        Bukkit.getServer().getWorld("world").setTime(40000L);
        Bukkit.getServer().getWorld("world").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getServer().getWorld("world").setWeatherDuration(99999999);

        if (orthography) {
            pm.registerEvents((Listener) new Chat(this), (Plugin) this);
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("PlayerList")) {
            String server = in.readUTF(); // The name of the server you got the player list of, as given in args.
            String[] playerList = in.readUTF().split(", ");
            player.sendMessage("Ку)");
            player.sendMessage(String.valueOf(playerList.length));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        e.setQuitMessage(null);
        if (cooldowns.containsKey(e.getPlayer().getName())){
            cooldowns.remove(e.getPlayer().getName());
        }
    }

    @EventHandler
    private void onPlayerTeleport(final PlayerTeleportEvent e) {
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) {
            e.getFrom().getWorld().playEffect(e.getFrom(), Effect.ENDER_SIGNAL, 0);
            e.getFrom().getWorld().playSound(e.getFrom(), Sound.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
        }
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            int cooldownTime = cd;

            switch (cmd.getName()) {
                case "fw":
                    if (player.hasPermission("fw")  && args.length == 0){
                        if (cooldowns.containsKey(player.getName())) {
                            long secondsLeft = ((cooldowns.get(player.getName()) / 1000) + cooldownTime) - (System.currentTimeMillis() / 1000);
                            if (secondsLeft > 0)
                                return false;
                        }
                        cooldowns.put(String.valueOf(player.getName()), System.currentTimeMillis());
                        fw.fw1(player, 1, 1, 0, 1);

                    }
                    else if (cmd.getName().equals("fw") && (player.hasPermission("fw.admin")
                            || player.hasPermission("admin")) && args.length == 4) {
                        int repeat = (Integer.valueOf(args[0]) <= maxRepeat ? Integer.parseInt(args[0]) : 1);
                        int cooldown = (Integer.valueOf(args[1]) >= minCooldown ? Integer.parseInt(args[1]) : 1);
                        int height = (Integer.valueOf(args[2]) <= maxHeight ? Integer.parseInt(args[2]) : 1);
                        int amount = (Integer.valueOf(args[3]) <= maxAmmount ? Integer.parseInt(args[3]) : 1);
                        fw.fw1(player, repeat, cooldown, height, amount);
                    }
                    else {
                        if (player.hasPermission("fw.admin")) {
                            player.sendMessage(prefix + "Используйте /fw <повторов> <задержка> <высота> <кол-во>");
                        } else {
                            player.sendMessage(prefix + "Используйте /fw");
                        }
                    }
                    break;
                case "setspawn":
                    if (player.hasPermission("admin")){
                        this.getConfig().set("spawn_location", (Object)LocationUtils.locationToString(player.getLocation(), true));
                        player.sendMessage(prefix + "Точка спавна успешно установлена! §7("+player.getLocation().getBlockX() + "," +
                                player.getLocation().getBlockY() + "," +
                                player.getLocation().getBlockZ() + "; " +
                                player.getLocation().getPitch() + "," +
                                player.getLocation().getYaw() +")");
                        this.saveConfig();
                    }
                    break;

                case "kb":
                    if(player.hasPermission("knockback") || player.hasPermission("admin")){
                        if (kb_users.contains(player)){
                            kb_users.remove(player);
                            player.sendMessage(kb_mess.replace("%s", "выключен"));
                        }
                        else {
                            kb_users.add(player);
                            player.sendMessage(kb_mess.replace("%s", "включен"));
                        }
                    } else {
                        player.sendMessage(prefix + "Если вы хотите отталкивать игроков - купите VIP");
                    }
                    break;

                case "fly":
                    if (player.hasPermission("fly") || player.hasPermission("admin")){
                        if (player.getAllowFlight() || player.isFlying()){
                            player.sendMessage(fly_mess.replace("%s", "выключен"));
                            player.setAllowFlight(false);
                            player.setFlying(false);
                        } else {
                            player.sendMessage(fly_mess.replace("%s", "включен"));
                            player.setAllowFlight(true);
                            player.setFlying(true);
                        }
                    } else {
                        player.sendMessage(prefix + "Если вы хотите летать - купите VIP");
                    }
                    break;

                case "glow":
                    if (player.hasPermission("glow") || player.hasPermission("admin")){
                        if (player.hasPotionEffect(PotionEffectType.GLOWING)) {
                            player.sendMessage(glow.replace("%s", "выключен"));
                            player.removePotionEffect(PotionEffectType.GLOWING);
                        }
                        else {
                            player.sendMessage(glow.replace("%s", "включен"));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20000000, 2, true));
                        }
                    }
                    else {
                        player.sendMessage(prefix + "Если вы хотите такой эффект - купите VIP");
                    }
                    break;

                case "build":
                    if (player.hasPermission("admin")){
                        if (!build){
                            build = true;
                            player.sendMessage(prefix + "Режим строительства §cвключен.");
                        } else {
                            build = false;
                            player.sendMessage(prefix + "Режим строительства §cвыключен.");
                        }
                    }
                    break;

                default:
                    player.sendMessage("САСАТЬ!");
                    break;
            }

        }
        else {
            Bukkit.getConsoleSender().sendMessage("§f[§cServerUtils§f] > " + ChatColor.DARK_RED + "Команды может использовать только игрок!");}
        return false;
    }

    private static void log(String args){
        Bukkit.getConsoleSender().sendMessage(args);
    }
}
