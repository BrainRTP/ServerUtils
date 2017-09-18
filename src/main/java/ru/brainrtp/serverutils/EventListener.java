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
        if(Main.build && e.getPlayer().hasPermission("hyneo.admin")) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if(!Main.build && !e.getPlayer().hasPermission("hyneo.admin")) {
            e.setCancelled(true);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFrame(PlayerInteractAtEntityEvent e){
        if(Main.build && e.getPlayer().hasPermission("hyneo.admin")) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPainting(HangingBreakByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (Main.build && e.getEntity().hasPermission("hyneo.admin")) {
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

//    @EventHandler
//    public void onDrop(final ItemSpawnEvent e) {
//        e.setCancelled(true);
//    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (Main.build && e.getEntity().hasPermission("hyneo.admin")) {
                e.setCancelled(false);
            } else {
                e.setCancelled(true);
            }
        }
//        e.getEntity().sendMessage(String.valueOf(e.getCause()));
//        if (e.getEntity() instanceof Player) {
//            Player player = (Player) e.getEntity();
//            if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
////                e.setCancelled(true);
//                e.getEntity().teleport(LocationUtils.stringToLocation(Main.spawn_location));
////                player.teleport(player.getWorld().getSpawnLocation());
//            }
//        }
    }



//    @EventHandler
//    public void onEntityDamage(EntityDamageEvent e) {
//        if(e.getEntity() instanceof Player) {
//            if (e.getCause() == DamageCause.VOID){
//            Player p = (Player) e.getEntity();
//            if(p.getWorld().getName().equalsIgnoreCase("world")){
//                getServer().broadcast(ChatColor.GREEN + p.getDisplayName() + " has been sent to spawn", "canMattigins");
//                p.teleport(p.getWorld().getSpawnLocation());
//                e.setCancelled(true);
//            }
//        }
//    }
//}

//    @EventHandler(priority = EventPriority.HIGHEST)
//    public void onDamage(EntityDamageByEntityEvent e) {
//
//        e.setCancelled(true);
//    }

//    @EventHandler
//    public void onQuit(PlayerQuitEvent e) {
//        e.setQuitMessage(null);
//    }

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
        if (player.hasPermission("fly") || player.hasPermission("hyneo.admin")){
            player.setAllowFlight(true);
            player.setFlying(true);
        }
        if (player.getGameMode().equals(GameMode.CREATIVE) && !player.hasPermission("hyneo.admin")) {
            player.setGameMode(GameMode.SURVIVAL);
        }
        player.sendMessage("§f§c                          ▁▂▁");
        player.sendMessage("              §fДобро пожаловать на §cHyNeoCraft§f!");
        player.sendMessage("§c» §7Выбери игровой режим для начала игры через стенды или");
        player.sendMessage("                     §c» §7игровое меню.");
        player.sendMessage("");

        // Добавить:
//        if (!player.hasPermission("hyneo.admin")) {
//             player.getGameMode().equals(GameMode.ADVENTURE);
//        }


//        player.getConfig
//        Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, (Runnable)new Runnable() {
//            @Override
//            public void run() {
//                 Player p = e.getPlayer();
//                if (p.hasPermission("lobby.fly")) {
//                    p.setAllowFlight(true);
//                    p.setFlying(true);
//                }
//                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20000000, 2));
//                 List<String> ls = (List<String>)EventListener.this.plugin.getConfig().getStringList("motd");
//                for (int i = 0; i < ls.size(); ++i) {
//                    p.sendMessage(ls.get(i).replace('&', '§'));
//                }
//                 Location loc = new Location(p.getWorld(), EventListener.this.plugin.getConfig().getDouble("spawn.x"), EventListener.this.plugin.getConfig().getDouble("spawn.y"), EventListener.this.plugin.getConfig().getDouble("spawn.z"));
//                loc.setPitch((float)EventListener.this.plugin.getConfig().getDouble("spawn.pitch"));
//                loc.setYaw((float)EventListener.this.plugin.getConfig().getDouble("spawn.yaw"));
//                p.teleport(loc);
//            }
//        }, 3L);
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
//            player.sendMessage("§f[§cHyNeoCraft§f] > ");
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
//        if (p.getLocation().getBlockY() < this.plugin.getConfig().getInt("height")) {
//             Location loc = new Location(p.getWorld(), this.plugin.getConfig().getDouble("spawn.x"), this.plugin.getConfig().getDouble("spawn.y"), this.plugin.getConfig().getDouble("spawn.z"));
//            loc.setPitch((float)this.plugin.getConfig().getDouble("spawn.pitch"));
//            loc.setYaw((float)this.plugin.getConfig().getDouble("spawn.yaw"));
//            p.teleport(loc);
//        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onArmorStand(PlayerArmorStandManipulateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void spawnMob(CreatureSpawnEvent event){
        event.setCancelled(true);
    }

    /*
    @EventHandler //(priority = EventPriority.MONITOR)
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (Main.build && e.getEntity().hasPermission("hyneo.admin")) {
            e.setCancelled(false);
        } else {
            e.setCancelled(true);
        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
             Block block = event.getClickedBlock();
             player.sendMessage("1");
            if (block.getType() == Material.WOOD_STAIRS
                    || block.getType() == Material.ACACIA_STAIRS
                    || block.getType() == Material.BIRCH_WOOD_STAIRS
                    || block.getType() == Material.BRICK_STAIRS
                    || block.getType() == Material.COBBLESTONE_STAIRS
                    || block.getType() == Material.DARK_OAK_STAIRS
                    || block.getType() == Material.JUNGLE_WOOD_STAIRS
                    || block.getType() == Material.NETHER_BRICK_STAIRS
                    || block.getType() == Material.QUARTZ_STAIRS
                    || block.getType() == Material.RED_SANDSTONE_STAIRS
                    || block.getType() == Material.SANDSTONE_STAIRS
                    || block.getType() == Material.SMOOTH_STAIRS
                    || block.getType() == Material.SPRUCE_WOOD_STAIRS
                    || block.getType() == Material.WOOD_STAIRS) {
                if (player.isInsideVehicle() || player.getVehicle() == null) {
                    player.sendMessage("2");
                    return;
                }
                player.sendMessage("3");
                Player p = player.getPlayer();
                Arrow arrow = (Arrow)p.getWorld().spawnEntity(block.getLocation().add(0.5, 0.0, 0.5), EntityType.ARROW);
                arrow.setPassenger((Entity)p);
            }
            else if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && player.getVehicle() != null && player.getVehicle() instanceof Arrow) {
                player.getVehicle().remove();
            }
        }
    }
    */
}
