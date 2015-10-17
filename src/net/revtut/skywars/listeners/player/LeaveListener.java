package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.events.player.PlayerJoinArenaEvent;
import net.revtut.libraries.minecraft.games.events.player.PlayerLeaveArenaEvent;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import net.revtut.libraries.minecraft.scoreboard.InfoBoard;
import net.revtut.libraries.minecraft.scoreboard.InfoBoardLabel;
import net.revtut.libraries.minecraft.text.TabAPI;
import net.revtut.skywars.SkyWars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Leave Listener
 */
public class LeaveListener implements Listener {

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

        final PlayerData player = event.getPlayer();

        // Change leave message
        final int numberPlayers = arena.getSize() - 1;
        final int maxPlayers = arena.getSession().getMaxPlayers();
        event.setLeaveMessage(plugin.getConfiguration().getPrefix() + "§c" + player.getName() + " has left! (" + numberPlayers + "/" + maxPlayers + ")");
    }
}