package net.RevTut.Skywars.arena;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.libraries.appearance.Fireworks;
import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.utils.Converters;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

/**
 * Arena Runnable.
 *
 * <P>Takes care of all the arenas when it comes to remaining time.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class ArenaRunnable implements Runnable {
    /** Main class */
    private final Main plugin;

    /** Runnable ID */
    private static int id;

    /**
     * Constructor of ArenaRunnable
     *
     * @param plugin        the Main class
     */
    public ArenaRunnable(Main plugin) {
        this.plugin = plugin;
    }

    /** Returns the task ID */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the runnable.
     *
     * @param id        new ID for the task
     */
    public void setId(int id) {
        this.id = id;
    }

    /** Cancel this task from being run. */
    public static void cancel() {
        Bukkit.getScheduler().cancelTask(id);
    }

    /** Runnable which controls the remaining time of all arenas */
    @Override
    public void run() {
        int remainingTime;
        for (Arena arena : Arena.getArenas()) {
            // Check if there are players in arena
            if (arena.getPlayers().size() < 1)
                continue;
            // Remaining time of that arena
            remainingTime = arena.getRemainingTime();
            if (remainingTime >= 0) {
                // Remaining time is over ZERO
                if (arena.getStatus() == ArenaStatus.LOBBY)
                    onLobby(arena);
                else if (arena.getStatus() == ArenaStatus.PREGAME)
                    onPreGame(arena);
                else if (arena.getStatus() == ArenaStatus.INGAME)
                    onInGame(arena);
                else if (arena.getStatus() == ArenaStatus.ENDGAME)
                    onEndGame(arena);
                // Decrement remaining time
                if (arena.getStatus() == ArenaStatus.LOBBY && remainingTime > 30 && arena.getPlayers().size() >= Arena.minReduceTimePlayers){
                    arena.sendMessageToArena("§7|§3SkyWars47| §6O minimo de jogadores foi atingido. O tempo foi reduzido.");
                    arena.setRemainingTime(31);
                }
                arena.setRemainingTime(remainingTime - 1);
            } else {
                // Change Arena Status
                if (arena.getStatus() == ArenaStatus.LOBBY)
                    // Minimum Players in the arena
                    if (arena.getPlayers().size() >= Arena.minPlayers)
                        fromLobbyToPreGame(arena);
                    else
                        arena.setRemainingTime(ArenaStatus.LOBBY.getTime());
                else if (arena.getStatus() == ArenaStatus.PREGAME)
                    fromPreGameToInGame(arena);
                else if (arena.getStatus() == ArenaStatus.INGAME)
                    fromInGameToEndGame(arena);
                else if (arena.getStatus() == ArenaStatus.ENDGAME)
                    fromEndGameToLobby(arena);
            }
        }
    }

    /**
     * Sets the player level to remaining time.
     *
     * @param arena     arena which is on lobby
     */
    public void onLobby(Arena arena) {
        int remainingTime = arena.getRemainingTime();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null){
                arena.removePlayer(alvoDat);
                continue;
            }
            alvo.setLevel(remainingTime);
        }
    }

    /**
     * Sets the player level to remaining time.
     * If the remaining time is of 10, 5...0 it will display a message on the
     * player's screen telling the remaining time.
     *
     * @param arena     arena which is on pre game
     */
    public void onPreGame(Arena arena) {
        int remainingTime = arena.getRemainingTime();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null){
                arena.removePlayer(alvoDat);
                continue;
            }
            alvo.setLevel(remainingTime);
            switch (remainingTime) {
                case 10:
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§b10"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 5:
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§45"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 4:
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§c4"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 3:
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§63"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 2:
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§e2"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 1:
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§a1"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 0:
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§2GO"));
                    TitleAPI.sendSubTitle(alvo, "");
                    alvo.playSound(alvo.getLocation(), Sound.ORB_PICKUP, 1, 1);
                    break;
            }
        }
    }

    /**
     * Controls the remaining time. If the remaining time is of 60, 10, 5...0 it will display a message on the
     * player's screen telling the remaining time.
     *
     * @param arena     arena which is in game
     */
    public void onInGame(Arena arena) {
        int remainingTime = arena.getRemainingTime();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null){
                arena.removePlayer(alvoDat);
                continue;
            }
            switch (remainingTime) {
                case 60:
                    TitleAPI.sendTimings(alvo, 20, 60, 20);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§b60"));
                    TitleAPI.sendSubTitle(alvo, Converters.convertToJSON("§7Seconds Remaining"));
                    break;
                case 10:
                    TitleAPI.sendTimings(alvo, 20, 60, 20);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§210"));
                    TitleAPI.sendSubTitle(alvo, Converters.convertToJSON("§7Seconds Remaining"));
                    break;
                case 5:
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§a5"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 4:
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§e4"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 3:
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§63"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 2:
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§c2"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 1:
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§41"));
                    TitleAPI.sendSubTitle(alvo, "");
                    break;
                case 0:
                    ArenaDat arenaDat = arena.getArenaDat();
                    if (arenaDat == null)
                        return;
                    if (arenaDat.getWinner() == null) {
                        TitleAPI.sendTimings(alvo, 5, 20, 5);
                        TitleAPI.sendTitle(alvo, Converters.convertToJSON("§4TIMEOUT"));
                        TitleAPI.sendSubTitle(alvo, Converters.convertToJSON("§7NO WINNER"));
                    } else {
                        TitleAPI.sendTimings(alvo, 5, 20, 5);
                        if (arenaDat.getWinner().equals(alvo.getUniqueId().toString()))
                            TitleAPI.sendTitle(alvo, Converters.convertToJSON("§aYOU WON"));
                        else
                            TitleAPI.sendTitle(alvo, Converters.convertToJSON("§cYOU LOST"));
                        TitleAPI.sendSubTitle(alvo, Converters.convertToJSON(arenaDat.getWinner()));
                    }
                    alvo.playSound(alvo.getLocation(), Sound.EXPLODE, 1, 1);
                    break;
            }
        }
    }

    /**
     * Launch fireworks on the winner of the game.
     *
     * @param arena     arena which is on end game
     */
    public void onEndGame(Arena arena) {
        // Launch Firework
        ArenaDat arenaDat = arena.getArenaDat();
        if (arenaDat == null)
            return;
        if (arenaDat.getWinner() == null)
            return;
        UUID uuid = UUID.fromString(arenaDat.getWinner());
        if (uuid == null)
            return;
        Player winner = Bukkit.getPlayer(uuid);
        if (winner == null)
            return;
        Fireworks.launchFirework(winner, 10, 2);
    }

    /**
     * Switch an arena from lobby to pre game.
     * Teleports all the players to the spawn locations with delay between teleports.
     *
     * @param arena     arena to switch
     */
    public void fromLobbyToPreGame(Arena arena) {
        // Change Status
        arena.setStatus(ArenaStatus.PREGAME);
        // Send Players To Spawns
        int i = 0;
        ArenaLocation arenaLocation = arena.getArenaLocation();
        if (arenaLocation == null)
            return;
        for (PlayerDat alvoDat : arena.getPlayers()) {
            final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null){
                arena.removePlayer(alvoDat);
                continue;
            }
            final Location spawnLocation = arenaLocation.getSpawnLocations().get(i);
            if (spawnLocation == null)
                continue;
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    alvo.teleport(spawnLocation);
                }
            }, i);
            i++;
        }
    }

    /**
     * Switch an arena from pre game to in game.
     * Remove the glass block under the player.
     *
     * @param arena     arena to switch
     */
    public void fromPreGameToInGame(Arena arena) {
        // Change Status
        arena.setStatus(ArenaStatus.INGAME);
        // Remove Glass
        for (PlayerDat alvoDat : arena.getPlayers()) {
            int i = 0;
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null){
                arena.removePlayer(alvoDat);
                continue;
            }
            Location alvoLocation = alvo.getLocation();
            while (alvoLocation.getBlock().getType() != Material.GLASS && i < 3) {
                alvoLocation.setY(alvoLocation.getY() - 1);
                i++;
            }
            alvoLocation.getBlock().setType(Material.AIR);
        }
    }

    /**
     * Switch an arena from in game to end game.
     * Teleport all the players to the center of the arena.
     *
     * @param arena     arena to switch
     */
    public void fromInGameToEndGame(Arena arena) {
        // Change Status
        arena.setStatus(ArenaStatus.ENDGAME);
        // Remove Glass
        int i = 0;
        ArenaLocation arenaLocation = arena.getArenaLocation();
        if (arenaLocation == null)
            return;
        final Location centerLocation = arenaLocation.getDeathSpawnLocation();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null){
                arena.removePlayer(alvoDat);
                continue;
            }
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

    /**
     * Switch an arena from end game to lobby.
     * Teleport all the players to the center of the arena.
     *
     * @param arena     arena to switch
     */
    public void fromEndGameToLobby(final Arena arena) {
        // Check if we can transfer this players to a new arena
        List<Arena> arenasAvailable = Arena.getAvailableArenas();
        Arena arenaMove = null;
        for (Arena arenaAlvo : arenasAvailable) {
            if (arenaAlvo.getPlayers().size() + arena.getPlayers().size() <= Arena.maxPlayers) {
                arenaMove = arenaAlvo;
                break;
            }
        }
        // Keep the same arena or move to an existing one
        if (arenaMove == null) {
            int i = 0;
            final Location lobbyLocation = arena.getArenaLocation().getLobbyLocation();
            for (PlayerDat alvoDat : arena.getPlayers()) {
                final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
                if (alvo == null)
                    continue;
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        alvo.teleport(lobbyLocation);
                    }
                }, i);
                alvo.teleport(lobbyLocation);
                i++;
            }
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    Arena.resetArena(arena);
                }
            }, i + 20);
        } else {
            int i = 0;
            // Remove all the players from this arena
            final Arena newArena = arenaMove;
            for (final PlayerDat alvoDat : arena.getPlayers()) {
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        arena.removePlayer(alvoDat);
                        // Add to new arena
                        if (!Arena.addPlayer(alvoDat, newArena)) {
                            /** Send him to Hub. Error while adding to arena */
                            System.out.println("Could not add player to new arena.");
                        }
                    }
                }, i);
                i++;
            }
            // Delete The Arena
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    Arena.removeArena(arena);
                }
            }, i + 20);
        }
    }
}