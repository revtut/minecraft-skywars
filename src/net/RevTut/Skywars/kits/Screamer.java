package net.RevTut.Skywars.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by waxcoder on 18-11-2014.
 */
public class Screamer implements Listener {

    private static ItemStack ScreamerItem = new ItemStack(Material.FIREBALL, 1);{
        ItemMeta ScreamerItemMeta = ScreamerItem.getItemMeta();
        ScreamerItemMeta.setDisplayName("§3Scream!!");
        ScreamerItem.setItemMeta(ScreamerItemMeta);
    }

    /* Create projectile with particle effect helix */

    public void kitScreamer(Player p) {
        p.getInventory().addItem(ScreamerItem);
    }

    public void pullPlayer(Player player, ItemStack itemStack, Projectile projectile) { // MAKES USE OF PROJECTILE HIT EVENT
        if (itemStack == null)
            return;
        if (itemStack.getType() == null)
            return;
        if (itemStack.getType() != Material.FIREBALL)
            return;
        if (!itemStack.hasItemMeta())
            return;
        if (!itemStack.getItemMeta().hasDisplayName())
            return;
        if (!itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§3Scream!!"))
            return;


        // Remove item
        int amount = itemStack.getAmount();
        if(amount > 1)
            itemStack.setAmount(itemStack.getAmount() - 1);
        else
            player.getInventory().remove(itemStack);
    }
}
