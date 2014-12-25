package net.revtut.skywars.arena;

import net.revtut.skywars.managers.KitManager;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.player.PlayerStatus;
import net.revtut.skywars.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Arena Object.
 *
 * <P>Game arena which includes several attributes arena related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Arena {

    /**
     * Arena Number
     */
    private final int arenaNumber;

    /**
     * Map Name
     */
    private String mapName;

    /**
     * Remaining time of the arena. Might be the current ingame time, current lobby time
     *
     * @see ArenaStatus
     */
    private int remainingTime;

    /**
     * Status of the arena
     *
     * @see ArenaStatus
     */
    private ArenaStatus status;

    /**
     * List of players which are on the arena
     */
    private final List<PlayerDat> players = new ArrayList<PlayerDat>();

    /**
     * Arena Location (all the locations of that arena)
     *
     * @see ArenaLocation
     */
    private ArenaLocation arenaLocation;

    /**
     * Arena Dat (status of that game)
     *
     * @see ArenaDat
     */
    private ArenaDat arenaDat;

    /**
     * Kit Manager (manages all the kits of the players)
     *
     * @see net.revtut.skywars.managers.KitManager
     */
    private KitManager kitManager;

    /**
     * Constructor of Arena
     *
     * @param arenaNumber   the arena to reset
     * @param mapName       the name of the map
     * @param arenaLocation the arena location of the arena
     */
    public Arena(int arenaNumber, String mapName, ArenaLocation arenaLocation) {
        this.arenaNumber = arenaNumber;
        this.mapName = mapName;
        this.arenaLocation = arenaLocation;
        this.arenaDat = new ArenaDat();
        this.remainingTime = ArenaStatus.LOBBY.getTime();
        this.status = ArenaStatus.LOBBY;
        this.kitManager = new KitManager();
    }

    /**
     * Send a message to all the players in the arena
     *
     * @param message message to be sent
     */
    public void sendMessage(Message message) {
        sendMessage(message, "");
    }

    /**
     * Send a message to all the players in the arena
     *
     * @param message message to be sent
     * @param args arguments of the message will be attached at the end of it
     */
    public void sendMessage(Message message, String args) {
        for (PlayerDat playerDat : players) {
            Player player = Bukkit.getPlayer(playerDat.getUUID());
            if (player == null)
                continue;
            // Send message
            String translatedMessage = Message.getMessage(message, player);
            player.sendMessage(translatedMessage + args);
        }
    }

    /**
     * Return the arena number
     *
     * @return arena number
     */
    public int getArenaNumber() {
        return arenaNumber;
    }

    /**
     * Return the map name
     *
     * @return name of the current map
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Return the remaining time of the arena (might be remaining time of lobby etc)
     *
     * @return remaining time
     */
    public int getRemainingTime() {
        return remainingTime;
    }

    /**
     * Return the arena status
     *
     * @return status of the arena
     * @see ArenaStatus
     */
    public ArenaStatus getStatus() {
        return status;
    }

    /**
     * Return the arena location
     *
     * @return locations of the arena
     * @see ArenaLocation
     */
    public ArenaLocation getArenaLocation() {
        return arenaLocation;
    }

    /**
     * Return the arena dat
     *
     * @return arena dat
     * @see ArenaDat
     */
    public ArenaDat getArenaDat() {
        return arenaDat;
    }

    /**
     * Return the kit manager
     *
     * @return kit manager
     * @see KitManager
     */
    public KitManager getKitManager() {
        return kitManager;
    }

    /**
     * Return a list with the players on the arena
     *
     * @return list with all the players
     */
    public List<PlayerDat> getPlayers() {
        return players;
    }

    /**
     * Return a list with the alive players on the arena
     *
     * @return list with all alive players
     */
    public List<PlayerDat> getAlivePlayers() {
        List<PlayerDat> alivePlayers = new ArrayList<PlayerDat>();
        // Lobby
        if (this.status == ArenaStatus.LOBBY || this.status == ArenaStatus.PREGAME)
            return this.getPlayers();
        // Already ingame
        for (PlayerDat player : players)
            if (player.getStatus() == PlayerStatus.ALIVE)
                alivePlayers.add(player);
        return alivePlayers;
    }

    /**
     * Return a list with the dead players on the arena
     *
     * @return list with all dead players
     */
    public List<PlayerDat> getDeadPlayers() {
        List<PlayerDat> deathPlayers = new ArrayList<PlayerDat>();
        for (PlayerDat player : this.players)
            if (player.getStatus() == PlayerStatus.DEAD)
                deathPlayers.add(player);
        return deathPlayers;
    }

    /**
     * Return a list with the spectator players on the arena
     *
     * @return list with all spectators players
     */
    public List<PlayerDat> getSpectatorPlayers() {
        List<PlayerDat> spectatorPlayers = new ArrayList<PlayerDat>();
        for (PlayerDat player : players)
            if (player.getStatus() == PlayerStatus.SPECTATOR)
                spectatorPlayers.add(player);
        return spectatorPlayers;
    }

    /**
     * Set the map name of the arena
     *
     * @param mapName the name of the new map
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * Set the remaining time of the arena
     *
     * @param remainingTime the remaining time to set the arena
     */
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    /**
     * Set the status of the arena
     *
     * @param status the arena status to replace the existing
     */
    public void setStatus(ArenaStatus status) {
        this.remainingTime = status.getTime();
        this.status = status;
    }

    /**
     * Set the arena location of the arena
     *
     * @param arenaLocation the arena location to replace the existing
     */
    public void setArenaLocation(ArenaLocation arenaLocation) {
        this.arenaLocation = arenaLocation;
    }

    /**
     * Set the arena dat of the arena
     *
     * @param arenaDat the arena dat to replace the existing
     */
    public void setArenaDat(ArenaDat arenaDat) {
        this.arenaDat = arenaDat;
    }

    /**
     * Set the kit manager of the arena
     *
     * @param kitManager the kit manager to replace the existing
     */
    public void setKitManager(KitManager kitManager) {
        this.kitManager = kitManager;
    }


    /**
     * Set the players of the arena
     */
    public void clearPlayers() {
        this.players.clear();
    }


}
