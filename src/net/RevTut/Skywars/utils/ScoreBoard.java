package net.RevTut.Skywars.utils;

import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * ScoreBoard.
 *
 * <P>Class to handle all the scoreboards in the server.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class ScoreBoard {

    /**
     * Map with all the scoreboards of the players
     */
    private static final Map<UUID, Scoreboard> scoreBoards = new HashMap<UUID, Scoreboard>();

    /**
     * Show a new scoreboard to a player
     *
     * @param p player to show the scoreboard
     * @return scoreboard of the player
     */
    public static Scoreboard showScoreBoard(Player p) {
        // Create the scoreboard
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§7► §3Sky Wars §7◄");

        // Alive
        final Score scoreAlive = objective.getScore("§aAlive:");
        scoreAlive.setScore(0);

        // Dead
        final Score scoreDead = objective.getScore("§cDead:");
        scoreDead.setScore(0);

        // Points
        final Score scorePoints = objective.getScore("§7Points:");
        scorePoints.setScore(0);

        // Scoreboard footer
        final Score separador = objective.getScore("§3----------");
        separador.setScore(-1);

        // Advertisement
        final Score website = objective.getScore("§7Website:");
        website.setScore(-2);
        final Score site = objective.getScore("§3revtut.net");
        site.setScore(-3);
        p.setScoreboard(board);

        // Add to the map
        scoreBoards.put(p.getUniqueId(), board);

        return board;
    }

    /**
     * Update alive players in the scoreboard of all the
     * players which are on that arena
     *
     * @param arena arena to update alive players
     */
    public static void updateAlive(Arena arena) {
        int alive = arena.getAlivePlayers().size();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;
            Scoreboard board = scoreBoards.get(alvo.getUniqueId());
            if (board == null)
                continue;
            Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
            objective.getScore("§aAlive:").setScore(alive);
        }
    }

    /**
     * Update death players in the scoreboard of all the
     * players which are on that arena
     *
     * @param arena arena to update death players
     */
    public static void updateDeath(Arena arena) {
        int dead = arena.getDeadPlayers().size();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;
            Scoreboard board = scoreBoards.get(alvo.getUniqueId());
            if (board == null)
                continue;
            Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
            objective.getScore("§cDead:").setScore(dead);
        }
    }

    /**
     * Update points of players in the scoreboard
     *
     * @param playerDat player to update the points
     */
    public static void updatePoints(PlayerDat playerDat) {
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return;
        int points = playerDat.getPoints();
        Scoreboard board = scoreBoards.get(player.getUniqueId());
        if (board == null)
            return;
        Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
        objective.getScore("§7Points:").setScore(points);
    }

    /**
     * Get the scoreboard of a given player
     *
     * @param uuid uuid of the player
     * @return scoreboard of the player
     */
    public static Scoreboard getScoreBoardByPlayer(UUID uuid) {
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
    public static boolean removePlayerScoreBoard(Player player) {
        if (scoreBoards.containsKey(player.getUniqueId())) {
            scoreBoards.remove(player.getUniqueId());
            return true;
        }
        return false;
    }
}


