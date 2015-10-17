package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.events.player.PlayerHungerEvent;
import net.revtut.libraries.minecraft.games.events.player.PlayerInteractionEvent;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.skywars.SkyWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Player Interaction Listener
 */
public class PlayerInteractionListener implements Listener {

    /**
     * Controls the player interaction event
     * @param event player interaction event
     */
    @EventHandler
    public void onInteraction(final PlayerInteractionEvent event) {
        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        final GameController gameController = SkyWars.getInstance().getGameController();
        if(gameController == null || !gameController.hasArena(arena))
            return;

        final PlayerData player = event.getPlayer();

        // Block non live players
        if(player.getState() != PlayerState.ALIVE)
            event.setCancelled(true);
    }
}