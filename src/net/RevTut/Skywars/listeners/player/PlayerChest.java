package net.RevTut.Skywars.listeners.player;

import net.RevTut.Skywars.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Player Interact With Chest.
 *
 * <P>Controls the interaction with chests and randomly fill the chests.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerChest implements Listener {

    /**
     * Main class
     */
    private final Main plugin;

    /**
     * Constructor of PlayerChest
     *
     * @param plugin main class
     */
    public PlayerChest(final Main plugin) {
        this.plugin = plugin;
        // Generate the random loot every 5 minutes
        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                generateItemStacks();
            }
        }, 0, 6000);
    }

    /**
     * List with all items available
     */
    private final List<ItemStack> itemStacks = new ArrayList<ItemStack>();

    /**
     * List with already filled chests
     */
    private static final List<Location> locChests = new ArrayList<Location>();

    /**
     * Takes care of what to do when a player interacts with a chest
     *
     * @param block block interacted with
     */
    public void onChestInteract(Block block) { // MAKES USE OF PLAYER INTERACT EVENT
        // Check if block is a Chest
        if (block == null)
            return;
        if (block.getType() != Material.CHEST)
            return;
        if (locChests.contains(block.getLocation()))
            return;
        // Chest
        Chest chest = (Chest) block.getState();
        Inventory chestInv = chest.getInventory();
        // Fill chest
        int numItems = plugin.rand.nextInt(10) + 15;
        for (int i = 0; i < numItems; i++) {
            int posItem = plugin.rand.nextInt(itemStacks.size());
            chestInv.addItem(itemStacks.get(posItem));
        }
        // Add to already filled chests
        locChests.add(chest.getLocation());
    }

    /**
     * Takes care of what to do when a chest is placed
     *
     * @param block placed block
     */
    public void onChestPlace(Block block) { // MAKES USE OF BLOCK PLACE EVENT
        if (block.getType() == Material.CHEST)
            locChests.add(block.getLocation());
    }

    /**
     * Generates all the ItemStacks that might be in a Chest
     */
    private void generateItemStacks() {
        Material[] materialsArmor = {Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.LEATHER_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.LEATHER_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.DIAMOND_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.LEATHER_BOOTS};
        Material[] materialsWeapon = {Material.BOW, Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD, Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.DIAMOND_PICKAXE, Material.IRON_PICKAXE, Material.FISHING_ROD};
        Material[] materialBuckets = {Material.WATER_BUCKET, Material.LAVA_BUCKET};
        Material[] materialFood = {Material.APPLE, Material.BREAD, Material.MUSHROOM_SOUP, Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.COOKED_FISH, Material.WHEAT, Material.GOLDEN_APPLE, Material.CARROT_ITEM, Material.BAKED_POTATO};
        Material[] materialBlock = {Material.WOOD, Material.LOG, Material.STONE, Material.COBBLESTONE};
        Material[] materialOther = {Material.EGG, Material.SNOW_BALL, Material.ARROW};
        Enchantment[] enchantsArmor = {Enchantment.OXYGEN, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_FALL, Enchantment.PROTECTION_FIRE, Enchantment.PROTECTION_PROJECTILE};
        Enchantment[] enchantWeapon = {Enchantment.DURABILITY, Enchantment.DAMAGE_ALL, Enchantment.FIRE_ASPECT, Enchantment.KNOCKBACK};
        Enchantment[] enchantOther = {Enchantment.ARROW_KNOCKBACK, Enchantment.ARROW_DAMAGE, Enchantment.ARROW_FIRE};

        // Armor
        for (int i = 0; i < 15; i++) {
            int pos = plugin.rand.nextInt(materialsArmor.length);
            int quantidade = 1;
            // ItemStack
            ItemStack itemStack = new ItemStack(materialsArmor[pos], quantidade);
            // Enchantment
            if (plugin.rand.nextFloat() < 0.80) {
                pos = plugin.rand.nextInt(enchantsArmor.length);
                Enchantment enchant = enchantsArmor[pos]; // Enchantment
                if (enchant.canEnchantItem(itemStack)) {
                    quantidade = plugin.rand.nextInt(2) + 1; // Enchantment level
                    itemStack.addUnsafeEnchantment(enchant, quantidade);
                }
            }
            // Add to list
            itemStacks.add(itemStack);
        }

        // Weapon
        for (int i = 0; i < 25; i++) {
            int pos = plugin.rand.nextInt(materialsWeapon.length);
            int quantidade = 1;
            // ItemStack
            ItemStack itemStack = new ItemStack(materialsWeapon[pos], quantidade);
            // Enchantment
            if (plugin.rand.nextFloat() < 70) {
                pos = plugin.rand.nextInt(enchantWeapon.length);
                Enchantment enchant = enchantWeapon[pos]; // Enchantment
                if (enchant.canEnchantItem(itemStack)) {
                    quantidade = plugin.rand.nextInt(2) + 1; // Enchantment level
                    itemStack.addUnsafeEnchantment(enchant, quantidade);
                }
            }
            // Add to list
            itemStacks.add(itemStack);
        }

        // Bucket
        for (int i = 0; i < 15; i++) {
            int pos = plugin.rand.nextInt(materialBuckets.length);
            int quantidade = 1;
            // ItemStack
            ItemStack itemStack = new ItemStack(materialBuckets[pos], quantidade);
            // Add to list
            itemStacks.add(itemStack);
        }

        // Food
        for (int i = 0; i < 10; i++) {
            int pos = plugin.rand.nextInt(materialFood.length);
            int quantidade = plugin.rand.nextInt(5) + 1;
            // ItemStack
            ItemStack itemStack = new ItemStack(materialFood[pos], quantidade);
            // Add to list
            itemStacks.add(itemStack);
        }

        // Block
        for (int i = 0; i < 15; i++) {
            int pos = plugin.rand.nextInt(materialBlock.length);
            int quantidade = plugin.rand.nextInt(15) + 1;
            // ItemStack
            ItemStack itemStack = new ItemStack(materialBlock[pos], quantidade);
            // Add to list
            itemStacks.add(itemStack);
        }

        // Other
        for (int i = 0; i < 20; i++) {
            int pos = plugin.rand.nextInt(materialOther.length);
            int quantidade = plugin.rand.nextInt(15) + 1;
            // ItemStack
            ItemStack itemStack = new ItemStack(materialOther[pos], quantidade);
            // Enchantment
            if (plugin.rand.nextFloat() < 0.80) {
                pos = plugin.rand.nextInt(enchantOther.length);
                Enchantment enchant = enchantWeapon[pos]; // Enchantment
                if (enchant.canEnchantItem(itemStack)) {
                    quantidade = plugin.rand.nextInt(2) + 1; // Enchantment level
                    itemStack.addUnsafeEnchantment(enchant, quantidade);
                }
            }
            // Add to list
            itemStacks.add(itemStack);
        }
    }

    /**
     * Removes the locations of already opened chests from the list
     *
     * @param world world of the locations to be removed
     */
    public void clearLocationsFromWorld(World world){
        List<Location> chestsLocations = new ArrayList<Location>(locChests);
        for(Location loc : chestsLocations)
            if(loc.getWorld().equals(world))
                locChests.remove(loc);
    }
}