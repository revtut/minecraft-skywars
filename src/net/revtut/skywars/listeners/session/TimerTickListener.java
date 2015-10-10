package net.revtut.skywars.listeners.session;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.arena.session.GameSession;
import net.revtut.libraries.minecraft.games.events.session.SessionTimerTickEvent;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import net.revtut.skywars.SkyWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Timer Tick Listener
 */
public class TimerTickListener implements Listener {

    /**
     * Controls the session timer tick event
     * @param event session timer tick event
     */
    @EventHandler
    public void onTimerTick(SessionTimerTickEvent event) {
        // Check if the arena belongs to this game
        GameSession session = event.getSession();
        Arena arena = session.getArena();
        if(!SkyWars.getInstance().getGameController().hasArena(arena))
            return;

        switch (session.getState()) {
            case LOBBY:
            case WARMUP:
                for(PlayerData player : arena.getAllPlayers())
                    player.getBukkitPlayer().setLevel(event.getTime());
                break;
        }
    }
}
