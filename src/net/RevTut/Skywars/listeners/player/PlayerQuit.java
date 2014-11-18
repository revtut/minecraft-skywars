package net.RevTut.Skywars.listeners.player;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Player Join.
 *
 * <P>Controls the join event.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerQuit implements Listener {

    /**
     * Main class
     */
    private final Main plugin;

    /**
     * Constructor of PlayerQuit
     *
     * @param plugin main class
     */
    public PlayerQuit(final Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of a player when he leaves. Delete the PlayerDat and the arena if needed.
     *
     * @param e player quit event
     * @see PlayerQuitEvent
     */
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        // Mensagem Entrada
        e.setQuitMessage(null);

        // PlayerDat
        final PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(p.getUniqueId());
        if (playerDat == null) {
            System.out.println("Error while updating PlayerDat on quit as it is NULL!");
            return;
        }
        plugin.playerManager.removePlayerDat(playerDat); // Remove playerDat

        // Remove from arena
        if (!plugin.arenaManager.removePlayer(playerDat, true)) {
            System.out.println("Error while removing PlayerDat from arena on quit!");
            return;
        }

        // MySQL Tasks
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                // PlayerDat
                plugin.mysql.updateMySQLPlayerDat(playerDat);
            }
        });
    }
}
