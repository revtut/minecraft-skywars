package net.revtut.skywars.managers;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaDat;
import net.revtut.skywars.arena.ArenaLocation;
import net.revtut.skywars.arena.ArenaStatus;
import net.revtut.skywars.libraries.algebra.AlgebraAPI;
import net.revtut.skywars.libraries.titles.TitleAPI;
import net.revtut.skywars.libraries.world.WorldAPI;
import net.revtut.skywars.listeners.player.PlayerDamage;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.player.PlayerStatus;
import net.revtut.skywars.utils.Message;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

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
    private final SkyWars plugin;

    /**
     * Constructor of ArenaManager
     *
     * @param plugin main class
     */
    public ArenaManager(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * List of all arenas in the server
     */
    private final List<Arena> arenas = new ArrayList<Arena>();

    /**
     * Minimum players for the game
     */
    public final int minPlayers = 2;

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

        String mapName= addNewMap(arenaNumber);
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
            plugin.getLogger().log(Level.SEVERE, "File with arena locations does not exists!");
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
                    lobbyLocation = new Location(Bukkit.getWorlds().get(0), parsed[0], parsed[1], parsed[2]);
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
            plugin.getLogger().log(Level.SEVERE, "Lobby location is null!");
            return null;
        }
        if (deathSpawnLocation == null) {
            plugin.getLogger().log(Level.SEVERE, "Death Spawn location is null!");
            return null;
        }
        if (firstCorner == null) {
            plugin.getLogger().log(Level.SEVERE, "First Corner location is null!");
            return null;
        }
        if (secondCorner == null) {
            plugin.getLogger().log(Level.SEVERE, "Second Corner location is null!");
            return null;
        }
        for (Location spawnLocation : spawnLocations)
            if (spawnLocation == null) {
                plugin.getLogger().log(Level.SEVERE, "One or more spawn locations are null!");
                return null;
            }else{
                // Change yaw and pitch of a location
                Location locationAt = AlgebraAPI.locationLookAt(spawnLocation, deathSpawnLocation);
                spawnLocation.setPitch(locationAt.getPitch());
                spawnLocation.setYaw(locationAt.getYaw());
            }

        World world = firstCorner.getWorld();
        double xMin, xMax, zMin, zMax;
        if(firstCorner.getX() < secondCorner.getX()){
            xMin = firstCorner.getX();
            xMax = secondCorner.getX();
        }else{
            xMin = secondCorner.getX();
            xMax = firstCorner.getX();
        }
        if(firstCorner.getZ() < secondCorner.getZ()){
            zMin = firstCorner.getZ();
            zMax = secondCorner.getZ();
        }else{
            zMin = secondCorner.getZ();
            zMax = firstCorner.getZ();
        }
        firstCorner = new Location(world, xMin, firstCorner.getY(), zMin);
        secondCorner = new Location(world, xMax, secondCorner.getY(), zMax);
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
            plugin.getLogger().log(Level.SEVERE, "ArenaDat is null when resetting the arena!");
            return false;
        }

        // MySQL Tasks
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                // PlayerDat
                plugin.mysql.updateArenaDat(arenaDat);
            }
        });

        // Clear chests locations
        World world = Bukkit.getWorld(arena.getMapName());
        if(world != null)
            plugin.playerChest.clearLocationsFromWorld(world);

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
            plugin.getLogger().log(Level.SEVERE, "ArenaDat is null when resetting the arena!");
            return false;
        }

        // MySQL Tasks
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                // PlayerDat
                plugin.mysql.updateArenaDat(arenaDat);
            }
        });

        // New Arena if Needed
        if (getNumberAvailableArenas() <= 1) {
            createNewArena();
        }

        // Clear chests locations
        World world = Bukkit.getWorld(arena.getMapName());
        if(world != null)
            plugin.playerChest.clearLocationsFromWorld(world);

        // Delete existing map
        if(!removeMap(arena))
            return false;

        // Remove From List
        arenas.remove(arena);

        return true;
    }

    /**
     * Unloads and remove the map from the Arena.
     *
     * @param arena the arena to remove the map from
     * @return true it was successful when removing it
     * @see Arena
     */
    private boolean removeMap(final Arena arena) {
        // Unload of the World
        boolean unloaded = WorldAPI.unloadWorld(arena.getMapName());

        if(!unloaded){
            plugin.getLogger().log(Level.SEVERE, "Error while unloading world " + arena.getMapName());
            return false;
        }

        // Remove Directory
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                if (!WorldAPI.removeDirectory(new File(System.getProperty("user.dir") + File.separator + arena.getMapName())))
                    plugin.getLogger().log(Level.SEVERE, "Error while removing world directory " + arena.getMapName());
            }
        }, 120L);

        return true;
    }

    /**
     * Remove a player from the arena he was playing. Hide him to the server
     *
     * @param playerDat  playerDat to be removed
     * @param checkArena check if arena has minimum players to continue the game
     * @return true it was successful when removing it
     * @see net.revtut.skywars.player.PlayerDat
     */
    public boolean removePlayer(final PlayerDat playerDat, boolean checkArena) {
        final Arena arena = getArenaByPlayer(playerDat);
        if (arena == null) {
            plugin.getLogger().log(Level.SEVERE, "Arena is null when removing a player!");
            return false;
        }

        // ArenaDat
        final ArenaDat arenaDat = arena.getArenaDat();
        if (arenaDat == null) {
            plugin.getLogger().log(Level.SEVERE, "ArenaDat is null when removing a player!");
            return false;
        }

        arena.getPlayers().remove(playerDat);

        final Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return false;

        // Message to arena
        arena.sendMessage(Message.PLAYER_LEFT_GAME, player.getDisplayName() + " §6(" + arena.getPlayers().size() + "/" + maxPlayers + ")");
        arenaDat.addGameEvent(ChatColor.stripColor(player.getDisplayName() + " saiu da arena!")); // Add to event log

        // Hide to Server
        plugin.arenaManager.hideToServer(player, true);

        // Update scoreboard
        plugin.scoreBoardManager.updateAlive(arena);
        plugin.scoreBoardManager.updateDeath(arena);

        // Remove from last damaged players
        PlayerDamage.lastPlayerDamager.remove(player.getUniqueId());

        if (checkArena) {
            // Check if game already started
            if ((arena.getStatus() == ArenaStatus.INGAME || arena.getStatus() == ArenaStatus.PREGAME) && arena.getAlivePlayers().size() <= 1) {
                // Send message
                arena.sendMessage(Message.NOT_ENOUGH_PLAYERS_TO_CONTINUE);
                // Send remaining players to new arena
                List<PlayerDat> arenaPlayers = new ArrayList<PlayerDat>(arena.getPlayers()); // Avoid concurrent modifications
                for (PlayerDat alvoDat : arenaPlayers) {
                    Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
                    if (alvo == null)
                        continue;
                    if (!removePlayer(alvoDat, false)) {
                        plugin.getLogger().log(Level.WARNING, "Error while removing PlayerDat from arena on quit!");
                        // Send him to Hub. Error while removing him from the arena
                        plugin.connectServer(alvo, "hub");
                    }
                    if (!addPlayer(alvoDat)) {
                        plugin.getLogger().log(Level.WARNING, "Could not add the player to an Arena when not enough players in arena!");
                        // Send him to Hub. No arena available
                        plugin.connectServer(alvo, "hub");
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
            } else if (arena.getStatus() == ArenaStatus.ENDGAME) {
                if (arena.getAlivePlayers().size() < 1) {
                    // Delete the arena
                    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            removeArena(arena);
                        }
                    }, 600);
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
    private String addNewMap(final int arenaNumber) {
        // Current Directory
        String currentDir = System.getProperty("user.dir");
        // Source Directory
        String[] listWorlds = new File(currentDir + File.separator + "worlds").list();
        if (listWorlds == null) {
            plugin.getLogger().log(Level.WARNING, "Worlds folder not found!");
            return null;
        }
        int posWorld = new Random().nextInt(listWorlds.length);
        final String srcPath = new File(currentDir + File.separator + "worlds" + File.separator + listWorlds[posWorld]).getAbsolutePath();
        // Target Directory
        final String mapName = listWorlds[posWorld] + "_" + arenaNumber;
        final String trgPath = new File(currentDir + File.separator + mapName).getAbsolutePath();


        // Copy World
        WorldAPI.copyDirectory(new File(srcPath), new File(trgPath));
        // Load World
        if (!WorldAPI.loadWorld(mapName)) {
            plugin.getLogger().log(Level.SEVERE, "Error while creating a new arena! World is null!");
            Arena arena = getArenaByNumber(arenaNumber);
            if (arena != null)
                removeArena(arena);
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

        // Player
        final Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (player == null)
            return false;

        // ArenaDat
        ArenaDat arenaDat = arena.getArenaDat();
        if (arenaDat == null) {
            plugin.getLogger().log(Level.SEVERE, "ArenaDat is null when trying to add a player!");
            // Send him to Hub. Error in arena
            plugin.connectServer(player, "hub");
            return false;
        }

        // Teleport To Lobby
        player.teleport(arena.getArenaLocation().getLobbyLocation());

        // Add Player To Arena
        arena.getPlayers().add(playerDat);

        // Title
        TitleAPI.sendTimes(player, plugin.fadeIn, plugin.timeOnScreen, plugin.fadeOut);
        TitleAPI.sendTitle(player, plugin.titleMessage);
        TitleAPI.sendSubTitle(player, plugin.subTitleMessage.replace("%gamenumber%", arena.getArenaDat().getGameNumber()));

        // Unhide to Arena
        plugin.arenaManager.unhideToArena(player, true);

        // Message to arena
        arena.sendMessage(Message.PLAYER_JOINED_GAME, player.getDisplayName() + " §6(" + arena.getPlayers().size() + "/" + maxPlayers + ")");
        arenaDat.addGameEvent(ChatColor.stripColor(player.getDisplayName() + " entrou na arena!")); // Add to event log

        // Config Player
        if (!plugin.playerManager.configPlayer(playerDat, PlayerStatus.WAITING, GameMode.ADVENTURE, false, false, 0, 0, 20.0, 20, true, true, 0))
            plugin.getLogger().log(Level.WARNING, "Error while configuring the player.");

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
        for (Player alvo : Bukkit.getOnlinePlayers()) {
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
        for (Player alvo : Bukkit.getOnlinePlayers()) {
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
            plugin.getLogger().log(Level.WARNING, "Error while hiding player to arena because PlayerDat is null.");
            return false;
        }
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (null == arena) {
            plugin.getLogger().log(Level.SEVERE, "Error while hiding player to arena because Arena is null.");
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
            plugin.getLogger().log(Level.WARNING, "Error while unhiding player to arena because PlayerDat is null.");
            return false;
        }
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (null == arena) {
            plugin.getLogger().log(Level.SEVERE, "Error while unhiding player to arena because Arena is null.");
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
     * Show player to all spectators
     *
     * @param player      player to unhide to the arena
     * @param toPlayerToo true if show all spectators in the arena to player
     * @return true if sucessfully hided to the players in the arena
     */
    public boolean showToSpectators(Player player, boolean toPlayerToo) {
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if (null == playerDat) {
            plugin.getLogger().log(Level.WARNING, "Error while hiding player to arena because PlayerDat is null.");
            return false;
        }
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (null == arena) {
            plugin.getLogger().log(Level.SEVERE, "Error while hiding player to arena because Arena is null.");
            return false;
        }
        for (PlayerDat alvoDat : arena.getPlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if (null == alvo)
                continue;

            if(alvoDat.getStatus() == PlayerStatus.ALIVE)
                continue;

            if(alvoDat.getStatus() == PlayerStatus.WAITING)
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
            plugin.getLogger().log(Level.SEVERE, "Error while checking if they are in the same arena because Player's Arena is null!");
            return false;
        }
        Arena alvoArena = getArenaByPlayer(alvoDat);
        if (null == alvoArena) {
            plugin.getLogger().log(Level.SEVERE, "Error while checking if they are in the same arena because Alvo's Arena is null!");
            return false;
        }
        return playerArena.getArenaNumber() == alvoArena.getArenaNumber();
    }
}
