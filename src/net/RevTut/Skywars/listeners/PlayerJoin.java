package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.libraries.world.WorldAPI;
import net.RevTut.Skywars.libraries.world.WorldServerNMS;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.Random;
import java.util.UUID;

/**
 * Created by waxcoder on 31-10-2014.
 */

public class PlayerJoin implements Listener {

    public Main plugin;

    public PlayerJoin(final Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();

        // MySQL Tasks
        final UUID uuid = p.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                // PlayerDat
                plugin.mysql.createPlayerDat(uuid);
            }
        });
        // Add to Arena
        PlayerDat playerDat = PlayerDat.getPlayerDatByUUID(uuid);
        if(playerDat == null){
            /**
             * Send him to HUB. Error while creating playerDat
             */
        }
        if(!Arena.addPlayer(playerDat)) {
            /**
             * Send him to HUB. No arena available
             */
        }
        // Check if arenas are needed
        if(Arena.getNumberAvailableArenas() <= 1){
            // Add new arena
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    WorldServerNMS.UnsafeLock lock = new WorldServerNMS.UnsafeLock(new WorldAPI());
                    lock.lock();

                    // Arena Number
                    int arenaNumber = Arena.nextArenaNumber();

                    // Current Directory
                    String currentDir = System.getProperty("user.dir");
                    // Source Directory
                    File[] listWorlds = new File(new File(currentDir).getParentFile().getAbsolutePath() + File.separator + "worlds").listFiles();
                    int posWorld = new Random().nextInt(listWorlds.length);
                    String srcPath = listWorlds[posWorld].getAbsolutePath();
                    // Target Directory
                    String trgPath = new File(new File(currentDir).getParentFile().getAbsolutePath() + File.separator + (listWorlds[posWorld].getName() + "_" + arenaNumber)).getAbsolutePath();

                    // Copy Worlds
                    WorldAPI.copyDirectoryAsync(srcPath, "WORLD TARGET DIRECTORY");
                    WorldAPI.loadWorldAsync("WORLD NAME");

                    lock.unlock();
                }
            });
            thread.start();
        }
    }

}
