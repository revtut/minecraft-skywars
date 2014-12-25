package net.RevTut.skywar.listeners.player;

import net.RevTut.skywar.SkyWars;
import net.RevTut.skywar.player.PlayerStatus;
import net.RevTut.skywar.arena.Arena;
import net.RevTut.skywar.arena.ArenaStatus;
import net.RevTut.skywar.player.PlayerDat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

/**
 * Player Bucket Empty.
 *
 * <P>Controls when a player empties a bucket.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerBucketEmpty implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerBucketEmpty
     *
     * @param plugin main class
     */
    public PlayerBucketEmpty(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of what to do when a bucket is emptied
     *
     * @param e player bucket empty event
     * @see PlayerBucketEmptyEvent
     */
    @EventHandler
    public void onEmptyBucket(PlayerBucketEmptyEvent e) {
        Player player = e.getPlayer();
        // Player Dat
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if (playerDat == null)
            return;
        // Arena
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (null == arena)
            return;
        if (arena.getStatus() != ArenaStatus.INGAME)
            e.setCancelled(true);
        if (playerDat.getStatus() != PlayerStatus.ALIVE)
            e.setCancelled(true);
    }
}
