package net.revtut.skywars;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.session.GameSession;
import net.revtut.libraries.games.arena.session.GameState;
import net.revtut.libraries.games.arena.types.ArenaSolo;
import net.revtut.libraries.games.classes.GameClass;
import net.revtut.libraries.games.events.session.SessionSwitchStateEvent;
import net.revtut.libraries.games.events.session.SessionTimerExpireEvent;
import net.revtut.libraries.games.events.session.SessionTimerTickEvent;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.PlayerState;
import org.bukkit.GameMode;
import org.bukkit.Location;
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
        Arena arena = session.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        switch (session.getState()) {
            case LOBBY:
                if(arena.getSize() < arena.getSession().getMaxPlayers()) { // Minimum players not achieved
                    event.setCancelled(true);
                    break;
                }
                session.updateState(GameState.WARMUP, 30);
                break;
            case WARMUP:
                session.updateState(GameState.START, Integer.MAX_VALUE);
                break;
            case START:
                event.setCancelled(true); // The only way to end a game is if someone wins
                break;
            case FINISH:
                session.updateState(GameState.BUILD, Integer.MAX_VALUE);
                break;
        }
    }

    /**
     * Controls the session switch state event
     * @param event session switch state event
     */
    @EventHandler
    public void onSwitchState(SessionSwitchStateEvent event) {
        // Check if the arena belongs to this game
        GameSession session = event.getSession();
        if(!plugin.getGameController().hasArena(session.getArena()))
            return;

        ArenaSolo arena = (ArenaSolo) session.getArena();

        // Needed variables for the switch case
        int playerIndex;
        Player bukkitPlayer;
        Location teleportLocation;
        GameClass gameClass;

        // Switch case
        switch (event.getNextState()) {
            case WARMUP:
                playerIndex = -1;
                for(PlayerData player : arena.getAllPlayers()) {
                    bukkitPlayer = player.getBukkitPlayer();
                    if(bukkitPlayer == null)
                        continue;

                    if(player.getState() == PlayerState.SPECTATOR) {
                        bukkitPlayer.teleport(arena.getSpectatorLocation());
                    } else {
                        ++playerIndex;

                        teleportLocation = arena.getSpawnLocations().get(playerIndex % arena.getSpawnLocations().size());
                        bukkitPlayer.teleport(teleportLocation);

                        // TODO Give kit chooser
                    }
                }
            case START:
                for(PlayerData player : arena.getAllPlayers()) {
                    if(player.getState() != PlayerState.ALIVE)
                        continue;

                    gameClass = player.getGameClass();
                    if(gameClass == null)
                        continue; // TODO Add default kit to the player
                    else
                        gameClass.equip(player);

                    bukkitPlayer = player.getBukkitPlayer();
                    if(bukkitPlayer == null)
                        continue;

                    bukkitPlayer.setGameMode(GameMode.SURVIVAL);
                }
                break;
            case DEATHMATCH:
                playerIndex = -1;
                for(PlayerData player : arena.getAllPlayers()) {
                    bukkitPlayer = player.getBukkitPlayer();
                    if(bukkitPlayer == null)
                        continue;

                    if(player.getState() == PlayerState.SPECTATOR) {
                        bukkitPlayer.teleport(arena.getSpectatorDeathMatchLocation());
                    } else if(player.getState() == PlayerState.DEAD) {
                        bukkitPlayer.teleport(arena.getDeadDeathMatchLocation());
                    } else {
                        ++playerIndex;

                        teleportLocation = arena.getDeathMatchLocations().get(playerIndex % arena.getDeathMatchLocations().size());
                        bukkitPlayer.teleport(teleportLocation);
                    }
                }
                break;
            case FINISH:
                for(PlayerData player : arena.getAllPlayers()) {
                    bukkitPlayer = player.getBukkitPlayer();
                    if(bukkitPlayer == null)
                        continue;

                    if(arena.getSession().getState() == GameState.DEATHMATCH) {
                        bukkitPlayer.teleport(arena.getDeadDeathMatchLocation());
                    } else {
                        bukkitPlayer.teleport(arena.getDeadLocation());
                    }

                    // TODO Change game mode when game finishes, launch fireworks
                }
                break;
        }
    }
}