package net.revtut.skywars.listeners.player;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaLocation;
import net.revtut.skywars.arena.ArenaStatus;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.player.PlayerStatus;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Player Move.
 *
 * <P>Controls the player move event.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerMove implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerMove
     *
     * @param plugin main class
     */
    public PlayerMove(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of a player when he moves
     *
     * @param e player move event
     * @see PlayerMoveEvent
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
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
            return;

        // Dead
        if (playerDat.getStatus() == PlayerStatus.DEAD){
            Location location = player.getLocation();
            ArenaLocation arenaLocation = arena.getArenaLocation();
            double minX = arenaLocation.getFirstCorner().getX();
            double minZ = arenaLocation.getFirstCorner().getZ();
            double maxX = arenaLocation.getSecondCorner().getX();
            double maxZ = arenaLocation.getSecondCorner().getZ();
            double x = location.getX();
            double z = location.getZ();

            // Check if he is outside the arena
            if(x < minX){
                Location safeLocation = new Location(location.getWorld(), location.getX() + 5, location.getY(), location.getZ());
                player.teleport(safeLocation);
            }
            if(x > maxX){
                Location safeLocation = new Location(location.getWorld(), location.getX() - 5, location.getY(), location.getZ());
                player.teleport(safeLocation);
            }
            if(z < minZ){
                Location safeLocation = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ() + 5);
                player.teleport(safeLocation);
            }
            if(z > maxZ){
                Location safeLocation = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ() - 5);
                player.teleport(safeLocation);
            }
        }
    }

}
