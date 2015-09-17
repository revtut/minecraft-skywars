package net.revtut.skywars;

import net.revtut.libraries.database.Database;
import net.revtut.libraries.database.types.DatabaseType;
import net.revtut.libraries.database.types.MySQL;
import net.revtut.libraries.games.GameAPI;
import net.revtut.libraries.games.GameController;
import net.revtut.libraries.games.arena.types.ArenaSolo;
import net.revtut.libraries.games.arena.types.ArenaType;
import net.revtut.libraries.utils.FilesAPI;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;

/**
 * Main class.
 *
 * <P>Skywars is a plugin where you have several players in islands. They all have the same amount of chests and resources available.</P>
 * <P>However inside the chests the furniture is completely random. The goal of a player is either to kill all the players or throw them
 * to the void since there is no ground on the map. If after a predefined amount of time the game is not over yet we will have a tie so
 * there will be no winner.</P>
 * <P>There is a wide variety of kits, each one gives a differente advantage to the player that uses it.</P>
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
     * Database of the game
     */
    private Database database;

    /**
     * Enable the plugin
     */
    @Override
    public void onEnable() {
        instance = this;

        readFiles();

        // Database
        if(database == null)
            getLogger().log(Level.SEVERE, "Database could not be created... (maybe unknown type of database?)");
        else {
            database.connect();
            if(database.getConnection() == null)
                getLogger().log(Level.SEVERE, "Connection to the database could not be made!");
        }

        // Integrate GameAPI
        gameController = GameAPI.getInstance().registerGame(this, new File(getDataFolder() + File.separator + "worlds"));

        // Initialize first arena
        ArenaSolo arena = (ArenaSolo) gameController.createArena(ArenaType.SOLO);
        World world = gameController.loadRandomWorld(getName() + "_" + arena.getId());
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
     * Get the instance of Sky Wars
     * @return instance of Sky Wars
     */
    public static SkyWars getInstance() {
        return instance;
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
     * Create the configuration files
     *
     * @return true if successfull
     */
    private boolean createFiles() {
        /* Main Folder */
        if(!getDataFolder().exists())
            if(!getDataFolder().mkdir())
                return false;

        /* Configuration File */
        final File configFile = new File(getDataFolder() + File.separator + "config.yml");
        if (!configFile.exists()) {
            try {
                if (!configFile.createNewFile())
                    return false;
            } catch (IOException e) {
                this.getLogger().log(Level.WARNING, "Error while creating config.yml. Reason: " + e.getMessage());
                return false;
            }
            if (!FilesAPI.copyFile(getResource("config.yml"), configFile))
                return false;
        }

        /* Database File */
        final File databaseFile = new File(getDataFolder() + File.separator + "database.yml");
        if (!databaseFile.exists()) {
            try {
                if (!databaseFile.createNewFile())
                    return false;
            } catch (IOException e) {
                this.getLogger().log(Level.WARNING, "Error while creating database.yml. Reason: " + e.getMessage());
                return false;
            }
            if (!FilesAPI.copyFile(getResource("database.yml"), databaseFile))
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
        /* Database File */
        File databaseFile = new File(getDataFolder() + File.separator + "database.yml");
        FileConfiguration databaseConfiguration = YamlConfiguration.loadConfiguration(databaseFile);

        String type, hostname, database, username, password;
        int port;

        type = databaseConfiguration.getString("Type");
        hostname = databaseConfiguration.getString("Hostname");
        port = databaseConfiguration.getInt("Port");
        database = databaseConfiguration.getString("Database");
        username = databaseConfiguration.getString("Username");
        password = databaseConfiguration.getString("Password");

        this.database = Database.createDatabase(DatabaseType.getByName(type), hostname, port, database, username, password);

        return true;
    }
}
