package ClanFox.nbt;


import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public final class nbt_1_12_R1 implements NBT {
    public nbt_1_12_R1() {
    }

    public final ItemStack setNBT(ItemStack i, String type, String data2) {
        net.minecraft.server.v1_12_R1.ItemStack stack = CraftItemStack.asNMSCopy(i);
        NBTTagCompound tag;
        (tag = new NBTTagCompound()).setString("clans", type);
        tag.setString("clansdata", data2);
        stack.setTag(tag);
        return CraftItemStack.asCraftMirror(stack);
    }

    public final String getNBT(ItemStack i, String s) {
        return CraftItemStack.asNMSCopy(i).getTag().getString(s);
    }

    public final boolean isNBT(ItemStack i, String s) {
        try {
            return CraftItemStack.asNMSCopy(i).getTag().getString(s) != "";
        } catch (Exception ex) {
            return false;
        }
    }

}
