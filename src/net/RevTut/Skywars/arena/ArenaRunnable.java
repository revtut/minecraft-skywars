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
 * Created by João on 02/11/2014.
 */
public class ArenaRunnable implements Runnable {

    private Main plugin;

    public ArenaRunnable(Main plugin) {
        this.plugin = plugin;
    }

    private static int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static void cancel() {
        Bukkit.getScheduler().cancelTask(id);
    }

    @Override
    public void run() {
        int remainingTime;
        for (Arena arena : Arena.getArenas()) {
            if (arena.getPlayers().size() < 1)
                continue;
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
                arena.setRemainingTime(remainingTime - 1);
            } else {
                // Change Arena Status
                if (arena.getStatus() == ArenaStatus.LOBBY)
                    if(arena.getPlayers().size() >= Arena.minPlayers)
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

    /* Lobby */
    public void onLobby(Arena arena) {
        int remainingTime = arena.getRemainingTime();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;
            alvo.setLevel(remainingTime);
        }
    }

    /* PreGame */
    public void onPreGame(Arena arena) {
        int remainingTime = arena.getRemainingTime();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;
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

    /* InGame */
    public void onInGame(Arena arena) {
        int remainingTime = arena.getRemainingTime();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;
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
                    TitleAPI.sendTimings(alvo, 5, 20, 5);
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§4TIMEOUT"));
                    TitleAPI.sendSubTitle(alvo, Converters.convertToJSON("§7NO WINNER"));
                    alvo.playSound(alvo.getLocation(), Sound.EXPLODE, 1, 1);
                    break;
            }
        }
    }

    /* EndGame */
    public void onEndGame(Arena arena) {
        // Launch Firework
        ArenaDat arenaDat = arena.getArenaDat();
        if (arenaDat == null)
            return;
        if(arenaDat.getWinner() == null)
            return;
        UUID uuid = UUID.fromString(arenaDat.getWinner());
        if (uuid == null)
            return;
        Player winner = Bukkit.getPlayer(uuid);
        if (winner == null)
            return;
        Fireworks.launchFirework(winner, 10, 2);
    }

    /* Lobby To PreGame */
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
            if (alvo == null)
                continue;
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

    /* PreGame To InGame */
    public void fromPreGameToInGame(Arena arena) {
        // Change Status
        arena.setStatus(ArenaStatus.INGAME);
        // Remove Glass
        for (PlayerDat alvoDat : arena.getPlayers()) {
            int i = 0;
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;
            Location alvoLocation = alvo.getLocation();
            while (alvoLocation.getBlock().getType() != Material.GLASS && i < 3) {
                alvoLocation.setY(alvoLocation.getY() - 1);
                i++;
            }
            alvoLocation.getBlock().setType(Material.AIR);
        }
    }

    /* InGame To EndGame */
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
            if (alvo == null)
                continue;
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

    /* EndGame To Lobby */
    public void fromEndGameToLobby(final Arena arena) {
        // Check if we can transfer this players to a new arena
        List<Arena> arenasAvailable = Arena.getAvailableArenas();
        Arena arenaMove = null;
        for(Arena arenaAlvo : arenasAvailable){
            if(arenaAlvo.getPlayers().size() + arena.getPlayers().size() <= Arena.maxPlayers) {
                arenaMove = arenaAlvo;
                break;
            }
        }
        // Keep the same arena or move to an existing one
        if(arenaMove == null){
            int i = 0;
            final Location lobbyLocation = arena.getArenaLocation().getLobbyLocation();
            for(PlayerDat alvoDat : arena.getPlayers()){
                final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
                if(alvo == null)
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
            }, i);
        }else{
            int i = 0;
            // Remove all the players from this arena
            for(final PlayerDat alvoDat : arena.getPlayers()){
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        arena.removePlayer(alvoDat);
                        // Add to new arena
                        if (!Arena.addPlayer(alvoDat, arena)) {
                            /** Send him to Hub. Error while adding to arena */
                            return;
                        }
                    }
                }, i);
                i++;
            }
            // Delete The Arena
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    Arena.removeMap(arena, true);
                }
            }, i);
        }
    }
}