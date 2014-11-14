package net.RevTut.Skywars.arena;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.RevTut.Skywars.libraries.world.WorldAPI;
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
 * Arena Object.
 * <p/>
 * <P>Game arena which includes several attributes arena related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Arena {

    /**
     * List of all arenas in the server
     */
    private static final List<Arena> arenas = new ArrayList<Arena>();

    /**
     * Minimum players for the game
     */
    public static final int minPlayers = 1;

    /**
     * Minimum players for lobby time be reduced
     */
    public static final int minReduceTimePlayers = 16;

    /**
     * Maximum players that can play in each game
     */
    public static final int maxPlayers = 24;

    /**
     * Arena Number
     */
    private final int arenaNumber;

    /**
     * Map Name
     */
    private String mapName;

    /**
     * Remaining time of the arena. Might be the current ingame time, current lobby time
     *
     * @see ArenaStatus
     */
    private int remainingTime;

    /**
     * Status of the arena
     *
     * @see ArenaStatus
     */
    private ArenaStatus status;

    /**
     * List of players which are on the arena
     */
    private final List<PlayerDat> players = new ArrayList<PlayerDat>();

    /**
     * Arena Location (all the locations of that arena)
     *
     * @see ArenaLocation
     */
    private ArenaLocation arenaLocation;

    /**
     * Arena Dat (status of that game)
     *
     * @see ArenaDat
     */
    private ArenaDat arenaDat;

    /**
     * Constructor of Arena
     *
     * @param arenaNumber   the arena to reset
     * @param mapName       the name of the map
     * @param arenaLocation the arena location of the arena
     */
    private Arena(int arenaNumber, String mapName, ArenaLocation arenaLocation) {
        this.arenaNumber = arenaNumber;
        this.mapName = mapName;
        this.arenaLocation = arenaLocation;
        this.arenaDat = new ArenaDat();
        this.remainingTime = ArenaStatus.LOBBY.getTime();
        this.status = ArenaStatus.LOBBY;
    }

    /**
     * Creates a new arena and add it to the server
     *
     * @return true it well succeeded
     * @see Arena
     */
    public static boolean createNewArena() {
        // Arena Number
        int arenaNumber = Arena.nextArenaNumber();

        String mapName = addNewMap(arenaNumber);
        if (mapName == null)
            return false;

        // Create Arena
        ArenaLocation arenaLocation = createArenaLocation(new File(System.getProperty("user.dir") + File.separator + mapName + File.separator + "locations.yml"), mapName);
        if (arenaLocation == null)
            return false;
        Arena arena = new Arena(arenaNumber, mapName, arenaLocation);
        arena.getArenaDat().setGameNumber(nextGameNumber()); // Set GameNumber
        if (addArena(arena))
            return true;
        return false;
    }

    /**
     * Loads the locations from a given map name.
     *
     * @param file    the locations file
     * @param mapName map name which will be used to create the locations
     * @return the arenalocation of that map
     * @see ArenaLocation
     */
    private static ArenaLocation createArenaLocation(File file, String mapName) {
        if (!file.exists()) {
            System.out.println("File with arena locations does not exists!");
            return null;
        }
        final FileConfiguration configLocations = YamlConfiguration.loadConfiguration(file);

        Location lobbyLocation = null, deathSpawnLocation = null, firstCorner = null, secondCorner = null;
        List<Location> spawnLocations = new ArrayList<Location>();
        for (final String message : configLocations.getConfigurationSection("").getKeys(false)) {
            // Spawn Locations
            if (message.equalsIgnoreCase("spawnLocations")) {
                for (final String spawnLoc : configLocations.getConfigurationSection("spawnLocations").getKeys(false)) {
                    // Location
                    String locString = configLocations.getConfigurationSection("spawnLocations").getString(spawnLoc);
                    String[] locStringArgs = locString.split(",");
                    float[] parsed = new float[3];
                    for (int a = 0; a < 3; a++) {
                        parsed[a] = Float.parseFloat(locStringArgs[a + 1]);
                    }
                    spawnLocations.add(new Location(Bukkit.getWorld(mapName), parsed[0], parsed[1], parsed[2]));
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
                    lobbyLocation = new Location(Bukkit.getWorld(mapName), parsed[0], parsed[1], parsed[2]);
                } else if (message.equalsIgnoreCase("deathspawnLocation")) {
                    deathSpawnLocation = new Location(Bukkit.getWorld(mapName), parsed[0], parsed[1], parsed[2]);
                } else if (message.equalsIgnoreCase("firstCorner")) {
                    firstCorner = new Location(Bukkit.getWorld(mapName), parsed[0], parsed[1], parsed[2]);
                } else if (message.equalsIgnoreCase("secondCorner")) {
                    secondCorner = new Location(Bukkit.getWorld(mapName), parsed[0], parsed[1], parsed[2]);
                }
            }
        }
        // Max and Min Locations
        Location temp = firstCorner;
        firstCorner = new Location(temp.getWorld(), Math.min(temp.getX(), secondCorner.getX()), Math.min(temp.getY(), secondCorner.getY()), Math.min(temp.getZ(), secondCorner.getZ()));
        secondCorner = new Location(temp.getWorld(), Math.max(temp.getX(), secondCorner.getX()), Math.max(temp.getY(), secondCorner.getY()), Math.max(temp.getZ(), secondCorner.getZ()));
        return new ArenaLocation(lobbyLocation, deathSpawnLocation, firstCorner, secondCorner, spawnLocations);
    }

    /**
     * Resets an arena to its original state. It removes the map game, then load a new one and finally
     * resets the arena Status, RemainingTime, ArenaLocation and GameNumber.
     *
     * @param arena the arena to be reseted
     * @return true if arena was reseted
     * @see Arena
     */
    public static boolean resetArena(Arena arena) {
        /* DELETE EXISTING MAP */
        if (!removeMap(arena))
            return false;

        /* ADD NEW MAP */
        String mapName = addNewMap(arena.getArenaNumber());
        if (mapName == null)
            return false;

        // Update Arena
        String gameNumber = nextGameNumber();
        arena.setMapName(mapName);
        arena.setArenaDat(new ArenaDat());
        arena.setRemainingTime(ArenaStatus.LOBBY.getTime());
        arena.setStatus(ArenaStatus.LOBBY);
        ArenaLocation arenaLocation = createArenaLocation(new File(System.getProperty("user.dir") + File.separator + mapName + File.separator + "locations.yml"), mapName);
        if (arenaLocation == null)
            return false;
        arena.setArenaLocation(arenaLocation);
        arena.getArenaDat().setGameNumber(gameNumber); // Set GameNumber
        return true;
    }

    /**
     * Removes an arena from the server.
     *
     * @param arena the arena to be removed
     * @return true it was successful when removing it
     * @see Arena
     */
    public static boolean removeArena(Arena arena) {
        // Remove From List
        arenas.remove(arena);

        // Delete existing map
        if (!removeMap(arena))
            return false;

        // New Arena if Needed
        if (Arena.getNumberAvailableArenas() <= 1) {
            if (!Arena.createNewArena())
                return true;
        }

        return true;
    }

    /**
     * Unloads and remove the map from the Arena.
     *
     * @param arena the arena to remove the map from
     * @return true it was successful when removing it
     * @see Arena
     */
    private static boolean removeMap(Arena arena) {
        // Unload of the World
        if (!WorldAPI.unloadWorld(arena.getMapName())) {
            System.out.println("Error while unloading world " + arena.getMapName());
            return false;
        }

        // Remove Directory
        if (WorldAPI.removeDirectory(new File(System.getProperty("user.dir") + File.separator + arena.getMapName())))
            return true;

        System.out.println("Error while removing world directory " + arena.getMapName());
        return false;
    }

    /**
     * Remove a player from the arena he was playing.
     *
     * @param playerDat playerDat to be removed
     * @return true it was successful when removing it
     * @see PlayerDat
     */
    public static boolean removePlayer(PlayerDat playerDat) {
        Arena arena = getArenaByPlayer(playerDat);
        if (arena == null)
            return false;

        arena.getPlayers().remove(playerDat);
        return true;
    }

    /**
     * Returns the new loaded map name. First it copies one random map from backup folder
     * to the root folder and then it loads that new map.
     *
     * @param arenaNumber the arena which will use the new map
     * @return the name of the map
     */
    private static String addNewMap(int arenaNumber) {
        // Current Directory
        String currentDir = System.getProperty("user.dir");
        // Source Directory
        String[] listWorlds = new File(currentDir + File.separator + "worlds").list();
        if (listWorlds == null) {
            System.out.println("Worlds folder not found!");
            return null;
        }
        int posWorld = new Random().nextInt(listWorlds.length);
        String srcPath = new File(currentDir + File.separator + "worlds" + File.separator + listWorlds[posWorld]).getAbsolutePath();
        // Target Directory
        String mapName = listWorlds[posWorld] + "_" + arenaNumber;
        String trgPath = new File(currentDir + File.separator + mapName).getAbsolutePath();

        // Copy World
        WorldAPI.copyDirectory(new File(srcPath), new File(trgPath));
        // Load World
        if (!WorldAPI.loadWorld(mapName)) {
            System.out.println("Error while creating a new arena! World is null!");
            return null;
        }

        return mapName;
    }

    /**
     * Adds a new arena to the server. Checks if there is already an arena
     * with the same arena number as the given one.
     *
     * @param arena the arena to add
     * @return wether it it was successful when adding arena or not
     */
    private static boolean addArena(Arena arena) {
        if (getArenaByNumber(arena.getArenaNumber()) != null)
            return false;
        arenas.add(arena);
        return true;
    }

    /**
     * Adds a new player to the game. Checks which one has more players online
     * and that has not started yet.
     *
     * @param playerDat the playerDat to add to the game
     * @return wether it was successful when adding the playerDat or not
     */
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

        // Add it to the arena
        if (addPlayer(playerDat, arena))
            return true;
        return false;
    }

    /**
     * Adds a player to the given arena. Checks if he is not already ingame.
     *
     * @param playerDat the playerDat to add to the game
     * @param arena     the arena where to add the playerDat
     * @return wether it it was successful when adding the playerDat or not
     */
    public static boolean addPlayer(PlayerDat playerDat, Arena arena) {
        if (getArenaByPlayer(playerDat) != null)
            return false;
        // Teleport To Lobby
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null) {
            return false;
        }
        player.teleport(arena.getArenaLocation().getLobbyLocation());
        // Add Player To Arena
        arena.getPlayers().add(playerDat);

        // Title
        TitleAPI.sendTimings(player, Main.fadeIn, Main.timeOnScreen, Main.fadeOut);
        TitleAPI.sendTitle(player, Main.titleMessage);
        TitleAPI.sendSubTitle(player, Main.subTitleMessage.replace("%gamenumber%", arena.getArenaDat().getGameNumber()));

        return true;
    }

    /**
     * Send a message to all the players in the arena
     *
     * @param message message to be sent
     */
    public void sendMessageToArena(String message) {
        for (PlayerDat playerDat : this.getPlayers()) {
            Player player = Bukkit.getPlayer(playerDat.getUUID());
            if (player == null) {
                this.removePlayer(playerDat);
                continue;
            }
            // Send message
            player.sendMessage(message);
        }
    }

    /**
     * Returns all the arenas from the server
     *
     * @return list with all the arenas
     */
    public static List<Arena> getArenas() {
        return Arena.arenas;
    }

    /**
     * Find an arena with a given number.
     *
     * @param number number of the arena
     * @return the arena which has that game number
     * @see Arena
     */
    private static Arena getArenaByNumber(int number) {
        for (int i = 0; i < Arena.arenas.size(); i++)
            if (Arena.arenas.get(i).getArenaNumber() == number)
                return Arena.arenas.get(i);
        return null;
    }

    /**
     * Find the arena where a player is playing.
     *
     * @param playerDat playerDat to get the arena
     * @return the arena where the player is playing
     * @see Arena
     */
    public static Arena getArenaByPlayer(PlayerDat playerDat) {
        for (int i = 0; i < Arena.arenas.size(); i++)
            for (int j = 0; j < Arena.arenas.get(i).getPlayers().size(); j++)
                if (Arena.arenas.get(i).getPlayers().get(j).getUUID() == playerDat.getUUID())
                    return Arena.arenas.get(i);
        return null;
    }

    /**
     * Returns the number of available arenas
     *
     * @return number of available arenas
     */
    public static int getNumberAvailableArenas() {
        int numero = 0;
        for (int i = 0; i < Arena.arenas.size(); i++)
            if (Arena.arenas.get(i).getStatus() == ArenaStatus.LOBBY)
                numero++;
        return numero;
    }

    /**
     * Returns a list with all available arenas
     *
     * @return list of available arenas
     */
    public static List<Arena> getAvailableArenas() {
        List<Arena> arenasAvailable = new ArrayList<Arena>();
        for (int i = 0; i < Arena.arenas.size(); i++)
            if (Arena.arenas.get(i).getStatus() == ArenaStatus.LOBBY)
                arenasAvailable.add(Arena.arenas.get(i));
        return arenasAvailable;
    }

    /**
     * Returns the next arena number
     *
     * @return next arena number
     */
    private static int nextArenaNumber() {
        List<Integer> arenasNumbers = new ArrayList<Integer>();
        // Get all arenas numbers
        for (Arena arena : arenas) arenasNumbers.add(arena.getArenaNumber());

        /* Check if there is a missing number in arenas numbers
        If there are 10 arenas, they should be 0, 1, ...., 9.
        But because of the way it is made, it might be 0, 1, ...,8,10.
        So we can assign the nextArenaNumber to the missing "9". */
        for (int i = 1; i <= arenasNumbers.size(); i++)
            if (!arenasNumbers.contains(i))
                return i;

        return arenas.size() + 1;
    }

    /**
     * Returns the next game number
     *
     * @return next game number (eg: 18AX2)
     */
    private static String nextGameNumber() {
        String gameNumber = "";
        for (Arena arena : arenas) {
            String arenaGameNumber = arena.getArenaDat().getGameNumber();
            if (gameNumber.length() == arenaGameNumber.length()) {
                int compResult = gameNumber.compareTo(arenaGameNumber);
                if (compResult < 0)
                    gameNumber = arenaGameNumber;
            } else if (gameNumber.length() < arenaGameNumber.length()) {
                gameNumber = arenaGameNumber;
            }
        }
        boolean nextCharacter = true;
        int i = gameNumber.length() - 1;
        while (nextCharacter) {
            // Starts on first character and then increments the "i" if needed
            if (0 <= i) {
                char currentChar = gameNumber.charAt(i);
                if (currentChar != 'Z' && currentChar != '9') {
                    char[] gameNumberChar = gameNumber.toCharArray();
                    gameNumberChar[i] = ++currentChar;
                    gameNumber = String.valueOf(gameNumberChar);
                    nextCharacter = false;
                } else if (currentChar == '9') {
                    char[] gameNumberChar = gameNumber.toCharArray();
                    gameNumberChar[i] = 'A';
                    gameNumber = String.valueOf(gameNumberChar);
                    nextCharacter = false;
                } else if (currentChar == 'Z') {
                    char[] gameNumberChar = gameNumber.toCharArray();
                    gameNumberChar[i] = '0';
                    gameNumber = String.valueOf(gameNumberChar);
                }
                i--;
            } else {
                gameNumber = "1" + gameNumber;
                nextCharacter = false;
            }
        }
        return gameNumber;
    }

    /**
     * Return the arena number
     *
     * @return arena number
     */
    public int getArenaNumber() {
        return arenaNumber;
    }

    /**
     * Return the map name
     *
     * @return name of the current map
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Return the remaining time of the arena (might be remaining time of lobby etc)
     *
     * @return remaining time
     */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * Return the arena status
     *
     * @return status of the arena
     * @see ArenaStatus
     */
    public ArenaStatus getStatus() {
        return status;
    }

    /**
     * Return the arena location
     *
     * @return locations of the arena
     * @see ArenaLocation
     */
    public ArenaLocation getArenaLocation() {
        return arenaLocation;
    }

    /**
     * Return the arena dat
     *
     * @return arena dat
     * @see ArenaDat
     */
    public ArenaDat getArenaDat() {
        return arenaDat;
    }

    /**
     * Return a list with the players on the arena
     *
     * @return list with all the players
     */
    public List<PlayerDat> getPlayers() {
        return players;
    }

    /**
     * Return a list with the alive players on the arena
     *
     * @return list with all alive players
     */
    public List<PlayerDat> getAlivePlayers() {
        List<PlayerDat> alivePlayers = new ArrayList<PlayerDat>();
        for (PlayerDat player : players)
            if (player.getStatus() == PlayerStatus.ALIVE)
                alivePlayers.add(player);
        return alivePlayers;
    }

    /**
     * Return a list with the dead players on the arena
     *
     * @return list with all dead players
     */
    public List<PlayerDat> getDeadPlayers() {
        List<PlayerDat> deathPlayers = new ArrayList<PlayerDat>();
        for (PlayerDat player : players)
            if (player.getStatus() == PlayerStatus.DEAD)
                deathPlayers.add(player);
        return deathPlayers;
    }

    /**
     * Return a list with the spectator players on the arena
     *
     * @return list with all spectators players
     */
    public List<PlayerDat> getSpectatorPlayers() {
        List<PlayerDat> spectatorPlayers = new ArrayList<PlayerDat>();
        for (PlayerDat player : players)
            if (player.getStatus() == PlayerStatus.SPECTATOR)
                spectatorPlayers.add(player);
        return spectatorPlayers;
    }

    /**
     * Set the map name of the arena
     *
     * @param mapName the name of the new map
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * Set the remaining time of the arena
     *
     * @param remainingTime the remaining time to set the arena
     */
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    /**
     * Set the status of the arena
     *
     * @param status the arena status to replace the existing
     */
    public void setStatus(ArenaStatus status) {
        this.remainingTime = status.getTime();
        this.status = status;
    }

    /**
     * Set the arena location of the arena
     *
     * @param arenaLocation the arena location to replace the existing
     */
    public void setArenaLocation(ArenaLocation arenaLocation) {
        this.arenaLocation = arenaLocation;
    }

    /**
     * Set the arena dat of the arena
     *
     * @param arenaDat the arena dat to replace the existing
     */
    public void setArenaDat(ArenaDat arenaDat) {
        this.arenaDat = arenaDat;
    }

    /**
     * Set the players of the arena
     */
    public void clearPlayers() {
        this.players.clear();
    }


}
