package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Bukkit;
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
     * Takes care of a player when he respawns
     *
     * @param e     player respawn event
     * @see         PlayerRespawnEvent
     */
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();
        // Player Dat
        PlayerDat playerDat = PlayerDat.getPlayerDatByUUID(player.getUniqueId());
        if(playerDat == null)
            return;
        // Arena
        Arena arena = Arena.getArenaByPlayer(playerDat);
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
