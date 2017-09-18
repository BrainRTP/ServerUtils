// 
// Decompiled by Procyon v0.5.30
// 

package hyneocraft;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class Holograms
{
    static void createHologram(final Player p, final String lab) {
        final ArmorStand as = (ArmorStand)p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
        as.setCustomName(lab);
        as.setCustomNameVisible(true);
        as.setVisible(false);
    }
}
