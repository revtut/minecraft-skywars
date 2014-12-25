package net.revtut.skywars.kits;

import net.revtut.skywars.utils.Message;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Tactical Kit.
 *
 * <P>Kit Tactical may become invisible two times for ten secons.</P>
 *
 * @author João Silva
 * @version 1.0
 */
public class Tatical {

    /**
     * Invisible Ender Pearls
     */
    private final ItemStack invisibleEnder = new ItemStack(Material.ENDER_PEARL, 2);
    {
        ItemMeta enderPearlMeta = invisibleEnder.getItemMeta();
        enderPearlMeta.setDisplayName("§3Tatical Phantom");
        invisibleEnder.setItemMeta(enderPearlMeta);
    }

    /**
     * Leather ChestPlate
     */
    private final ItemStack leatherChestPlate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);

    {
        // Set chesplate's color
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherChestPlate.getItemMeta();
        leatherArmorMeta.setColor(Color.GREEN);
        leatherChestPlate.setItemMeta(leatherArmorMeta);
    }

    /**
     * Give kit tatical to a player
     *
     * @param p player to give the kit
     */
    public void kitTatical(Player p) {
        p.getInventory().addItem(invisibleEnder);
        p.getInventory().setChestplate(leatherChestPlate);

    }

    /**
     * Set a player invisible for a duration
     *
     * @param player player to set invisible
     * @param action interact action
     * @param itemStack itemstack of the player
     * @param duration duration of invisibility
     * @return true if player was set invisible
     */

    public boolean setInvisible(Player player, Action action, ItemStack itemStack, int duration) { // MAKES USE OF PLAYER INTERACT EVENT
        // Check action type
        if(action != Action.RIGHT_CLICK_AIR)
            if(action != Action.RIGHT_CLICK_BLOCK)
                return false;
        // Type of item
        if(itemStack.getType() != Material.ENDER_PEARL)
            return false;
        if (!itemStack.hasItemMeta())
            return false;
        if (!itemStack.getItemMeta().hasDisplayName())
            return false;
        if (!itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§3Tatical Phantom"))
            return false;
        // Add potion effect
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * duration, 1));
        // Remove item
        int amount = itemStack.getAmount();
        if(amount > 1)
            itemStack.setAmount(itemStack.getAmount() - 1);
        else
            player.getInventory().remove(itemStack);
        // Send message
        player.sendMessage(Message.getMessage(Message.TACTICAL_ENABLED_INVISIBILITY, player) + duration + "s!");
        return true;
    }
}
