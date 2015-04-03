package net.revtut.skywars.listeners.environment;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.player.PlayerDat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 * Creature Spawn Event.
 *
 * <P>Controls the creature spawn event.</P>
 *
 * @author João Silva
 * @version 1.0
 */
public class CreatureSpawn implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of CreatureSpawn
     *
     * @param plugin main class
     */
    public CreatureSpawn(SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of what to do when a creature spawns
     *
     * @param e creature spawn event
     * @see CreatureSpawnEvent
     */
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM)
            e.setCancelled(true);
    }
}
