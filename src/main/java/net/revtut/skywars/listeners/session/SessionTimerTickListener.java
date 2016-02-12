package net.revtut.skywars.listeners.session;

import net.revtut.libraries.minecraft.bukkit.appearance.Effects;
import net.revtut.libraries.minecraft.bukkit.games.GameController;
import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.arena.session.GameSession;
import net.revtut.libraries.minecraft.bukkit.games.arena.types.ArenaSolo;
import net.revtut.libraries.minecraft.bukkit.games.events.session.SessionTimerTickEvent;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;
import net.revtut.libraries.minecraft.bukkit.games.utils.Winner;
import net.revtut.skywars.SkyWars;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

/**
 * Session Timer Tick Listener
 */
public class SessionTimerTickListener implements Listener {

    /**
     * Controls the session timer tick event
     * @param event session timer tick event
     */
    @EventHandler
    public void onTimerTick(final SessionTimerTickEvent event) {
        // Check if the arena belongs to this game
        final GameSession session = event.getSession();
        final Arena arena = session.getArena();
        final GameController gameController = SkyWars.getInstance().getGameController();
        if(gameController == null || !gameController.hasArena(arena))
            return;

        switch (session.getState()) {
            case LOBBY:
            case WARMUP:
                for(final GamePlayer player : arena.getAllPlayers()) {
                    final Player bukkitPlayer = Bukkit.getPlayer(player.getUuid());
                    if(bukkitPlayer != null)
                        bukkitPlayer.setLevel(event.getTime());
                }
                break;
            case FINISH:
                final ArenaSolo arenaSolo = (ArenaSolo) arena;

                // Get spawn locations depending if they were previously on death match or not
                final List<Location> spawnLocations;
                if(arena.getSession().getDeathMatchPlayers().size() == 0)
                    spawnLocations = arenaSolo.getSpawnLocations();
                else
                    spawnLocations = arenaSolo.getDeathMatchLocations();

                // Launch fireworks on spawn locations and on winner
                int i = 0;
                for(final Location spawnLocation : spawnLocations) {
                    final Location location = new Location(spawnLocation.getWorld(), spawnLocation.getX(), spawnLocation.getY() + 20, spawnLocation.getZ());
                    Effects.launchFirework(location, 1, i++);
                }

                final Winner winner = arena.getSession().getWinner();
                if(winner != null && winner instanceof GamePlayer) {
                    final Player bukkitWinner = Bukkit.getPlayer(((GamePlayer) winner).getUuid());
                    if (bukkitWinner != null)
                        Effects.launchFirework(bukkitWinner, 10, 2);
                }
                break;
        }
    }
}
