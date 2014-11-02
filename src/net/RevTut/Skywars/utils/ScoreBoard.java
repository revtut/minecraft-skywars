package net.RevTut.Skywars.utils;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
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

    /* Show ScoreBoard to Player */
    public static void showScoreBoard(Player p, Arena arena) {
        /* Cria a scoreboard */
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("test", "dummy");

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§7► §3Sky Wars §7◄");

        /* Alive */
        final Score scoreAlive = objective.getScore("§aAlive:");
        scoreAlive.setScore(0);

        /* Dead */
        final Score scoreDead = objective.getScore("§cDead:");
        scoreDead.setScore(0);

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

    /* Update Alive */
    public static void updateAlive(Arena arena) {
        int alive = arena.getAlivePlayers().size();
        for(PlayerDat alvoDat : arena.getPlayers()){
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if(alvo != null){
                Scoreboard board = scoreBoards.get(alvo.getUniqueId());
                if (board != null) {
                    Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
                    objective.getScore("§aAlive:").setScore(alive);
                }
            }
        }
    }

    /* Update Death */
    public static void updateDeath(Arena arena) {
        int dead = arena.getDeadPlayers().size();
        for(PlayerDat alvoDat : arena.getPlayers()){
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if(alvo != null){
                Scoreboard board = scoreBoards.get(alvo.getUniqueId());
                if (board != null) {
                    Objective objective = board.getObjective(DisplaySlot.SIDEBAR);
                    objective.getScore("§cDead:").setScore(dead);
                }
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


