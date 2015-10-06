package net.revtut.skywars;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.ArenaPreference;
import net.revtut.libraries.games.arena.session.GameState;
import net.revtut.libraries.games.events.player.*;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.PlayerState;
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
        int numberPlayers = arena.getSize();
        int maxPlayers = arena.getSession().getMaxPlayers();
        event.setJoinMessage(plugin.getConfiguration().getPrefix() + "§a" + player.getName() + " has joined! (" + numberPlayers + "/" + maxPlayers + ")");

        // TODO Add scoreboard to the player
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
        event.setJoinMessage(plugin.getConfiguration().getPrefix() + "§a" + player.getName() + " is spectating! (" + numberPlayers + "/" + maxPlayers + ")");
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