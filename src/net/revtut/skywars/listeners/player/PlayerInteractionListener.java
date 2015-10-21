package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.events.player.PlayerInteractionEvent;
import net.revtut.libraries.minecraft.games.player.GamePlayer;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.skywars.SkyWars;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Player Interaction Listener
 */
public class PlayerInteractionListener implements Listener {

    /**
     * List with all chests that were already randomized
     */
    private final List<Location> openedChests;

    /**
     * List with all items available to chests
     */
    private final List<ItemStack> itemStacks;

    /**
     * Random class
     */
    private final Random random;

    /**
     * Constructor of PlayerInteractionListener
     */
    public PlayerInteractionListener() {
        this.openedChests = new ArrayList<>();
        this.itemStacks = new ArrayList<>();
        this.random = new Random();
    }

    /**
     * Controls the player interaction event
     * @param event player interaction event
     */
    @EventHandler
    public void onInteraction(final PlayerInteractionEvent event) {
        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        final GameController gameController = SkyWars.getInstance().getGameController();
        if(gameController == null || !gameController.hasArena(arena))
            return;

        final GamePlayer player = event.getPlayer();

        // Block non live players
        if(player.getState() != PlayerState.ALIVE) {
            event.setCancelled(true);
        }

        // Randomize chest
        if(!(event.getClickedBlock().getState() instanceof Chest))
            return;
        final Block block = event.getClickedBlock();
        if (openedChests.contains(block.getLocation()))
            return;
        // Chest
        final Chest chest = (Chest) block.getState();
        randomizeChest(chest);

        openedChests.add(chest.getLocation());
    }

    private void randomizeChest(final Chest chest) {
        final int numItems = random.nextInt(10) + 15;
        for (int i = 0; i < numItems; i++) {
            final int posItem = random.nextInt(itemStacks.size());
            chest.getInventory().addItem(itemStacks.get(posItem));
        }
    }

    /**
     * Generates all the ItemStacks that might be in a Chest
     */
    private void generateItemStacks() {

        final Material[] materialsArmor = {Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.LEATHER_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.LEATHER_LEGGINGS, Material.CHAINMAIL_BOOTS, Material.DIAMOND_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.LEATHER_BOOTS};
        final Material[] materialsWeapon = {Material.BOW, Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD, Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.DIAMOND_PICKAXE, Material.IRON_PICKAXE, Material.FISHING_ROD};
        final Material[] materialBuckets = {Material.WATER_BUCKET, Material.LAVA_BUCKET};
        final Material[] materialFood = {Material.APPLE, Material.BREAD, Material.MUSHROOM_SOUP, Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.COOKED_FISH, Material.WHEAT, Material.GOLDEN_APPLE, Material.CARROT_ITEM, Material.BAKED_POTATO};
        final Material[] materialBlock = {Material.WOOD, Material.LOG, Material.STONE, Material.COBBLESTONE};
        final Material[] materialOther = {Material.EGG, Material.SNOW_BALL, Material.ARROW};
        final Enchantment[] enchantsArmor = {Enchantment.OXYGEN, Enchantment.PROTECTION_ENVIRONMENTAL, Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_FALL, Enchantment.PROTECTION_FIRE, Enchantment.PROTECTION_PROJECTILE};
        final Enchantment[] enchantWeapon = {Enchantment.DURABILITY, Enchantment.DAMAGE_ALL, Enchantment.FIRE_ASPECT, Enchantment.KNOCKBACK};
        final Enchantment[] enchantBow = {Enchantment.ARROW_KNOCKBACK, Enchantment.ARROW_FIRE, Enchantment.ARROW_DAMAGE};

        // Armor
        for (int i = 0; i < 15; i++) {
            int pos = random.nextInt(materialsArmor.length);
            int quantidade = 1;
            // ItemStack
            final ItemStack itemStack = new ItemStack(materialsArmor[pos], quantidade);
            // Enchantment
            if (random.nextFloat() < 0.80) {
                pos = random.nextInt(enchantsArmor.length);
                final Enchantment enchant = enchantsArmor[pos]; // Enchantment
                if (enchant.canEnchantItem(itemStack)) {
                    quantidade = random.nextInt(2) + 1; // Enchantment level
                    itemStack.addUnsafeEnchantment(enchant, quantidade);
                }
            }
            // Add to list
            itemStacks.add(itemStack);
        }

        // Weapon
        for (int i = 0; i < 25; i++) {
            int pos = random.nextInt(materialsWeapon.length);
            int quantidade = 1;
            // ItemStack
            final ItemStack itemStack = new ItemStack(materialsWeapon[pos], quantidade);
            // Enchantment
            if (random.nextFloat() < 70) {
                if(itemStack.getType().equals(Material.BOW)){
                    pos = random.nextInt(enchantBow.length);
                    final Enchantment enchant = enchantBow[pos]; // Enchantment
                    if (enchant.canEnchantItem(itemStack)) {
                        quantidade = random.nextInt(2) + 1; // Enchantment level
                        itemStack.addUnsafeEnchantment(enchant, quantidade);
                    }
                } else {
                    pos = random.nextInt(enchantWeapon.length);
                    final Enchantment enchant = enchantWeapon[pos]; // Enchantment
                    if (enchant.canEnchantItem(itemStack)) {
                        quantidade = random.nextInt(2) + 1; // Enchantment level
                        itemStack.addUnsafeEnchantment(enchant, quantidade);
                    }
                }
            }
            // Add to list
            itemStacks.add(itemStack);
        }

        // Bucket
        for (int i = 0; i < 15; i++) {
            final int pos = random.nextInt(materialBuckets.length);
            final int quantidade = 1;
            // ItemStack
            final ItemStack itemStack = new ItemStack(materialBuckets[pos], quantidade);
            // Add to list
            itemStacks.add(itemStack);
        }

        // Food
        for (int i = 0; i < 10; i++) {
            final int pos = random.nextInt(materialFood.length);
            final int quantidade = random.nextInt(5) + 1;
            // ItemStack
            final ItemStack itemStack = new ItemStack(materialFood[pos], quantidade);
            // Add to list
            itemStacks.add(itemStack);
        }

        // Block
        for (int i = 0; i < 15; i++) {
            final int pos = random.nextInt(materialBlock.length);
            final int quantidade = random.nextInt(15) + 5;
            // ItemStack
            final ItemStack itemStack = new ItemStack(materialBlock[pos], quantidade);
            // Add to list
            itemStacks.add(itemStack);
        }

        // Other
        for (int i = 0; i < 20; i++) {
            final int pos = random.nextInt(materialOther.length);
            final int quantidade = random.nextInt(15) + 1;
            // ItemStack
            final ItemStack itemStack = new ItemStack(materialOther[pos], quantidade);
            // Add to list
            itemStacks.add(itemStack);
        }
    }

    /**
     * Removes the locations of already opened chests from the list
     *
     * @param world world of the locations to be removed
     */
    public void clearLocationsFromWorld(final World world){
        final List<Location> chestsLocations = new ArrayList<>(openedChests);
        chestsLocations.stream().filter(loc -> loc.getWorld().equals(world)).forEach(chestsLocations::remove);
    }
}