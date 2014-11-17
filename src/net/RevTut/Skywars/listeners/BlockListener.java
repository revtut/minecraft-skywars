package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaStatus;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

/**
 * Block Listener.
 *
 * <P>Controls the blocks event.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class BlockListener implements Listener {

    /**
     * Main class
     */
    private final Main plugin;

    /**
     * Constructor of BlockListener
     *
     * @param plugin main class
     */
    public BlockListener(final Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of what to do when a block is placed
     *
     * @param e     block place event
     * @see         BlockPlaceEvent
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
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

    /**
     * Takes care of what to do when a block is broken
     *
     * @param e     block break event
     * @see         BlockBreakEvent
     */
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
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

    /**
     * Takes care of what to do when a bucket is emptied
     *
     * @param e     player bucket empty event
     * @see         PlayerBucketEmptyEvent
     */
    @EventHandler
    public void onEmptyBucket(PlayerBucketEmptyEvent e){
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

    /**
     * Takes care of what to do when player clicks in his inventory
     *
     * @param e     inventory click event
     * @see         InventoryClickEvent
     */
    @EventHandler
    public void onFillBucket(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
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
