package net.RevTut.Skywars.listeners.player;

import net.RevTut.Skywars.Main;
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
    private final Main plugin;

    /**
     * Constructor of PlayerJoin
     *
     * @param plugin main class
     */
    public PlayerJoin(final Main plugin) {
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

        // Tasks
        final UUID uuid = p.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                // Tab List
                TabAPI.setTab(p, plugin.tabTitle, plugin.tabFooter);

                // PlayerDat
                plugin.mysql.createPlayerDat(uuid);

                // PlayerDat
                final PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(uuid);
                if (playerDat == null) {
                    System.out.println("PlayerDat is null on join!");
                    /** Send him to Hub. Error in playerDat */
                    plugin.connectServer(p, "hub");
                    return;
                }

                // Add to Arena
                if (!plugin.arenaManager.addPlayer(playerDat)) {
                    System.out.println("Could not add the player to an Arena on join!");
                    /** Send him to Hub. No arena available */
                    plugin.connectServer(p, "hub");
                    return;
                }

                // Arena
                Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
                if (arena == null) {
                    System.out.println("Player's Arena is null on join!");
                    /** Send him to Hub. Error in arena */
                    plugin.connectServer(p, "hub");
                    return;
                }

                // New Arena if Needed
                if (plugin.arenaManager.getNumberAvailableArenas() <= 1)
                    Bukkit.getScheduler().runTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            plugin.arenaManager.createNewArena();
                        }
                    });


                // NameTag
                Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
                    @Override
                    public void run() {
                        // Scoreboard
                        Scoreboard board = plugin.scoreBoardManager.getScoreBoardByPlayer(p.getUniqueId());
                        if (board != null) {
                            p.setScoreboard(board);
                            NameTagAPI.setNameTag(board, p, true);
                        }

                        // PlayerDat
                        final PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(p.getUniqueId());
                        if (playerDat == null) {
                            System.out.println("PlayerDat is null on join!");
                            /** Send him to Hub. Error in playerDat */
                            plugin.connectServer(p, "hub");
                            return;
                        }

                        // Update points in ScoreBoard
                        plugin.scoreBoardManager.updatePoints(playerDat);
                    }
                }, 10);
            }
        });
    }
}
