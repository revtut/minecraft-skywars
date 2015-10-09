package net.revtut.skywars;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.arena.ArenaFlag;
import net.revtut.libraries.games.arena.session.GameSession;
import net.revtut.libraries.games.arena.session.GameState;
import net.revtut.libraries.games.arena.types.ArenaSolo;
import net.revtut.libraries.games.classes.GameClass;
import net.revtut.libraries.games.events.session.SessionSwitchStateEvent;
import net.revtut.libraries.games.events.session.SessionTimerExpireEvent;
import net.revtut.libraries.games.events.session.SessionTimerTickEvent;
import net.revtut.libraries.games.player.PlayerData;
import net.revtut.libraries.games.player.PlayerState;
import net.revtut.libraries.scoreboard.InfoBoard;
import net.revtut.libraries.scoreboard.InfoBoardLabel;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Session Listener
 */
public class SessionListener implements Listener {

    /**
     * Game API instance
     */
    private SkyWars plugin = SkyWars.getInstance();

    /**
     * Controls the session timer tick event
     * @param event session timer tick event
     */
    @EventHandler
    public void onTimerTick(SessionTimerTickEvent event) {
        // Check if the arena belongs to this game
        GameSession session = event.getSession();
        Arena arena = session.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        switch (session.getState()) {
            case LOBBY:
            case WARMUP:
                for(PlayerData player : arena.getAllPlayers())
                    player.getBukkitPlayer().setLevel(event.getTime());
                break;
        }
    }

    /**
     * Controls the session timer expire event
     * @param event session timer expire event
     */
    @EventHandler
    public void onTimerExpire(SessionTimerExpireEvent event) {
        // Check if the arena belongs to this game
        GameSession session = event.getSession();
        Arena arena = session.getArena();
        if(!plugin.getGameController().hasArena(arena))
            return;

        switch (session.getState()) {
            case LOBBY:
                if(arena.getSize() < arena.getSession().getMinPlayers()) { // Minimum players not achieved
                    event.setCancelled(true);
                    break;
                }
                session.updateState(GameState.WARMUP, 30);
                break;
            case WARMUP:
                session.updateState(GameState.START, Integer.MAX_VALUE);
                break;
            case START:
            case DEATHMATCH:
                event.setCancelled(true); // The only way to end a game is if someone wins
                break;
            case FINISH:
                // TODO rejoin all the players and close this arena
                break;
        }
    }

    /**
     * Controls the session switch state event
     * @param event session switch state event
     */
    @EventHandler
    public void onSwitchState(SessionSwitchStateEvent event) {
        // Check if the arena belongs to this game
        GameSession session = event.getSession();
        if(!plugin.getGameController().hasArena(session.getArena()))
            return;

        ArenaSolo arena = (ArenaSolo) session.getArena();

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
    private void onWarmUp(ArenaSolo arena) {
        int playerIndex = -1;
        Player bukkitPlayer;
        Location teleportLocation;
        for(PlayerData player : arena.getAllPlayers()) {
            bukkitPlayer = player.getBukkitPlayer();
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

        // Update scoreboard (label map)
        InfoBoard infoBoard = SkyWars.getInstance().getInfoBoardManager().getInfoBoard(arena);
        InfoBoardLabel mapLabel = infoBoard.getLabel("map");
        mapLabel.setText("§6Map: §f" + arena.getWorld().getName().split("_")[2]);
        infoBoard.updateLabel(mapLabel);
    }

    /**
     * Method to take care of what to do when state changes to start
     * @param arena arena that is now on start
     */
    private void onStart(ArenaSolo arena) {
        Player bukkitPlayer;
        GameClass gameClass;
        for(PlayerData player : arena.getAllPlayers()) {
            if (player.getState() != PlayerState.ALIVE)
                continue;

            gameClass = player.getGameClass();
            if (gameClass == null) {
                // TODO Add default kit to the player
            } else
                gameClass.equip(player);

            bukkitPlayer = player.getBukkitPlayer();
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
    private void onDeathMatch(ArenaSolo arena) {
        int playerIndex = -1;
        Player bukkitPlayer;
        Location teleportLocation;
        for(PlayerData player : arena.getAllPlayers()) {
            bukkitPlayer = player.getBukkitPlayer();
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
    private void onFinish(ArenaSolo arena) {
        Player bukkitPlayer;
        for(PlayerData player : arena.getAllPlayers()) {
            bukkitPlayer = player.getBukkitPlayer();
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