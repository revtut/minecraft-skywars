package net.RevTut.Skywars.listeners.player;

import net.RevTut.Skywars.SkyWars;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.logging.Level;

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
    private final SkyWars plugin;

    /**
     * Constructor of PlayerRespawn
     *
     * @param plugin main class
     */
    public PlayerRespawn(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of a player when he respawns
     *
     * @param e player respawn event
     * @see PlayerRespawnEvent
     */
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        final Player player = e.getPlayer();
        // Player Dat
        final PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if (playerDat == null)
            return;
        // Arena
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (arena == null)
            return;

        // Config Player
        if (!plugin.playerManager.configPlayer(playerDat, PlayerStatus.DEAD, GameMode.ADVENTURE, true, true, 0, 0, 20.0, 20, true, true, 0)) {
            plugin.getLogger().log(Level.WARNING, "Error while configuring the player.");
            return;
        }

        // Ninja
        Location locSpawn = arena.getKitManager().hacker.respawnPlayer(player, arena);
        if (locSpawn != null){
            e.setRespawnLocation(locSpawn);
            return;
        }

        // Location
        Location deadSpawn = arena.getArenaLocation().getDeathSpawnLocation();
        if (deadSpawn == null)
            return;
        // Set respawn location
        e.setRespawnLocation(deadSpawn);
    }

}
