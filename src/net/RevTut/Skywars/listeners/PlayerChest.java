package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaStatus;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
public class PlayerChest implements Listener{

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
        generateItemStacks();
    }

    /** List with all items available */
    private List<ItemStack> itemStacks = new ArrayList<ItemStack>();

    /** List with already filled chests */
    private List<Location> locChests = new ArrayList<Location>();

    /**
     * Takes care of what to do when a player interacts with a chest
     *
     * @param e     player interact event
     * @see         PlayerInteractEvent
     */
    @EventHandler
    public void onChestInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        // Player Dat
        PlayerDat playerDat = PlayerDat.getPlayerDatByUUID(player.getUniqueId());
        if(playerDat == null)
            return;
        // Arena
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if(null == arena)
            return;
        if(arena.getStatus() != ArenaStatus.INGAME)
            return;
        // Check if block is a Chest
        if(e.getClickedBlock() == null)
            return;
        if(e.getClickedBlock().getType() != Material.CHEST)
            return;
        if(locChests.contains(e.getClickedBlock().getLocation()))
            return;
        // Chest
        Chest chest = (Chest) e.getClickedBlock().getState();
        Inventory chestInv = chest.getInventory();
        // Fill chest
        int numItems = plugin.rand.nextInt(10) + 15;
        for(int i = 0; i < numItems; i++){
            int posItem = plugin.rand.nextInt(itemStacks.size());
            chestInv.addItem(itemStacks.get(posItem));
        }
        // Add to already filled chests
        locChests.add(chest.getLocation());
    }

    /**
     * Takes care of what to do when a chest is placed
     *
     * @param e     block place event
     * @see         BlockPlaceEvent
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
        if(e.getBlockPlaced().getType() == Material.CHEST)
            locChests.add(e.getBlockPlaced().getLocation());
    }

    /** Generates all the ItemStacks that might be in a Chest */
    private void generateItemStacks(){
        Material[] materialsArmor = { Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.LEATHER_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.LEATHER_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.DIAMOND_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.LEATHER_BOOTS};
        Material[] materialsWeapon = { Material.BOW, Material.ARROW, Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD, Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.STONE_AXE, Material.WOOD_AXE, Material.DIAMOND_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.STONE_PICKAXE, Material.WOOD_PICKAXE, Material.DIAMOND_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.STONE_SPADE, Material.WOOD_SPADE};
        Material[] materialBuckets = { Material.WATER_BUCKET, Material.LAVA_BUCKET, Material.BUCKET, Material.MILK_BUCKET};
        Material[] materialFood = { Material.APPLE, Material.BREAD, Material.MUSHROOM_SOUP, Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.COOKED_FISH, Material.WHEAT, Material.GOLDEN_APPLE, Material.CARROT, Material.BAKED_POTATO };
        Material[] materialBlock = { Material.WOOD, Material.LOG, Material.STONE, Material.COBBLESTONE };
        Material[] materialOther = { Material.ENDER_PEARL, Material.TORCH, Material.STICK, Material.FURNACE, Material.CHEST, Material.WORKBENCH };
        Enchantment[] enchantsArmor = {Enchantment.DURABILITY, Enchantment.OXYGEN, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_FALL, Enchantment.PROTECTION_FIRE, Enchantment.PROTECTION_PROJECTILE };
        Enchantment[] enchantWeapon = { Enchantment.DURABILITY, Enchantment.ARROW_KNOCKBACK, Enchantment.ARROW_DAMAGE, Enchantment.ARROW_FIRE, Enchantment.DAMAGE_ALL, Enchantment.DAMAGE_ARTHROPODS, Enchantment.DAMAGE_UNDEAD, Enchantment.DIG_SPEED, Enchantment.FIRE_ASPECT, Enchantment.KNOCKBACK, Enchantment.THORNS };

        // Armor
        for(int i = 0; i < 15; i++){
            int pos = plugin.rand.nextInt(materialsArmor.length);
            int quantidade = 1;
            // ItemStack
            ItemStack itemStack = new ItemStack(materialsArmor[pos], quantidade);
            // Enchantment
            if(plugin.rand.nextFloat() < 0.70){
                pos = plugin.rand.nextInt(enchantsArmor.length);
                Enchantment enchant = enchantsArmor[pos]; // Enchantment
                if(enchant.canEnchantItem(itemStack)) {
                    quantidade = plugin.rand.nextInt(2) + 1; // Enchantment level
                    itemStack.addUnsafeEnchantment(enchant, quantidade);
                }
            }
            // Add to list
            itemStacks.add(itemStack);
        }

        // Weapon
        for(int i = 0; i < 25; i++){
            int pos = plugin.rand.nextInt(materialsWeapon.length);
            int quantidade = 1;
            // ItemStack
            ItemStack itemStack = new ItemStack(materialsWeapon[pos], quantidade);
            // Enchantment
            if(plugin.rand.nextFloat() < 0.70){
                pos = plugin.rand.nextInt(enchantWeapon.length);
                Enchantment enchant = enchantWeapon[pos]; // Enchantment
                if(enchant.canEnchantItem(itemStack)) {
                    quantidade = plugin.rand.nextInt(2) + 1; // Enchantment level
                    itemStack.addUnsafeEnchantment(enchant, quantidade);
                }
            }
            // Add to list
            itemStacks.add(itemStack);
        }

        // Bucket
        for(int i = 0; i < 20; i++){
            int pos = plugin.rand.nextInt(materialBuckets.length);
            int quantidade = plugin.rand.nextInt(5) + 1;
            // ItemStack
            ItemStack itemStack = new ItemStack(materialBuckets[pos], quantidade);
            // Add to list
            itemStacks.add(itemStack);
        }

        // Food
        for(int i = 0; i < 10; i++){
            int pos = plugin.rand.nextInt(materialFood.length);
            int quantidade = plugin.rand.nextInt(5) + 1;
            // ItemStack
            ItemStack itemStack = new ItemStack(materialFood[pos], quantidade);
            // Add to list
            itemStacks.add(itemStack);
        }

        // Block
        for(int i = 0; i < 10; i++){
            int pos = plugin.rand.nextInt(materialBlock.length);
            int quantidade = plugin.rand.nextInt(15) + 1;
            // ItemStack
            ItemStack itemStack = new ItemStack(materialBlock[pos], quantidade);
            // Add to list
            itemStacks.add(itemStack);
        }

        // Other
        for(int i = 0; i < 5; i++){
            int pos = plugin.rand.nextInt(materialOther.length);
            int quantidade = plugin.rand.nextInt(15) + 1;
            // ItemStack
            ItemStack itemStack = new ItemStack(materialOther[pos], quantidade);
            // Add to list
            itemStacks.add(itemStack);
        }
    }
}
