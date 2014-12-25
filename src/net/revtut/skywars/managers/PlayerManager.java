package net.revtut.skywars.managers;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.player.PlayerStatus;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Player Manager.
 *
 * <P>Manages all the players in the server.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerManager {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerManager
     *
     * @param plugin main class
     */
    public PlayerManager(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * List of all playerDats in the server
     */
    private final List<PlayerDat> playersDat = new ArrayList<PlayerDat>();

    /**
     * Add a new player dat to the server
     *
     * @param playerDat player dat to add to the server
     * @return true if successfull
     */
    public boolean addPlayerDat(PlayerDat playerDat) {
        if (getPlayerDatByUUID(playerDat.getUUID()) != null)
            return false;
        playersDat.add(playerDat);
        return true;
    }

    /**
     * Removes a player dat from the server
     *
     * @param playerDat player dat to remove from the server
     */
    public void removePlayerDat(PlayerDat playerDat) {
        playersDat.remove(playerDat);
    }

    /**
     * Get a player dat from a given UUID
     *
     * @param uuid uuid to get the player dat
     * @return player dat of that UUID
     */
    public PlayerDat getPlayerDatByUUID(UUID uuid) {
        for (PlayerDat alvoDat : playersDat)
            if (alvoDat.getUUID() == uuid)
                return alvoDat;
        return null;
    }

    /**
     * Configure a player
     *
     * @param playerDat     player to configure
     * @param status        status of the player
     * @param gameMode      gamemode of the player
     * @param allowFlight   allow player to fly
     * @param setFlying     set player as flying
     * @param expLevel      experience level of the player
     * @param expPercent    experience percentage of the player
     * @param healthLevel   health level of the player
     * @param foodLevel     food level of the player
     * @param removePotions true if needs to remove all potions of the player
     * @param clearInv      true if needs to clear players inventory
     * @param fireTicks     fire ticks of the player
     * @return true if successfull
     */
    public boolean configPlayer(PlayerDat playerDat, PlayerStatus status, GameMode gameMode, boolean allowFlight, boolean setFlying, int expLevel, float expPercent, double healthLevel, int foodLevel, boolean removePotions, boolean clearInv, int fireTicks) {
        playerDat.setStatus(status);
        // Player
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if (null == player)
            return false;
        player.setGameMode(gameMode);
        player.setAllowFlight(allowFlight);
        player.setFlying(setFlying);
        player.setTotalExperience(expLevel);
        player.setExp(expPercent);
        player.setHealth(healthLevel);
        player.setFoodLevel(foodLevel);
        if (removePotions)
            player.getActivePotionEffects().clear();
        if (clearInv) {
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
        }
        player.setFireTicks(fireTicks);
        return true;
    }

}
