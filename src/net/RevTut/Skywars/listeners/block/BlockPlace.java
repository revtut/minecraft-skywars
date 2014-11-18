package net.RevTut.Skywars.listeners.block;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaStatus;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * Block Place.
 *
 * <P>Controls when a block is placed.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class BlockPlace implements Listener {

    /**
     * Main class
     */
    private final Main plugin;

    /**
     * Constructor of BlockPlace
     *
     * @param plugin main class
     */
    public BlockPlace(final Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of what to do when a block is placed
     *
     * @param e block place event
     * @see BlockPlaceEvent
     */
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
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

        // Check if its a Chest
        plugin.playerChest.onChestPlace(e.getBlock());

        // Engineer
        arena.getKitManager().engineer.mineLandPlace(player, player.getItemInHand(), e.getBlock().getLocation());
    }
}
