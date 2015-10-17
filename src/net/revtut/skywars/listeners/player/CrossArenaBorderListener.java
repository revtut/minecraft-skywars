package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.arena.session.GameSession;
import net.revtut.libraries.minecraft.games.events.player.PlayerCrossArenaBorderEvent;
import net.revtut.libraries.minecraft.games.events.session.SessionTimerTickEvent;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.skywars.SkyWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Cross Arena Border Listener
 */
public class CrossArenaBorderListener implements Listener {

    /**
     * Controls the player cross arena border event
     * @param event player cross arena border event
     */
    @EventHandler
    public void onCrossBorder(final PlayerCrossArenaBorderEvent event) {
        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        final GameController gameController = SkyWars.getInstance().getGameController();
        if(gameController == null || !gameController.hasArena(arena))
            return;

        final PlayerData player = event.getPlayer();

        // Do not allow spectators to cross border
        if(player.getState() != PlayerState.ALIVE)
            event.setCancelled(true);
    }
}