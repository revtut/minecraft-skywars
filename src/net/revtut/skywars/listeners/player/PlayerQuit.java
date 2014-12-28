package net.revtut.skywars.listeners.player;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaStatus;
import net.revtut.skywars.player.PlayerDat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

/**
 * Player Join.
 *
 * <P>Controls the join event.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerQuit implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerQuit
     *
     * @param plugin main class
     */
    public PlayerQuit(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of a player when he leaves. Delete the PlayerDat and the arena if needed.
     *
     * @param e player quit event
     * @see PlayerQuitEvent
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Player p = e.getPlayer();

        // Mensagem Saida
        e.setQuitMessage(null);

        // PlayerDat
        final PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(p.getUniqueId());
        if (playerDat == null) {
            plugin.getLogger().log(Level.WARNING, "Error while updating PlayerDat on quit as it is NULL!");
            return;
        }

        // Arena
        final Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (arena != null)
            if(arena.getStatus() == ArenaStatus.INGAME)
                playerDat.addPoints(0 - plugin.pointsPerKill * (1 + arena.getPlayers().size()/plugin.arenaManager.maxPlayers)); // Remove points because left before endgame

        // Remove from arena
        if (!plugin.arenaManager.removePlayer(playerDat, true)) {
            plugin.getLogger().log(Level.WARNING, "Error while removing PlayerDat from arena on quit!");
            return;
        }

        // Remove from scoreboard
        if(!plugin.scoreBoardManager.removePlayerScoreBoard(p))
            plugin.getLogger().log(Level.WARNING, "Error while removing player's scoreboard as it is null!");

        // Remove playerDat
        plugin.playerManager.removePlayerDat(playerDat);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                // MySQL Tasks
                plugin.mysql.updatePlayerDat(playerDat);
            }
        });
    }
}