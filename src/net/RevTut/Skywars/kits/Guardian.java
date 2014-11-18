package net.RevTut.Skywars.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Guardian Kit.
 *
 * <P>Kit guardian can be speedy for 2 times 10 seconds and have anti-fall damage level 1</P>
 *
 * @author WaxCoder
 * @version 1.0
 */

public class Guardian implements Listener {

    /**
     * guardianBoots with anti fall damage
     */
    private static ItemStack guardianBoots = new ItemStack(Material.IRON_BOOTS, 1);
    {
        ItemMeta guardianMeta = guardianBoots.getItemMeta();
        guardianMeta.setDisplayName("§3Guardian Boots");
        guardianBoots.setItemMeta(guardianMeta);
        guardianBoots.addEnchantment(Enchantment.PROTECTION_FALL, 1);
    }

    /**
     * Give kit guardian to a player
     *
     * @param p player to give the kit
     */
    public static void kitGuardian(Player p){
        p.getInventory().addItem(guardianBoots);
    }

    /**
     * Set a player speed for a duration
     *
     * @param player player to set speed
     * @param duration duration of speed
     * @param itemStack itemstack of the player
     * @return true if player was set invisible
     */

    public boolean setGuardian(Player player, ItemStack itemStack, int duration){
        // Add potion effect
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, 1));
        // Remove item
        itemStack.setAmount(itemStack.getAmount() - 1);
        return true;
    }

}
