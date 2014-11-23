package net.RevTut.Skywars.listeners.environment;

import net.RevTut.Skywars.SkyWars;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 * Projectile Hit.
 *
 * <P>Controls the projectile hits.</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class ProjectileHit implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of ProjectileHit
     *
     * @param plugin main class
     */
    public ProjectileHit(SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of what to do when a projectile hit something
     *
     * @param e projectile hit event
     * @see ProjectileHitEvent
     */
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Projectile projectile = e.getEntity();
        if (!(projectile.getShooter() instanceof Player))
            return;
        Player player = (Player) projectile.getShooter();
        // Player Dat
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if (playerDat == null)
            return;
        // Arena
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (null == arena)
            return;

        // Ninja
        arena.getKitManager().ninja.throwPlayer(player, player.getItemInHand(), projectile);
    }

}
