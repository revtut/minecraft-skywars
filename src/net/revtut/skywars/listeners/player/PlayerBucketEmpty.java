package net.revtut.skywars.listeners.player;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.player.PlayerStatus;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaStatus;
import net.revtut.skywars.player.PlayerDat;
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
