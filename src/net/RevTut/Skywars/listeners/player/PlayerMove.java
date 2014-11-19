package net.RevTut.Skywars.listeners.player;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaStatus;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Player Move.
 *
 * <P>Controls the player move event.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerMove implements Listener {

    /**
     * Main class
     */
    private final Main plugin;

    /**
     * Constructor of PlayerMove
     *
     * @param plugin main class
     */
    public PlayerMove(final Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of a player when he moves
     *
     * @param e player move event
     * @see PlayerMoveEvent
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
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
            return;
        if (playerDat.getStatus() != PlayerStatus.ALIVE)
            return;

        // Engineer
        arena.getKitManager().engineer.landMineActivate(player);
    }

}
