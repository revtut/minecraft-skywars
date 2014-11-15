package net.RevTut.Skywars.arena;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.RevTut.Skywars.libraries.world.WorldAPI;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.utils.ScoreBoard;
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
 * Arena Manager.
 *
 * <P>Manages all arenas in the server. Includes methods to CRUD methods regarind arenas and players.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class ArenaManager {

    /**
     * List of all arenas in the server
     */
    private final List<Arena> arenas = new ArrayList<Arena>();

    /**
     * Minimum players for the game
     */
    public final int minPlayers = 1;

    /**
     * Minimum players for lobby time be reduced
     */
    public final int minReduceTimePlayers = 16;

    /**
     * Maximum players that can play in each game
     */
    public final int maxPlayers = 24;


    /**
     * Creates a new arena and add it to the server
     *
     * @return true it well succeeded
     * @see Arena
     */
    public boolean createNewArena() {
        // Arena Number
        int arenaNumber = nextArenaNumber();

        String mapName = addNewMap(arenaNumber);
        if (mapName == null)
            return false;

        // Create Arena
        ArenaLocation arenaLocation = createArenaLocation(new File(System.getProperty("user.dir") + File.separator + mapName + File.separator + "locations.yml"), mapName);
        if (arenaLocation == null)
            return false;
        Arena arena = new Arena(arenaNumber, mapName, arenaLocation);
        arena.getArenaDat().setGameNumber(nextGameNumber()); // Set GameNumber
        return addArena(arena);
    }

    /**
     * Loads the locations from a given map name.
     *
     * @param file    the locations file
     * @param mapName map name which will be used to create the locations
     * @return the arenalocation of that map
     * @see ArenaLocation
     */
    private ArenaLocation createArenaLocation(File file, String mapName) {
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
        if(temp == null){
            System.out.println("Locations are null!");
            return null;
        }
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
    public boolean resetArena(Arena arena) {
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
    public boolean removeArena(Arena arena) {
        // Remove From List
        arenas.remove(arena);

        // Delete existing map
        if (!removeMap(arena))
            return false;

        // New Arena if Needed
        if (getNumberAvailableArenas() <= 1) {
            if (!createNewArena())
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
    private boolean removeMap(Arena arena) {
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
    public boolean removePlayer(PlayerDat playerDat) {
        Arena arena = getArenaByPlayer(playerDat);
        if (arena == null)
            return false;

        arena.getPlayers().remove(playerDat);

        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if(player == null)
            return false;

        // Message to arena
        arena.sendMessage("§7|" + "§3Sky Wars" + "§7| §6" + player.getDisplayName() + "§6 saiu da arena!");

        return true;
    }

    /**
     * Returns the new loaded map name. First it copies one random map from backup folder
     * to the root folder and then it loads that new map.
     *
     * @param arenaNumber the arena which will use the new map
     * @return the name of the map
     */
    private String addNewMap(int arenaNumber) {
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
    private boolean addArena(Arena arena) {
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
    public boolean addPlayer(PlayerDat playerDat) {
        if (getArenaByPlayer(playerDat) != null)
            return false;
        // Look for available arena
        int posArena = -1;
        int maxPlayers = -1;
        for (int i = 0; i < arenas.size(); i++)
            if (arenas.get(i).getStatus() == ArenaStatus.LOBBY) // Waiting For Players
                if (arenas.get(i).getPlayers().size() < maxPlayers) // Not Full
                    if (arenas.get(i).getPlayers().size() > maxPlayers) // Arena With Highest Amount of Players
                        posArena = i;
        // Check if an arena was found
        if (posArena == -1)
            return false;
        Arena arena = arenas.get(posArena);

        // Add it to the arena
        return addPlayer(playerDat, arena);
    }

    /**
     * Adds a player to the given arena. Checks if he is not already ingame.
     *
     * @param playerDat the playerDat to add to the game
     * @param arena     the arena where to add the playerDat
     * @return wether it it was successful when adding the playerDat or not
     */
    public boolean addPlayer(PlayerDat playerDat, Arena arena) {
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

        // Message to arena
        arena.sendMessage("§7|" + "§3Sky Wars" + "§7| §6" + player.getDisplayName() + "§6 entrou na arena!");

        // Update scoreboard
        ScoreBoard.updateAlive(arena);
        ScoreBoard.updateDeath(arena);

        return true;
    }

    /**
     * Returns all the arenas from the server
     *
     * @return list with all the arenas
     */
    public List<Arena> getArenas() {
        return this.arenas;
    }

    /**
     * Find an arena with a given number.
     *
     * @param number number of the arena
     * @return the arena which has that game number
     * @see Arena
     */
    private Arena getArenaByNumber(int number) {
        for (Arena arena : arenas)
            if (arena.getArenaNumber() == number)
                return arena;
        return null;
    }

    /**
     * Find the arena where a player is playing.
     *
     * @param playerDat playerDat to get the arena
     * @return the arena where the player is playing
     * @see Arena
     */
    public Arena getArenaByPlayer(PlayerDat playerDat) {
        for (Arena arena : arenas)
            for (int j = 0; j < arena.getPlayers().size(); j++)
                if (arena.getPlayers().get(j).getUUID() == playerDat.getUUID())
                    return arena;
        return null;
    }

    /**
     * Returns the number of available arenas
     *
     * @return number of available arenas
     */
    public int getNumberAvailableArenas() {
        int numero = 0;
        for (Arena arena : arenas)
            if (arena.getStatus() == ArenaStatus.LOBBY)
                numero++;
        return numero;
    }

    /**
     * Returns a list with all available arenas
     *
     * @return list of available arenas
     */
    public List<Arena> getAvailableArenas() {
        List<Arena> arenasAvailable = new ArrayList<Arena>();
        for (Arena arena : arenas)
            if (arena.getStatus() == ArenaStatus.LOBBY)
                arenasAvailable.add(arena);
        return arenasAvailable;
    }

    /**
     * Returns the next arena number
     *
     * @return next arena number
     */
    private int nextArenaNumber() {
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
    private String nextGameNumber() {
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
                } else {
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

}
