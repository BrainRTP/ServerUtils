package ru.brainrtp.serverutils;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;


class fw {
    private static Random random = new Random();

    static void fw1(Player player, int repeat, int cooldown, int height, int amount) {
        new BukkitRunnable() {
            int countIteration = 0;

            @Override
            public void run() {
                for (int i = 0; i < amount; i++) {
                    if (!player.isOnline()) break;
                    Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
                    FireworkMeta fwm = fw.getFireworkMeta();
                    int rt = random.nextInt(5);
                    FireworkEffect.Type type = FireworkEffect.Type.values()[rt];
                    Color c1 = Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                    Color c2 = Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
                    FireworkEffect effect = FireworkEffect.builder().flicker(random.nextBoolean())
                            .withColor(c1).withFade(c2).with(type)
                            .trail(random.nextBoolean()).build();
                    fwm.addEffect(effect);
                    fwm.setPower(height);
                    fw.setFireworkMeta(fwm);
                }
                countIteration++;
                if (countIteration == repeat)
                    this.cancel();
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0, cooldown*20);
    }
}