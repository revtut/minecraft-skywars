package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.events.player.PlayerInteractionEvent;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.skywars.SkyWars;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Player Interaction Listener
 */
public class PlayerInteractionListener implements Listener {

    /**
     * List with all chests that were already randomized
     */
    private final List<Location> openedChests;

    /**
     * Constructor of PlayerInteractionListener
     */
    public PlayerInteractionListener() {
        this.openedChests = new ArrayList<>();
    }

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
        if(player.getState() != PlayerState.ALIVE) {
            event.setCancelled(true);
        }

        // Randomize chest
        if(!(event.getClickedBlock().getState() instanceof Chest))
            return;
        final Chest chest = (Chest) event.getClickedBlock().getState();
        randomizeChest(chest);
    }

    private void randomizeChest(final Chest chest) {

    }
}