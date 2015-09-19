package net.revtut.skywars;

import net.revtut.libraries.database.Database;
import net.revtut.libraries.database.types.DatabaseType;
import net.revtut.libraries.games.GameAPI;
import net.revtut.libraries.games.GameController;
import net.revtut.libraries.games.GameListener;
import net.revtut.libraries.games.arena.ArenaFlag;
import net.revtut.libraries.games.arena.session.GameSession;
import net.revtut.libraries.games.arena.types.ArenaSolo;
import net.revtut.libraries.games.arena.types.ArenaType;
import net.revtut.libraries.utils.FilesAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Main class.
 *
 * <P>Sky Wars is a plugin where you have several players in islands. They all have the same amount of chests and resources available.</P>
 * <P>However inside the chests the furniture is completely random. The goal of a player is either to kill all the players or throw them
 * to the void since there is no ground on the map. If after a predefined amount of time the game is not over yet we will have a tie so
 * there will be no winner.</P>
 * <P>There is a wide variety of kits, each one gives a different advantage to the player that uses it.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class SkyWars extends JavaPlugin {

    /**
     * Instance of Sky Wars
     */
    private static SkyWars instance;

    /**
     * Controller of the game
     */
    private GameController gameController;

    /**
     * Configuration of the game
     */
    private Configuration configuration;

    /**
     * Database of the game
     */
    private Database database;

    /**
     * Enable the plugin
     */
    @Override
    public void onEnable() {
        instance = this;

        // Configuration files
        if(!createFiles()) {
            getLogger().log(Level.SEVERE, "Configuration files could not be created!");

            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if(!readFiles()) {
            getLogger().log(Level.WARNING, "Configuration files could not be read!");

            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Database
        if(database == null) {
            getLogger().log(Level.SEVERE, "Database could not be created... (maybe unknown type of database?)");

            Bukkit.getPluginManager().disablePlugin(this);
            return;
        } else {
            database.connect();
            if(database.getConnection() == null) {
                getLogger().log(Level.SEVERE, "Connection to the database could not be established!");

                Bukkit.getPluginManager().disablePlugin(this);
                return;
            }
        }

        // Integrate GameAPI
        gameController = GameAPI.getInstance().registerGame(this, new File(getDataFolder() + File.separator + "worlds"));
        if(gameController == null) {
            getLogger().log(Level.SEVERE, "Error while registering the game! (maybe it is already registered?)");

            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Create initial arena
        createArena();

        // Register events
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new GameListener(), this);
        pluginManager.registerEvents(new SessionListener(), this);
    }

    /**
     * Disable the plugin
     */
    @Override
    public void onDisable() {
        // Close database connection
        if(database != null) {
            database.rollback();
            database.close();
        }
    }

    /**
     * Get the instance of the game
     * @return instance of the game
     */
    public static SkyWars getInstance() {
        return instance;
    }

    /**
     * Get the configuration of the game
     * @return configuration of the game
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Get the database of the game
     * @return database of the game
     */
    public Database getDatabaseInstance() {
        return database;
    }

    /**
     * Get the controller of the game
     * @return controller of the game
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Create a arena for the game
     */
    public void createArena() {
        ArenaSolo arena = (ArenaSolo) gameController.createArena(ArenaType.SOLO);
        initializeArena(arena);
        initializeFlags(arena);
    }

    /**
     * Initialize a arena
     * @param arena arena to be initialized (must extend arena solo)
     */
    private void initializeArena(ArenaSolo arena) {
        World world = gameController.loadRandomWorld(getName() + "_" + arena.getId());

        // World arena locations
        File locationFile = new File(world.getWorldFolder() + File.separator + "location.yml");
        FileConfiguration locConfig = YamlConfiguration.loadConfiguration(locationFile);

        // Single locations
        Location lobby = new Location(Bukkit.getWorld(locConfig.getString("Lobby.world")),
                                                        locConfig.getDouble("Lobby.x"),
                                                        locConfig.getDouble("Lobby.y"),
                                                        locConfig.getDouble("Lobby.z")),
                spectator = new Location(world,
                        locConfig.getDouble("Spectator.x"),
                        locConfig.getDouble("Spectator.y"),
                        locConfig.getDouble("Spectator.z")),
                dead = new Location(world,
                        locConfig.getDouble("Dead.x"),
                        locConfig.getDouble("Dead.y"),
                        locConfig.getDouble("Dead.z"));

        // Array locations
        Location corners[] = new Location[] {
                new Location(world,
                        locConfig.getDouble("Corners.First.x"),
                        locConfig.getDouble("Corners.First.y"),
                        locConfig.getDouble("Corners.First.z")),
                new Location(world,
                        locConfig.getDouble("Corners.Second.x"),
                        locConfig.getDouble("Corners.Second.y"),
                        locConfig.getDouble("Corners.Second.z"))
        };

        // List locations
        List<Location> spawnLocations = new ArrayList<>();
        ConfigurationSection section; Location spawnLocation;
        for (final String spawnNumber : locConfig.getConfigurationSection("Spawns").getKeys(false)) {
            section = locConfig.getConfigurationSection("Spawns." + spawnNumber);
            spawnLocation = new Location(world,
                                    section.getDouble("x"),
                                    section.getDouble("y"),
                                    section.getDouble("z"));
            spawnLocations.add(spawnLocation);
        }

        List<Location> deathMatchLocations = new ArrayList<>();
        Location deathMatchLocation;
        for (final String deathMatchSpawnNumber : locConfig.getConfigurationSection("DeathMatch").getKeys(false)) {
            section = locConfig.getConfigurationSection("DeathMatch." + deathMatchSpawnNumber);
            deathMatchLocation = new Location(world,
                                            section.getDouble("x"),
                                            section.getDouble("y"),
                                            section.getDouble("z"));
            deathMatchLocations.add(deathMatchLocation);
        }

        // Game Session
        GameSession session = new GameSession(arena, configuration.getMinPlayers(), configuration.getMaxPlayers());

        // Initialize the arena
        arena.initialize(world, lobby, spectator, corners, spawnLocations, dead, deathMatchLocations, session);
    }

    /**
     * Initialize the flags of the arena
     * @param arena arena to be updated
     */
    private void initializeFlags(ArenaSolo arena) {
        arena.updateFlag(ArenaFlag.BLOCK_BREAK, false);
        arena.updateFlag(ArenaFlag.BLOCK_PLACE, false);
        arena.updateFlag(ArenaFlag.BUCKET_EMPTY, false);
        arena.updateFlag(ArenaFlag.BUCKET_FILL, false);
        arena.updateFlag(ArenaFlag.CHAT, true);
        arena.updateFlag(ArenaFlag.DROP_ITEM, false);
        arena.updateFlag(ArenaFlag.DAMAGE, false);
        arena.updateFlag(ArenaFlag.INVENTORY_CLICK, true);
        arena.updateFlag(ArenaFlag.INTERACT, true);
        arena.updateFlag(ArenaFlag.MOVE, true);
        arena.updateFlag(ArenaFlag.PICKUP_ITEM, false);
    }

    /**
     * Create the configuration files
     *
     * @return true if successful
     */
    private boolean createFiles() {
        // Create main folder
        if(!getDataFolder().exists())
            if(!getDataFolder().mkdir())
                return false;

        // Configuration file
        final File configFile = new File(getDataFolder() + File.separator + "config.yml");
        if (!configFile.exists()) {
            try {
                if (!configFile.createNewFile())
                    return false;
            } catch (IOException e) {
                this.getLogger().log(Level.WARNING, "Error while creating config.yml. Reason: " + e.getMessage());
                return false;
            }
            if (!FilesAPI.copyFile(getResource("resources/config.yml"), configFile))
                return false;
        }

        // Database file
        final File databaseFile = new File(getDataFolder() + File.separator + "database.yml");
        if (!databaseFile.exists()) {
            try {
                if (!databaseFile.createNewFile())
                    return false;
            } catch (IOException e) {
                this.getLogger().log(Level.WARNING, "Error while creating database.yml. Reason: " + e.getMessage());
                return false;
            }
            if (!FilesAPI.copyFile(getResource("resources/database.yml"), databaseFile))
                return false;
        }
        return true;
    }

    /**
     * Read the configuration files and assign the variables
     *
     * @return true if successful, false otherwise
     */
    private boolean readFiles() {
        // Configuration File
        File configurationFile = new File(getDataFolder() + File.separator + "configuration.yml");
        FileConfiguration configurationConfiguration = YamlConfiguration.loadConfiguration(configurationFile);

        int minPlayers = configurationConfiguration.getInt("MinPlayers"),
                maxPlayers = configurationConfiguration.getInt("MaxPlayers"),
                maxSlots = configurationConfiguration.getInt("MaxSlots");
        String prefix = configurationConfiguration.getString("MessagePrefix"),
                tabTitle = configurationConfiguration.getString("TabTitle"),
                tabFooter = configurationConfiguration.getString("TabFooter");
        double coinsMultiplier = configurationConfiguration.getDouble("CoinsMultiplier");

        this.configuration = new Configuration(minPlayers, maxPlayers, maxSlots, prefix, tabTitle, tabFooter, coinsMultiplier);

        // Database File
        File databaseFile = new File(getDataFolder() + File.separator + "database.yml");
        FileConfiguration databaseConfiguration = YamlConfiguration.loadConfiguration(databaseFile);

        String type = databaseConfiguration.getString("Type"),
                hostname = databaseConfiguration.getString("Hostname"),
                database = databaseConfiguration.getString("Database"),
                username = databaseConfiguration.getString("Username"),
                password = databaseConfiguration.getString("Password");
        int port = databaseConfiguration.getInt("Port");

        this.database = Database.createDatabase(DatabaseType.getByName(type), hostname, port, database, username, password);

        return true;
    }
}