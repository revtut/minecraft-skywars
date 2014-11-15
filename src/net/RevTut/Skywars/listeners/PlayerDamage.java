package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Player Damage.
 *
 * <P>Controls the damage event.</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class PlayerDamage implements Listener {

    /**
     * Main class
     */
    private final Main plugin;

    /**
     * Constructor of PlayerDamage
     *
     * @param plugin main class
     */
    public PlayerDamage(final Main plugin) {
        this.plugin = plugin;
    }

    /** Map with last targets and damagers */
    public static Map<UUID, UUID> lastPlayerDamager = new HashMap<UUID, UUID>();

    /**
     * Control the damage by entity on the server
     *
     * @param e     entity damage by entity event
     * @see         EntityDamageByEntityEvent
     * @see         PlayerStatus
     */
    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player))
            return;
        // Target Player
        Player alvo = (Player) e.getEntity();
        PlayerDat alvoDat = PlayerDat.getPlayerDatByUUID(alvo.getUniqueId());
        if(alvoDat == null){
            e.setCancelled(true);
            return;
        }
        if(alvoDat.getStatus() != PlayerStatus.ALIVE){ // Check if target is alive
            e.setCancelled(true);
            return;
        }
        Arena alvoArena = plugin.arenaManager.getArenaByPlayer(alvoDat);
        if(alvoArena == null){
            e.setCancelled(true);
            return;
        }
        // Damager
        Player damager = null;
        Entity entity = e.getDamager();
        if(entity instanceof Snowball)
            damager = (Player) ((Snowball) entity).getShooter();
        else if(entity instanceof FishHook)
            damager = (Player) ((FishHook) entity).getShooter();
        else if(entity instanceof Arrow)
            damager = (Player) ((Arrow) entity).getShooter();
        if(damager == null){
            e.setCancelled(true);
            return;
        }
        PlayerDat damagerDat = PlayerDat.getPlayerDatByUUID(damager.getUniqueId());
        if(damagerDat == null){
            e.setCancelled(true);
            return;
        }
        if(damagerDat.getStatus() != PlayerStatus.ALIVE){ // Check if damager is alive
            e.setCancelled(true);
            return;
        }
        Arena damagerArena = plugin.arenaManager.getArenaByPlayer(damagerDat);
        if(damagerArena == null){
            e.setCancelled(true);
            return;
        }
        // Check if they are in the same arena
        if(alvoArena.getArenaNumber() != damagerArena.getArenaNumber()){
            e.setCancelled(true);
            return;
        }
        // Add to last damager map
        PlayerDamage.lastPlayerDamager.put(alvoDat.getUUID(), damagerDat.getUUID());
    }

    /**
     * Control the entity damage on the server. If the game is not considered "ALIVE" and damage cause
     * is void, then theplayer will be teleported to the spawn location of the world he is
     * currently in.
     *
     * @param e     entity damage event
     * @see         EntityDamageEvent
     * @see         PlayerStatus
     */
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player))
            return;
        // Target Player
        Player alvo = (Player) e.getEntity();
        PlayerDat alvoDat = PlayerDat.getPlayerDatByUUID(alvo.getUniqueId());
        if(alvoDat == null){
            e.setCancelled(true);
            return;
        }
        Arena alvoArena = plugin.arenaManager.getArenaByPlayer(alvoDat);
        if(alvoArena == null){
            e.setCancelled(true);
            return;
        }
        if(alvoDat.getStatus() != PlayerStatus.ALIVE){ // Check if target is alive
            if(e.getCause() == EntityDamageEvent.DamageCause.VOID){ // Void damage
                Location lobbyLocation = alvoArena.getArenaLocation().getLobbyLocation();
                e.setCancelled(true);
                if(lobbyLocation == null)
                    return;
                alvo.teleport(lobbyLocation); // Teleport to spawn
            }
        }
    }

}
