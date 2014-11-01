package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaLocation;
import net.RevTut.Skywars.libraries.world.WorldAPI;
import net.RevTut.Skywars.libraries.world.WorldServerNMS;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
            return;
        }
        if(!Arena.addPlayer(playerDat)) {
            /**
             * Send him to HUB. No arena available
             */
            return;
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
                    String mapName = (listWorlds[posWorld].getName() + "_" + arenaNumber);
                    String trgPath = new File(new File(currentDir).getParentFile().getAbsolutePath() + File.separator + mapName).getAbsolutePath();

                    // Copy World
                    WorldAPI.copyDirectoryAsync(srcPath, trgPath);
                    // Load World
                    WorldAPI.loadWorldAsync(mapName);

                    // Create Arena
                    final File locations = new File(new File(currentDir).getParentFile().getAbsolutePath() + File.separator + mapName + File.separator + "locations.yml");
                    final FileConfiguration configLocations = YamlConfiguration.loadConfiguration(locations);
                    Location lobbyLocation = null, deathSpawnLocation = null, firstCorner = null, secondCorner = null;
                    List<Location> spawnLocations = new ArrayList<Location>();
                    for (final String message : configLocations.getConfigurationSection("").getKeys(false)) {
                        // Spawn Locations
                        if(message.equalsIgnoreCase("spawnLocations")){
                            for (final String spawnLoc : configLocations.getConfigurationSection("spawnLocations").getKeys(false)) {
                                // Location
                                String locString = configLocations.getString(spawnLoc);
                                String[] locStringArgs = locString.split(",");
                                float[] parsed = new float[3];
                                for (int a = 0; a < 3; a++) {
                                    parsed[a] = Float.parseFloat(locStringArgs[a + 1]);
                                }
                                spawnLocations.add(new Location(Bukkit.getWorld(locStringArgs[0]), parsed[0], parsed[1], parsed[2]));
                            }
                        }else {
                            // Location
                            String locString = configLocations.getString(message);
                            String[] locStringArgs = locString.split(",");
                            float[] parsed = new float[3];
                            for (int a = 0; a < 3; a++) {
                                parsed[a] = Float.parseFloat(locStringArgs[a + 1]);
                            }
                            // Check which location it is
                            if (message.equalsIgnoreCase("lobbyLocation")) {
                                lobbyLocation = new Location(Bukkit.getWorld(locStringArgs[0]), parsed[0], parsed[1], parsed[2]);
                            } else if (message.equalsIgnoreCase("deathspawnLocation")) {
                                deathSpawnLocation = new Location(Bukkit.getWorld(locStringArgs[0]), parsed[0], parsed[1], parsed[2]);
                            } else if (message.equalsIgnoreCase("firstCorner")) {
                                firstCorner = new Location(Bukkit.getWorld(locStringArgs[0]), parsed[0], parsed[1], parsed[2]);
                            } else if (message.equalsIgnoreCase("secondCorner")) {
                                secondCorner = new Location(Bukkit.getWorld(locStringArgs[0]), parsed[0], parsed[1], parsed[2]);
                            }
                        }
                    }
                    ArenaLocation arenaLocation = new ArenaLocation(lobbyLocation, deathSpawnLocation, firstCorner, secondCorner, spawnLocations);
                    Arena arena = new Arena(arenaNumber, mapName, arenaLocation);

                    lock.unlock();
                }
            });
            thread.start();
        }
    }

}
