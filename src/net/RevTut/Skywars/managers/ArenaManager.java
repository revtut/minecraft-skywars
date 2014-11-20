package net.RevTut.Skywars.managers;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaDat;
import net.RevTut.Skywars.arena.ArenaLocation;
import net.RevTut.Skywars.arena.ArenaStatus;
import net.RevTut.Skywars.libraries.algebra.AlgebraAPI;
import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.RevTut.Skywars.libraries.world.WorldAPI;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
     * Main class
     */
    private final Main plugin;

    /**
     * Constructor of ArenaManager
     *
     * @param plugin main class
     */
    public ArenaManager(final Main plugin) {
        this.plugin = plugin;
    }

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
                        parsed[a] = Float.parseFloat(locStringArgs[a]);
                    }
                    spawnLocations.add(new Location(Bukkit.getWorld(mapName), parsed[0], parsed[1], parsed[2]));
                }
            } else {
                // Location
                String locString = configLocations.getString(message);
                String[] locStringArgs = locString.split(",");
                float[] parsed = new float[3];
                for (int a = 0; a < 3; a++) {
                    parsed[a] = Float.parseFloat(locStringArgs[a]);
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
        if (lobbyLocation == null) {
            System.out.println("Lobby location is null!");
            return null;
        }
        if (deathSpawnLocation == null) {
            System.out.println("Death Spawn location is null!");
            return null;
        }
        if (firstCorner == null) {
            System.out.println("First Corner location is null!");
            return null;
        }
        if (secondCorner == null) {
            System.out.println("Second Corner location is null!");
            return null;
        }
        for (Location spawnLocation : spawnLocations)
            if (spawnLocation == null) {
                System.out.println("One or more spawn locations are null!");
                return null;
            }else{
                // Change yaw and pitch of a location
                Location locationAt = AlgebraAPI.locationLookAt(spawnLocation, deathSpawnLocation);
                spawnLocation.setPitch(locationAt.getPitch());
                spawnLocation.setYaw(locationAt.getYaw());
            }

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
    public boolean resetArena(Arena arena) {
        // ArenaDat
        final ArenaDat arenaDat = arena.getArenaDat();
        if (arenaDat == null) {
            System.out.println("ArenaDat is null when resetting the arena!");
            return false;
        }

        // MySQL Tasks
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                // PlayerDat
                plugin.mysql.updateMySQLArenaDat(arenaDat);
            }
        });


        // Delete map
        if (!removeMap(arena))
            return false;

        // Add new map
        String mapName = addNewMap(arena.getArenaNumber());
        if (mapName == null)
            return false;

        // Update Arena
        String gameNumber = nextGameNumber();
        arena.setMapName(mapName);
        arena.setArenaDat(new ArenaDat());
        arena.setRemainingTime(ArenaStatus.LOBBY.getTime());
        arena.setStatus(ArenaStatus.LOBBY);
        arena.setKitManager(new KitManager());
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
        // ArenaDat
        final ArenaDat arenaDat = arena.getArenaDat();
        if (arenaDat == null) {
            System.out.println("ArenaDat is null when resetting the arena!");
            return false;
        }

        // MySQL Tasks
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                // PlayerDat
                plugin.mysql.updateMySQLArenaDat(arenaDat);
            }
        }, 1);

        // New Arena if Needed
        if (getNumberAvailableArenas() <= 1) {
            createNewArena();
        }

        // Remove From List
        arenas.remove(arena);

        // Delete existing map
        return removeMap(arena);
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
     * Remove a player from the arena he was playing. Hide him to the server
     *
     * @param playerDat  playerDat to be removed
     * @param checkArena check if arena has minimum players to continue the game
     * @return true it was successful when removing it
     * @see PlayerDat
     */
    public boolean removePlayer(PlayerDat playerDat, boolean checkArena) {
        final Arena arena = getArenaByPlayer(playerDat);
        if (arena == null) {
            System.out.println("Arena is null when removing a player!");
            return false;
        }

        // ArenaDat
        ArenaDat arenaDat = arena.getArenaDat();
        if (arenaDat == null) {
            System.out.println("ArenaDat is null when removing a player!");
            return false;
        }

        arena.getPlayers().remove(playerDat);

        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return false;

        // Message to arena
        arena.sendMessage("§7|" + "§3Sky Wars" + "§7| §6" + player.getDisplayName() + "§6 saiu da arena!");
        arenaDat.addGameEvent(ChatColor.stripColor(player.getDisplayName() + " saiu da arena!")); // Add to event log

        // Hide to Server
        plugin.arenaManager.hideToServer(player, true);

        // Update scoreboard
        plugin.scoreBoardManager.updateAlive(arena);
        plugin.scoreBoardManager.updateDeath(arena);

        if (checkArena) {
            // Check if game already started
            if (arena.getStatus() == ArenaStatus.PREGAME || arena.getStatus() == ArenaStatus.INGAME) {
                if (arena.getAlivePlayers().size() <= 1) {
                    // Send message
                    arena.sendMessage("§7|" + "§3Sky Wars" + "§7| §4Asignando a uma nova arena devido a jogadores insuficientes!");
                    // Send remaining players to new arena
                    List<PlayerDat> arenaPlayers = new ArrayList<PlayerDat>(arena.getPlayers()); // Avoid concurrent modifications
                    for (PlayerDat alvoDat : arenaPlayers) {
                        Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
                        if (alvo == null)
                            continue;
                        if (!removePlayer(alvoDat, false)) {
                            System.out.println("Error while removing PlayerDat from arena on quit!");
                            /** Send him to Hub. Error while removing him from the arena */
                        }
                        if (!addPlayer(alvoDat)) {
                            System.out.println("Could not add the player to an Arena when not enough players in arena!");
                            /** Send him to Hub. No arena available */
                        }
                    }
                    // Config arena dat
                    arenaDat.addGameEvent("Terminou o jogo numero " + arenaDat.getGameNumber());
                    arenaDat.setEndDate(new Date());
                    arenaDat.setWinner("NULL");
                    // Delete the arena
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            removeArena(arena);
                        }
                    }, 100);
                }
            } else if (arena.getStatus() == ArenaStatus.ENDGAME) {
                if (arena.getAlivePlayers().size() < 1) {
                    // Delete the arena
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            removeArena(arena);
                        }
                    }, 100);
                }
            }
        }

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
        int maxArenaPlayers = -1;
        for (int i = 0; i < arenas.size(); i++)
            if (arenas.get(i).getStatus() == ArenaStatus.LOBBY) { // Waiting For Players
                int playersInArena = arenas.get(i).getPlayers().size();
                if (playersInArena < maxPlayers && playersInArena > maxArenaPlayers) { // Not Full & Arena With Highest Amount of Players
                    posArena = i;
                    maxArenaPlayers = playersInArena;
                }
            }

        // Check if an arena was found
        if (posArena == -1)
            return false;

        Arena arena = arenas.get(posArena);

        // Add it to the arena
        return addPlayer(playerDat, arena);
    }

    /**
     * Adds a player to the given arena. Checks if he is not already ingame.
     * Reveals him to the players in the same arena
     *
     * @param playerDat the playerDat to add to the game
     * @param arena     the arena where to add the playerDat
     * @return wether it it was successful when adding the playerDat or not
     */
    public boolean addPlayer(PlayerDat playerDat, final Arena arena) {
        if (getArenaByPlayer(playerDat) != null)
            return false;

        // ArenaDat
        ArenaDat arenaDat = arena.getArenaDat();
        if (arenaDat == null) {
            System.out.println("ArenaDat is null when trying to add a player!");
            /** Send him to Hub. Error in arena */
            return false;
        }

        // Teleport To Lobby
        final Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return false;
        Bukkit.getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                player.teleport(arena.getArenaLocation().getLobbyLocation());
            }
        });

        // Add Player To Arena
        arena.getPlayers().add(playerDat);

        // Title
        TitleAPI.sendTimings(player, plugin.fadeIn, plugin.timeOnScreen, plugin.fadeOut);
        TitleAPI.sendTitle(player, plugin.titleMessage);
        TitleAPI.sendSubTitle(player, plugin.subTitleMessage.replace("%gamenumber%", arena.getArenaDat().getGameNumber()));

        // Unhide to Arena
        plugin.arenaManager.unhideToArena(player, true);

        // Message to arena
        arena.sendMessage("§7|" + "§3Sky Wars" + "§7| §6" + player.getDisplayName() + "§6 entrou na arena!");
        arenaDat.addGameEvent(ChatColor.stripColor(player.getDisplayName() + " entrou na arena!")); // Add to event log

        // Config Player
        if (!plugin.playerManager.configPlayer(playerDat, PlayerStatus.WAITING, GameMode.ADVENTURE, false, false, 0, 0, 20.0, 20, true, true, 0))
            System.out.println("Error while configuring the player.");

        // Reset game kills
        playerDat.resetGameKills();

        // Update scoreboard
        plugin.scoreBoardManager.updateAlive(arena);
        plugin.scoreBoardManager.updateDeath(arena);

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
                if (arena.getPlayers().size() < maxPlayers)
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
        return nextGameNumber("");
    }

    /**
     * Returns the next game number
     *
     * @param lastGameNumber last game number
     * @return next game number (eg: 18AX2)
     */
    public String nextGameNumber(String lastGameNumber) {
        for (Arena arena : arenas) {
            String arenaGameNumber = arena.getArenaDat().getGameNumber();
            if (lastGameNumber.length() == arenaGameNumber.length()) {
                int compResult = lastGameNumber.compareTo(arenaGameNumber);
                if (compResult < 0)
                    lastGameNumber = arenaGameNumber;
            } else if (lastGameNumber.length() < arenaGameNumber.length()) {
                lastGameNumber = arenaGameNumber;
            }
        }
        boolean nextCharacter = true;
        int i = lastGameNumber.length() - 1;
        while (nextCharacter) {
            // Starts on first character and then increments the "i" if needed
            if (0 <= i) {
                char currentChar = lastGameNumber.charAt(i);
                if (currentChar != 'Z' && currentChar != '9') {
                    char[] gameNumberChar = lastGameNumber.toCharArray();
                    gameNumberChar[i] = ++currentChar;
                    lastGameNumber = String.valueOf(gameNumberChar);
                    nextCharacter = false;
                } else if (currentChar == '9') {
                    char[] gameNumberChar = lastGameNumber.toCharArray();
                    gameNumberChar[i] = 'A';
                    lastGameNumber = String.valueOf(gameNumberChar);
                    nextCharacter = false;
                } else {
                    char[] gameNumberChar = lastGameNumber.toCharArray();
                    gameNumberChar[i] = '0';
                    lastGameNumber = String.valueOf(gameNumberChar);
                }
                i--;
            } else {
                lastGameNumber = "1" + lastGameNumber;
                nextCharacter = false;
            }
        }
        return lastGameNumber;
    }

    /**
     * Hide player to the server
     *
     * @param player      player to hide to server
     * @param toPlayerToo true if hide all server to player
     */
    public void hideToServer(Player player, boolean toPlayerToo) {
        Player[] players = Bukkit.getOnlinePlayers().clone();
        for (Player alvo : players) {
            alvo.hidePlayer(player);
            if (toPlayerToo)
                player.hidePlayer(alvo);
        }
    }

    /**
     * Unhide player to the server
     *
     * @param player      player to unhide to server
     * @param toPlayerToo true if unhide all server to player
     */
    public void unhideToServer(Player player, boolean toPlayerToo) {
        Player[] players = Bukkit.getOnlinePlayers().clone();
        for (Player alvo : players) {
            alvo.showPlayer(player);
            if (toPlayerToo)
                player.showPlayer(alvo);
        }
    }

    /**
     * Hide player to the arena
     *
     * @param player      player to unhide to the arena
     * @param toPlayerToo true if hide all in the arena to player
     * @return true if sucessfully hided to the players in the arena
     */
    public boolean hideToArena(Player player, boolean toPlayerToo) {
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if (null == playerDat) {
            System.out.println("Error while hiding player to arena because PlayerDat is null.");
            return false;
        }
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (null == arena) {
            System.out.println("Error while hiding player to arena because Arena is null.");
            return false;
        }
        for (PlayerDat alvoDat : arena.getPlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (null == alvo)
                continue;
            alvo.hidePlayer(player);
            if (toPlayerToo)
                player.hidePlayer(alvo);
        }
        return true;
    }

    /**
     * Unhide player to the arena
     *
     * @param player      player to unhide to the arena
     * @param toPlayerToo true if hide all in the arena to player
     * @return true if sucessfully unhided to the players in the arena
     */
    public boolean unhideToArena(Player player, boolean toPlayerToo) {
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if (null == playerDat) {
            System.out.println("Error while unhiding player to arena because PlayerDat is null.");
            return false;
        }
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (null == arena) {
            System.out.println("Error while unhiding player to arena because Arena is null.");
            return false;
        }
        for (PlayerDat alvoDat : arena.getPlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (null == alvo)
                continue;
            alvo.showPlayer(player);
            if (toPlayerToo)
                player.showPlayer(alvo);
        }
        return true;
    }

    /**
     * Check if two players are in the same arena
     *
     * @param playerDat player to check
     * @param alvoDat   player to check
     * @return true if they are in the same arena
     */
    public boolean inSameArena(PlayerDat playerDat, PlayerDat alvoDat) {
        Arena playerArena = getArenaByPlayer(playerDat);
        if (null == playerArena) {
            System.out.println("Error while checking if they are in the same arena because Player's Arena is null!");
            return false;
        }
        Arena alvoArena = getArenaByPlayer(alvoDat);
        if (null == alvoArena) {
            System.out.println("Error while checking if they are in the same arena because Alvo's Arena is null!");
            return false;
        }
        return playerArena.getArenaNumber() == alvoArena.getArenaNumber();
    }
}
