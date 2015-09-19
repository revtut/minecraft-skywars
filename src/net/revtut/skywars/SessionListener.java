package net.revtut.skywars;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.session.GameSession;
import net.revtut.libraries.games.arena.session.GameState;
import net.revtut.libraries.games.arena.types.ArenaSolo;
import net.revtut.libraries.games.events.session.SessionTimerExpireEvent;
import net.revtut.libraries.games.events.session.SessionTimerTickEvent;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.PlayerState;
import org.bukkit.entity.Player;
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

        switch (session.getState()) {
            case LOBBY:
            case WARMUP:
                for(PlayerData player : arena.getAllPlayers())
                    player.getBukkitPlayer().setLevel(event.getTime());
                break;
        }
    }

    /**
     * Controls the session timer expire event
     * @param event session timer expire event
     */
    @EventHandler
    public void onTimerExpire(SessionTimerExpireEvent event) {
        // Check if the arena belongs to this game
        GameSession session = event.getSession();
        if(!plugin.getGameController().hasArena(session.getArena()))
            return;

        ArenaSolo arena = (ArenaSolo) session.getArena();
        switch (session.getState()) {
            case BUILD:
                session.updateState(GameState.LOBBY, 500);
                if(session.getState() != GameState.LOBBY)
                    return;
                break;
            case LOBBY:
                session.updateState(GameState.WARMUP, 500);
                if(session.getState() != GameState.WARMUP)
                    return;

                int playerIndex = -1;
                Player bukkitPlayer;
                for(PlayerData player : arena.getAllPlayers()) {
                    if(player.getState() != PlayerState.ALIVE)
                        continue;

                    ++playerIndex;

                    bukkitPlayer = player.getBukkitPlayer();

                    bukkitPlayer.teleport(arena.getSpawnLocations().get(playerIndex % arena.getSpawnLocations().size()));
                    bukkitPlayer.getInventory().clear();

                    // TODO Give kit chooser
                }
                break;
        }
    }
}