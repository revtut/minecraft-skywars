package net.revtut.skywars.arena.tasks;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaDat;
import net.revtut.skywars.arena.ArenaStatus;
import net.revtut.skywars.libraries.appearance.AppearanceAPI;
import net.revtut.skywars.player.PlayerDat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Arena EndGame Runnable.
 *
 * <P>Takes care of all the arenas which are on "EndGame".</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class ArenaEndGame implements Runnable {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Runnable ID
     */
    private static int id;

    /**
     * Constructor of ArenaEndGame
     *
     * @param plugin the Main class
     */
    public ArenaEndGame(SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Returns the task ID
     *
     * @return ID of the task
     */
    public static int getId() {
        return id;
    }

    /**
     * Sets the ID of the runnable.
     *
     * @param id new ID for the task
     */
    public static void setId(int id) {
        ArenaEndGame.id = id;
    }

    /**
     * Cancel this task from being run.
     */
    public static void cancel() {
        Bukkit.getScheduler().cancelTask(id);
    }

    /**
     * Runnable which controls the remaining time of all arenas
     */
    @Override
    public void run() {
        plugin.arenaManager.getArenas().forEach(arena -> {
            int remainingTime;

            // Check if there are players in arena
            if (arena.getPlayers().size() < 1)
                return;

            // Check status
            if(arena.getStatus() != ArenaStatus.ENDGAME)
                return;

            // Remaining time of that arena
            remainingTime = arena.getRemainingTime();
            if (remainingTime >= 0) {
                onEndGame(arena);
                arena.setRemainingTime(remainingTime - 1);
            } else {
                fromEndGameToLobby(arena);
            }
        });
    }

    /**
     * Launch fireworks on the winner of the game.
     *
     * @param arena arena which is on end game
     */
    private void onEndGame(Arena arena) {
        // Launch Firework
        ArenaDat arenaDat = arena.getArenaDat();
        if (arenaDat == null)
            return;
        if (arenaDat.getWinner().equals("NULL"))
            return;

        UUID uuid = UUID.fromString(arenaDat.getWinner());
        Player winner = Bukkit.getPlayer(uuid);
        if (winner == null)
            return;

        AppearanceAPI.launchFirework(winner, 20, 1);
    }

    /**
     * Switch an arena from end game to lobby.
     * Teleport all the players to the center of the arena.
     *
     * @param arena arena to switch
     */
    private void fromEndGameToLobby(final Arena arena) {
        // Check if we can transfer this players to a new arena
        List<Arena> arenasAvailable = plugin.arenaManager.getAvailableArenas();
        Arena arenaMove = null;
        for (Arena arenaAlvo : arenasAvailable) {
            if (arenaAlvo.getPlayers().size() + arena.getPlayers().size() <= plugin.arenaManager.maxPlayers) {
                arenaMove = arenaAlvo;
                break;
            }
        }

        // Set the new arena
        final Arena newArena;
        if (arenaMove != null) {
            newArena = arenaMove; // Move to the new arena
            Bukkit.getScheduler().runTaskLater(plugin, () -> plugin.arenaManager.removeArena(arena), 1200);
        } else {
            newArena = arena; // Stay on the same arena
            Bukkit.getScheduler().runTaskLater(plugin, () -> plugin.arenaManager.resetArena(arena), 1200);
        }

        // Remove players and add them to new arena
        List<PlayerDat> players = new ArrayList<>(arena.getPlayers());
        players.forEach(player -> {
            plugin.arenaManager.removePlayer(player, false);

            // Add to new arena
            if (!plugin.arenaManager.addPlayer(player, newArena)) {
                // Send him to Hub. Error while adding to arena
                Player alvo = Bukkit.getPlayer(player.getUUID());
                if(alvo != null)
                    plugin.connectServer(alvo, "hub");

                plugin.getLogger().log(Level.WARNING, "Could not add player to the new arena.");
            }
        });
    }
}