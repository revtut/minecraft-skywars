package net.RevTut.Skywars.listeners.player;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Player Respawn.
 *
 * <P>Controls the respawn event.</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class PlayerRespawn implements Listener {

    /**
     * Main class
     */
    private final Main plugin;

    /**
     * Constructor of PlayerRespawn
     *
     * @param plugin main class
     */
    public PlayerRespawn(final Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of a player when he respawns
     *
     * @param e     player respawn event
     * @see         PlayerRespawnEvent
     */
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();
        // Player Dat
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if(playerDat == null)
            return;
        // Arena
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if(arena == null)
            return;
        // Location
        Location deadSpawn = arena.getArenaLocation().getDeathSpawnLocation();
        if(deadSpawn == null)
            return;
        // Set respawn location
        e.setRespawnLocation(deadSpawn);
    }

}
