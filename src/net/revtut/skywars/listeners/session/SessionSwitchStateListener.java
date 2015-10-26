package net.revtut.skywars.listeners.session;

import net.revtut.libraries.minecraft.bukkit.games.GameController;
import net.revtut.libraries.minecraft.bukkit.games.arena.ArenaFlag;
import net.revtut.libraries.minecraft.bukkit.games.arena.session.GameSession;
import net.revtut.libraries.minecraft.bukkit.games.arena.session.GameState;
import net.revtut.libraries.minecraft.bukkit.games.arena.types.ArenaSolo;
import net.revtut.libraries.minecraft.bukkit.games.classes.GameClass;
import net.revtut.libraries.minecraft.bukkit.games.events.session.SessionSwitchStateEvent;
import net.revtut.libraries.minecraft.bukkit.games.player.GamePlayer;
import net.revtut.libraries.minecraft.bukkit.games.player.PlayerState;
import net.revtut.skywars.InfoBoardManager;
import net.revtut.skywars.SkyWars;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Session Switch State Listener
 */
public class SessionSwitchStateListener implements Listener {

    /**
     * Controls the session switch state event
     * @param event session switch state event
     */
    @EventHandler
    public void onSwitchState(final SessionSwitchStateEvent event) {
        // Check if the arena belongs to this game
        final GameSession session = event.getSession();
        final GameController gameController = SkyWars.getInstance().getGameController();
        if(gameController == null || !gameController.hasArena(session.getArena()))
            return;

        final ArenaSolo arena = (ArenaSolo) session.getArena();

        switch (event.getNextState()) {
            case WARMUP:
                onWarmUp(arena);
                break;
            case START:
                onStart(arena);
                break;
            case DEATHMATCH:
                onDeathMatch(arena);
                break;
            case FINISH:
                onFinish(arena);
                break;
        }
    }

    /**
     * Method to take care of what to do when state changes to warm up
     * @param arena arena that is now on warm up
     */
    private void onWarmUp(final ArenaSolo arena) {
        int playerIndex = -1;
        Player bukkitPlayer;
        Location teleportLocation;
        for(final GamePlayer player : arena.getAllPlayers()) {
            bukkitPlayer = Bukkit.getPlayer(player.getUuid());
            if(bukkitPlayer == null)
                continue;

            if(player.getState() == PlayerState.SPECTATOR) {
                bukkitPlayer.teleport(arena.getSpectatorLocation());
            } else {
                ++playerIndex;

                teleportLocation = arena.getSpawnLocations().get(playerIndex % arena.getSpawnLocations().size());
                bukkitPlayer.teleport(teleportLocation);

                // TODO Give kit chooser
            }
        }

        // Update scoreboard
        final InfoBoardManager infoBoardManager = SkyWars.getInstance().getInfoBoardManager();
        infoBoardManager.updateMap(arena, arena.getWorld().getName().split("_")[2]);
    }

    /**
     * Method to take care of what to do when state changes to start
     * @param arena arena that is now on start
     */
    private void onStart(final ArenaSolo arena) {
        Player bukkitPlayer;
        GameClass gameClass;
        for(final GamePlayer player : arena.getAllPlayers()) {
            if (player.getState() != PlayerState.ALIVE)
                continue;

            gameClass = player.getGameClass();
            if (gameClass == null) {
                // TODO Add default kit to the player
            } else
                gameClass.equip(player);

            bukkitPlayer = Bukkit.getPlayer(player.getUuid());
            if(bukkitPlayer == null)
                continue;

            bukkitPlayer.setGameMode(GameMode.SURVIVAL);
            bukkitPlayer.getLocation().getBlock().getRelative(BlockFace.DOWN).setType(Material.AIR);
        }

        // Update flags
        arena.updateFlag(ArenaFlag.BLOCK_BREAK, true);
        arena.updateFlag(ArenaFlag.BLOCK_PLACE, true);
        arena.updateFlag(ArenaFlag.BUCKET_EMPTY, true);
        arena.updateFlag(ArenaFlag.BUCKET_FILL, true);
        arena.updateFlag(ArenaFlag.DROP_ITEM, true);
        arena.updateFlag(ArenaFlag.DAMAGE, true);
        arena.updateFlag(ArenaFlag.HUNGER, true);
        arena.updateFlag(ArenaFlag.PICKUP_ITEM, true);
    }

    /**
     * Method to take care of what to do when state changes to death match
     * @param arena arena that is now on death match
     */
    private void onDeathMatch(final ArenaSolo arena) {
        int playerIndex = -1;
        Player bukkitPlayer;
        Location teleportLocation;
        for(final GamePlayer player : arena.getAllPlayers()) {
            bukkitPlayer = Bukkit.getPlayer(player.getUuid());
            if(bukkitPlayer == null)
                continue;

            if(player.getState() == PlayerState.SPECTATOR) {
                bukkitPlayer.teleport(arena.getSpectatorDeathMatchLocation());
            } else if(player.getState() == PlayerState.DEAD) {
                bukkitPlayer.teleport(arena.getDeadDeathMatchLocation());
            } else {
                ++playerIndex;

                teleportLocation = arena.getDeathMatchLocations().get(playerIndex % arena.getDeathMatchLocations().size());
                bukkitPlayer.teleport(teleportLocation);
            }
        }
    }

    /**
     * Method to take care of what to do when state changes to finish
     * @param arena arena that is now on finish
     */
    private void onFinish(final ArenaSolo arena) {
        Player bukkitPlayer;
        for(final GamePlayer player : arena.getAllPlayers()) {
            bukkitPlayer = Bukkit.getPlayer(player.getUuid());
            if(bukkitPlayer == null)
                continue;

            if(arena.getSession().getState() == GameState.DEATHMATCH) {
                bukkitPlayer.teleport(arena.getDeadDeathMatchLocation());
            } else {
                bukkitPlayer.teleport(arena.getDeadLocation());
            }

            bukkitPlayer.setGameMode(GameMode.SPECTATOR);
        }

        // Update flags
        arena.updateFlag(ArenaFlag.BLOCK_BREAK, false);
        arena.updateFlag(ArenaFlag.BLOCK_PLACE, false);
        arena.updateFlag(ArenaFlag.BUCKET_EMPTY, false);
        arena.updateFlag(ArenaFlag.BUCKET_FILL, false);
        arena.updateFlag(ArenaFlag.DROP_ITEM, false);
        arena.updateFlag(ArenaFlag.DAMAGE, false);
        arena.updateFlag(ArenaFlag.HUNGER, false);
        arena.updateFlag(ArenaFlag.PICKUP_ITEM, false);
    }
}
