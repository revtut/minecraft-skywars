package net.RevTut.Skywars.arena;

import net.RevTut.Skywars.libraries.world.WorldAPI;
import net.RevTut.Skywars.libraries.world.WorldServerNMS;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by João on 31/10/2014.
 */
public class Arena {
    /* Arena's List */
    private static List<Arena> arenas = new ArrayList<Arena>();

    /* Arena's Configuration */
    public static final int minPlayers = 8; // Minimum player for the game starts
    public static final int minReduceTimePlayers = 16; // Minimum player for the lobby time reduces
    public static final int maxPlayers = 24; // Maximum player that can play in arena

    /* Arena Info */
    private final int arenaNumber;
    private String mapName;
    private int remainingTime; // Arena's remaining time. It depends on the arena's status, that means it can be the currentInGameTime, currentLobbyTime, etc.
    private ArenaStatus status; // Current status of the Arena
    private List<PlayerDat> players = new ArrayList<PlayerDat>();

    /* Arena Location */
    private ArenaLocation arenaLocation;

    /* Arena Dat */
    private ArenaDat arenaDat;

    /* Constructor */
    public Arena(int arenaNumber, String mapName, ArenaLocation arenaLocation) {
        this.arenaNumber = arenaNumber;
        this.mapName = mapName;
        this.arenaLocation = arenaLocation;
        this.arenaDat = new ArenaDat();
        this.remainingTime = ArenaStatus.LOBBY.getTime();
        this.status = ArenaStatus.LOBBY;
    }

    /* Static Methods */
    public static boolean addArena(Arena arena) {
        if (getArenaByNumber(arena.getArenaNumber()) != null)
            return false;
        arenas.add(arena);
        return true;
    }

    public static boolean addPlayer(PlayerDat playerDat) {
        if (getArenaByPlayer(playerDat) != null)
            return false;
        // Look for available arena
        int posArena = -1;
        int maxPlayers = -1;
        for (int i = 0; i < Arena.arenas.size(); i++)
            if (Arena.arenas.get(i).getStatus() == ArenaStatus.LOBBY) // Waiting For Players
                if (Arena.arenas.get(i).getPlayers().size() < Arena.maxPlayers) // Not Full
                    if (Arena.arenas.get(i).getPlayers().size() > maxPlayers) // Arena With Highest Amount of Players
                        posArena = i;
        // Check if an arena was found
        if (posArena == -1)
            return false;
        Arena arena = Arena.arenas.get(posArena);
        // Add Player To Arena
        arena.getPlayers().add(playerDat);
        // Teleport To Lobby
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null) {
            return false;
        }
        player.teleport(arena.getArenaLocation().getLobbyLocation());
        return true;
    }

    public static void removeArena(Arena arena) {
        arenas.remove(arena);
    }

    public static void removePlayer(PlayerDat playerDat) {
        Arena arena = getArenaByPlayer(playerDat);
        if (arena != null)
            arena.getPlayers().remove(playerDat);
    }

    public static Arena getArenaByNumber(int number) {
        for (int i = 0; i < Arena.arenas.size(); i++)
            if (Arena.arenas.get(i).getArenaNumber() == number)
                return Arena.arenas.get(i);
        return null;
    }

    public static Arena getArenaByPlayer(PlayerDat playerDat) {
        for (int i = 0; i < Arena.arenas.size(); i++)
            for (int j = 0; j < Arena.arenas.get(i).getPlayers().size(); j++)
                if (Arena.arenas.get(i).getPlayers().get(j).getUUID() == playerDat.getUUID())
                    return Arena.arenas.get(i);
        return null;
    }

    public static int getNumberAvailableArenas() {
        int numero = 0;
        for (int i = 0; i < Arena.arenas.size(); i++)
            if (Arena.arenas.get(i).getStatus() == ArenaStatus.LOBBY)
                numero++;
        return numero;
    }

    public static int nextArenaNumber() {
        List<Integer> arenasNumbers = new ArrayList<Integer>();
        // Get all arenas numbers
        for (int i = 0; i < arenas.size(); i++)
            arenasNumbers.add(arenas.get(i).getArenaNumber());
        /* Check if there is a missing number in arenas numbers
        If there are 10 arenas, they should be 0, 1, ...., 9.
        But because of the way it is made, it might be 0, 1, ...,8,10.
        So we can assign the nextArenaNumber to the missing "9". */
        for (int i = 0; i < arenas.size(); i++)
            if (!arenasNumbers.contains(i))
                return i;
        return arenas.size() + 1;
    }

    public static String nextGameNumber() {
        String gameNumber = "";
        for (int i = 0; i < arenas.size(); i++) {
            String arenaGameNumber = arenas.get(i).getArenaDat().getGameNumber();
            if (gameNumber.length() == arenaGameNumber.length()) {
                int compResult = gameNumber.compareTo(arenaGameNumber);
                if (compResult < 0)
                    gameNumber = arenaGameNumber;
            } else if (gameNumber.length() < arenaGameNumber.length()) {
                gameNumber = arenaGameNumber;
            }
        }
        boolean nextCharacter = true;
        int i = 0;
        while (nextCharacter) {
            if (i < gameNumber.length()) {
                char currentChar = gameNumber.charAt(i);
                if (currentChar != 'Z') {
                    char[] gameNumberChar = gameNumber.toCharArray();
                    gameNumberChar[i] = currentChar++;
                    gameNumber = String.valueOf(gameNumberChar);
                    nextCharacter = false;
                } else {
                    char[] gameNumberChar = gameNumber.toCharArray();
                    gameNumberChar[i] = 0;
                    gameNumber = String.valueOf(gameNumberChar);
                }
                i++;
            } else {
                gameNumber = "1" + gameNumber;
                nextCharacter = false;
            }
        }
        return gameNumber;
    }

    public static void createNewArena() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                WorldServerNMS.UnsafeLock lock = new WorldServerNMS.UnsafeLock(new WorldAPI());
                lock.lock();

                // Arena Number
                int arenaNumber = Arena.nextArenaNumber();

                // Current Directory
                String currentDir = System.getProperty("user.dir");
                // Source Directory
                File[] listWorlds = new File(new File(currentDir).getParentFile().getAbsolutePath() + File.separator + "worlds").listFiles();
                int posWorld = new Random().nextInt(listWorlds.length);
                String srcPath = listWorlds[posWorld].getAbsolutePath();
                // Target Directory
                String mapName = (listWorlds[posWorld].getName() + "_" + arenaNumber);
                String trgPath = new File(new File(currentDir).getParentFile().getAbsolutePath() + File.separator + mapName).getAbsolutePath();

                // Copy World
                WorldAPI.copyDirectoryAsync(srcPath, trgPath);
                // Load World
                WorldAPI.loadWorldAsync(mapName);

                // Create Arena
                final File locations = new File(new File(currentDir).getParentFile().getAbsolutePath() + File.separator + mapName + File.separator + "locations.yml");
                final FileConfiguration configLocations = YamlConfiguration.loadConfiguration(locations);
                Location lobbyLocation = null, deathSpawnLocation = null, firstCorner = null, secondCorner = null;
                List<Location> spawnLocations = new ArrayList<Location>();
                for (final String message : configLocations.getConfigurationSection("").getKeys(false)) {
                    // Spawn Locations
                    if (message.equalsIgnoreCase("spawnLocations")) {
                        for (final String spawnLoc : configLocations.getConfigurationSection("spawnLocations").getKeys(false)) {
                            // Location
                            String locString = configLocations.getString(spawnLoc);
                            String[] locStringArgs = locString.split(",");
                            float[] parsed = new float[3];
                            for (int a = 0; a < 3; a++) {
                                parsed[a] = Float.parseFloat(locStringArgs[a + 1]);
                            }
                            spawnLocations.add(new Location(Bukkit.getWorld(locStringArgs[0]), parsed[0], parsed[1], parsed[2]));
                        }
                    } else {
                        // Location
                        String locString = configLocations.getString(message);
                        String[] locStringArgs = locString.split(",");
                        float[] parsed = new float[3];
                        for (int a = 0; a < 3; a++) {
                            parsed[a] = Float.parseFloat(locStringArgs[a + 1]);
                        }
                        // Check which location it is
                        if (message.equalsIgnoreCase("lobbyLocation")) {
                            lobbyLocation = new Location(Bukkit.getWorld(locStringArgs[0]), parsed[0], parsed[1], parsed[2]);
                        } else if (message.equalsIgnoreCase("deathspawnLocation")) {
                            deathSpawnLocation = new Location(Bukkit.getWorld(locStringArgs[0]), parsed[0], parsed[1], parsed[2]);
                        } else if (message.equalsIgnoreCase("firstCorner")) {
                            firstCorner = new Location(Bukkit.getWorld(locStringArgs[0]), parsed[0], parsed[1], parsed[2]);
                        } else if (message.equalsIgnoreCase("secondCorner")) {
                            secondCorner = new Location(Bukkit.getWorld(locStringArgs[0]), parsed[0], parsed[1], parsed[2]);
                        }
                    }
                }
                ArenaLocation arenaLocation = new ArenaLocation(lobbyLocation, deathSpawnLocation, firstCorner, secondCorner, spawnLocations);
                Arena arena = new Arena(arenaNumber, mapName, arenaLocation);

                lock.unlock();
            }
        });
        thread.start();
    }

    /* Get's */
    public int getArenaNumber() {
        return arenaNumber;
    }

    public String getMapName() {
        return mapName;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public ArenaStatus getStatus() {
        return status;
    }

    public ArenaLocation getArenaLocation() {
        return arenaLocation;
    }

    public ArenaDat getArenaDat() {
        return arenaDat;
    }

    public List<PlayerDat> getPlayers() {
        return players;
    }

    public List<PlayerDat> getAlivePlayers() {
        List<PlayerDat> alivePlayers = new ArrayList<PlayerDat>();
        for (int i = 0; i < players.size(); i++)
            if (players.get(i).getStatus() == PlayerStatus.ALIVE)
                alivePlayers.add(players.get(i));
        return alivePlayers;
    }

    public List<PlayerDat> getDeathPlayers() {
        List<PlayerDat> deathPlayers = new ArrayList<PlayerDat>();
        for (int i = 0; i < players.size(); i++)
            if (players.get(i).getStatus() == PlayerStatus.DEAD)
                deathPlayers.add(players.get(i));
        return deathPlayers;
    }

    public List<PlayerDat> getSpectatorPlayers() {
        List<PlayerDat> spectatorPlayers = new ArrayList<PlayerDat>();
        for (int i = 0; i < players.size(); i++)
            if (players.get(i).getStatus() == PlayerStatus.SPECTATOR)
                spectatorPlayers.add(players.get(i));
        return spectatorPlayers;
    }

    /* Set's */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setStatus(ArenaStatus status) {
        this.remainingTime = status.getTime();
        this.status = status;
    }

    public void setArenaLocation(ArenaLocation arenaLocation) {
        this.arenaLocation = arenaLocation;
    }

    public void setArenaDat(ArenaDat arenaDat) {
        this.arenaDat = arenaDat;
    }

    public void setPlayers(List<PlayerDat> players) {
        this.players = players;
    }
}
