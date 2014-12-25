package net.RevTut.skywar.listeners.player;

import net.RevTut.skywar.SkyWars;
import net.RevTut.skywar.arena.ArenaLocation;
import net.RevTut.skywar.arena.ArenaStatus;
import net.RevTut.skywar.player.PlayerDat;
import net.RevTut.skywar.player.PlayerStatus;
import net.RevTut.skywar.arena.Arena;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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

        // Alive
        if (playerDat.getStatus() == PlayerStatus.ALIVE){
            // Engineer
            arena.getKitManager().engineer.landMineActivate(player);

            // Check if there is near players
            for(PlayerDat alvoDat : arena.getPlayers()){
                if(alvoDat.getStatus() != PlayerStatus.DEAD)
                    continue;
                Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
                if(alvo == null)
                    continue;
                Location location = alvo.getLocation();
                if(alvo.getUniqueId() == player.getUniqueId())
                    continue;
                if(alvo.getWorld() != player.getWorld())
                    continue;
                if(player.getLocation().distanceSquared(location) > 25)
                    continue;
                Location safeLocation = new Location(location.getWorld(), location.getX(), location.getY() + 5, location.getZ(), location.getYaw(), location.getPitch());
                int i = 0;
                do{
                    safeLocation.setY(safeLocation.getY() + i);
                    i++;
                }while(safeLocation.getBlock().getType() != Material.AIR);
                alvo.teleport(safeLocation);
                if(alvo.getAllowFlight())
                    alvo.setFlying(true);
            }
            return;
        }

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

            // Check if he is near a player
            for(PlayerDat alvoDat : arena.getPlayers()){
                if(alvoDat.getStatus() != PlayerStatus.ALIVE)
                    continue;
                Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
                if(alvo == null)
                    continue;
                if(alvo.getUniqueId() == player.getUniqueId())
                    continue;
                if(alvo.getWorld() != player.getWorld())
                    continue;
                if(alvo.getLocation().distanceSquared(location) > 25)
                    continue;
                Location safeLocation = new Location(location.getWorld(), location.getX(), location.getY() + 5, location.getZ(), location.getYaw(), location.getPitch());
                int i = 0;
                do{
                    safeLocation.setY(safeLocation.getY() + i);
                    i++;
                }while(safeLocation.getBlock().getType() != Material.AIR);
                player.teleport(safeLocation);
                if(player.getAllowFlight())
                    player.setFlying(true);
            }
        }
    }

}
