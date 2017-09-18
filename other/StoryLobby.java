// 
// Decompiled by Procyon v0.5.30
// 

package ru.brainrtp.serverutils;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;

public class StoryLobby extends JavaPlugin
{
    Thread thread;
    private static StoryLobby instance;
    private HashSet<Player> user;
    
    public StoryLobby() {
        this.user = new HashSet<Player>();
    }
    
    public static StoryLobby getInstance() {
        return StoryLobby.instance;
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final Player p = (Player)sender;
        if (cmd.getName().equals("setspawn") && sender.isOp()) {
            this.getConfig().set("spawn_location", p.getLocation());
//            this.getConfig().set("spawn.y", (Object)p.getLocation().getY());
//            this.getConfig().set("spawn.z", (Object)p.getLocation().getZ());
//            this.getConfig().set("spawn.yaw", (Object)p.getLocation().getYaw());
//            this.getConfig().set("spawn.pitch", (Object)p.getLocation().getPitch());
            p.sendMessage("§bСпавн установлен!");
            this.saveConfig();
        }
        if (cmd.getName().equals("kb")) {
            if (p.hasPermission("lobby.kb")) {
                if (this.user.contains(p)) {
                    p.sendMessage(this.getConfig().getString("kb-off-message"));
                    this.user.remove(p);
                }
                else {
                    p.sendMessage(this.getConfig().getString("kb-message"));
                    this.user.add(p);
                }
            }
            else {
                p.sendMessage(this.getConfig().getString("kb-np-message"));
            }
        }
        if (cmd.getName().equals("fly")) {
            if (p.hasPermission("lobby.fly")) {
                if (p.getAllowFlight()) {
                    p.sendMessage(this.getConfig().getString("fly-off-message"));
                    p.setAllowFlight(false);
                    p.setFlying(false);
                }
                else {
                    p.sendMessage(this.getConfig().getString("fly-message"));
                    p.setAllowFlight(true);
                    p.setFlying(true);
                }
            }
            else {
                p.sendMessage(this.getConfig().getString("fly-np-message"));
            }
        }
        if (cmd.getName().equals("glow")) {
            if (p.hasPermission("lobby.glow")) {
                if (p.hasPotionEffect(PotionEffectType.GLOWING)) {
                    p.sendMessage(this.getConfig().getString("effect-off-message"));
                    p.removePotionEffect(PotionEffectType.GLOWING);
                }
                else {
                    p.sendMessage(this.getConfig().getString("effect-message"));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20000000, 2, true));
                }
            }
            else {
                p.sendMessage(this.getConfig().getString("effect-np-message"));
            }
        }
        return true;
    }
    
//    public void onDisable() {
//        this.thread.stop();
//        this.thread.interrupt();
//        this.thread = null;
//    }
    
    public void onEnable() {
        StoryLobby.instance = this;
        this.getConfig().options().copyDefaults(true);
        Bukkit.getServer().getWorld("world").setTime(40000L);
        Bukkit.getServer().getWorld("world").setGameRuleValue("doDaylightCycle", "false");
        Bukkit.getServer().getWorld("world").setWeatherDuration(99999999);
        this.getServer().getPluginManager().registerEvents((Listener)new EventListener(this), (Plugin)this);
        this.saveConfig();
//        (this.thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    final Iterator<Player> pl = ((HashSet)StoryLobby.this.user.clone()).iterator();
//                    while (pl.hasNext()) {
//                        try {
//                            Thread.sleep(500L);
//                        }
//                        catch (InterruptedException ex) {}
//                        final Player p = pl.next();
//                        if (!p.isOnline()) {
//                            StoryLobby.this.user.remove(p);
//                        }
//                        else {
//                            for (final Entity e : p.getNearbyEntities(1.0, 1.0, 1.0)) {
//                                if (e.getType() == EntityType.PLAYER) {
//                                    final Player p2 = (Player)e;
//                                    if (p.getName().equals(p2.getName())) {
//                                        continue;
//                                    }
//                                    p2.sendMessage("§7[§braft§7] §c> §rВы не можете подходить к данному игроку, у него включен режим отталкивания (§c/kb§r)");
//                                    p2.setVelocity(p2.getLocation().getDirection().setY(-1.35).multiply(-1));
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        })).start();
    }
}
