package ru.brainrtp.serverutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.util.Vector;

public class EventListener implements Listener
{
    private static Main plugin;
    public int n;

    public EventListener(Main instance) {
        this.plugin = instance;
    }
    
    @EventHandler
    public void onCommandPreprocess( PlayerCommandPreprocessEvent e) {
        if (e.getMessage().equals("/help")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if(Main.build && e.getPlayer().hasPermission("admin")) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void jumpPad(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getAction().equals(Action.PHYSICAL)) {
            if (e.getClickedBlock().getType() == Material.SOIL)
                e.setCancelled(true);

            if (e.getClickedBlock().getType() == Material.STONE_PLATE) {
//                Vector v = p.getVelocity();
//                String[] ln = velstr.split(",");
//                if ((ln.length == 1) && (FLOAT.matcher(velstr).matches())) {
//                    double power = Double.parseDouble(velstr);
//                    v.setY(Math.min(10, kick ? power * p.getVelocity().getY() : power));
//                } else if ((ln.length == 3) &&
//                        FLOAT.matcher(ln[0]).matches() &&
//                        FLOAT.matcher(ln[1]).matches() &&
//                        FLOAT.matcher(ln[2]).matches()) {
//                    double powerx = Double.parseDouble(ln[0]);
//                    double powery = Double.parseDouble(ln[1]);
//                    double powerz = Double.parseDouble(ln[2]);
//                    if (kick) {
//                        v = p.getLocation().getDirection();
//                        v = v.normalize();
//                        v = v.multiply(new Vector(powerx, powery, powerz));
//                        p.setFallDistance(0);
//                    } else v = new Vector(Math.min(10, powerx), Math.min(10, powery), Math.min(10, powerz));
//                }
//                p.setVelocity(v);
//                double x = p.getVelocity().getBlockX();
//                Vector v = p.getLocation().getDirection();
//                v.ni*1;

//                p.setVelocity(p.getLocation().getDirection().multiply(5).normalize());
//                p.setVelocity(p.getLocation().getDirection().multiply(new Vector(5, 0, 0)));
//                Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin) Main.instance, new Runnable() {
//                    public void run() {
//                        p.setVelocity(p.getLocation().getDirection().multiply(5).normalize());
//                    }
//                }, 0, 0);
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () ->
                {
                    p.setVelocity(p.getLocation().getDirection().add(new Vector((float) 5, (float) 0.1, (float) 0).multiply(5)));
                }, 1);

//                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, () ->
//                {
//                    p.setVelocity(p.getLocation().getDirection().multiply(50).normalize());
//                    p.setVelocity(new Vector(10, 1, 0).multiply(50).normalize());
//                }, 1);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(Main.build && e.getPlayer().hasPermission("admin")) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFrame(PlayerInteractAtEntityEvent e){
        if(Main.build && e.getPlayer().hasPermission("admin")) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPainting(HangingBreakByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (Main.build && e.getEntity().hasPermission("admin")) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDrop(final PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (Main.build && e.getEntity().hasPermission("admin")) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBurn(BlockBurnEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onIgnite(BlockIgniteEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onRain(WeatherChangeEvent e) {
        e.setCancelled(true);
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player player = e.getPlayer();
        player.getInventory().setHeldItemSlot(0);
        player.teleport(LocationUtils.stringToLocation(Settings.spawnLocation));
        player.setHealth(20.0);
        player.setFoodLevel(20);
        player.setExp(0);
        player.setWalkSpeed(0.4f);
        if (player.hasPermission("fly") || player.hasPermission("admin")){
            player.setAllowFlight(true);
            player.setFlying(true);
        }
        if (player.getGameMode().equals(GameMode.CREATIVE) && !player.hasPermission("admin")) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        for (String motd : Settings.motd){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', motd));
        }
    }
    
    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        int locY = player.getLocation().getBlockY();
        if (locY < -1) {
            player.teleport(LocationUtils.stringToLocation(Settings.spawnLocation));
        }
        if (Main.kb_users.contains(player)) {
            for (final Player pl : Bukkit.getOnlinePlayers()) {
                if (!pl.getName().equals(e.getPlayer().getName()) &&
                        !Main.kb_users.contains(pl) &&
                        pl.getLocation().toVector().isInSphere(e.getPlayer().getLocation().toVector(), 1.5)) {
                    double x = Math.sin(Math.toRadians(pl.getLocation().getYaw())) * Math.cos(Math.toRadians(pl.getLocation().getPitch()) / 2);
                    double z = -Math.cos(Math.toRadians(pl.getLocation().getYaw())) * Math.cos(Math.toRadians(pl.getLocation().getPitch()) / 2);
                    double y = Math.sin(Math.toRadians(pl.getLocation().getPitch()));
                    pl.setVelocity(new Vector(x, y, z));
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onArmorStand(PlayerArmorStandManipulateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void spawnMob(CreatureSpawnEvent event){
        event.setCancelled(true);
    }

}
