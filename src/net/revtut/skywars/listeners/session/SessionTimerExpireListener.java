package net.revtut.skywars.listeners.session;

import net.revtut.libraries.minecraft.bukkit.games.GameAPI;
import net.revtut.libraries.minecraft.bukkit.games.GameController;
import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.arena.session.GameSession;
import net.revtut.libraries.minecraft.bukkit.games.arena.session.GameState;
import net.revtut.libraries.minecraft.bukkit.games.events.session.SessionTimerExpireEvent;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;
import net.revtut.skywars.SkyWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

/**
 * Session Timer Expire Listener
 */
public class SessionTimerExpireListener implements Listener {

    /**
     * Controls the session timer expire event
     * @param event session timer expire event
     */
    @EventHandler
    public void onTimerExpire(final SessionTimerExpireEvent event) {
        // Check if the arena belongs to this game
        final GameSession session = event.getSession();
        final Arena arena = session.getArena();
        final GameController gameController = SkyWars.getInstance().getGameController();
        if(gameController == null || !gameController.hasArena(arena))
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
                for(GamePlayer player : new ArrayList<>(arena.getAllPlayers())) {
                    arena.leave(player);
                    GameAPI.getInstance().joinRandomGame(player);
                }
                break;
        }
    }
}
