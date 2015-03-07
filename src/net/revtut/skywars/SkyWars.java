package net.revtut.skywars;

import net.revtut.libraries.converters.ConvertersAPI;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaDat;
import net.revtut.skywars.arena.tasks.ArenaEndGame;
import net.revtut.skywars.arena.tasks.ArenaInGame;
import net.revtut.skywars.arena.tasks.ArenaLobby;
import net.revtut.skywars.arena.tasks.ArenaPreGame;
import net.revtut.skywars.commands.appearance.Fireworks;
import net.revtut.skywars.commands.building.GameMode;
import net.revtut.skywars.commands.building.Speed;
import net.revtut.skywars.commands.game.Info;
import net.revtut.skywars.commands.game.Stats;
import net.revtut.skywars.commands.game.Watch;
import net.revtut.skywars.commands.mechanics.Time;
import net.revtut.skywars.commands.teleport.*;
import net.revtut.skywars.listeners.block.BlockBreak;
import net.revtut.skywars.listeners.block.BlockPlace;
import net.revtut.skywars.listeners.environment.ProjectileHit;
import net.revtut.skywars.listeners.environment.Weather;
import net.revtut.skywars.listeners.player.*;
import net.revtut.skywars.managers.ArenaManager;
import net.revtut.skywars.managers.PlayerManager;
import net.revtut.skywars.managers.ScoreBoardManager;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.utils.Message;
import net.revtut.skywars.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.sql.SQLException;
import java.util.Random;
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
     * Title message on join
     */
    public String titleMessage = "§3RevTut";

    /**
     * Subtitle message on join
     */
    public String subTitleMessage = "§7Network";

    /**
     * Fade in message time
     */
    public int fadeIn = 20;

    /**
     * Fade out message time
     */
    public int fadeOut = 20;

    /**
     * Time on screen of messages
     */
    public int timeOnScreen = 20;

    /**
     * Tab list title
     */
    public String tabTitle = "§3RevTut";

    /**
     * Tab list footer
     */
    public String tabFooter = "§6www.revtut.net";

    /**
     * MySQL object
     */
    public MySQL mysql;

    /**
     * Arena Manager
     */
    public ArenaManager arenaManager;

    /**
     * Player Manager
     */
    public PlayerManager playerManager;

    /**
     * ScoreBoard Manager
     */
    public ScoreBoardManager scoreBoardManager;

    /**
     * Player Chest
     */
    public PlayerChest playerChest;

    /**
     * Name of the server
     */
    public final String servidor = Bukkit.getServerName();

    /**
     * Random Class
     */
    public final Random rand = new Random();

    /**
     * Enable the plugin
     */
    @Override
    public void onEnable() {
        /* Create Classes */
        arenaManager = new ArenaManager(this);
        playerManager = new PlayerManager(this);
        scoreBoardManager = new ScoreBoardManager(this);

        /* Set Main Classes */
        PlayerDat.plugin = this;
        Message.plugin = this;

        /* Create Files */
        if (!createFiles())
            this.getLogger().log(Level.WARNING, "Error while trying to create the initial files.");

        /* Read Files */
        if (!readFiles())
            this.getLogger().log(Level.WARNING, "Error while trying to read the files.");

        /* Create Initial Arenas */
        String lastGameNumber = mysql.lastGameNumber();
        if (lastGameNumber == null) {
            this.getLogger().log(Level.WARNING, "Error while creating the initial arenas as last game number is null.");
            return;
        }
        lastGameNumber = arenaManager.nextGameNumber(lastGameNumber);
        // Arena 1
        if (!arenaManager.createNewArena()) {
            this.getLogger().log(Level.WARNING, "Error while creating the initial arenas.");
            return;
        }
        Arena arena = arenaManager.getArenas().get(0);
        ArenaDat arenaDat = arena.getArenaDat();
        if (arenaDat == null) {
            this.getLogger().log(Level.WARNING, "Error while creating the initial arenas as arena dat is null.");
            return;
        }
        arenaDat.setGameNumber(lastGameNumber);
        if (!arenaManager.createNewArena()) {
            this.getLogger().log(Level.WARNING, "Error while creating the initial arenas.");
            return;
        }

        /* Arena Runnables */
        ArenaLobby taskLobby = new ArenaLobby(this);
        ArenaLobby.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, taskLobby, 20, 20));
        ArenaPreGame taskPreGame = new ArenaPreGame(this);
        ArenaPreGame.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, taskPreGame, 20, 20));
        ArenaInGame taskInGame = new ArenaInGame(this);
        ArenaInGame.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, taskInGame, 20, 20));
        ArenaEndGame taskEndGame = new ArenaEndGame(this);
        ArenaEndGame.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, taskEndGame, 20, 20));

        /* Player Chest */
        playerChest = new PlayerChest(this);

        /* Register Plugin Messages */
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        /* Register Events */
        PluginManager pm = Bukkit.getServer().getPluginManager();
        /* Listeners */
        // Block
        pm.registerEvents(new BlockBreak(this), this);
        pm.registerEvents(new BlockPlace(this), this);
        // Environment
        pm.registerEvents(new ProjectileHit(this), this);
        pm.registerEvents(new Weather(this), this);
        // Player
        pm.registerEvents(new PlayerBucketEmpty(this), this);
        pm.registerEvents(new PlayerBucketFill(this), this);
        pm.registerEvents(new PlayerChat(this), this);
        pm.registerEvents(new PlayerCommand(this), this);
        pm.registerEvents(new PlayerDamage(this), this);
        pm.registerEvents(new PlayerDeath(this), this);
        pm.registerEvents(new PlayerDrop(this), this);
        pm.registerEvents(new PlayerFood(this), this);
        pm.registerEvents(new PlayerInteract(this), this);
        pm.registerEvents(new PlayerInventoryClick(this), this);
        pm.registerEvents(new PlayerJoin(this), this);
        pm.registerEvents(new PlayerMove(this), this);
        pm.registerEvents(new PlayerPickup(this), this);
        pm.registerEvents(new PlayerQuit(this), this);
        pm.registerEvents(new PlayerRespawn(this), this);

        /* Commands */
        // Appearance
        getCommand("firework").setExecutor(new Fireworks(this));
        // Building
        getCommand("gamemode").setExecutor(new GameMode(this));
        getCommand("speed").setExecutor(new Speed(this));
        // Game
        getCommand("information").setExecutor(new Info(this));
        getCommand("statistics").setExecutor(new Stats(this));
        getCommand("watch").setExecutor(new Watch(this));
        // Mechanics
        getCommand("time").setExecutor(new Time(this));
        // Teleport
        getCommand("spawn").setExecutor(new Spawn(this));
        getCommand("teleportall").setExecutor(new TeleportAll(this));
        getCommand("teleporthere").setExecutor(new TeleportHere(this));
        getCommand("teleportpos").setExecutor(new TeleportPos(this));
        getCommand("teleport").setExecutor(new TeleportTo(this));
    }

    /**
     * Disable the plugin
     */
    @Override
    public void onDisable() {
        /* Close MySQL */
        if (!mysql.closeConnection())
            this.getLogger().log(Level.WARNING, "Error while trying to close connection.");
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

        /* Config File */
        final File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            try {
                if (!config.createNewFile())
                    return false;
            } catch (IOException e) {
                this.getLogger().log(Level.WARNING, "Error while creating config.yml. Reason: " + e.getMessage());
                return false;
            }
            if (!copyFile(getResource("config.yml"), config))
                return false;
        }

        /* MySQL File */
        final File mysqlFile = new File(getDataFolder() + File.separator + "mysql.yml");
        if (!mysqlFile.exists()) {
            try {
                if (!mysqlFile.createNewFile())
                    return false;
            } catch (IOException e) {
                this.getLogger().log(Level.WARNING, "Error while creating mysql.yml. Reason: " + e.getMessage());
                return false;
            }
            if (!copyFile(getResource("mysql.yml"), mysqlFile))
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
    private boolean copyFile(final InputStream in, final File file) {
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
        final File configFile = new File(getDataFolder() + File.separator + "config.yml");
        final FileConfiguration configConfiguration = YamlConfiguration.loadConfiguration(configFile);
        // Title
        titleMessage = ConvertersAPI.convertToJSON(configConfiguration.getString("Title").replaceAll("&", "§"));
        subTitleMessage = ConvertersAPI.convertToJSON(configConfiguration.getString("Subtitle").replaceAll("&", "§"));
        fadeIn = configConfiguration.getInt("FadeIn");
        fadeOut = configConfiguration.getInt("FadeOut");
        timeOnScreen = configConfiguration.getInt("TimeOnScreen");
        // Tab
        tabTitle = ConvertersAPI.convertSpecialCharacters(ConvertersAPI.convertToJSON(configConfiguration.getString("TabTitle").replaceAll("&", "§")));
        tabFooter = ConvertersAPI.convertSpecialCharacters(ConvertersAPI.convertToJSON(configConfiguration.getString("TabFooter").replaceAll("&", "§")));

        /* MySQL File */
        final File mysqlFile = new File(getDataFolder() + File.separator + "mysql.yml");
        final FileConfiguration mysqlConfiguration = YamlConfiguration.loadConfiguration(mysqlFile);
        mysql = new MySQL(this, mysqlConfiguration.getString("Hostname"), mysqlConfiguration.getString("Port"), mysqlConfiguration.getString("Database"), mysqlConfiguration.getString("Username"), mysqlConfiguration.getString("Password"));
        if (!mysql.openConnection())
            return false;
        mysql.createMySQL();
        // Check MySQL Task
        checkMySQL();

        return true;
    }

    /**
     * Connect a player to a server
     *
     * @param player player to send
     * @param server server to connect
     */
    public void connectServer(Player player, String server) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);

        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
            out.writeUTF(player.getName());
        } catch (IOException e) {
            this.getLogger().log(Level.WARNING, "Error while sending player to " + server);
            this.getLogger().log(Level.WARNING, e.getMessage());
        }

        player.sendPluginMessage(this, "BungeeCord", b.toByteArray());
    }


    /**
     * Check if MySQL connection is opened, if not it tries to open it
     */
    public void checkMySQL() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            try {
                mysql.getConnection().createStatement().executeQuery("SELECT 1;");
            } catch (SQLException e) {
                getLogger().log(Level.WARNING, "MySQL connection is closed!");
                mysql.openConnection();
            }
        }, 1200, 1200);
    }
}
