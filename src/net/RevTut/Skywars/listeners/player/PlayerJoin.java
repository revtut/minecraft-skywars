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

        // ScoreBoard
        plugin.scoreBoardManager.createScoreBoard(p);

        // MySQL Tasks
        final UUID uuid = p.getUniqueId();
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                // Tab List
                TabAPI.setTab(p, plugin.tabTitle, plugin.tabFooter);

                // Scoreboard e NameTag
                final Scoreboard board = plugin.scoreBoardManager.getScoreBoardByPlayer(uuid);
                if(board != null){
                    p.setScoreboard(board);
                    NameTagAPI.setNameTag(board, p, true);
                }

                // Hide to Server
                plugin.arenaManager.hideToServer(p, true);

                // PlayerDat
                plugin.mysql.createPlayerDat(uuid);

                // Config Player
                Bukkit.getScheduler().runTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        // PlayerDat
                        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(uuid);
                        if (playerDat == null) {
                            System.out.println("PlayerDat is null on join!");
                            /** Send him to Hub. Error in playerDat */
                            return;
                        }
                        // Add to Arena
                        if (!plugin.arenaManager.addPlayer(playerDat)) {
                            System.out.println("Could not add the player to an Arena on join!");
                            /** Send him to Hub. No arena available */
                            return;
                        }
                        // Arena
                        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
                        if (arena == null) {
                            System.out.println("Player's Arena is null on join!");
                            /** Send him to Hub. Error in arena */
                            return;
                        }
                        // New Arena if Needed
                        if (plugin.arenaManager.getNumberAvailableArenas() <= 1) {
                            plugin.arenaManager.createNewArena();
                        }
                        // Update points in ScoreBoard
                        plugin.scoreBoardManager.updatePoints(playerDat);
                    }
                });
            }
        }, 5L);
    }
}
