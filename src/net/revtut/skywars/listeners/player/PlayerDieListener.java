package net.revtut.skywars.listeners.player;

import net.revtut.libraries.minecraft.games.GameController;
import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.arena.session.GameState;
import net.revtut.libraries.minecraft.games.arena.types.ArenaSolo;
import net.revtut.libraries.minecraft.games.events.player.PlayerDieEvent;
import net.revtut.libraries.minecraft.games.player.PlayerData;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.libraries.minecraft.utils.BypassesAPI;
import net.revtut.skywars.InfoBoardManager;
import net.revtut.skywars.SkyWars;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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

        final PlayerData target = event.getPlayer();
        final PlayerData killer = event.getKiller();

        // Death message
        if(killer == null)
            event.setDeathMessage(plugin.getConfiguration().getPrefix() + "§a" + target.getName() + " died!");
        else
            event.setDeathMessage(plugin.getConfiguration().getPrefix() + "§a" + target.getName() + " was killed by " + killer.getName() + "!");

        // Update player
        target.updateState(PlayerState.DEAD);
        target.getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
        BypassesAPI.respawnBypass(target.getBukkitPlayer());

        final ArenaSolo arenaSolo = (ArenaSolo) arena;
        if(arenaSolo.getSession().getState() == GameState.DEATHMATCH)
            target.getBukkitPlayer().teleport(arenaSolo.getDeadDeathMatchLocation());
        else
            target.getBukkitPlayer().teleport(arenaSolo.getDeadLocation());

        // Scoreboard
        final InfoBoardManager infoBoardManager = plugin.getInfoBoardManager();
        infoBoardManager.updateAlive(arena, arena.getPlayers(PlayerState.ALIVE).size());
        infoBoardManager.updateDead(arena, arena.getPlayers(PlayerState.DEAD).size());
    }
}