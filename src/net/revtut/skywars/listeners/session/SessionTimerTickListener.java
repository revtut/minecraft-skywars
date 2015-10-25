package net.revtut.skywars.listeners.session;

import net.revtut.libraries.minecraft.appearance.AppearanceAPI;
import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.arena.session.GameSession;
import net.revtut.libraries.minecraft.games.arena.types.ArenaSolo;
import net.revtut.libraries.minecraft.games.events.session.SessionTimerTickEvent;
import net.revtut.libraries.minecraft.games.player.GamePlayer;
import net.revtut.libraries.minecraft.games.utils.Winner;
import net.revtut.skywars.SkyWars;
import org.bukkit.Location;
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
                for(final GamePlayer player : arena.getAllPlayers())
                    player.getBukkitPlayer().setLevel(event.getTime());
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
                    AppearanceAPI.launchFirework(location, 1, i++);
                }

                final Winner winner = arena.getSession().getWinner();
                if(winner != null && winner instanceof GamePlayer)
                    AppearanceAPI.launchFirework(((GamePlayer)winner).getBukkitPlayer(), 10, 2);
                break;
        }
    }
}
