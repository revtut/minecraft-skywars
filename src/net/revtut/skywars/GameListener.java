package net.revtut.skywars;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.ArenaPreference;
import net.revtut.libraries.games.arena.session.GameState;
import net.revtut.libraries.games.arena.types.ArenaSolo;
import net.revtut.libraries.games.events.player.*;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.PlayerState;
import net.revtut.libraries.scoreboard.InfoBoard;
import net.revtut.libraries.scoreboard.InfoBoardLabel;
import net.revtut.libraries.utils.BypassesAPI;
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
    private SkyWars plugin = SkyWars.getInstance();

    /**
     * Controls the player cross arena border event
     * @param event player cross arena border event
     */
    @EventHandler
    public void onCrossBorder(PlayerCrossArenaBorderEvent event) {
        // Check if the arena belongs to this game
        Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        PlayerData player = event.getPlayer();

        // Do not allow spectators to cross border
        if(player.getState() != PlayerState.ALIVE) {
            event.setCancelled(true);
            return;
        }
    }

    /**
     * Controls the player die event
     * @param event player die event
     */
    @EventHandler
    public void onDie(PlayerDieEvent event) {
        // Check if the arena belongs to this game
        Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        PlayerData target = event.getPlayer();
        PlayerData killer = event.getKiller();

        // Death message
        if(killer == null)
            event.setDeathMessage(plugin.getConfiguration().getPrefix() + "§a" + target.getName() + " died!");
        else
            event.setDeathMessage(plugin.getConfiguration().getPrefix() + "§a" + target.getName() + " was killed by " + killer.getName() + "!");

        // Update player
        target.updateState(PlayerState.DEAD);
        target.getBukkitPlayer().setGameMode(GameMode.SPECTATOR);
        BypassesAPI.respawnBypass(target.getBukkitPlayer());

        ArenaSolo arenaSolo = (ArenaSolo) arena;
        if(arenaSolo.getSession().getState() == GameState.DEATHMATCH)
            target.getBukkitPlayer().teleport(arenaSolo.getDeadDeathMatchLocation());
        else
            target.getBukkitPlayer().teleport(arenaSolo.getDeadLocation());

        // Scoreboard
        InfoBoard infoBoard = plugin.getInfoBoardManager().getInfoBoard(arena);
        InfoBoardLabel aliveLabel = infoBoard.getLabel("alive");
        InfoBoardLabel deadLabel = infoBoard.getLabel("dead");
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
    public void onJoin(PlayerJoinArenaEvent event) {
        // Check if the arena belongs to this game
        Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        // Check maximum slots
        int onlinePlayers = plugin.getGameController().getOnlinePlayers().size();
        int maxSlots = plugin.getConfiguration().getMaxSlots();
        if(onlinePlayers >= maxSlots) {
            event.setCancelled(true);
            return;
        }

        PlayerData player = event.getPlayer();

        // Change join message
        int numberPlayers = arena.getSize() + 1;
        int maxPlayers = arena.getSession().getMaxPlayers();
        event.setJoinMessage(plugin.getConfiguration().getPrefix() + "§a" + player.getName() + " has joined! (" + numberPlayers + "/" + maxPlayers + ")");

        // Bukkit player
        Player bukkitPlayer = player.getBukkitPlayer();
        if(bukkitPlayer == null)
            return;

        // Scoreboard
        InfoBoard infoBoard = plugin.getInfoBoardManager().getInfoBoard(arena);
        InfoBoardLabel aliveLabel = infoBoard.getLabel("alive");
        aliveLabel.setText("§aAlive: §f" + numberPlayers);
        infoBoard.updateLabel(aliveLabel);

        infoBoard.send(bukkitPlayer);

        // TODO Add lobby items
        // TODO Change its tab list etc

        // Create more arenas if needed
        if(plugin.getGameController().getAvailableArenas().size() <= 1)
            plugin.createArena();
    }

    /**
     * Controls the player leave arena event
     * @param event player leave arena event
     */
    @EventHandler
    public void onLeave(PlayerLeaveArenaEvent event) {
        // Check if the arena belongs to this game
        Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        PlayerData player = event.getPlayer();

        // Change leave message
        int numberPlayers = arena.getSize();
        int maxPlayers = arena.getSession().getMaxPlayers();
        event.setLeaveMessage(plugin.getConfiguration().getPrefix() + "§c" + player.getName() + " has left! (" + numberPlayers + "/" + maxPlayers + ")");

        // Delete arena if needed
        if(arena.getSession() != null && arena.getSession().getState() != GameState.LOBBY) {
            if(arena.getPlayers(PlayerState.ALIVE).size() <= 1) {
                Arena targetArena;
                for(PlayerData target : arena.getAllPlayers()) {
                    if(target == player)
                        continue;

                    targetArena = plugin.getGameController().getAvailableArena(ArenaPreference.MORE_PLAYERS);

                    // No arena available or not allowed to join the arena
                    if(targetArena == null || !arena.join(target)) {
                        // TODO take care when player can not rejoin an arena
                    }
                }
                plugin.getGameController().removeArena(arena);
            }
        }
    }

    /**
     * Controls the player spectate arena event
     * @param event player spectate arena event
     */
    @EventHandler
    public void onSpectate(PlayerSpectateArenaEvent event) {
        // Check if the arena belongs to this game
        Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        PlayerData player = event.getPlayer();

        // Change join message
        int numberPlayers = arena.getSize();
        int maxPlayers = arena.getSession().getMaxPlayers();
        event.setJoinMessage(plugin.getConfiguration().getPrefix() + "�a" + player.getName() + " is spectating! (" + numberPlayers + "/" + maxPlayers + ")");
    }

    /**
     * Controls the player talk event
     * @param event player talk event
     */
    @EventHandler
    public void onTalk(PlayerTalkEvent event) {
        // Check if the arena belongs to this game
        Arena arena = event.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        PlayerData player = event.getPlayer();

        // Block dead players
        if(player.getState() == PlayerState.DEAD) { // TODO Warn that dead players may not talk
            event.setCancelled(true);
            return;
        }
    }
}