package net.RevTut.Skywars.utils;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

public class ScoreBoard implements Listener {
    public Main plugin;

    public ScoreBoard(final Main plugin) {
        this.plugin = plugin;
    }

    /* ScoreBoards */
    private static HashMap<UUID, Scoreboard> scoreBoards = new HashMap<UUID, Scoreboard>();

    /* ScoreBoard Info */
    private static int online = 0;
    private static int points = 0;

    /* Show ScoreBoard to Player */
    public static void showScoreBoard(Player p) {
        /* Cria a scoreboard */
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§7► §3RevTut §7◄");

            /* Online */
        final Score scoreon = objective.getScore("§7Online:");
        scoreon.setScore(online);

            /* Gaming Points */
        final Score scoregp = objective.getScore("§7Points:");
        scoregp.setScore(points);

            /* Scoreboard footer */
        final Score separador = objective.getScore("§3----------");
        separador.setScore(-1);

        final Score website = objective.getScore("§7Website:");
        website.setScore(-2);
        final Score site = objective.getScore("§3revtut.net");
        site.setScore(-3);
        p.setScoreboard(board);
        scoreBoards.put(p.getUniqueId(), board);
    }

    /* Update Online Players */
    public static void updateOnline(Player p) {
        if (p != null) {
            Scoreboard board = scoreBoards.get(p.getUniqueId());
            if (board != null) {
                Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
                if (objective != null) {
                    objective.getScore("§7Online:").setScore(online);
                }
            }
        }
    }

    /* Update Points */
    public static void updatePoints(Player p) {
        if (p != null) {
            Scoreboard board = scoreBoards.get(p.getUniqueId());
            if (board != null) {
                Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
                points = PlayerDat.getPlayerDatByUUID(p.getUniqueId()).getPoints();
                objective.getScore("§7Points:").setScore(points);
            }
        }
    }

    /* Get Player's ScoreBoard */
    public static Scoreboard getScoreBoardByPlayer(UUID uuid) {
        if (scoreBoards.containsKey(uuid))
            return scoreBoards.get(uuid);
        return null;
    }

    /* Remove ScoreBoard From Map */
    public static boolean removePlayerScoreBoard(Player player) {
        if (scoreBoards.containsKey(player.getUniqueId())) {
            scoreBoards.remove(player.getUniqueId());
            return true;
        }
        return false;
    }
}


