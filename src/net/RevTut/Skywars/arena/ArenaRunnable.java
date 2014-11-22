package net.RevTut.Skywars.arena;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.libraries.appearance.AppearanceAPI;
import net.RevTut.Skywars.libraries.converters.ConvertersAPI;
import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.util.Date;
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

    /**
     * Main class
     */
    private final Main plugin;

    /**
     * Runnable ID
     */
    private static int id;

    /**
     * Constructor of ArenaRunnable
     *
     * @param plugin the Main class
     */
    public ArenaRunnable(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Returns the task ID
     *
     * @return ID of the task
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the runnable.
     *
     * @param id new ID for the task
     */
    public void setId(int id) {
        ArenaRunnable.id = id;
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
                if (arena.getStatus() == ArenaStatus.LOBBY && remainingTime > 30 && arena.getPlayers().size() >= plugin.arenaManager.minReduceTimePlayers) {
                    arena.sendMessage("§7|§3SkyWars47| §6O minimo de jogadores foi atingido. O tempo foi reduzido.");
                    remainingTime = 31;
                }
                arena.setRemainingTime(remainingTime - 1);
            } else {
                // Change Arena Status
                if (arena.getStatus() == ArenaStatus.LOBBY)
                    // Minimum Players in the arena
                    if (arena.getPlayers().size() >= plugin.arenaManager.minPlayers)
                        fromLobbyToPreGame(arena);
                    else {
                        arena.sendMessage("§7|§3SkyWars§7| §4Minimo de " + plugin.arenaManager.minPlayers + " jogadores nao atingidos.");
                        arena.setRemainingTime(ArenaStatus.LOBBY.getTime());
                    }
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
     * @param arena arena which is on lobby
     */
    public void onLobby(Arena arena) {
        int remainingTime = arena.getRemainingTime();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;
            alvo.setLevel(remainingTime);
        }
    }

    /**
     * Sets the player level to remaining time.
     * If the remaining time is of 10, 5...0 it will display a message on the
     * player's screen telling the remaining time.
     *
     * @param arena arena which is on pre game
     */
    public void onPreGame(Arena arena) {
        int remainingTime = arena.getRemainingTime();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;
            alvo.setLevel(remainingTime);
            switch (remainingTime) {
                case 10:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 5, 20, 5);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§b10"));
                            TitleAPI.sendSubTitle(alvo, "");
                        }
                    });
                    break;
                case 5:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 5, 20, 5);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§45"));
                            TitleAPI.sendSubTitle(alvo, "");
                        }
                    });
                    break;
                case 4:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 5, 20, 5);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§c4"));
                            TitleAPI.sendSubTitle(alvo, "");
                        }
                    });
                    break;
                case 3:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 5, 20, 5);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§63"));
                            TitleAPI.sendSubTitle(alvo, "");
                        }
                    });
                    break;
                case 2:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 5, 20, 5);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§e2"));
                            TitleAPI.sendSubTitle(alvo, "");
                        }
                    });
                    break;
                case 1:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 5, 20, 5);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§a1"));
                            TitleAPI.sendSubTitle(alvo, "");
                        }
                    });
                    break;
                case 0:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 5, 20, 5);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§2GO"));
                            TitleAPI.sendSubTitle(alvo, "");
                        }
                    });
                    alvo.playSound(alvo.getLocation(), Sound.ORB_PICKUP, 1, 1);
                    break;
            }
        }
    }

    /**
     * Controls the remaining time. If the remaining time is of 60, 10, 5...0 it will display a message on the
     * player's screen telling the remaining time.
     *
     * @param arena arena which is in game
     */
    public void onInGame(Arena arena) {
        int remainingTime = arena.getRemainingTime();
        for (final PlayerDat alvoDat : arena.getPlayers()) {
            final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;
            switch (remainingTime) {
                case 60:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 20, 60, 20);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§b60"));
                            TitleAPI.sendSubTitle(alvo, ConvertersAPI.convertToJSON("§7Segundos Restantes"));
                        }
                    });
                    break;
                case 10:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 20, 60, 20);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§210"));
                            TitleAPI.sendSubTitle(alvo, ConvertersAPI.convertToJSON("§7Segundos Restantes"));
                        }
                    });
                    break;
                case 5:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 5, 20, 5);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§a5"));
                            TitleAPI.sendSubTitle(alvo, "");
                        }
                    });
                    break;
                case 4:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 5, 20, 5);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§e4"));
                            TitleAPI.sendSubTitle(alvo, "");
                        }
                    });
                    break;
                case 3:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 5, 20, 5);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§63"));
                            TitleAPI.sendSubTitle(alvo, "");
                        }
                    });
                    break;
                case 2:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 5, 20, 5);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§c2"));
                            TitleAPI.sendSubTitle(alvo, "");
                        }
                    });
                    break;
                case 1:
                    Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                        @Override
                        public void run() {
                            TitleAPI.sendTimings(alvo, 5, 20, 5);
                            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§41"));
                            TitleAPI.sendSubTitle(alvo, "");
                        }
                    });
                    break;
                case 0:
                    final ArenaDat arenaDat = arena.getArenaDat();
                    if (arenaDat == null)
                        return;
                    // Points earned
                    int poinsEarned = (int) (plugin.pointsPerGame + plugin.pointsPerGame * ((float) alvoDat.getGameKills() / arenaDat.getInitialPlayers().size()) + plugin.pointsPerGame * ((float) plugin.rand.nextInt(26) / 100));
                    alvoDat.addPoints(poinsEarned);
                    // Check winner
                    if (arenaDat.getWinner().equals("NULL")) {
                        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                            @Override
                            public void run() {
                                TitleAPI.sendTimings(alvo, 5, 60, 5);
                                TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§4TEMPO ESGOTADO"));
                                TitleAPI.sendSubTitle(alvo, ConvertersAPI.convertToJSON("§7SEM VENCEDOR"));
                            }
                        });
                    } else {
                        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                            @Override
                            public void run() {
                                // Title timings
                                TitleAPI.sendTimings(alvo, 5, 60, 5);
                                Player winner = Bukkit.getPlayer(UUID.fromString(arenaDat.getWinner()));
                                if (winner != null){
                                    // Title
                                    if (winner.getUniqueId().equals(alvo.getUniqueId())) {
                                        TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§aVENCESTE"));
                                        alvoDat.addWin(); // Add win
                                        // Points earned
                                        int poinsEarned = (int) (plugin.pointsPerWin + plugin.pointsPerWin * ((float) alvoDat.getGameKills() / arenaDat.getInitialPlayers().size()) + plugin.pointsPerWin * ((float) plugin.rand.nextInt(51) / 100));
                                        alvoDat.addPoints(poinsEarned);
                                    } else
                                        TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§4PERDESTE"));
                                    // Subtitle
                                    TitleAPI.sendSubTitle(alvo, ConvertersAPI.convertToJSON("§7Vencedor: " + winner.getName()));
                                }
                                else{
                                    TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§4PERDESTE"));
                                    TitleAPI.sendSubTitle(alvo, "");
                                }
                            }
                        });
                    }
                    alvo.playSound(alvo.getLocation(), Sound.EXPLODE, 1, 1);
                    break;
            }
        }
        if (remainingTime == 0) {
            ArenaDat arenaDat = arena.getArenaDat();
            if (arenaDat == null)
                return;
            if (arenaDat.getWinner().equals("NULL")) {
                arena.sendMessage("§7|" + "§3Sky Wars" + "§7| §4Tempo esgotado. Nao houve um vencedor.");
            } else {
                Player winner = Bukkit.getPlayer(UUID.fromString(arenaDat.getWinner()));
                if (null != winner) {
                    arena.sendMessage("§7|" + "§3Sky Wars" + "§7| §aVencedor do jogo foi " + winner.getName());
                } else
                    arena.sendMessage("§7|" + "§3Sky Wars" + "§7| §aVencedor do jogo ja saiu do jogo.");
            }
        }
    }

    /**
     * Launch fireworks on the winner of the game.
     *
     * @param arena arena which is on end game
     */
    public void onEndGame(Arena arena) {
        // Launch Firework
        ArenaDat arenaDat = arena.getArenaDat();
        if (arenaDat == null)
            return;
        if (arenaDat.getWinner().equals("NULL"))
            return;
        UUID uuid = UUID.fromString(arenaDat.getWinner());
        Player winner = Bukkit.getPlayer(uuid);
        if (winner == null)
            return;
        AppearanceAPI.launchFirework(winner, 10, 2);
    }

    /**
     * Switch an arena from lobby to pre game.
     * Teleports all the players to the spawn locations with delay between teleports.
     *
     * @param arena arena to switch
     */
    public void fromLobbyToPreGame(Arena arena) {
        // Change Status
        arena.setStatus(ArenaStatus.PREGAME);
        // Arena Dat
        ArenaDat arenaDat = arena.getArenaDat();
        if (null == arenaDat) {
            System.out.println("ArenaDat is null when changing from Lobby to PreGame!");
            return;
        }
        // Message
        String mapName = arena.getMapName().replace("" + arena.getArenaNumber(), "").replaceAll("_", " ");
        arena.sendMessage("§7|" + "§3Sky Wars" + "§7| §6Mapa de Jogo: " + mapName);
        // Send Players To Spawns
        int i = 0;
        ArenaLocation arenaLocation = arena.getArenaLocation();
        if (null == arenaLocation) {
            System.out.println("Arena location is null when changing from Lobby to PreGame!");
            return;
        }
        for (PlayerDat alvoDat : arena.getPlayers()) {
            final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;
            // Config Player
            if (!plugin.playerManager.configPlayer(alvoDat, alvoDat.getStatus(), GameMode.ADVENTURE, false, false, 0, 0, 20.0, 20, true, true, 0)) {
                System.out.println("Error while configuring the player.");
                continue;
            }
            // Give kit menu to the player
            arena.getKitManager().giveKitMenuItem(alvoDat);
            // Teleport player
            final Location spawnLocation = arenaLocation.getSpawnLocations().get(i);
            if (spawnLocation == null) {
                System.out.println("Spawn location " + i + " is null when chaning from Lobby to PreGame");
                continue;
            }
            arenaDat.addInitialPlayer(alvoDat.getUUID().toString()); // Add to initial players list
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
     * @param arena arena to switch
     */
    public void fromPreGameToInGame(Arena arena) {
        // Change Status
        arena.setStatus(ArenaStatus.INGAME);
        // Arena Dat
        ArenaDat arenaDat = arena.getArenaDat();
        if (null == arenaDat) {
            System.out.println("ArenaDat is null when changing from PreGame to InGame!");
            return;
        }
        arenaDat.setStartDate(new Date()); // Set start date
        arenaDat.addGameEvent("Comecou o jogo numero " + arenaDat.getGameNumber());
        // Loop all the players
        for (final PlayerDat alvoDat : arena.getPlayers()) {
            int i = 0;
            final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;
            // Config Player
            if (!plugin.playerManager.configPlayer(alvoDat, alvoDat.getStatus(), GameMode.SURVIVAL, false, false, 0, 0, 20.0, 20, true, true, 0)) {
                System.out.println("Error while configuring the player.");
                continue;
            }
            // Give the Kit
            arena.getKitManager().giveChoosenKit(alvoDat);
            // Status (add some delay so they dont lose life when falling)
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    alvo.sendMessage("§7|" + "§3Sky Wars" + "§7| §aPodes agora abrir os baus!");
                    alvoDat.setStatus(PlayerStatus.ALIVE); // Set as alive player
                }
            }, 40);
            // Remove Glass
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
     * @param arena arena to switch
     */
    public void fromInGameToEndGame(Arena arena) {
        // Change Status
        arena.setStatus(ArenaStatus.ENDGAME);
        // Arena Dat
        ArenaDat arenaDat = arena.getArenaDat();
        if (null == arenaDat) {
            System.out.println("ArenaDat is null when changing from InGame to EndGame!");
            return;
        }
        arenaDat.setEndDate(new Date()); // Set end date
        arenaDat.addGameEvent("Terminou o jogo numero " + arenaDat.getGameNumber());
        // Message Game Number
        arena.sendMessage("§7|" + "§3Sky Wars" + "§7| §6Para reportar o jogo, anexe o ID: " + arenaDat.getGameNumber());
        // Teleport to Center Location
        int i = 0;
        ArenaLocation arenaLocation = arena.getArenaLocation();
        if (arenaLocation == null)
            return;
        final Location centerLocation = arenaLocation.getDeathSpawnLocation();
        for (PlayerDat alvoDat : arena.getPlayers()) {
            final Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (alvo == null)
                continue;
            // Config Player
            if (!plugin.playerManager.configPlayer(alvoDat, alvoDat.getStatus(), GameMode.ADVENTURE, true, true, 0, 0, 20.0, 20, true, true, 0)) {
                System.out.println("Error while configuring the player.");
                continue;
            }
            // Unhide
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

    /**
     * Switch an arena from end game to lobby.
     * Teleport all the players to the center of the arena.
     *
     * @param arena arena to switch
     */
    public void fromEndGameToLobby(final Arena arena) {
        // Check if we can transfer this players to a new arena
        List<Arena> arenasAvailable = plugin.arenaManager.getAvailableArenas();
        Arena arenaMove = null;
        for (Arena arenaAlvo : arenasAvailable) {
            if (arenaAlvo.getPlayers().size() + arena.getPlayers().size() <= plugin.arenaManager.maxPlayers) {
                arenaMove = arenaAlvo;
                break;
            }
        }

        // Set the new arena
        final Arena newArena;
        if (arenaMove != null) {
            newArena = arenaMove; // Move to the new arena
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.arenaManager.removeArena(arena);
                }
            }, 100);
        } else {
            newArena = arena; // Stay on the same arena
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.arenaManager.resetArena(arena);
                }
            }, 100);
        }

        // Remove players and add them to new arena
        int i = 0;
        for (final PlayerDat alvoDat : arena.getPlayers()) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
                @Override
                public void run() {
                    plugin.arenaManager.removePlayer(alvoDat, false);
                    // Add to new arena
                    if (!plugin.arenaManager.addPlayer(alvoDat, newArena)) {
                        // Send him to Hub. Error while adding to arena
                        Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
                        if(alvo != null)
                            plugin.connectServer(alvo, "hub");
                        System.out.println("Could not add player to the new arena.");
                    }
                }
            }, i);
            i++;
        }
    }
}