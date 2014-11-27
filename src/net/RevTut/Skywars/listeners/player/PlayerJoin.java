package net.RevTut.Skywars.listeners.player;

import net.RevTut.Skywars.SkyWars;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.libraries.nametag.NameTagAPI;
import net.RevTut.Skywars.libraries.tab.TabAPI;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;
import java.util.logging.Level;

/**
 * Player Join.
 *
 * <P>Controls the join event.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerJoin implements Listener {

    /**
     * Main class{}
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerJoin
     *
     * @param plugin main class
     */
    public PlayerJoin(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of a player when he joins. Create the PlayerDat of him and assign him to
     * an existing arena. If needed it creates a new one.
     *
     * @param e player join event
     * @see PlayerJoinEvent
     */
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();

        // Mensagem Entrada
        e.setJoinMessage(null);

        // Hide to Server
        plugin.arenaManager.hideToServer(p, true);

        // Scoreboard
        plugin.scoreBoardManager.createScoreBoard(p);

        // Tab List
        TabAPI.setTab(p, plugin.tabTitle, plugin.tabFooter);

        final UUID uuid = p.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                // PlayerDat
                plugin.mysql.createPlayerDat(uuid);

                Bukkit.getScheduler().runTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        // PlayerDat
                        final PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(p.getUniqueId());
                        if (playerDat == null) {
                            plugin.getLogger().log(Level.WARNING, "PlayerDat is null on join!");
                            // Send him to Hub. Error in playerDat
                            plugin.connectServer(p, "hub");
                            return;
                        }

                        // Add to Arena
                        if (!plugin.arenaManager.addPlayer(playerDat)) {
                            plugin.getLogger().log(Level.WARNING, "Could not add the player to an Arena on join!");
                            // Send him to Hub. No arena available
                            plugin.connectServer(p, "hub");
                            return;
                        }

                        // Arena
                        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
                        if (arena == null) {
                            plugin.getLogger().log(Level.WARNING, "Player's Arena is null on join!");
                            // Send him to Hub. Error in arena
                            plugin.connectServer(p, "hub");
                            return;
                        }

                        // New Arena if Needed
                        if (plugin.arenaManager.getNumberAvailableArenas() <= 1)
                            plugin.arenaManager.createNewArena();
                    }
                });
            }
        });

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                // PlayerDat
                final PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(p.getUniqueId());
                if (playerDat == null) {
                    plugin.getLogger().log(Level.WARNING, "PlayerDat is null on join!");
                    // Send him to Hub. Error in playerDat
                    plugin.connectServer(p, "hub");
                    return;
                }

                // Arena
                Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
                if (arena == null) {
                    plugin.getLogger().log(Level.WARNING, "Player's Arena is null on join!");
                    // Send him to Hub. Error in arena
                    plugin.connectServer(p, "hub");
                    return;
                }

                // Scoreboard
                Scoreboard board = plugin.scoreBoardManager.getScoreBoardByPlayer(p.getUniqueId());
                if (board != null) {
                    p.setScoreboard(board);
                    NameTagAPI.setNameTag(board, p, true);
                }

                // Update ScoreBoard to player
                plugin.scoreBoardManager.updateAlive(arena, playerDat);
                plugin.scoreBoardManager.updateDeath(arena, playerDat);

                // Update points in ScoreBoard
                plugin.scoreBoardManager.updatePoints(playerDat);
            }
        }, 10);
    }
}
