package net.RevTut.Skywars.listeners.player;

import net.RevTut.Skywars.SkyWars;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaStatus;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

/**
 * Player Interact.
 *
 * <P>Controls the player interact event.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerInteract implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerInteract
     *
     * @param plugin main class
     */
    public PlayerInteract(final SkyWars plugin) {
        this.plugin = plugin;
    }


    /**
     * Takes care of what to do when a player interacts with something
     *
     * @param e player interact event
     * @see PlayerInteractEvent
     */
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        // Player Dat
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if (playerDat == null)
            return;

        // Arena
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (null == arena)
            return;

        // Kit Menu
        Inventory inventory = arena.getKitManager().createKitMenu(playerDat, player.getItemInHand());
        if(inventory != null)
            player.openInventory(inventory);

        // Check status
        if (arena.getStatus() != ArenaStatus.INGAME) {
            e.setCancelled(true);
            return;
        }
        if (playerDat.getStatus() != PlayerStatus.ALIVE) {
            e.setCancelled(true);

            // Compass Menu
            if(playerDat.getStatus() != PlayerStatus.WAITING) {
                inventory = arena.getKitManager().createCompassMenu(player.getItemInHand(), playerDat, arena);
                if(inventory != null)
                    player.openInventory(inventory);
            }
            return;
        }

        // Chest interact
        plugin.playerChest.onChestInteract(e.getClickedBlock());

        // Guardian
        if(arena.getKitManager().guardian.setSpeed(player, e.getAction(), player.getItemInHand(), 10))
            e.setCancelled(true);
        // Tactical
        if(arena.getKitManager().tatical.setInvisible(player, e.getAction(), player.getItemInHand(), 10))
            e.setCancelled(true);
    }

}
