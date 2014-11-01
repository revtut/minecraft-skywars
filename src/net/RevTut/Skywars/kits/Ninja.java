package net.RevTut.Skywars.kits;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

public class Ninja implements Listener {

    ItemStack hook = new ItemStack(Material.FISHING_ROD, 2);

    {
        ItemMeta hookMeta = hook.getItemMeta();
        hook.setDurability((short) 2);
        hookMeta.setDisplayName("§3Ninja Rod");
        hook.setItemMeta(hookMeta);
    }

    ItemStack ironArmor1 = new ItemStack(Material.LEATHER_HELMET, 1);

    {

    }

    ItemStack ironArmor2 = new ItemStack(Material.LEATHER_CHESTPLATE, 1);

    {

    }

    ItemStack ironArmor3 = new ItemStack(Material.LEATHER_LEGGINGS, 1);

    {
    }

    ItemStack ironArmor4 = new ItemStack(Material.LEATHER_BOOTS, 1);

    {
        ironArmor4.addEnchantment(Enchantment.PROTECTION_FALL, 2);
    }

    public void kitNinja(Player p) {
        p.getInventory().addItem(hook);
        p.getInventory().setHelmet(ironArmor1);
        p.getInventory().setChestplate(ironArmor2);
        p.getInventory().setLeggings(ironArmor3);
        p.getInventory().setBoots(ironArmor4);

    }

    @EventHandler
    public void setHook(ProjectileHitEvent ev) {
        Entity e = ev.getEntity();
        Player p = (Player) ev.getEntity().getShooter();
        Location A = p.getLocation();
        Location B = e.getLocation();
        Vector dir = B.toVector().subtract(A.toVector().normalize());
        p.setVelocity(dir.multiply(2));
        e.remove();
    }
}
