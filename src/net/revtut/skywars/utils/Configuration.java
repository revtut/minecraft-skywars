package net.revtut.skywars.utils;

import org.bukkit.Location;

/**
 * Configuration of the game
 */
public class Configuration {

    /**
     * Location of the lobby
     */
    private final Location lobby;

    /**
     * Minimum players to start the game
     */
    private final int minPlayers;

    /**
     * Maximum players on a arena
     */
    private final int maxPlayers;

    /**
     * Maximum players on this game
     */
    private final int maxSlots;

    /**
     * Prefix of the game messages
     */
    private final String prefix;

    /**
     * Title of the tab list
     */
    private final String tabTitle;

    /**
     * Footer of the tab list
     */
    private final String tabFooter;

    /**
     * Coins multiplier
     */
    private final double coinsMultiplier;

    /**
     * Constructor of Configuration
     * @param lobby location of the lobby
     * @param minPlayers minimum players to start the game
     * @param maxPlayers maximum players on a arena
     * @param maxSlots maximum players on this game
     * @param prefix prefix of the game messages
     * @param tabTitle title of the tab list
     * @param tabFooter footer of the tab list
     * @param coinsMultiplier coins multiplier
     */
    public Configuration(final Location lobby, final int minPlayers, final int maxPlayers, final int maxSlots, final String prefix, final String tabTitle, final String tabFooter, final double coinsMultiplier) {
        this.lobby = lobby;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.maxSlots = maxSlots;
        this.prefix = prefix;
        this.tabTitle = tabTitle;
        this.tabFooter = tabFooter;
        this.coinsMultiplier = coinsMultiplier;
    }

    /**
     * Get the lobby location
     * @return lobby location
     */
    public Location getLobby() {
        return lobby;
    }

    /**
     * Get the minimum players to start a game
     * @return minimum players to start a game
     */
    public int getMinPlayers() {
        return minPlayers;
    }

    /**
     * Get the maximum players on a arena
     * @return maximum players on a arena
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Get the maximum players on this game
     * @return maximum players on this game
     */
    public int getMaxSlots() {
        return maxSlots;
    }

    /**
     * Get the prefix of the game messages
     * @return prefix of the game messages
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Get the title of the tab list
     * @return title of the tab list
     */
    public String getTabTitle() {
        return tabTitle;
    }

    /**
     * Get the footer of the tab list
     * @return footer of the tab list
     */
    public String getTabFooter() {
        return tabFooter;
    }

    /**
     * Get the coins multiplier
     * @return coins multiplier
     */
    public double getCoinsMultiplier() {
        return coinsMultiplier;
    }
}
