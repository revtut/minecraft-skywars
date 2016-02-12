package net.revtut.skywars.listeners.player;

import net.revtut.libraries.Libraries;
import net.revtut.libraries.minecraft.bukkit.appearance.Items;
import net.revtut.libraries.minecraft.bukkit.games.GameController;
import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.arena.session.GameState;
import net.revtut.libraries.minecraft.bukkit.games.events.player.PlayerInteractionEvent;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;
import net.revtut.libraries.minecraft.bukkit.games.player.PlayerState;
import net.revtut.skywars.SkyWars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Player Interaction Listener
 */
public class PlayerInteractionListener implements Listener {

    /**
     * List with all chests that were already randomized
     */
    private static final List<Location> openedChests = new ArrayList<>();

    /**
     * List with all items available to chests
     */
    private static final List<ItemStack> itemStacks = new ArrayList<>();

    /**
     * Constructor of PlayerInteractionListener
     */
    public PlayerInteractionListener() {
        Bukkit.getScheduler().runTaskTimer(Libraries.getInstance(), this::randomizeItems, 0L, 6000L);
    }

    /**
     * Mark a chest on a given location as an already opened chest
     * @param location location of the chest
     */
    public static void setOpenedChest(final Location location) {
        openedChests.add(location);
    }

    /**
     * Removes the locations of already opened chests from the list
     * @param world world of the locations to be removed
     */
    public static void clearOpenedChests(final World world){
        final List<Location> chestsLocations = new ArrayList<>(openedChests);
        chestsLocations.stream().filter(loc -> loc.getWorld().equals(world)).forEach(chestsLocations::remove);
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
            return;
        }

        // Cancel physical interaction
        if(event.getAction() == Action.PHYSICAL && arena.getSession() != null && arena.getSession().getState() == GameState.LOBBY) {
            event.setCancelled(true);
            return;
        }

        // Check if it is a Chest, if so randomize its contents
        if(event.getClickedBlock() == null)
            return;
        if(!(event.getClickedBlock().getState() instanceof Chest))
            return;
        final Block block = event.getClickedBlock();
        if (openedChests.contains(block.getLocation()))
            return;

        final Chest chest = (Chest) block.getState();
        randomizeChest(chest);
        setOpenedChest(chest.getLocation());
    }

    /**
     * Randomize the items inside a chest
     * @param chest chest to be randomized
     */
    private void randomizeChest(final Chest chest) {
        final int numItems = (int) (Math.random() * 10) + 15;
        for (int i = 0; i < numItems; i++) {
            final int posItem = (int) (Math.random() * itemStacks.size());
            int posChest;
            do {
                posChest = (int) (Math.random() * chest.getBlockInventory().getSize());
            } while(chest.getBlockInventory().getItem(posChest) != null);
            chest.getBlockInventory().setItem(posChest, itemStacks.get(posItem));
        }
    }

    /**
     * Generates all the ItemStacks that might be in a Chest
     */
    private void randomizeItems() {
        // Materials
        final Material[] materialsArmor = {
                Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.LEATHER_HELMET,
                Material.CHAINMAIL_CHESTPLATE, Material.DIAMOND_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.LEATHER_CHESTPLATE,
                Material.CHAINMAIL_LEGGINGS, Material.DIAMOND_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.LEATHER_LEGGINGS,
                Material.CHAINMAIL_BOOTS, Material.DIAMOND_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.LEATHER_BOOTS
        };
        final Material[] materialsWeapon = {
                Material.BOW, Material.FISHING_ROD,
                Material.DIAMOND_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOOD_SWORD,
                Material.DIAMOND_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.DIAMOND_PICKAXE, Material.IRON_PICKAXE
        };
        final Material[] materialBuckets = {
                Material.WATER_BUCKET, Material.LAVA_BUCKET
        };
        final Material[] materialFood = {
                Material.APPLE, Material.GOLDEN_APPLE,
                Material.BREAD, Material.WHEAT,
                Material.MUSHROOM_SOUP, Material.CARROT_ITEM,
                Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.COOKED_FISH,
                Material.BAKED_POTATO
        };
        final Material[] materialBlock = {
                Material.WOOD, Material.LOG,
                Material.STONE, Material.COBBLESTONE
        };
        final Material[] materialProjectiles = {
                Material.EGG,
                Material.SNOW_BALL,
                Material.ARROW
        };

        // Enchantments
        final Enchantment[] enchantsArmor = {
                Enchantment.OXYGEN, Enchantment.PROTECTION_ENVIRONMENTAL,
                Enchantment.PROTECTION_EXPLOSIONS, Enchantment.PROTECTION_FALL,
                Enchantment.PROTECTION_FIRE, Enchantment.PROTECTION_PROJECTILE
        };
        final Enchantment[] enchantWeapon = {
                Enchantment.DURABILITY, Enchantment.DAMAGE_ALL,
                Enchantment.FIRE_ASPECT, Enchantment.KNOCKBACK,
                Enchantment.ARROW_KNOCKBACK, Enchantment.ARROW_FIRE, Enchantment.ARROW_DAMAGE
        };

        // Armor
        for (int i = 0; i < 15; i++)
            itemStacks.add(Items.getRandomItem(materialsArmor, enchantsArmor, 0.80, 2));

        // Weapon
        for (int i = 0; i < 25; i++)
            itemStacks.add(Items.getRandomItem(materialsWeapon, enchantWeapon, 0.70, 2));

        // Bucket
        for (int i = 0; i < 15; i++)
            itemStacks.add(Items.getRandomItem(materialBuckets));

        // Food
        for (int i = 0; i < 10; i++)
            itemStacks.add(Items.getRandomItem(materialFood));

        // Block
        for (int i = 0; i < 15; i++)
            itemStacks.add(Items.getRandomItem(materialBlock));

        // Projectiles
        for (int i = 0; i < 20; i++)
            itemStacks.add(Items.getRandomItem(materialProjectiles));
    }
}