package net.RevTut.Skywars;

import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.libraries.world.WorldAPI;
import net.RevTut.Skywars.utils.Converters;
import net.RevTut.Skywars.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Logger;

public class Main extends JavaPlugin {
    /* Title Message */
    public String titleMessage;
    public String subTitleMessage;
    public int fadeIn;
    public int fadeOut;
    public int timeOnScreen;
    /* Tab List */
    public String tabTitle;
    public String tabFooter;
    /* MySQL */
    public MySQL mysql;
    /* Logger */
    public Logger log = Logger.getLogger("Minecraft");

    /* Obtem Nome do servidor */
    public String servidor = Bukkit.getServerName();

    @Override
    public void onEnable() {
        /* Create Files */
        if (!createFiles())
            System.out.println("Error while trying to create the initial files.");

        /* Read Files */
        if (!readFiles())
            System.out.println("Error while trying to read the files.");

        /* Create Initial Arenas */
        Arena.createNewArena();
        Arena.createNewArena();
    }

    @Override
    public void onDisable() {
        /* Close MySQL */
        if (!mysql.closeConnection())
            System.out.println("Error while trying to close connection.");
    }

    private boolean createFiles() {
        /* Config File */
        final File config = new File(getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            try {
                config.getParentFile().mkdirs();
                config.createNewFile();
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
                mysqlConf.getParentFile().mkdirs();
                mysqlConf.createNewFile();
            } catch (IOException e) {
                System.out.println("Error while creating mysql.yml. Reason: " + e.getMessage());
            }
            if (!copy(getResource("mysql.yml"), mysqlConf))
                return false;
        }
        return true;
    }

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
        if (mysql.openConnection())
            mysql.createMySQL();
        return true;
    }
}
