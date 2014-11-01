package net.RevTut.Skywars.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

public class Tatical implements Listener {

    ItemStack enderTransform = new ItemStack(Material.ENDER_PEARL, 2);

    {
        ItemMeta enderMeta = enderTransform.getItemMeta();
        enderMeta.setDisplayName("§3Tatical Phantom");
        enderTransform.setItemMeta(enderMeta);
    }

    ItemStack ironArmor1 = new ItemStack(Material.IRON_HELMET, 1);

    {

    }

    ItemStack ironArmor2 = new ItemStack(Material.IRON_CHESTPLATE, 1);

    {

    }

    ItemStack ironArmor3 = new ItemStack(Material.IRON_LEGGINGS, 1);

    {
    }

    ItemStack ironArmor4 = new ItemStack(Material.IRON_BOOTS, 1);

    {

    }

    public void kitTatical(Player p) {
        p.getInventory().addItem(enderTransform);
        p.getInventory().setHelmet(ironArmor1);
        p.getInventory().setChestplate(ironArmor2);
        p.getInventory().setLeggings(ironArmor3);
        p.getInventory().setBoots(ironArmor4);

    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if ((p.getItemInHand().getType() == Material.ENDER_PEARL) && ((e.getAction() == Action.RIGHT_CLICK_AIR)) || ((e.getAction() == Action.RIGHT_CLICK_BLOCK))) {
            if (p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§3Tatical Phantom")) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY,400,1));
            }
        }
    }

}
