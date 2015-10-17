package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.events.player.PlayerSpectateArenaEvent;
import net.revtut.libraries.minecraft.games.events.player.PlayerTalkEvent;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.skywars.SkyWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Talk Listener
 */
public class TalkListener implements Listener {

    /**
     * Controls the player talk event
     * @param event player talk event
     */
    @EventHandler
    public void onTalk(final PlayerTalkEvent event) {
        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        final GameController gameController = SkyWars.getInstance().getGameController();
        if(gameController == null || !gameController.hasArena(arena))
            return;

        final PlayerData player = event.getPlayer();

        // Block dead players
        if(player.getState() == PlayerState.DEAD) // TODO Warn that dead players may not talk
            event.setCancelled(true);
    }
}