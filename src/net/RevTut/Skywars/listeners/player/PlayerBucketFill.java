package net.RevTut.Skywars.listeners.player;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaStatus;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
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
    private final Main plugin;

    /**
     * Constructor of PlayerBucketFill
     *
     * @param plugin main class
     */
    public PlayerBucketFill(final Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of what to do when a bucket is filled
     *
     * @param e     player bucket fill event
     * @see         PlayerBucketFillEvent
     */
    @EventHandler
    public void onFillBucket(PlayerBucketFillEvent e){
        Player player = e.getPlayer();
        // Player Dat
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if(playerDat == null)
            return;
        // Arena
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if(null == arena)
            return;
        if(arena.getStatus() != ArenaStatus.INGAME)
            e.setCancelled(true);
        if(playerDat.getStatus() != PlayerStatus.ALIVE)
            e.setCancelled(true);
    }
}
