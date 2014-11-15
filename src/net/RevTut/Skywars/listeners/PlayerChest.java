package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

/**
 * Player Interact With Chest.
 *
 * <P>Controls the interaction with chests and randomly fill the chests.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerChest {

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
    }

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
        Chest chest = (Chest) e.getClickedBlock();
        Inventory chestInv = chest.getInventory();
    }
}
