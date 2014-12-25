package net.revtut.skywars.listeners.player;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.player.PlayerStatus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * Player Food.
 *
 * <P>Controls the food level change event.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerFood implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerFood
     *
     * @param plugin main class
     */
    public PlayerFood(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of a food when it changes level
     *
     * @param e food level change event
     * @see FoodLevelChangeEvent
     */
    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e) {
        Player player = (Player) e.getEntity();
        // Player Dat
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if (playerDat == null)
            return;
        if (playerDat.getStatus() != PlayerStatus.ALIVE)
            e.setCancelled(true);
    }

}
