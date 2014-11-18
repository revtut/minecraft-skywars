package net.RevTut.Skywars.listeners.player;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaStatus;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

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
    private final Main plugin;

    /**
     * Constructor of PlayerInteract
     *
     * @param plugin main class
     */
    public PlayerInteract(final Main plugin) {
        this.plugin = plugin;
    }


    /**
     * Takes care of what to do when a player interacts with something
     *
     * @param e     player interact event
     * @see         PlayerInteractEvent
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
        if (arena.getStatus() != ArenaStatus.INGAME) {
            e.setCancelled(true);
            return;
        }
        if (playerDat.getStatus() != PlayerStatus.ALIVE) {
            e.setCancelled(true);
            return;
        }

        // Chest interact
        plugin.playerChest.onChestInteract(e.getClickedBlock());

        // Engineer
        plugin.kitManager.engineer.landMineActivate(e.getAction(), e.getClickedBlock());
    }

}
