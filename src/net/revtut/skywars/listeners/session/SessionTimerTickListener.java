package net.revtut.skywars.listeners.session;

import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.arena.session.GameSession;
import net.revtut.libraries.minecraft.games.events.session.SessionTimerTickEvent;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import net.revtut.skywars.SkyWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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
                for(final PlayerData player : arena.getAllPlayers())
                    player.getBukkitPlayer().setLevel(event.getTime());
                break;
        }
    }
}
