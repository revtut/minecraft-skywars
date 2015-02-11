package net.revtut.skywars.kits;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Teleporter Kit.
 *
 * <P>Kit Teleporter may use its enderpearls to teleport between islands.</P>
 *
 * @author João Silva
 * @version 1.0
 */
public class Teleporter {

    /**
     * Teleporter Ender Pearls
     */
    private final ItemStack teleporterEnder = new ItemStack(Material.ENDER_PEARL, 5);
    {
        ItemMeta enderPearlMeta = teleporterEnder.getItemMeta();
        enderPearlMeta.setDisplayName("§3Teleporter Pearls");
        teleporterEnder.setItemMeta(enderPearlMeta);
    }

    /**
     * Leather Helmet
     */
    private final ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET, 1);

    {
        // Set helmet's color
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherHelmet.getItemMeta();
        leatherArmorMeta.setColor(Color.MAROON);
        leatherHelmet.setItemMeta(leatherArmorMeta);
    }

    /**
     * Give kit teleporter to a player
     *
     * @param p player to give the kit
     */
    public void kitTeleporter(Player p) {
        p.getInventory().addItem(teleporterEnder);
        p.getInventory().setHelmet(leatherHelmet);

    }
}
