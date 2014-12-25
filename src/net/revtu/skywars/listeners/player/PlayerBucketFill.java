package net.revtu.skywars.listeners.player;

import net.revtu.skywars.SkyWars;
import net.revtu.skywars.arena.ArenaStatus;
import net.revtu.skywars.player.PlayerDat;
import net.revtu.skywars.player.PlayerStatus;
import net.revtu.skywars.arena.Arena;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketFillEvent;

/**
 * Player Bucket Fill.
 *
 * <P>Controls when a player fills a bucket.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerBucketFill implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerBucketFill
     *
     * @param plugin main class
     */
    public PlayerBucketFill(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of what to do when a bucket is filled
     *
     * @param e player bucket fill event
     * @see PlayerBucketFillEvent
     */
    @EventHandler
    public void onFillBucket(PlayerBucketFillEvent e) {
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
