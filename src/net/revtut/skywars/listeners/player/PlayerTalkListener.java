package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.events.player.PlayerTalkEvent;
import net.revtut.libraries.minecraft.games.player.GamePlayer;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.skywars.SkyWars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Date;

/**
 * Player Talk Listener
 */
public class PlayerTalkListener implements Listener {

    /**
     * Controls the player talk event
     * @param event player talk event
     */
    @EventHandler
    public void onTalk(final PlayerTalkEvent event) {
        final SkyWars plugin = SkyWars.getInstance();

        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        final GameController gameController = SkyWars.getInstance().getGameController();
        if(gameController == null || !gameController.hasArena(arena))
            return;

        final GamePlayer player = event.getPlayer();

        // Block non live players
        if(player.getState() != PlayerState.ALIVE) {
            event.setCancelled(true);
            final Player bukkitPlayer = player.getBukkitPlayer();
            if(bukkitPlayer != null)
                bukkitPlayer.sendMessage(plugin.getConfiguration().getPrefix() + "§cYou may not talk when you are not alive!");
        }

        // Change message style
        final String message = player.getName() + " » " + event.getMessage();
        event.setFormattedMessage(message);

        // Log message
        arena.getSession().addChatMessage(message, new Date());
    }
}