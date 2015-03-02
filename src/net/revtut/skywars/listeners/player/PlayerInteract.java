package net.revtut.skywars.listeners.player;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaStatus;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.player.PlayerStatus;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
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

        if(e.getAction() != Action.PHYSICAL) {
            // Kit Menu
            Inventory inventory = arena.getKitManager().createKitMenu(playerDat, player.getItemInHand());
            if(inventory != null)
                player.openInventory(inventory);
        }

        // Lobby Kit
        if(arena.getKitManager().lobby.connectToHub(player, e.getItem(), e.getAction())) {
            plugin.connectServer(player, "hub");
            return;
        }
        if(player.getItemInHand() != null) {
            if (player.getItemInHand().getType() == Material.WRITTEN_BOOK) {
                e.setCancelled(false);
                return;
            }
        }

        // Check status
        if (arena.getStatus() != ArenaStatus.INGAME) {
            e.setCancelled(true);
            return;
        }

        if (playerDat.getStatus() != PlayerStatus.ALIVE) {
            e.setCancelled(true);
            return;
        }

        if(e.getAction() != Action.PHYSICAL) {
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

}
