package net.RevTut.Skywars.kits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Engineer Kit.
 *
 * <P>Kit Engineer with land mines. Will explode when a player step on them.</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class Engineer {

    public boolean force_dead;

    /**
     * List with all the mines in the arena
     */
    private List<Location> landMinesList = new ArrayList<Location>();

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

    //Teste Handler Mine
    @EventHandler
    public void MOVE_landmine(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
            Location loc = event.getPlayer().getLocation();
            Block cover = loc.getBlock();
            loc.setY(loc.getY() - 1.0D);
            if ((cover.getType().equals(Material.IRON_BLOCK)))
            {
                loc.getBlock().setType(Material.AIR);
                if (this.force_dead) {
                    event.getPlayer().setHealth(1.0D);
                }
                Bukkit.broadcastMessage(player.getName() + " was killed by a mine!");
                loc.getWorld().createExplosion(player.getLocation(),0.20F, true);
            }
        }


    /**
     * Check if a player stepped in a land mine
     *
     * @param action action of the player
     * @param block  block interacted with
     */

   /* public void landMineActivate(Action action, Block block) { // MAKES USE OF PLAYER INTERACT EVENT
        if (!action.equals(Action.PHYSICAL))
            return;
        if (block == null)
            return;
        if (block.getType() == null)
            return;
        if (!block.getType().equals(Material.IRON_BLOCK))
            return;
        if (!landMinesList.contains(block.getLocation()))
            return;
        landMinesList.remove(block.getLocation());
        block.setType(Material.AIR);
        // Create Explosion
        block.getWorld().createExplosion(block.getLocation(), 2, false);
    }/*

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
        landMinesList.add(location);
        // Send message
        player.sendMessage("§7|" + "§3Sky Wars" + "§7| §6Crias te uma mina!");
    }
}