package net.revtut.skywars.listeners;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.arena.session.GameState;
import net.revtut.libraries.minecraft.games.arena.types.ArenaSolo;
import net.revtut.libraries.minecraft.games.events.player.*;
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
 * Game Listener
 */
public class GameListener implements Listener {

    /**
     * Game instance
     */
    private final SkyWars plugin = SkyWars.getInstance();

    /**
     * Controls the player cross arena border event
     * @param event player cross arena border event
     */
    @EventHandler
    public void onCrossBorder(final PlayerCrossArenaBorderEvent event) {
        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        final PlayerData player = event.getPlayer();

        // Do not allow spectators to cross border
        if(player.getState() != PlayerState.ALIVE)
            event.setCancelled(true);
    }

    /**
     * Controls the player die event
     * @param event player die event
     */
    @EventHandler
    public void onDie(final PlayerDieEvent event) {
        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
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

        // TODO respawn event

        // Scoreboard
        final InfoBoard infoBoard = plugin.getInfoBoardManager().getInfoBoard(arena);
        final InfoBoardLabel aliveLabel = infoBoard.getLabel("alive");
        final InfoBoardLabel deadLabel = infoBoard.getLabel("dead");
        aliveLabel.setText("§aAlive: §f" + arena.getPlayers(PlayerState.ALIVE).size());
        deadLabel.setText("§cDead: §f" + arena.getPlayers(PlayerState.DEAD).size());
        infoBoard.updateLabel(aliveLabel);
        infoBoard.updateLabel(deadLabel);
    }

    /**
     * Controls the player join arena event
     * @param event player join arena event
     */
    @EventHandler
    public void onJoin(final PlayerJoinArenaEvent event) {
        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
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

    /**
     * Controls the player leave arena event
     * @param event player leave arena event
     */
    @EventHandler
    public void onLeave(final PlayerLeaveArenaEvent event) {
        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        final PlayerData player = event.getPlayer();

        // Change leave message
        final int numberPlayers = arena.getSize() - 1;
        final int maxPlayers = arena.getSession().getMaxPlayers();
        event.setLeaveMessage(plugin.getConfiguration().getPrefix() + "§c" + player.getName() + " has left! (" + numberPlayers + "/" + maxPlayers + ")");
    }

    /**
     * Controls the player spectate arena event
     * @param event player spectate arena event
     */
    @EventHandler
    public void onSpectate(final PlayerSpectateArenaEvent event) {
        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        // Change join message
        event.setJoinMessage(null);
    }

    /**
     * Controls the player talk event
     * @param event player talk event
     */
    @EventHandler
    public void onTalk(final PlayerTalkEvent event) {
        // Check if the arena belongs to this game
        final Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        final PlayerData player = event.getPlayer();

        // Block dead players
        if(player.getState() == PlayerState.DEAD) // TODO Warn that dead players may not talk
            event.setCancelled(true);
    }
}