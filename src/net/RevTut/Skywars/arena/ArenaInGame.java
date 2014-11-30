package net.RevTut.Skywars.arena;

import net.RevTut.Skywars.SkyWars;
import net.RevTut.Skywars.libraries.algebra.AlgebraAPI;
import net.RevTut.Skywars.libraries.converters.ConvertersAPI;
import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import net.RevTut.Skywars.utils.Message;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Arena InGame Runnable.
 *
 * <P>Takes care of all the arenas which are on "InGame".</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class ArenaInGame implements Runnable {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Runnable ID
     */
    private static int id;

    /**
     * Constructor of ArenaInGame
     *
     * @param plugin the Main class
     */
    public ArenaInGame(SkyWars plugin) {
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
        ArenaInGame.id = id;
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
            if(arena.getStatus() != ArenaStatus.INGAME)
                continue;

            // Remaining time of that arena
            remainingTime = arena.getRemainingTime();
            if (remainingTime >= 0) {
                onInGame(arena);
                arena.setRemainingTime(remainingTime - 1);
            } else {
                fromInGameToEndGame(arena);
            }
        }
    }

    /**
     * Controls the remaining time. If the remaining time is of 60, 10, 5...0 it will display a message on the
     * player's screen telling the remaining time.
     *
     * @param arena arena which is in game
     */
    private void onInGame(Arena arena) {
        int remainingTime = arena.getRemainingTime();
        for (final PlayerDat alvoDat : arena.getPlayers()) {
            final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;

            // Set compass target
            Player closest = AlgebraAPI.closestPlayer(alvo);
            if(null != closest){
                alvo.setCompassTarget(closest.getLocation());

                for(ItemStack itemStack : alvo.getInventory().getContents()) {
                    if(null == itemStack)
                        continue;

                    if(!itemStack.getType().equals(Material.COMPASS))
                        continue;

                    double distance = AlgebraAPI.distanceBetween(alvo.getLocation(), closest.getLocation());

                    ItemMeta compassMeta = itemStack.getItemMeta();
                    compassMeta.setDisplayName("§6" + closest.getName() + " §7- §e" + distance + "m");
                    itemStack.setItemMeta(compassMeta);
                }
            }

            // Send message to dead players
            if(alvoDat.getStatus() == PlayerStatus.DEAD)
                if(remainingTime % 30 == 0)
                    alvo.sendMessage(Message.getMessage(Message.DONT_LEAVE_ARENA, alvo));

            switch (remainingTime) {
                case 60:
                    TitleAPI.sendTimes(alvo, 20, 60, 20);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§b60"));
                    break;
                case 10:
                    TitleAPI.sendTimes(alvo, 20, 60, 20);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§210"));
                    break;
                case 5:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§a5"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 4:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§e4"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 3:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§63"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 2:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§c2"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 1:
                    TitleAPI.sendTimes(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§41"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 0:
                    final ArenaDat arenaDat = arena.getArenaDat();
                    if (arenaDat == null)
                        return;

                    // Points earned for playing the game
                    int poinsEarned = (int) (plugin.pointsPerGame + plugin.pointsPerGame * ((float) alvoDat.getGameKills() / arenaDat.getInitialPlayers().size()) + plugin.pointsPerGame * ((float) plugin.rand.nextInt(26) / 100));
                    alvoDat.addPoints(poinsEarned);

                    // Check winner
                    if (arenaDat.getWinner().equals("NULL")) {
                        // Titles
                        TitleAPI.sendTimes(alvo, 5, 60, 5);
                        TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.TIME_OUT, alvo)));
                        TitleAPI.sendSubTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.NO_WINNER, alvo)));

                        // Sound
                        alvo.playSound(alvo.getLocation(), Sound.EXPLODE, 1, 10);

                        // Chat message
                        alvo.sendMessage(Message.getMessage(Message.GAME_TIMEOUT, alvo));
                    } else {
                        // Title timings
                        TitleAPI.sendTimes(alvo, 5, 60, 5);

                        Player winner = Bukkit.getPlayer(UUID.fromString(arenaDat.getWinner()));
                        if (winner != null) {
                            // Check if he is the winner
                            if (winner.getUniqueId().equals(alvo.getUniqueId())) {
                                TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_WON, alvo)));

                                // Add win
                                alvoDat.addWin();

                                // Points earned for winning
                                poinsEarned = (int) (plugin.pointsPerWin + plugin.pointsPerWin * ((float) alvoDat.getGameKills() / arenaDat.getInitialPlayers().size()) + plugin.pointsPerWin * ((float) plugin.rand.nextInt(51) / 100));
                                alvoDat.addPoints(poinsEarned);

                                // Sound
                                alvo.playSound(alvo.getLocation(), Sound.LEVEL_UP, 1, 10);
                            } else{
                                TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_LOST, alvo)));

                                // Sound
                                alvo.playSound(alvo.getLocation(), Sound.ORB_PICKUP, 1, 10);
                            }

                            // Subtitle
                            TitleAPI.sendSubTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.WINNER, alvo)) + winner.getName());

                            // Chat message
                            alvo.sendMessage(Message.getMessage(Message.GAME_WINNER, alvo) + winner.getName());
                        } else{
                            // Titles
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_LOST, alvo)));
                            TitleAPI.sendSubTitle(alvo, "");

                            // Sound
                            alvo.playSound(alvo.getLocation(), Sound.ORB_PICKUP, 1, 10);

                            // Chat message
                            alvo.sendMessage(Message.getMessage(Message.GAME_WINNER, alvo) + "John Doe");
                        }
                    }
                    break;
            }
        }
    }

    /**
     * Switch an arena from in game to end game.
     * Teleport all the players to the center of the arena.
     *
     * @param arena arena to switch
     */
    private void fromInGameToEndGame(Arena arena) {
        // Arena Dat
        ArenaDat arenaDat = arena.getArenaDat();
        if (null == arenaDat) {
            plugin.getLogger().log(Level.SEVERE, "ArenaDat is null when changing from InGame to EndGame!");
            return;
        }

        // Change Status
        arena.setStatus(ArenaStatus.ENDGAME);

        arenaDat.setEndDate(new Date()); // Set end date
        arenaDat.addGameEvent("Terminou o jogo numero " + arenaDat.getGameNumber());

        // Message game report
        arena.sendMessage(Message.GAME_REPORT, arenaDat.getGameNumber());

        // Teleport to Center Location
        ArenaLocation arenaLocation = arena.getArenaLocation();
        if (arenaLocation == null)
            return;

        final Location centerLocation = arenaLocation.getDeathSpawnLocation();
        int i = 0;
        for (PlayerDat alvoDat : arena.getPlayers()) {
            final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;

            // Config Player
            if (!plugin.playerManager.configPlayer(alvoDat, alvoDat.getStatus(), GameMode.ADVENTURE, true, true, 0, 0, 20.0, 20, true, true, 0)) {
                plugin.getLogger().log(Level.WARNING, "Error while configuring the player.");
                continue;
            }

            // Unhide to the arena
            plugin.arenaManager.unhideToArena(alvo, false);

            // Teleport
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    alvo.teleport(centerLocation);
                }
            }, i);
            alvo.teleport(centerLocation);
            i++;
        }
    }
}