package net.revtut.skywars.kits;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Healthy Kit.
 *
 * <P>Kit Healthy gives more hearths to the player.</P>
 *
 * @author João Silva
 * @version 1.0
 */
public class Healthy {
    /**
     * Leather Helmet
     */
    private final ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET, 1);

    {
        // Set helmet's color
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherHelmet.getItemMeta();
        leatherArmorMeta.setColor(Color.WHITE);
        leatherHelmet.setItemMeta(leatherArmorMeta);
    }

    /**
     * Leather ChestPlate
     */
    private final ItemStack leatherChestPlate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);

    {
        // Set helmet's color
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherChestPlate.getItemMeta();
        leatherArmorMeta.setColor(Color.WHITE);
        leatherChestPlate.setItemMeta(leatherArmorMeta);
    }

    /**
     * Leather Leggings
     */
    private final ItemStack leatherLeggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);

    {
        // Set helmet's color
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherLeggings.getItemMeta();
        leatherArmorMeta.setColor(Color.WHITE);
        leatherLeggings.setItemMeta(leatherArmorMeta);
    }

    /**
     * Leather Boots
     */
    private final ItemStack leatherBoots = new ItemStack(Material.LEATHER_BOOTS, 1);

    {
        // Set helmet's color
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherBoots.getItemMeta();
        leatherArmorMeta.setColor(Color.WHITE);
        leatherBoots.setItemMeta(leatherArmorMeta);
    }

    /**
     * Give kit healthy to a player
     *
     * @param p player to give the kit
     */
    public void kitHealthy(Player p) {
        p.getInventory().setHelmet(leatherHelmet);
        p.getInventory().setChestplate(leatherChestPlate);
        p.getInventory().setLeggings(leatherLeggings);
        p.getInventory().setBoots(leatherBoots);

        // Maximum health
        p.setMaxHealth(p.getMaxHealth() + (2*3));
        p.setHealth(p.getMaxHealth());
    }
}
