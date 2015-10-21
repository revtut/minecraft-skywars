package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.events.player.PlayerLeaveArenaEvent;
import net.revtut.libraries.minecraft.games.player.GamePlayer;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.skywars.InfoBoardManager;
import net.revtut.skywars.SkyWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Player Leave Listener
 */
public class PlayerLeaveListener implements Listener {

    /**
     * Controls the player leave arena event
     * @param event player leave arena event
     */
    @EventHandler
    public void onLeave(final PlayerLeaveArenaEvent event) {
        final SkyWars plugin = SkyWars.getInstance();
        
        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        final GameController gameController = SkyWars.getInstance().getGameController();
        if(gameController == null || !gameController.hasArena(arena))
            return;

        final GamePlayer player = event.getPlayer();

        // Change leave message
        final int numberPlayers = arena.getSize() - 1;
        final int maxPlayers = arena.getSession().getMaxPlayers();
        event.setLeaveMessage(plugin.getConfiguration().getPrefix() + "§c" + player.getName() + " has left! (" + numberPlayers + "/" + maxPlayers + ")");

        // Scoreboard
        final InfoBoardManager infoBoardManager = plugin.getInfoBoardManager();
        if(player.getState() == PlayerState.ALIVE)
            infoBoardManager.updateAlive(arena, arena.getPlayers(PlayerState.ALIVE).size() - 1);
        else if(player.getState() == PlayerState.DEAD)
            infoBoardManager.updateDead(arena, arena.getPlayers(PlayerState.DEAD).size() - 1);
    }
}