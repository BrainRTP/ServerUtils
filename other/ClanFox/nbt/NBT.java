package ClanFox.nbt;


import org.bukkit.inventory.ItemStack;

public interface NBT {
    ItemStack setNBT(ItemStack i, String type, String data);

    String getNBT(ItemStack i, String type);

    boolean isNBT(ItemStack i, String type);
}
