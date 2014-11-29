package net.RevTut.Skywars.managers;

import net.RevTut.Skywars.SkyWars;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Score Board Manager.
 *
 * <P>Manages all the scoreboards in the server.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class ScoreBoardManager {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Map with all the scoreboards of the players
     */
    private final Map<UUID, Scoreboard> scoreBoards = new HashMap<UUID, Scoreboard>();

    /**
     * Constructor of ScoreBoardManager
     *
     * @param plugin main class
     */
    public ScoreBoardManager(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Creates a new scoreboard to a player and saves it to a list of
     * players scoreboards
     *
     * @param p player to create the scoreboard
     * @return scoreboard of the player
     */
    public Scoreboard createScoreBoard(final Player p) {
        // Create scoreboard manager
        final ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        // New scoreboard
        final Scoreboard newBoard = scoreboardManager.getNewScoreboard();
        // Register new objective
        final Objective objective = newBoard.registerNewObjective("test", "dummy");
        // Display slot
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        // Display name
        objective.setDisplayName("§7► §3Sky Wars §7◄");

        // Alive
        final Score scoreAlive = objective.getScore(Message.getMessage(Message.ALIVE, p));
        scoreAlive.setScore(0);

        // Dead
        final Score scoreDead = objective.getScore(Message.getMessage(Message.DEAD, p));
        scoreDead.setScore(0);

        // Points
        final Score scorePoints = objective.getScore("§7" + ChatColor.stripColor(Message.getMessage(Message.POINTS, p)));
        scorePoints.setScore(0);

        // Scoreboard footer
        final Score separador = objective.getScore("§3----------");
        separador.setScore(-1);

        // Advertisement
        final Score website = objective.getScore("§7Website:");
        website.setScore(-2);
        final Score site = objective.getScore("§3revtut.net");
        site.setScore(-3);

        // Add to the map
        scoreBoards.put(p.getUniqueId(), newBoard);

        return newBoard;
    }

    /**
     * Update alive players in the scoreboard of all the
     * players which are on that arena
     *
     * @param arena arena to update alive players
     */
    public void updateAlive(Arena arena) {
        for (PlayerDat alvoDat : arena.getPlayers()) {
            updateAlive(arena, alvoDat);
        }
    }

    /**
     * Update alive players in the scoreboard of all the
     * players which are on that arena
     *
     * @param arena arena to update alive players
     * @param playerDat player to be updated
     */
    public void updateAlive(Arena arena, PlayerDat playerDat) {
        int alive = arena.getAlivePlayers().size();
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return;
        Scoreboard board = scoreBoards.get(player.getUniqueId());
        if (board == null)
            return;
        Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
        objective.getScore(Message.getMessage(Message.ALIVE, player)).setScore(alive);
    }

    /**
     * Update death players in the scoreboard of all the
     * players which are on that arena
     *
     * @param arena arena to update death players
     */
    public void updateDeath(Arena arena) {
        for (PlayerDat alvoDat : arena.getPlayers()) {
            updateDeath(arena, alvoDat);
        }
    }

    /**
     * Update death players in the scoreboard of all the
     * players which are on that arena
     *
     * @param arena arena to update death players
     * @param playerDat player to be updated
     */
    public void updateDeath(Arena arena, PlayerDat playerDat) {
        int dead = arena.getDeadPlayers().size();
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return;
        Scoreboard board = scoreBoards.get(player.getUniqueId());
        if (board == null)
            return;
        Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
        objective.getScore(Message.getMessage(Message.DEAD, player)).setScore(dead);
    }

    /**
     * Update points of players in the scoreboard
     *
     * @param playerDat player to update the points
     */
    public void updatePoints(PlayerDat playerDat) {
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return;
        final int points = playerDat.getPoints();
        final Scoreboard board = scoreBoards.get(player.getUniqueId());
        if (board == null)
            return;
        Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
        objective.getScore("§7" + ChatColor.stripColor(Message.getMessage(Message.POINTS, player))).setScore(points);
    }

    /**
     * Get the scoreboard of a given player
     *
     * @param uuid uuid of the player
     * @return scoreboard of the player
     */
    public Scoreboard getScoreBoardByPlayer(UUID uuid) {
        if (scoreBoards.containsKey(uuid))
            return scoreBoards.get(uuid);
        return null;
    }

    /**
     * Remove the scoreboard of a player from the Map
     *
     * @param player player which scoreboard will be removed
     * @return true if successfull
     */
    public boolean removePlayerScoreBoard(Player player) {
        if (scoreBoards.containsKey(player.getUniqueId())) {
            scoreBoards.remove(player.getUniqueId());
            return true;
        }
        return false;
    }
}


