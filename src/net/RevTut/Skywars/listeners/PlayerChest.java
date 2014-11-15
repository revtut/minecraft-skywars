package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        if(playerDat.getStatus() != PlayerStatus.ALIVE)
            return;
        // Check if block is a Chest
        if(e.getClickedBlock().getType() != Material.CHEST)
            return;
        // Chest
        Chest chest = (Chest) e.getClickedBlock().getState();
        Inventory chestInv = chest.getInventory();
        // Fill chest
        int numItems = plugin.rand.nextInt(11) + 5;
        for(int i = 0; i < numItems; i++){
            int posItem = plugin.rand.nextInt(itemStacks.size());
            chestInv.addItem(itemStacks.get(posItem));
        }
    }

    /** Generates all the ItemStacks that might be in a Chest */
    private void generateItemStacks(){
        Material[] materials = Material.class.getEnumConstants();
        Enchantment[] enchants = Enchantment.class.getEnumConstants();
        for(int i = 0; i < 100; i++){
            int pos = plugin.rand.nextInt(materials.length);
            // ItemStack
            ItemStack itemStack = new ItemStack(materials[pos]);
            // Enchantment
            pos = plugin.rand.nextInt(enchants.length);
            Enchantment enchant = enchants[pos]; // Enchantment
            pos = plugin.rand.nextInt(2) + 1; // Enchantment level
            itemStack.addEnchantment(enchant, pos);
            // Add to list
            itemStacks.add(itemStack);
        }
    }
}
