package net.RevTut.Skywars.kits;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Guardian Kit.
 *
 * <P>Kit Guardian can increase speed two times for seconds and have anti-fall damage</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class Guardian {

    /**
     * Rage mode activator
     */
    private ItemStack rageTear = new ItemStack(Material.GHAST_TEAR, 2);
    {
        ItemMeta enderPearlMeta = rageTear.getItemMeta();
        enderPearlMeta.setDisplayName("§3Guardian Rage");
        rageTear.setItemMeta(enderPearlMeta);
    }

    /**
     * Iron Boots
     */
    private ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS, 1);
    {
        ironBoots.addEnchantment(Enchantment.PROTECTION_FALL, 1);
    }

    /**
     * Give kit guardian to a player
     *
     * @param p player to give the kit
     */
    public void kitGuardian(Player p){
        p.getInventory().addItem(rageTear);
        p.getInventory().setBoots(ironBoots);
    }

    /**
     * Set a player speed for a duration
     *
     * @param player player to set speed
     * @param action interact action
     * @param duration speed duration
     * @param itemStack itemstack of the player
     * @return true if player was set invisible
     */
    public boolean setSpeed(Player player, Action action, ItemStack itemStack, int duration){
        // Check action type
        if(action != Action.RIGHT_CLICK_AIR)
            if(action != Action.RIGHT_CLICK_BLOCK)
                return false;
        // Type of item
        if(itemStack.getType() != Material.GHAST_TEAR)
            return false;
        if (!itemStack.hasItemMeta())
            return false;
        if (!itemStack.getItemMeta().hasDisplayName())
            return false;
        if (!itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§3Guardian Rage"))
            return false;
        // Add potion effect
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * duration, 2));
        // Remove item
        int amount = itemStack.getAmount();
        if(amount > 1)
            itemStack.setAmount(itemStack.getAmount() - 1);
        else
            player.getInventory().remove(itemStack);
        // Send message
        player.sendMessage("§7|" + "§3Sky Wars" + "§7| §6Ativaste o modo rage por " + duration + " segundos!");
        return true;
    }

}
