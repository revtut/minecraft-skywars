package net.RevTut.Skywars;

import net.RevTut.Skywars.arena.ArenaManager;
import net.RevTut.Skywars.arena.ArenaRunnable;
import net.RevTut.Skywars.libraries.nametag.NameTagAPI;
import net.RevTut.Skywars.listeners.*;
import net.RevTut.Skywars.utils.Converters;
import net.RevTut.Skywars.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

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
public class Main extends JavaPlugin {
    /**
     * Title message on join
     */
    public static String titleMessage;

    /**
     * Subtitle message on join
     */
    public static String subTitleMessage;

    /**
     * Fade in message time
     */
    public static int fadeIn;

    /**
     * Fade out message time
     */
    public static int fadeOut;

    /**
     * Time on screen of messages
     */
    public static int timeOnScreen;

    /**
     * Tab list title
     */
    public String tabTitle;

    /**
     * Tab list footer
     */
    public String tabFooter;

    /**
     * MySQL object
     */
    public MySQL mysql;

    /**
     * Arena Manager
     */
    public ArenaManager arenaManager;

    /**
     * Name of the server
     */
    public String servidor = Bukkit.getServerName();

    /**
     * Enable the plugin
     */
    @Override
    public void onEnable() {
        /* Create Files */
        if (!createFiles())
            System.out.println("Error while trying to create the initial files.");

        /* Read Files */
        if (!readFiles())
            System.out.println("Error while trying to read the files.");

        /* Create Arena Manager */
        arenaManager = new ArenaManager();
        /* Create Initial Arenas */
        arenaManager.createNewArena();
        arenaManager.createNewArena();

        /* Arena Runnable */
        ArenaRunnable task = new ArenaRunnable(this);
        task.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, 20, 20));

        /* Regist Events */
        PluginManager pm = Bukkit.getServer().getPluginManager();
        /* Libraries */
        pm.registerEvents(new NameTagAPI(), this);
        /* Listeners  */
        pm.registerEvents(new PlayerChat(this), this);
        pm.registerEvents(new PlayerDamage(this), this);
        pm.registerEvents(new PlayerDeath(this), this);
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerQuit(this), this);
        pm.registerEvents(new PlayerRespawn(this), this);
    }

    /**
     * Disable the plugin
     */
    @Override
    public void onDisable() {
        /* Close MySQL */
        if (!mysql.closeConnection())
            System.out.println("Error while trying to close connection.");
    }

    /**
     * Create the configuration files
     *
     * @return true if successfull
     */
    private boolean createFiles() {
        /* Config File */
        final File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            try {
                if(!config.getParentFile().mkdirs())
                    return false;
                if(!config.createNewFile())
                    return false;
            } catch (IOException e) {
                System.out.println("Error while creating config.yml. Reason: " + e.getMessage());
            }
            if (!copy(getResource("config.yml"), config))
                return false;
        }
        /* MySQL File */
        final File mysqlConf = new File(getDataFolder() + File.separator + "mysql.yml");
        if (!mysqlConf.exists()) {
            try {
                if(!mysqlConf.getParentFile().mkdirs())
                    return false;
                if(!mysqlConf.createNewFile())
                    return false;
            } catch (IOException e) {
                System.out.println("Error while creating mysql.yml. Reason: " + e.getMessage());
            }
            if (!copy(getResource("mysql.yml"), mysqlConf))
                return false;
        }
        return true;
    }

    /**
     * Copy from a file to another one
     *
     * @param in   file to be copied
     * @param file file to copy to
     * @return true if successfull
     */
    private boolean copy(final InputStream in, final File file) {
        try {
            final OutputStream out = new FileOutputStream(file);
            final byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Read the configuration files and assign the variables
     *
     * @return true if successfull
     */
    private boolean readFiles() {
        /* Config File */
        final File config = new File(getDataFolder() + File.separator + "config.yml");
        final FileConfiguration configConf = YamlConfiguration.loadConfiguration(config);
        // Title
        titleMessage = Converters.convertToJSON(configConf.getString("Title").replaceAll("&", "§"));
        subTitleMessage = Converters.convertToJSON(configConf.getString("Subtitle").replaceAll("&", "§"));
        fadeIn = configConf.getInt("FadeIn");
        fadeOut = configConf.getInt("FadeOut");
        timeOnScreen = configConf.getInt("TimeOnScreen");
        // Tab
        tabTitle = Converters.convertSpecialCharacters(Converters.convertToJSON(configConf.getString("TabTitle").replaceAll("&", "§")));
        tabFooter = Converters.convertSpecialCharacters(Converters.convertToJSON(configConf.getString("TabFooter").replaceAll("&", "§")));
        /* MySQL File */
        final File mysqlFile = new File(getDataFolder() + File.separator + "mysql.yml");
        final FileConfiguration mysqlConf = YamlConfiguration.loadConfiguration(mysqlFile);
        mysql = new MySQL(mysqlConf.getString("Hostname"), mysqlConf.getString("Port"), mysqlConf.getString("Database"), mysqlConf.getString("Username"), mysqlConf.getString("Password"));
        if (!mysql.openConnection())
            return false;
        mysql.createMySQL();

        return true;
    }
}
