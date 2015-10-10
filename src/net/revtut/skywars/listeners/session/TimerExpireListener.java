package net.revtut.skywars.listeners.session;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.arena.session.GameSession;
import net.revtut.libraries.minecraft.games.arena.session.GameState;
import net.revtut.libraries.minecraft.games.events.session.SessionTimerExpireEvent;
import net.revtut.skywars.SkyWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Timer Expire Listener
 */
public class TimerExpireListener implements Listener {

    /**
     * Controls the session timer expire event
     * @param event session timer expire event
     */
    @EventHandler
    public void onTimerExpire(SessionTimerExpireEvent event) {
        // Check if the arena belongs to this game
        GameSession session = event.getSession();
        Arena arena = session.getArena();
        if(!SkyWars.getInstance().getGameController().hasArena(arena))
            return;

        switch (session.getState()) {
            case LOBBY:
                if(arena.getSize() < arena.getSession().getMinPlayers()) { // Minimum players not achieved
                    event.setCancelled(true);
                    break;
                }
                session.updateState(GameState.WARMUP, 30);
                break;
            case WARMUP:
                session.updateState(GameState.START, Integer.MAX_VALUE);
                break;
            case START:
            case DEATHMATCH:
                event.setCancelled(true); // The only way to end a game is if someone wins
                break;
            case FINISH:
                // TODO rejoin all the players and close this arena
                break;
        }
    }
}
