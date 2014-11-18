package net.RevTut.Skywars.kits;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

/**
 * Ninja Kit.
 *
 * <P>Kit Ninja with fishing rod which may be used to climb walls and pulls him over.</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class Ninja {

    /**
     * Fishing Rod Hooker
     */
    private static ItemStack hook = new ItemStack(Material.FISHING_ROD, 2);

    {
        ItemMeta hookMeta = hook.getItemMeta();
        hook.setDurability((short) 2); // Two uses only
        hookMeta.setDisplayName("§3Ninja Rod");
        hook.setItemMeta(hookMeta);
    }

    /**
     * Leather Helmet
     */
    private static ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET, 1);

    {
        // Set helmet's color
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherHelmet.getItemMeta();
        leatherArmorMeta.setColor(Color.RED);
        leatherHelmet.setItemMeta(leatherArmorMeta);
    }

    /**
     * Leather Boots
     */
    private static ItemStack leatherBoots = new ItemStack(Material.LEATHER_BOOTS, 1);

    {
        leatherBoots.addEnchantment(Enchantment.PROTECTION_FALL, 2);
        // Set boot's color
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherBoots.getItemMeta();
        leatherArmorMeta.setColor(Color.BLACK);
        leatherBoots.setItemMeta(leatherArmorMeta);
    }

    /**
     * Give kit ninja to a player
     *
     * @param p player to give the kit
     */
    public static void kitNinja(Player p) {
        p.getInventory().addItem(hook);
        p.getInventory().setHelmet(leatherHelmet);
        p.getInventory().setBoots(leatherBoots);
    }

    /**
     * Throw a player in the direction of a projectile
     *
     * @param player     player to throw
     * @param itemStack  item stack used by the player
     * @param projectile projectile thrown by the player
     */

    public void throwPlayer(Player player, ItemStack itemStack, Projectile projectile) { // MAKES USE OF PROJECTILE HIT EVENT
        if (itemStack == null)
            return;
        if (itemStack.getType() == null)
            return;
        if (itemStack.getType() != Material.FISHING_ROD)
            return;
        if (!itemStack.hasItemMeta())
            return;
        if (!itemStack.getItemMeta().hasDisplayName())
            return;
        if (!itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§3Ninja Rod"))
            return;
        // Locations needed
        Location A = player.getLocation(); // Location of the player
        Location B = projectile.getLocation(); // Location of the projectile
        // Direction to throw player
        Vector dir = B.toVector().subtract(A.toVector().normalize());
        player.setVelocity(dir.multiply(2));
        // Remove projectile
        projectile.remove();
    }
}
