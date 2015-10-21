package net.revtut.skywars.listeners.arena;

import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.events.arena.ArenaBlockPlaceEvent;
import net.revtut.libraries.minecraft.games.events.player.PlayerInteractionEvent;
import net.revtut.libraries.minecraft.games.player.GamePlayer;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.skywars.SkyWars;
import net.revtut.skywars.listeners.player.PlayerInteractionListener;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Arena Block Place Listener
 */
public class ArenaBlockPlaceListener implements Listener {

    /**
     * Controls the arena block place event
     * @param event arena block place event
     */
    @EventHandler
    public void onBlockPlace(final ArenaBlockPlaceEvent event) {
        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        final GameController gameController = SkyWars.getInstance().getGameController();
        if(gameController == null || !gameController.hasArena(arena))
            return;

        final GamePlayer player = event.getPlayer();

        // Block non live players
        if(player.getState() != PlayerState.ALIVE) {
            event.setCancelled(true);
            return;
        }

        // Check if its a chest
        if(event.getBlock().getState() instanceof Chest)
            PlayerInteractionListener.setOpenedChest(event.getBlock().getLocation());
    }
}