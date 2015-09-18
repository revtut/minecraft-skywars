package net.revtut.skywars;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.session.GameSession;
import net.revtut.libraries.games.events.session.SessionTimerExpireEvent;
import net.revtut.libraries.games.events.session.SessionTimerTickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Session Listener
 */
public class SessionListener implements Listener {

    /**
     * Game API instance
     */
    private SkyWars plugin = SkyWars.getInstance();

    /**
     * Controls the session timer tick event
     * @param event session timer tick event
     */
    @EventHandler
    public void onTimerTick(SessionTimerTickEvent event) {
        // Check if the arena belongs to this game
        GameSession session = event.getSession();
        Arena arena = session.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;
    }

    /**
     * Controls the session timer expire event
     * @param event session timer expire event
     */
    @EventHandler
    public void onTimerExpire(SessionTimerExpireEvent event) {
        // Check if the arena belongs to this game
        GameSession session = event.getSession();
        Arena arena = session.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;
    }
}