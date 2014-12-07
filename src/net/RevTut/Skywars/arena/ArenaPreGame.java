package net.RevTut.Skywars.arena;

import net.RevTut.Skywars.SkyWars;
import net.RevTut.Skywars.libraries.converters.ConvertersAPI;
import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import net.RevTut.Skywars.utils.Message;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.logging.Level;

/**
 * Arena Pre-Game Runnable.
 *
 * <P>Takes care of all the arenas which are on "Pre-Game".</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class ArenaPreGame implements Runnable {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Runnable ID
     */
    private static int id;

    /**
     * Constructor of ArenaPreGame
     *
     * @param plugin the Main class
     */
    public ArenaPreGame(SkyWars plugin) {
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
        ArenaPreGame.id = id;
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
        int remainingTime;
        for (final Arena arena : plugin.arenaManager.getArenas()) {
            // Check if there are players in arena
            if (arena.getPlayers().size() < 1)
                continue;

            // Check status
            if(arena.getStatus() != ArenaStatus.PREGAME)
                continue;

            // Remaining time of that arena
            remainingTime = arena.getRemainingTime();
            if (remainingTime >= 0) {
                onPreGame(arena);
                arena.setRemainingTime(remainingTime - 1);
            } else {
                fromPreGameToInGame(arena);
            }
        }
    }

    /**
     * Sets the player level to remaining time.
     * If the remaining time is of 10, 5...0 it will display a message on the
     * player's screen telling the remaining time.
     *
     * @param arena arena which is on pre game
     */
    private void onPreGame(Arena arena) {
        int remainingTime = arena.getRemainingTime();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;

            alvo.setLevel(remainingTime);

            switch (remainingTime) {
                case 10:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§110"));
                    TitleAPI.sendSubTitle(alvo, "");
                    alvo.playSound(alvo.getLocation(), Sound.NOTE_SNARE_DRUM, 1, 10);
                    break;
                case 9:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§19"));
                    TitleAPI.sendSubTitle(alvo, "");
                    alvo.playSound(alvo.getLocation(), Sound.NOTE_SNARE_DRUM, 1, 10);
                    break;
                case 8:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§98"));
                    TitleAPI.sendSubTitle(alvo, "");
                    alvo.playSound(alvo.getLocation(), Sound.NOTE_SNARE_DRUM, 1, 10);
                    break;
                case 7:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§37"));
                    TitleAPI.sendSubTitle(alvo, "");
                    alvo.playSound(alvo.getLocation(), Sound.NOTE_SNARE_DRUM, 1, 10);
                    break;
                case 6:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§b6"));
                    TitleAPI.sendSubTitle(alvo, "");
                    alvo.playSound(alvo.getLocation(), Sound.NOTE_SNARE_DRUM, 1, 10);
                    break;
                case 5:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§45"));
                    TitleAPI.sendSubTitle(alvo, "");
                    alvo.playSound(alvo.getLocation(), Sound.NOTE_PIANO, 1, 10);
                    break;
                case 4:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§c4"));
                    TitleAPI.sendSubTitle(alvo, "");
                    alvo.playSound(alvo.getLocation(), Sound.NOTE_PIANO, 1, 10);
                    break;
                case 3:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§63"));
                    TitleAPI.sendSubTitle(alvo, "");
                    alvo.playSound(alvo.getLocation(), Sound.NOTE_PIANO, 1, 10);
                    break;
                case 2:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§e2"));
                    TitleAPI.sendSubTitle(alvo, "");
                    alvo.playSound(alvo.getLocation(), Sound.NOTE_PIANO, 1, 10);
                    break;
                case 1:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§a1"));
                    TitleAPI.sendSubTitle(alvo, "");
                    alvo.playSound(alvo.getLocation(), Sound.NOTE_PIANO, 1, 10);
                    break;
                case 0:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§2GO"));
                    TitleAPI.sendSubTitle(alvo, "");
                    alvo.playSound(alvo.getLocation(), Sound.LEVEL_UP, 1, 10);
                    break;
            }
        }
    }

    /**
     * Switch an arena from pre game to in game.
     * Remove the glass block under the player.
     *
     * @param arena arena to switch
     */
    private void fromPreGameToInGame(Arena arena) {
        // Arena Dat
        ArenaDat arenaDat = arena.getArenaDat();
        if (null == arenaDat) {
            plugin.getLogger().log(Level.SEVERE, "ArenaDat is null when changing from PreGame to InGame!");
            return;
        }

        // Change Status
        arena.setStatus(ArenaStatus.INGAME);

        arenaDat.setStartDate(new Date()); // Set start date
        arenaDat.addGameEvent("Comecou o jogo numero " + arenaDat.getGameNumber());

        // Loop all the players
        for (final PlayerDat alvoDat : arena.getPlayers()) {
            final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;

            // Config Player
            if (!plugin.playerManager.configPlayer(alvoDat, alvoDat.getStatus(), GameMode.SURVIVAL, false, false, 0, 0, 20.0, 20, true, true, 0)) {
                plugin.getLogger().log(Level.WARNING, "Error while configuring the player.");
                continue;
            }

            // Give the Kit
            arena.getKitManager().giveChoosenKit(alvoDat);
            arena.getKitManager().giveCompassItem(alvoDat);

            // Remove Glass
            int i = 0;
            Location alvoLocation = alvo.getLocation();
            while (alvoLocation.getBlock().getType() != Material.GLASS && (alvoLocation.getBlock().getType() != Material.STAINED_GLASS) && i < 3) {
                alvoLocation.setY(alvoLocation.getY() - i);
                i++;
            }
            alvoLocation.getBlock().setType(Material.AIR);

            // Status (add some delay so they dont lose life when falling)
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    alvo.sendMessage(Message.getMessage(Message.ALLOWED_OPEN_CHESTS, alvo));
                    alvoDat.setStatus(PlayerStatus.ALIVE); // Set as alive player
                }
            }, 40);
        }
    }
}