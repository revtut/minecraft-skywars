package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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
public class Engineer implements Listener {

    /**
     * Main Class
     */
    private final Main plugin;

    /**
     * Constructor of Kit Manager
     *
     * @param plugin main class
     */
    public Engineer(Main plugin) {
        this.plugin = plugin;
    }

    /** List with all the mines in the arena */
    private final List<Location> landMinesList = new ArrayList<Location>();

    /** Land Mine */
    private final ItemStack mine = new ItemStack(Material.IRON_BLOCK, 2);

    {
        ItemMeta mineMeta = mine.getItemMeta();
        mineMeta.setDisplayName("§3Land Mine");
        mine.setItemMeta(mineMeta);
    }

    /** Iron Helmet */
    private final ItemStack ironHelmet = new ItemStack(Material.IRON_HELMET, 1);

    {
        ironHelmet.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 2);
    }

    /** Iron ChestPlate */
    private final ItemStack ironChestPlate = new ItemStack(Material.IRON_CHESTPLATE, 1);

    {
        ironChestPlate.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
    }

    /** Iron Leggings */
    private final ItemStack ironLeggings = new ItemStack(Material.IRON_LEGGINGS, 1);

    {
        ironLeggings.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
    }

    /** Iron Boots */
    private final ItemStack ironBoots = new ItemStack(Material.IRON_BOOTS, 1);

    {
        ironBoots.addEnchantment(Enchantment.PROTECTION_FALL, 2);
    }

    /**
     * Give kit engineer to a player
     *
     * @param p player to give the kit
     */
    public void kitEngineer(Player p) {
        p.getInventory().addItem(mine);
        p.getInventory().setHelmet(ironHelmet);
        p.getInventory().setChestplate(ironChestPlate);
        p.getInventory().setLeggings(ironLeggings);
        p.getInventory().setBoots(ironBoots);

    }

    /**
     * Check if a player stepped in a land mine
     *
     * @param action action of the player
     * @param block block interacted with
     */
    public void landMineActivate(Action action, Block block) {
        if (!action.equals(Action.PHYSICAL))
            return;
        if (!block.getType().equals(Material.IRON_BLOCK))
            return;
        if (!landMinesList.contains(block.getLocation()))
            return;
        landMinesList.remove(block.getLocation());
        block.setType(Material.AIR);
        // Create Explosion
        block.getWorld().createExplosion(block.getLocation(), 2, false);
    }

    /**
     * Check if a player is placing a land mine. If so tell him he placed one and add the location
     * to the lands mine list
     *
     * @param player player who placed the block
     * @param itemStack item stack placed by the player
     * @param location location of the placed block
     */
    public void mineLandPlace(Player player, ItemStack itemStack, Location location) {
        if (itemStack.getType() != Material.IRON_BLOCK)
            return;
        if(!itemStack.hasItemMeta())
            return;
        if(!itemStack.getItemMeta().hasDisplayName())
            return;
        if(!itemStack.getItemMeta().getDisplayName().equalsIgnoreCase("§3Land Mine"))
            return;
        // Add to list of placed mines
        landMinesList.add(location);
        // Send message
        player.sendMessage("§7|" + "§3Sky Wars" + "§7| §6Crias te uma mina!");
    }
}