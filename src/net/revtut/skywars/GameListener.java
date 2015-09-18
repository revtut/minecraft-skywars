package net.revtut.skywars;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.events.player.*;
import net.revtut.libraries.games.player.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Game Listener
 */
public class GameListener implements Listener {

    /**
     * Game API instance
     */
    private SkyWars plugin = SkyWars.getInstance();

    /**
     * Controls the player cross arena border event
     * @param event player cross arena border event
     */
    @EventHandler
    public void onCrossBorder(PlayerCrossArenaBorderEvent event) {
        // Check if the arena belongs to this game
        Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        PlayerData player = event.getPlayer();
    }

    /**
     * Controls the player join arena event
     * @param event player join arena event
     */
    @EventHandler
    public void onJoin(PlayerJoinArenaEvent event) {
        // Check if the arena belongs to this game
        Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        PlayerData player = event.getPlayer();
    }

    /**
     * Controls the player leave arena event
     * @param event player leave arena event
     */
    @EventHandler
    public void onLeave(PlayerLeaveArenaEvent event) {
        // Check if the arena belongs to this game
        Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        PlayerData player = event.getPlayer();
    }

    /**
     * Controls the player spectate arena event
     * @param event player spectate arena event
     */
    @EventHandler
    public void onSpectate(PlayerSpectateArenaEvent event) {
        // Check if the arena belongs to this game
        Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        PlayerData player = event.getPlayer();
    }

    /**
     * Controls the player talk event
     * @param event player talk event
     */
    @EventHandler
    public void onTalk(PlayerTalkEvent event) {
        // Check if the arena belongs to this game
        Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        PlayerData player = event.getPlayer();
    }
}