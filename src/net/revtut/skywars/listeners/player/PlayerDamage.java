package net.revtut.skywars.listeners.player;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaStatus;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.player.PlayerStatus;
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
 * @author João Silva
 * @version 1.0
 */
public class PlayerDamage implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerDamage
     *
     * @param plugin main class
     */
    public PlayerDamage(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Map with last targets and damagers
     */
    public static final Map<UUID, UUID> lastPlayerDamager = new HashMap<>();

    /**
     * Control the damage by entity on the server
     *
     * @param e entity damage by entity event
     * @see EntityDamageByEntityEvent
     * @see net.revtut.skywars.player.PlayerStatus
     */
    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;
        // Target Player
        Player alvo = (Player) e.getEntity();
        PlayerDat alvoDat = plugin.playerManager.getPlayerDatByUUID(alvo.getUniqueId());
        if (alvoDat == null) {
            e.setCancelled(true);
            return;
        }
        // Arena
        Arena alvoArena = plugin.arenaManager.getArenaByPlayer(alvoDat);
        if (alvoArena == null) {
            e.setCancelled(true);
            return;
        }
        if (alvoArena.getStatus() != ArenaStatus.INGAME) { // Check if game already started
            e.setCancelled(true);
            return;
        }

        // Damager
        Player damager = null;
        Entity entity = e.getDamager();
        if (entity instanceof Player)
            damager = (Player) entity;
        else if (entity instanceof Snowball)
            damager = (Player) ((Snowball) entity).getShooter();
        else if (entity instanceof Egg)
            damager = (Player) ((Egg) entity).getShooter();
        else if (entity instanceof FishHook)
            damager = (Player) ((FishHook) entity).getShooter();
        else if (entity instanceof Arrow)
            if(((Arrow) entity).getShooter() instanceof Player)
                damager = (Player) ((Arrow) entity).getShooter();
            else {
                e.setCancelled(true);
                return;
            }
        if (damager == null) {
            e.setCancelled(true);
            return;
        }

        PlayerDat damagerDat = plugin.playerManager.getPlayerDatByUUID(damager.getUniqueId());
        if (damagerDat == null) {
            e.setCancelled(true);
            return;
        }
        if (damagerDat.getStatus() != PlayerStatus.ALIVE) { // Check if damager is alive
            e.setCancelled(true);
            return;
        }
        // Check if they are in the same arena
        if (!plugin.arenaManager.inSameArena(alvoDat, damagerDat)) {
            e.setCancelled(true);
            return;
        }

        // Lifestealer
        double health = damager.getHealth() + alvoArena.getKitManager().lifestealer.stealLife(damagerDat, alvoArena, e.getDamage());
        if(health > 20)
            health = 20;
        damager.setHealth(health);

        // Check if was not "suicide"
        if(alvo.getUniqueId().equals(damager.getUniqueId()))
            return;

        // Add to last damager map
        PlayerDamage.lastPlayerDamager.put(alvoDat.getUUID(), damagerDat.getUUID());
    }

    /**
     * Control the entity damage on the server. If the game is not considered "ALIVE" and damage cause
     * is void, then theplayer will be teleported to the spawn location of the world he is
     * currently in.
     *
     * @param e entity damage event
     * @see EntityDamageEvent
     * @see PlayerStatus
     */
    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;
        // Target Player
        Player alvo = (Player) e.getEntity();
        PlayerDat alvoDat = plugin.playerManager.getPlayerDatByUUID(alvo.getUniqueId());
        if (alvoDat == null) {
            e.setCancelled(true);
            return;
        }
        // Arena
        Arena alvoArena = plugin.arenaManager.getArenaByPlayer(alvoDat);
        if (alvoArena == null) {
            e.setCancelled(true);
            return;
        }
        if (alvoArena.getStatus() != ArenaStatus.INGAME) { // Check if game already started
            e.setCancelled(true);
            // Void damage
            if(alvoArena.getStatus() != ArenaStatus.PREGAME)
                if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                    Location lobbyLocation = alvoArena.getArenaLocation().getLobbyLocation();
                    if (lobbyLocation == null)
                        return;
                    alvo.teleport(lobbyLocation); // Teleport to lobby
                }
            return;
        }
        if (alvoDat.getStatus() != PlayerStatus.ALIVE) { // Check if target is alive
            e.setCancelled(true);
            // Void damage
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                Location deathSpawn = alvoArena.getArenaLocation().getDeathSpawnLocation();
                if (deathSpawn == null)
                    return;
                alvo.teleport(deathSpawn); // Teleport to death spawn
            }
        }
    }

}
