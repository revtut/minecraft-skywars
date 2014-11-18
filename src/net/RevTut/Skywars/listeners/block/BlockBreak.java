package net.RevTut.Skywars.listeners.block;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaStatus;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Block Break.
 *
 * <P>Controls when a block is break.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class BlockBreak implements Listener {

    /**
     * Main class
     */
    private final Main plugin;

    /**
     * Constructor of BlockBreak
     *
     * @param plugin main class
     */
    public BlockBreak(final Main plugin) {
        this.plugin = plugin;
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
}