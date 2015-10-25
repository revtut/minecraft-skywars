package net.revtut.skywars.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Utilities for the MiniGame
 */
public class Utils {

    /**
     * Parse the location of a section from a configuration file
     * @param configuration configuration file to parse location
     * @param section section of the location
     * @return parsed location
     */
    public static Location parseLocation(final FileConfiguration configuration, final String section) {
        return parseLocation(configuration, section, Bukkit.getWorld(configuration.getString(section + ".world")));
    }

    /**
     * Parse the location of a section from a configuration file
     * @param configuration configuration file to parse location
     * @param section section of the location
     * @param world world of the location
     * @return parsed location
     */
    public static Location parseLocation(final FileConfiguration configuration, final String section, final World world) {
        return new Location(
                world,
                configuration.getDouble(section + ".x"),
                configuration.getDouble(section + ".y"),
                configuration.getDouble(section + ".z")
        );
    }

}
