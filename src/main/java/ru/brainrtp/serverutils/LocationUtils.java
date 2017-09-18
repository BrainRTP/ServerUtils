package ru.brainrtp.serverutils;

import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationUtils
{
    public static Location stringToLocation(final String line) {
        if (line == null) {
            return null;
        }
        final String[] loc = line.split(";");
        World world = Bukkit.getWorld(loc[0]);
        if (world == null) {
            world = Bukkit.createWorld(new WorldCreator(loc[0]).generator("EmptyGenerator"));
        }
        final Location location = new Location(world, Double.parseDouble(loc[1]), Double.parseDouble(loc[2]), Double.parseDouble(loc[3]));
        if (loc.length > 4) {
            location.setPitch(Float.parseFloat(loc[4]));
            location.setYaw(Float.parseFloat(loc[5]));
        }
        return location;
    }

    public static String locationToString(final Location loc, final boolean pitch) {
        String s = loc.getWorld().getName();
        s = s + ";" + loc.getX();
        s = s + ";" + loc.getY();
        s = s + ";" + loc.getZ();
        if (pitch) {
            s = s + ";" + loc.getPitch();
            s = s + ";" + loc.getYaw();
        }
        return s;
    }
}
