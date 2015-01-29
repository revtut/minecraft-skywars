package net.revtut.skywars.managers;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.utils.Message;
import org.bukkit.Bukkit;
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
    private final Map<UUID, Scoreboard> scoreBoards = new HashMap<>();

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
     * @param player player to create the scoreboard
     * @return scoreboard of the player
     */
    public Scoreboard createScoreBoard(final Player player) {
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

        // Points
        final Score scorePoints = objective.getScore(Message.getMessage(Message.POINTS, player));
        scorePoints.setScore(14);
        final Score scoreNumberPoints = objective.getScore("" + 0);
        scoreNumberPoints.setScore(13);

        // Blank line
        final Score scoreBlankLine1 = objective.getScore(" ");
        scoreBlankLine1.setScore(12);

        // Alive
        final Score scoreAlive = objective.getScore(Message.getMessage(Message.ALIVE, player));
        scoreAlive.setScore(11);
        final Score scoreNumberAlive = objective.getScore("" + 0 + " ");
        scoreNumberAlive.setScore(10);

        // Dead
        final Score scoreDead = objective.getScore(Message.getMessage(Message.DEAD, player));
        scoreDead.setScore(9);
        final Score scoreNumberDead = objective.getScore("" + 0 + "  ");
        scoreNumberDead.setScore(8);

        // Blank line
        final Score scoreBlankLine2 = objective.getScore("");
        scoreBlankLine2.setScore(7);

        // Wins / Losses
        final Score scoreWinsLosses = objective.getScore(Message.getMessage(Message.WINS, player) + " / " + Message.getMessage(Message.LOSSES, player));
        scoreWinsLosses.setScore(6);
        final Score scoreNumberWinsLosses = objective.getScore("" + 0 + " / " + 0);
        scoreNumberWinsLosses.setScore(5);

        // Kills / Deaths
        final Score scoreKillsDeaths = objective.getScore(Message.getMessage(Message.KILLS, player) + " / " + Message.getMessage(Message.DEATHS, player));
        scoreKillsDeaths.setScore(4);
        final Score scoreNumberKillsDeaths = objective.getScore("" + 0 + " / " + 0 + " ");
        scoreNumberKillsDeaths.setScore(3);

        // Scoreboard footer
        final Score separador = objective.getScore("§3------------");
        separador.setScore(2);

        // Advertisement
        final Score website = objective.getScore("§3Website");
        website.setScore(1);
        final Score site = objective.getScore("§6www.revtut.net");
        site.setScore(0);

        // Add to the map
        scoreBoards.put(player.getUniqueId(), newBoard);

        return newBoard;
    }

    /**
     * Update points of players in the scoreboard
     *
     * @param playerDat player to update the points
     */
    public void updatePoints(PlayerDat playerDat) {
        int scoreToUpdate = 13;
        int points = playerDat.getPoints();
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return;
        final Scoreboard board = scoreBoards.get(player.getUniqueId());
        if (board == null)
            return;
        Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
        for(String entry : board.getEntries()) {
            Score score = objective.getScore(entry);
            if(score.getScore() == scoreToUpdate) {
                board.resetScores(entry);

                score = objective.getScore("" + points);
                score.setScore(scoreToUpdate);
                break;
            }
        }
    }

    /**
     * Update alive players in the scoreboard of all the
     * players which are on that arena
     *
     * @param arena arena to update alive players
     */
    public void updateAlive(Arena arena) {
        arena.getPlayers().forEach(alvoDat -> updateAlive(arena, alvoDat));
    }

    /**
     * Update alive players in the scoreboard of all the
     * players which are on that arena
     *
     * @param arena arena to update alive players
     * @param playerDat player to be updated
     */
    public void updateAlive(Arena arena, PlayerDat playerDat) {
        int scoreToUpdate = 10;
        int alive = arena.getAlivePlayers().size();
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return;
        Scoreboard board = scoreBoards.get(player.getUniqueId());
        if (board == null)
            return;
        Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
        for(String entry : board.getEntries()) {
            Score score = objective.getScore(entry);
            if(score.getScore() == scoreToUpdate) {
                board.resetScores(entry);

                score = objective.getScore("" + alive + " ");
                score.setScore(scoreToUpdate);
                break;
            }
        }
    }

    /**
     * Update death players in the scoreboard of all the
     * players which are on that arena
     *
     * @param arena arena to update death players
     */
    public void updateDeath(Arena arena) {
        arena.getPlayers().forEach(alvoDat -> updateDeath(arena, alvoDat));
    }

    /**
     * Update death players in the scoreboard of all the
     * players which are on that arena
     *
     * @param arena arena to update death players
     * @param playerDat player to be updated
     */
    public void updateDeath(Arena arena, PlayerDat playerDat) {
        int scoreToUpdate = 8;
        int dead = arena.getDeadPlayers().size();
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return;
        Scoreboard board = scoreBoards.get(player.getUniqueId());
        if (board == null)
            return;
        Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
        for(String entry : board.getEntries()) {
            Score score = objective.getScore(entry);
            if(score.getScore() == scoreToUpdate) {
                board.resetScores(entry);

                score = objective.getScore("" + dead + "  ");
                score.setScore(scoreToUpdate);
                break;
            }
        }
    }

    /**
     * Update wins and losses of players in the scoreboard
     *
     * @param playerDat player to update the points
     */
    public void updateWinsLosses(PlayerDat playerDat) {
        int scoreToUpdate = 5;
        int wins = playerDat.getWins();
        int losses = playerDat.getLosses();
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return;
        final Scoreboard board = scoreBoards.get(player.getUniqueId());
        if (board == null)
            return;
        Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
        for(String entry : board.getEntries()) {
            Score score = objective.getScore(entry);
            if(score.getScore() == scoreToUpdate) {
                board.resetScores(entry);

                score = objective.getScore("" + wins + " / " + losses);
                score.setScore(scoreToUpdate);
                break;
            }
        }
    }

    /**
     * Update kills and deaths of players in the scoreboard
     *
     * @param playerDat player to update the points
     */
    public void updateKillsDeaths(PlayerDat playerDat) {
        int scoreToUpdate = 3;
        int kills = playerDat.getKills();
        int deaths = playerDat.getDeaths();
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return;
        final Scoreboard board = scoreBoards.get(player.getUniqueId());
        if (board == null)
            return;
        Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
        for(String entry : board.getEntries()) {
            Score score = objective.getScore(entry);
            if(score.getScore() == scoreToUpdate) {
                board.resetScores(entry);

                score = objective.getScore("" + kills + " / " + deaths + " ");
                score.setScore(scoreToUpdate);
                break;
            }
        }
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


