package ClanFox;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class Head implements Listener{

    private ItemStack Item_Skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
    private SkullMeta Meta_Skull = (SkullMeta) Item_Skull.getItemMeta();
    private ArrayList<String> Lore_Skull = new ArrayList<>();

    private static Main plugin;

    public Head(final Main instance) {
        Head.plugin = instance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Initialize_Hotbar(e.getPlayer());
        e.getPlayer().getInventory().setItem(4, Item_Skull);
    }

    public void Initialize_Hotbar(Player p) {
        Meta_Skull.setOwner(p.getName());
        Lore_Skull.clear();
        Lore_Skull.add("Custom lore");
        Meta_Skull.setLore(Lore_Skull);
        Meta_Skull.setDisplayName("Голова игрока " + p.getName());
        Item_Skull.getItemMeta().addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        Item_Skull.setItemMeta(Meta_Skull);

    }
    @EventHandler
    public void blockPlace(PlayerInteractEvent e){
        if (e.hasItem()) {
            e.getPlayer().sendMessage(String.valueOf(e.getItem().getItemMeta().getItemFlags()));
        }

        if (e.getItem().getItemMeta().getItemFlags().contains(ItemFlag.HIDE_POTION_EFFECTS)){
            e.getPlayer().sendMessage("EEE&ĘëбоииииИ!!!");
        } else {
            e.getPlayer().sendMessage("Нихуя!");
        }
    }
}
