package net.revtut.skywars.listeners.player;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaStatus;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.player.PlayerStatus;
import net.revtut.skywars.utils.Reward;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

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
    private final SkyWars plugin;

    /**
     * Constructor of PlayerQuit
     *
     * @param plugin main class
     */
    public PlayerQuit(final SkyWars plugin) {
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

        // Mensagem Saida
        e.setQuitMessage(null);

        // PlayerDat
        final PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(p.getUniqueId());
        if (playerDat == null) {
            plugin.getLogger().log(Level.WARNING, "Error while updating PlayerDat on quit as it is NULL!");
            return;
        }

        // Arena
        final Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (arena != null)
            if(arena.getStatus() == ArenaStatus.INGAME)
                if(!p.hasPermission("rev.nolosepoints"))
                    playerDat.addPoints(0 - Reward.KILL.calculatePoints(playerDat)); // Remove points because left before endgame

        // Simulate player death
        if(playerDat.getStatus() == PlayerStatus.ALIVE) {
            PlayerDeath playerDeath = new PlayerDeath(plugin);
            playerDeath.updateArena(arena, p);
        }

        // MySQL Tasks
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> plugin.mysql.updatePlayerDat(playerDat));

        // Remove from arena
        if (!plugin.arenaManager.removePlayer(playerDat, true))
            plugin.getLogger().log(Level.WARNING, "Error while removing PlayerDat from arena on quit!");

        // Remove from scoreboard
        plugin.scoreBoardManager.removePlayerScoreBoard(p);

        // Remove playerDat
        plugin.playerManager.removePlayerDat(playerDat);

        // Remove from last damagers
        Map<UUID, UUID> lastDamagers = new HashMap<>(PlayerDamage.lastPlayerDamager);
        lastDamagers.entrySet().stream().filter(entry -> entry.getValue().equals(playerDat.getUUID())).forEach(entry -> PlayerDamage.lastPlayerDamager.remove(entry.getKey()));

        // Remove the craft player
        CraftPlayer craftPlayer = (CraftPlayer) p;
        craftPlayer.remove();
    }
}
