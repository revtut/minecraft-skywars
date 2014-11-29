package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.listeners.player.PlayerDamage;
import net.RevTut.Skywars.utils.Message;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Engineer Kit.
 *
 * <P>Kit Engineer with land mines. Will explode when a player step on them.</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class Engineer{

    public boolean force_dead = false;

    /**
     * List with all the mines in the arena
     */
    private Map<Location, UUID> landMinesList = new HashMap<Location, UUID>();

    /**
     * Land Mine
     */
    private ItemStack mine = new ItemStack(Material.IRON_BLOCK, 2);

    {
        ItemMeta mineMeta = mine.getItemMeta();
        mineMeta.setDisplayName("§3Land Mine");
        mine.setItemMeta(mineMeta);
    }

    /**
     * Iron ChestPlate
     */
    private ItemStack ironChestPlate = new ItemStack(Material.IRON_CHESTPLATE, 1);

    {
        ironChestPlate.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
    }

    /**
     * Give kit engineer to a player
     *
     * @param p player to give the kit
     */
    public void kitEngineer(Player p) {
        p.getInventory().addItem(mine);
        p.getInventory().setChestplate(ironChestPlate);
    }

    /**
     * Check if a player stepped in a land mine
     *
     * @param player to check if stepped a mine
     */

    public void landMineActivate(Player player) { // MAKES USE OF PLAYER MOVE EVENT
        Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
        if (block == null)
            return;
        if (block.getType() == null)
            return;
        if (!block.getType().equals(Material.IRON_BLOCK))
            return;
        if (!landMinesList.containsKey(block.getLocation()))
            return;
        landMinesList.remove(block.getLocation());
        // Add to last damager map
        PlayerDamage.lastPlayerDamager.put(player.getUniqueId(), landMinesList.get(block.getLocation()));
        // Check if player must die
        if(force_dead)
            player.setHealth(1.0);
        // Create Explosion
        block.setType(Material.AIR);
        block.getWorld().createExplosion(block.getLocation(), 5, false);
    }

    /**
     * Check if a player is placing a land mine. If so tell him he placed one and add the location
     * to the lands mine list
     *
     * @param player    player who placed the block
     * @param itemStack item stack placed by the player
     * @param location  location of the placed block
     */
    public void mineLandPlace(Player player, ItemStack itemStack, Location location) { // MAKES USE OF BLOCK PLACE EVENT
        if (itemStack == null)
            return;
        if (itemStack.getType() == null)
            return;
        if (itemStack.getType() != Material.IRON_BLOCK)
            return;
        if (!itemStack.hasItemMeta())
            return;
        if (!itemStack.getItemMeta().hasDisplayName())
            return;
        if (!itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§3Land Mine"))
            return;
        // Add to list of placed mines
        landMinesList.put(location, player.getUniqueId());
        // Send message
        player.sendMessage(Message.getMessage(Message.ENGINEER_PLACED_MINE, player));
    }
}