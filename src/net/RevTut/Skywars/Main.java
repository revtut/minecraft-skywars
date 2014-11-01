package net.RevTut.Skywars;

import net.RevTut.Skywars.utils.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Main extends JavaPlugin {

    /* MySQL */
    public MySQL mysql;
    /* Logger */
    public Logger log = Logger.getLogger("Minecraft");

    /* Obtem Nome do servidor */
    public String servidor = Bukkit.getServerName();

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

}
