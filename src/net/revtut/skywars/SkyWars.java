package net.revtut.skywars;

import net.revtut.libraries.generic.database.Database;
import net.revtut.libraries.generic.database.types.DatabaseType;
import net.revtut.libraries.generic.util.Files;
import net.revtut.libraries.minecraft.games.GameAPI;
import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.ArenaFlag;
import net.revtut.libraries.minecraft.games.arena.session.GameSession;
import net.revtut.libraries.minecraft.games.arena.session.GameState;
import net.revtut.libraries.minecraft.games.arena.types.ArenaSolo;
import net.revtut.libraries.minecraft.games.arena.types.ArenaType;
import net.revtut.libraries.minecraft.maths.AlgebraAPI;
import net.revtut.libraries.minecraft.maths.ConvertersAPI;
import net.revtut.libraries.minecraft.scoreboard.InfoBoard;
import net.revtut.skywars.listeners.GameListener;
import net.revtut.skywars.listeners.arena.ArenaLoadListener;
import net.revtut.skywars.listeners.session.SwitchStateListener;
import net.revtut.skywars.listeners.session.TimerExpireListener;
import net.revtut.skywars.listeners.session.TimerTickListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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
     * Manager of the Information Boards
     */
    private InfoBoardManager infoBoardManager;

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
        gameController.createArena(ArenaType.SOLO);

        infoBoardManager = new InfoBoardManager();

        // Register events
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ArenaLoadListener(), this);

        pluginManager.registerEvents(new SwitchStateListener(), this);
        pluginManager.registerEvents(new TimerExpireListener(), this);
        pluginManager.registerEvents(new TimerTickListener(), this);

        pluginManager.registerEvents(new GameListener(), this);
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
     * Get the information board manager
     * @return information board manager
     */
    public InfoBoardManager getInfoBoardManager() {
        return infoBoardManager;
    }

    /**
     * Get the controller of the game
     * @return controller of the game
     */
    public GameController getGameController() {
        return gameController;
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
            if (!Files.copyFile(getResource("resources/config.yml"), configFile))
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
            if (!Files.copyFile(getResource("resources/database.yml"), databaseFile))
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
        File configurationFile = new File(getDataFolder() + File.separator + "config.yml");
        FileConfiguration configurationConfiguration = YamlConfiguration.loadConfiguration(configurationFile);

        Location lobby = Utils.parseLocation(configurationConfiguration, "Lobby");
        int minPlayers = configurationConfiguration.getInt("MinPlayers"),
                maxPlayers = configurationConfiguration.getInt("MaxPlayers"),
                maxSlots = configurationConfiguration.getInt("MaxSlots");
        String prefix = ConvertersAPI.convertToJSON(ChatColor.translateAlternateColorCodes('&', configurationConfiguration.getString("MessagePrefix"))),
                tabTitle = ConvertersAPI.convertToJSON(ChatColor.translateAlternateColorCodes('&', configurationConfiguration.getString("TabTitle"))),
                tabFooter = ConvertersAPI.convertToJSON(ChatColor.translateAlternateColorCodes('&', configurationConfiguration.getString("TabFooter")));
        double coinsMultiplier = configurationConfiguration.getDouble("CoinsMultiplier");

        this.configuration = new Configuration(lobby, minPlayers, maxPlayers, maxSlots, prefix, tabTitle, tabFooter, coinsMultiplier);

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