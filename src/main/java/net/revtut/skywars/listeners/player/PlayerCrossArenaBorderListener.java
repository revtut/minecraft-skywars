package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.bukkit.games.GameController;
import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.events.player.PlayerCrossArenaBorderEvent;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;
import net.revtut.libraries.minecraft.bukkit.games.player.PlayerState;
import net.revtut.skywars.SkyWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Player Cross Arena Border Listener
 */
public class PlayerCrossArenaBorderListener implements Listener {

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

        final GamePlayer player = event.getPlayer();

        // Do not allow spectators to cross border
        if(player.getState() != PlayerState.ALIVE)
            event.setCancelled(true);
    }
}