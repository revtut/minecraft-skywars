package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.bukkit.appearance.Packets;
import net.revtut.libraries.minecraft.bukkit.games.GameController;
import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.arena.session.GameState;
import net.revtut.libraries.minecraft.bukkit.games.arena.types.ArenaSolo;
import net.revtut.libraries.minecraft.bukkit.games.events.player.PlayerDieEvent;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;
import net.revtut.libraries.minecraft.bukkit.games.player.PlayerState;
import net.revtut.skywars.InfoBoardManager;
import net.revtut.skywars.SkyWars;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.logging.Level;

/**
 * Player Die Listener
 */
public class PlayerDieListener implements Listener {

    /**
     * Controls the player die event
     * @param event player die event
     */
    @EventHandler
    public void onDie(final PlayerDieEvent event) {
        final SkyWars plugin = SkyWars.getInstance();

        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        final GameController gameController = SkyWars.getInstance().getGameController();
        if(gameController == null || !gameController.hasArena(arena))
            return;

        final GamePlayer target = event.getPlayer();
        final GamePlayer killer = event.getKiller();

        // Death message
        if(killer == null)
            event.setDeathMessage(plugin.getConfiguration().getPrefix() + "§a" + target.getName() + " died!");
        else
            event.setDeathMessage(plugin.getConfiguration().getPrefix() + "§a" + target.getName() + " was killed by " + killer.getName() + "!");

        // Update player
        target.updateState(PlayerState.DEAD);
        target.setGameMode(GameMode.SPECTATOR);
        Packets.respawnBypass(Bukkit.getPlayer(target.getUuid()));

        final ArenaSolo arenaSolo = (ArenaSolo) arena;
        if(arenaSolo.getSession().getState() == GameState.DEATHMATCH)
            target.teleport(arenaSolo.getDeadDeathMatchLocation());
        else
            target.teleport(arenaSolo.getDeadLocation());

        // Scoreboard
        final InfoBoardManager infoBoardManager = plugin.getInfoBoardManager();
        infoBoardManager.updateAlive(arena, arena.getPlayers(PlayerState.ALIVE).size());
        infoBoardManager.updateDead(arena, arena.getPlayers(PlayerState.DEAD).size());

        // Check if the game has finished
        final List<GamePlayer> alivePlayers = arena.getPlayers(PlayerState.ALIVE);
        if(alivePlayers.size() <= 1) {
            arena.getSession().updateState(GameState.FINISH, 20);
            if(alivePlayers.size() == 1)
                arena.getSession().setWinner(alivePlayers.get(0));
        }
    }
}