package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

/**
 * Player Respawn.
 *
 * <P>Controls the respawn event.</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class PlayerFood implements Listener {

    /**
     * Main class
     */
    private final Main plugin;

    /**
     * Constructor of PlayerFood
     *
     * @param plugin main class
     */
    public PlayerFood(final Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of a food when it changes level
     *
     * @param e     food level change event
     * @see         FoodLevelChangeEvent
     */
    @EventHandler
    public void onFoodChange(FoodLevelChangeEvent e){
        Player player = (Player) e.getEntity();
        // Player Dat
        PlayerDat playerDat = PlayerDat.getPlayerDatByUUID(player.getUniqueId());
        if(playerDat == null)
            return;
        if(playerDat.getStatus() != PlayerStatus.ALIVE)
            e.setCancelled(true);
    }

}
