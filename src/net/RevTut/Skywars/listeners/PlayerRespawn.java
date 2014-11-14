package net.RevTut.Skywars.listeners;

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
        // Send to spawn
        player.teleport(player.getWorld().getSpawnLocation());
    }

}
