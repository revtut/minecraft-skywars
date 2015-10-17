package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.arena.session.GameState;
import net.revtut.libraries.minecraft.games.arena.types.ArenaSolo;
import net.revtut.libraries.minecraft.games.events.player.PlayerDieEvent;
import net.revtut.libraries.minecraft.games.events.player.PlayerJoinArenaEvent;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.libraries.minecraft.scoreboard.InfoBoard;
import net.revtut.libraries.minecraft.scoreboard.InfoBoardLabel;
import net.revtut.libraries.minecraft.text.TabAPI;
import net.revtut.libraries.minecraft.utils.BypassesAPI;
import net.revtut.skywars.SkyWars;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Join Listener
 */
public class JoinListener implements Listener {

    /**
     * Controls the player join arena event
     * @param event player join arena event
     */
    @EventHandler
    public void onJoin(final PlayerJoinArenaEvent event) {
        final SkyWars plugin = SkyWars.getInstance();

        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        final GameController gameController = SkyWars.getInstance().getGameController();
        if(gameController == null || !gameController.hasArena(arena))
            return;

        // Check maximum slots
        final int onlinePlayers = plugin.getGameController().getOnlinePlayers().size();
        final int maxSlots = plugin.getConfiguration().getMaxSlots();
        if(onlinePlayers >= maxSlots) {
            event.setCancelled(true);
            return;
        }

        final PlayerData player = event.getPlayer();

        // Change join message
        final int numberPlayers = arena.getSize() + 1;
        final int maxPlayers = arena.getSession().getMaxPlayers();
        event.setJoinMessage(plugin.getConfiguration().getPrefix() + "§a" + player.getName() + " has joined! (" + numberPlayers + "/" + maxPlayers + ")");

        // Bukkit player
        final Player bukkitPlayer = player.getBukkitPlayer();
        if(bukkitPlayer == null)
            return;

        // Scoreboard
        final InfoBoard infoBoard = plugin.getInfoBoardManager().getInfoBoard(arena);
        final InfoBoardLabel aliveLabel = infoBoard.getLabel("alive");
        aliveLabel.setText("§aAlive: §f" + numberPlayers);
        infoBoard.updateLabel(aliveLabel);
        infoBoard.send(bukkitPlayer);

        // Tab list
        TabAPI.setTab(bukkitPlayer, plugin.getConfiguration().getTabTitle(), plugin.getConfiguration().getTabFooter());

        // TODO Add lobby items
    }
}