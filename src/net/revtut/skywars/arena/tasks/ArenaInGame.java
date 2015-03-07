package net.revtut.skywars.arena.tasks;

import net.revtut.libraries.actionbar.ActionBarAPI;
import net.revtut.libraries.algebra.AlgebraAPI;
import net.revtut.libraries.bypasses.BypassesAPI;
import net.revtut.libraries.converters.ConvertersAPI;
import net.revtut.libraries.titles.TitleAPI;
import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaDat;
import net.revtut.skywars.arena.ArenaLocation;
import net.revtut.skywars.arena.ArenaStatus;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.player.PlayerStatus;
import net.revtut.skywars.utils.Message;
import net.revtut.skywars.utils.Reward;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        plugin.arenaManager.getArenas().forEach(arena -> {
            int remainingTime;

            // Check if there are players in arena
            if (arena.getPlayers().size() < 1)
                return;

            // Check status
            if(arena.getStatus() != ArenaStatus.INGAME)
                return;

            // Remaining time of that arena
            remainingTime = arena.getRemainingTime();
            if (remainingTime >= 0) {
                onInGame(arena);
                arena.setRemainingTime(remainingTime - 1);
            } else {
                fromInGameToEndGame(arena);
            }
        });
    }

    /**
     * Controls the remaining time. If the remaining time is of 60, 10, 5...0 it will display a message on the
     * player's screen telling the remaining time.
     *
     * @param arena arena which is in game
     */
    private void onInGame(Arena arena) {
        int remainingTime = arena.getRemainingTime();
        arena.getPlayers().forEach(alvoDat -> {
            final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                return;

            // Update the compass
            updateClosestTarget(alvoDat);

            switch (remainingTime) {
                case 60:
                    TitleAPI.sendTimes(alvo, 20, 60, 20);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§b60"));
                    break;
                case 30:
                    TitleAPI.sendTimes(alvo, 20, 60, 20);
                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§b30"));
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

                    // Respawn the player if needed
                    if(alvo.isDead())
                        BypassesAPI.respawnBypass(alvo);

                    // Bypass spectatores
                    if(alvoDat.getStatus() == PlayerStatus.SPECTATOR)
                        return;

                    // Points earned for playing the game
                    int pointsEarned = Reward.PARTICIPATION.calculatePoints(alvoDat);

                    // Arena ended without any winner
                    if (arenaDat.getWinner().equals("NULL")) {
                        // Titles
                        TitleAPI.sendTimes(alvo, 5, 60, 5);
                        TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.TIME_OUT, alvo)));
                        TitleAPI.sendSubTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.NO_WINNER, alvo)));

                        // Sound
                        alvo.playSound(alvo.getLocation(), Sound.EXPLODE, 1, 10);

                        // Chat message
                        alvo.sendMessage(Message.getMessage(Message.GAME_TIMEOUT, alvo));
                        break;
                    }

                    Player winner = Bukkit.getPlayer(UUID.fromString(arenaDat.getWinner()));
                    String winnerName = "John Doe";
                    if(winner != null)
                        winnerName = winner.getName();

                    // Check if he is the winner
                    if (winnerName.equalsIgnoreCase(alvo.getName())) {
                        // Points earned for winning
                        pointsEarned += Reward.WIN.calculatePoints(alvoDat);

                        // Add win
                        alvoDat.addWin();

                        // Sound
                        alvo.playSound(alvo.getLocation(), Sound.LEVEL_UP, 1, 10);

                        // Titles
                        TitleAPI.sendTimes(alvo, 5, 60, 5);
                        TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_WON, alvo)));
                        TitleAPI.sendSubTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.WINNER, alvo) + winnerName));
                    } else {
                        // Sound
                        alvo.playSound(alvo.getLocation(), Sound.ORB_PICKUP, 1, 10);

                        // Titles
                        TitleAPI.sendTimes(alvo, 5, 60, 5);
                        TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_LOST, alvo)));
                        TitleAPI.sendSubTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.WINNER, alvo) + winnerName));
                    }

                    // Add the points
                    alvoDat.addPoints(pointsEarned);

                    // Chat message
                    alvo.sendMessage(Message.getMessage(Message.GAME_WINNER, alvo) + winnerName);

                    // Update stats
                    plugin.scoreBoardManager.updateWinsLosses(alvoDat);

                    break;
            }
        });
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
        arenaDat.addGameEvent("The game " + arenaDat.getGameNumber() + " has ended!");

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
            Bukkit.getScheduler().runTaskLater(plugin, () -> alvo.teleport(centerLocation), i);
            i++;
        }
    }

    /**
     * Update the closest target to a given player
     *
     * @param playerDat player dat to uppdate the closest target
     */
    private void updateClosestTarget(PlayerDat playerDat) {
        final Player alvo = Bukkit.getPlayer(playerDat.getUUID());
        if (alvo == null)
            return;

        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if(null == arena)
            return;

        // Create list with all alive players
        List<Player> playerList = new ArrayList<>();
        for(PlayerDat targetDat : arena.getAlivePlayers()) {
            Player target = Bukkit.getPlayer(targetDat.getUUID());
            if(target == null)
                continue;
            playerList.add(target);
        }
        Player closest = AlgebraAPI.closestPlayer(playerList, alvo);
        if(null == closest)
            return;

        double distance = AlgebraAPI.distanceBetween(alvo.getLocation(), closest.getLocation());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        // Set compass target
        if(playerDat.getStatus() == PlayerStatus.ALIVE) { // Player is alive, change compass
            alvo.setCompassTarget(closest.getLocation());

            for(ItemStack itemStack : alvo.getInventory().getContents()) {
                if(null == itemStack)
                    continue;

                if(!itemStack.getType().equals(Material.COMPASS))
                    continue;


                ItemMeta compassMeta = itemStack.getItemMeta();
                compassMeta.setDisplayName("§6" + closest.getName() + " §7- §e" + decimalFormat.format(distance) + "m");
                itemStack.setItemMeta(compassMeta);
                break;
            }
        } else if (playerDat.getStatus() == PlayerStatus.DEAD || playerDat.getStatus() == PlayerStatus.SPECTATOR) { // Player is spectating send action bar message
            ActionBarAPI.sendActionBar(alvo, ConvertersAPI.convertToJSON("§6" + closest.getName() + " §7- §e" + decimalFormat.format(distance) + "m"), 1);

            // Send message to spectator players
            if(arena.getRemainingTime() % 30 == 0)
                alvo.sendMessage(Message.getMessage(Message.DONT_LEAVE_ARENA, alvo));
        }
    }
}