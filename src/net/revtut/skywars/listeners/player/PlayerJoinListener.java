package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.bukkit.appearance.Packets;
import net.revtut.libraries.minecraft.bukkit.games.GameController;
import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.events.player.PlayerJoinArenaEvent;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;
import net.revtut.libraries.minecraft.bukkit.scoreboard.InfoBoard;
import net.revtut.skywars.InfoBoardManager;
import net.revtut.skywars.SkyWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Player Join Listener
 */
public class PlayerJoinListener implements Listener {

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

        final GamePlayer player = event.getPlayer();

        // Change join message
        final int numberPlayers = arena.getSize() + 1;
        final int maxPlayers = arena.getSession().getMaxPlayers();
        event.setJoinMessage(plugin.getConfiguration().getPrefix() + "§a" + player.getName() + " has joined! (" + numberPlayers + "/" + maxPlayers + ")");

        // Bukkit player
        final Player bukkitPlayer = Bukkit.getPlayer(player.getUuid());
        if(bukkitPlayer == null)
            return;

        // Scoreboard
        final InfoBoardManager infoBoardManager = plugin.getInfoBoardManager();
        infoBoardManager.updateAlive(arena, numberPlayers);
        final InfoBoard infoBoard = infoBoardManager.getInfoBoard(arena);
        infoBoard.send(bukkitPlayer);

        // Tab list
        Packets.sendTab(bukkitPlayer, plugin.getConfiguration().getTabTitle(), plugin.getConfiguration().getTabFooter());

        // TODO Add lobby items
    }
}