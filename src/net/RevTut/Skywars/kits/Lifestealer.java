package net.revtut.skywars.kits;

import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.managers.KitManager;
import net.revtut.skywars.player.PlayerDat;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Lifestealer Kit.
 *
 * <P>Kit Lifestealer will steal life from his opponent and increase his own life with 20% of the stolen.</P>
 *
 * @author João Silva
 * @version 1.0
 */
public class Lifestealer {

    /**
     * Percentage of life stealed
     */
    private final double lifeStealedPercent = 0.20;

    /**
     * Red Leather Helmet
     */
    private final ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET, 1);

    {
        // Set helmet's color
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherHelmet.getItemMeta();
        leatherArmorMeta.setColor(Color.RED);
        leatherHelmet.setItemMeta(leatherArmorMeta);
    }

    /**
     * Give kit lifestealer to a player
     *
     * @param p player to give the kit
     */
    public void kitLifestealer(Player p) {
        p.getInventory().setHelmet(leatherHelmet);
    }

    /**
     * Check how much life stealed from its opponent may be used for
     * its own regeneration
     *
     * @param playerDat player to check it can steal life
     * @param arena arena where player is currently playing
     * @param damage damage made by the stealer
     * @return life that may be stealed
     */
    public double stealLife(PlayerDat playerDat, Arena arena, double damage){
        KitManager kitManager = arena.getKitManager();
        if(!kitManager.playerKit.containsKey(playerDat.getUUID()))
            return 0;

        Kit kit = kitManager.playerKit.get(playerDat.getUUID());
        if(kit != Kit.LIFESTEALER)
            return 0;
        return (damage * lifeStealedPercent);
    }
}