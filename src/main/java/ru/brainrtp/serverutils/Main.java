package ru.brainrtp.serverutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin implements Listener {

    private HashMap<String, String> prisoner = new HashMap<String, String>();
    private static HashMap<String, Long> cooldowns = new HashMap<>();
    static List<Player> kb_users = new ArrayList<Player>();
    static boolean build = false;
    public static Main instance;
    private static String username = "user";
    private static String pass = "pass";
    private static String database = "database";
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String table = "table";
    protected int i;
    protected static int old;

    @Override
    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(new EventListener(this),this);
        Bukkit.getServer().getWorld("world").setTime(40000L);
        Bukkit.getServer().getWorld("world").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getServer().getWorld("world").setWeatherDuration(99999999);

        MySQL mySQL = new MySQL();
        mySQL.setup(username, pass, database, url);
        ResultSet players = mySQL.query("SELECT * FROM " + table + ";");
        try {
            while (players.next()) {
                old++;
            }
        } catch (SQLException e) {e.printStackTrace();}
        mySQL.closeConnection();

        if (Settings.orthography) {
            pm.registerEvents(new Chat(this), this);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        e.setQuitMessage(null);
        if (cooldowns.containsKey(e.getPlayer().getName())){
            cooldowns.remove(e.getPlayer().getName());
        }
    }

//    @EventHandler
//    private void onPlayerTeleport(final PlayerTeleportEvent e) {
//        if (e.getCause() == PlayerTeleportEvent.TeleportCause.COMMAND) {
//            e.getFrom().getWorld().playEffect(e.getFrom(), Effect.ENDER_SIGNAL, 0);
//            e.getFrom().getWorld().playSound(e.getFrom(), Sound.ENTITY_ENDERMEN_TELEPORT, 1.0f, 1.0f);
//        }
//    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            int cooldownTime = Settings.cd;

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
                        int repeat = (Integer.valueOf(args[0]) <= Settings.maxRepeat ? Integer.parseInt(args[0]) : 1);
                        int cooldown = (Integer.valueOf(args[1]) >= Settings.minCooldown ? Integer.parseInt(args[1]) : 1);
                        int height = (Integer.valueOf(args[2]) <= Settings.maxHeight ? Integer.parseInt(args[2]) : 1);
                        int amount = (Integer.valueOf(args[3]) <= Settings.maxAmmount ? Integer.parseInt(args[3]) : 1);
                        fw.fw1(player, repeat, cooldown, height, amount);
                    }
                    else {
                        if (player.hasPermission("fw.admin")) {
                            player.sendMessage(Settings.prefix + "Используйте /fw <повторов> <задержка> <высота> <кол-во>");
                        } else {
                            player.sendMessage(Settings.prefix + "Используйте /fw");
                        }
                    }
                    break;
                case "setspawn":
                    if (player.hasPermission("admin")){
                        this.getConfig().set("spawnLocation", LocationUtils.locationToString(player.getLocation(), true));
                        player.sendMessage(Settings.prefix + "Точка спавна успешно установлена! §7("+player.getLocation().getBlockX() + "," +
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
                            player.sendMessage(Settings.kbMess.replace("%s", "выключен"));
                        }
                        else {
                            kb_users.add(player);
                            player.sendMessage(Settings.kbMess.replace("%s", "включен"));
                        }
                    } else {
                        player.sendMessage(Settings.prefix + "§cЭта функция доступна только ютуберам!");
                    }
                    break;

                case "fly":
                    if (player.hasPermission("fly") || player.hasPermission("admin")){
                        if (player.getAllowFlight() || player.isFlying()){
                            player.sendMessage(Settings.flyMess.replace("%s", "выключен"));
                            player.setAllowFlight(false);
                            player.setFlying(false);
                        } else {
                            player.sendMessage(Settings.flyMess.replace("%s", "включен"));
                            player.setAllowFlight(true);
                            player.setFlying(true);
                        }
                    } else {
                        player.sendMessage(Settings.prefix + "§cЭта функция доступна только от §aVIP §cи выше!");
                    }
                    break;

                case "glow":
                    if (player.hasPermission("glow") || player.hasPermission("admin")){
                        if (player.hasPotionEffect(PotionEffectType.GLOWING)) {
                            player.sendMessage(Settings.glow.replace("%s", "выключен"));
                            player.removePotionEffect(PotionEffectType.GLOWING);
                        }
                        else {
                            player.sendMessage(Settings.glow.replace("%s", "включен"));
                            player.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20000000, 2, true));
                        }
                    }
                    else {
                        player.sendMessage(Settings.prefix + "§cЭта функция доступна только от §aVIP §cи выше!");
                    }
                    break;

                case "do":
                    if (player.hasPermission("admin")) {
                        Player sp = Bukkit.getPlayer(args[0]);
                        if (sp == null || !sp.isOnline()) {
                            sender.sendMessage(Settings.prefix + "§cИгрок не найден");
                            return true;
                        }
                        String what = "";
                        for (int i = 1; i < args.length; ++i) {
                            String arg = String.valueOf(args[i]) + " ";
                            what = String.valueOf(what) + arg;
                        }
                        sp.chat(what);
                    }
                    break;

                case "build":
                    if (player.hasPermission("admin")){
                        if (!build){
                            build = true;
                            player.sendMessage(Settings.prefix + "Режим строительства §cвключен.");
                        } else {
                            build = false;
                            player.sendMessage(Settings.prefix + "Режим строительства §cвыключен.");
                        }
                    }
                    break;
                case "reglist":
                    if(player.hasPermission("admin")){

                        MySQL mySQL = new MySQL();
                        mySQL.setup(username, pass, database, url);

                        ResultSet players = mySQL.query("SELECT * FROM " + table + ";");
                        try {
                            while (players.next()) {
                                i++;
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        player.sendMessage("§7§m-------§7 AuthMe §m-------");
                        player.sendMessage(" §eВсего:   §b"+i);
                        player.sendMessage(" §eСегодня: §c"+(i-old));
                        player.sendMessage("§7§m----------------------");
                        i = 0;
                        mySQL.closeConnection();
                        break;
                    }
                case "bc":
                    if(player.hasPermission("admin")){
                        String msg = "";
                        for (int i = 0; i < args.length; ++i) {
                            final String arg = String.valueOf(args[i]) + " ";
                            msg = String.valueOf(msg) + arg;
                        }
                        msg = msg.trim();
                        msg = ChatColor.translateAlternateColorCodes('&', msg);
                        Bukkit.broadcastMessage(msg);
                    }
                default:
                    break;
            }

        }
        else {
            if (cmd.getName().equals("bc")) {
                String msg = "";
                for (int i = 0; i < args.length; ++i) {
                    final String arg = String.valueOf(args[i]) + " ";
                    msg = String.valueOf(msg) + arg;
                }
                msg = msg.trim();
                msg = ChatColor.translateAlternateColorCodes('&', msg);
                Bukkit.broadcastMessage(msg);
            }
            else if (cmd.getName().equals("do")){
                Player sp = Bukkit.getPlayer(args[0]);
                if (sp == null || !sp.isOnline()) {
                    sender.sendMessage(Settings.prefix + "§cИгрок не найден");
                    return true;
                }
                String what = "";
                for (int i = 1; i < args.length; ++i) {
                    String arg = String.valueOf(args[i]) + " ";
                    what = String.valueOf(what) + arg;
                }
                sp.chat(what);

            } else {
                Bukkit.getConsoleSender().sendMessage("§f[§cServerUtils§f] > " + ChatColor.DARK_RED + "Команды может использовать только игрок!");
            }
        }
        return false;
    }

    private static void log(String args){
        Bukkit.getConsoleSender().sendMessage(args);
    }
}
