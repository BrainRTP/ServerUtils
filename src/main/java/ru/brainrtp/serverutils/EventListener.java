// 
// Decompiled by Procyon v0.5.30
// 

package ru.brainrtp.serverutils;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
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
    public void onPlace(BlockPlaceEvent e) {
        if(!Main.build && !e.getPlayer().hasPermission("admin")) {
            e.setCancelled(true);
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
        player.teleport(LocationUtils.stringToLocation(Main.spawn_location));
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
        player.sendMessage("§f§c                          ▁▂▁");
        player.sendMessage("              §fДобро пожаловать на §cMineCraftCraft§f!");
        player.sendMessage("§c» §7Выбери игровой режим для начала игры через стенды или");
        player.sendMessage("                     §c» §7игровое меню.");
        player.sendMessage("");
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
            player.teleport(LocationUtils.stringToLocation(Main.spawn_location));
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
