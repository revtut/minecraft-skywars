package net.revtu.skywars.listeners.player;

import net.revtu.skywars.SkyWars;
import net.revtu.skywars.arena.ArenaStatus;
import net.revtu.skywars.player.PlayerStatus;
import net.revtu.skywars.arena.Arena;
import net.revtu.skywars.player.PlayerDat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * Player Pickup.
 *
 * <P>Controls the pickup item event.</P>
 *
 * @author João Silva
 * @version 1.0
 */
public class PlayerPickup implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerPickup
     *
     * @param plugin main class
     */
    public PlayerPickup(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of a player when he pickups an item
     *
     * @param e player pickup item event
     * @see PlayerPickupItemEvent
     */
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent e) {
        Player player = e.getPlayer();
        // Player Dat
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if (playerDat == null)
            return;
        // Arena
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (null == arena)
            return;
        if (arena.getStatus() != ArenaStatus.INGAME)
            e.setCancelled(true);
        if (playerDat.getStatus() != PlayerStatus.ALIVE)
            e.setCancelled(true);
    }

}
