package net.RevTut.Skywars.player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * PlayerDat Object.
 *
 * <P>A complement to Player Object, which saves all the information related to the game and server.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerDat {

    /**
     * List of all playerDats in the server
     */
    private static final List<PlayerDat> playersDat = new ArrayList<PlayerDat>();

    /**
     * UUID of the player
     */
    private final UUID uuid;

    /**
     * Points of the player
     */
    private int points;

    /**
     * Last login of the player
     */
    private final Date lastLogin;

    /**
     * Playtime of the player in this game
     */
    private final long playTime;

    /**
     * Number of wins
     */
    private int wins;

    /**
     * Number of losses
     */
    private int losses;

    /**
     * Number of kills
     */
    private int kills;

    /**
     * Number of deaths
     */
    private int deaths;

    /**
     * Status of the player
     */
    private PlayerStatus status;

    /**
     * Constructor of PlayerDat
     *
     * @param uuid      uuid of the player
     * @param lastLogin last login of the player
     * @param playTime  play time of the player
     * @param points    total points of the player
     * @param wins      wins in this game of the player
     * @param losses    losses in this game of the player
     * @param kills     kills in this game of the player
     * @param deaths    deaths in this game of the player
     */
    public PlayerDat(UUID uuid, Date lastLogin, long playTime, int points, int wins, int losses, int kills, int deaths) {
        this.uuid = uuid;
        this.lastLogin = lastLogin;
        this.playTime = playTime;
        this.points = points;
        this.wins = wins;
        this.losses = losses;
        this.kills = kills;
        this.deaths = deaths;
        this.status = PlayerStatus.WAITING;
    }

    /**
     * Add a new player dat to the server
     *
     * @param playerDat player dat to add to the server
     * @return true if successfull
     */
    public static boolean addPlayerDat(PlayerDat playerDat) {
        if (getPlayerDatByUUID(playerDat.getUUID()) != null)
            return false;
        playersDat.add(playerDat);
        return true;
    }

    /**
     * Removes a player dat from the server
     *
     * @param playerDat player dat to remove from the server
     */
    public static void removePlayerDat(PlayerDat playerDat) {
        playersDat.remove(playerDat);
    }

    /**
     * Get a player from a given UUID
     *
     * @param uuid uuid to get the player dat
     * @return player dat of that UUID
     */
    public static PlayerDat getPlayerDatByUUID(UUID uuid) {
        for (int i = 0; i < PlayerDat.playersDat.size(); i++)
            if (PlayerDat.playersDat.get(i).getUUID() == uuid)
                return PlayerDat.playersDat.get(i);
        return null;
    }

    /**
     * Get the UUID of the player dat
     *
     * @return uuid of the player dat
     */
    public UUID getUUID() {
        return uuid;
    }

    /**
     * Get the play time of the player dat.
     * Adds to the play time the difference between the current
     * date and the last login date
     *
     * @return play time of the player dat
     */
    public long getPlayTime() {
        long seconds = (new Date().getTime() - lastLogin.getTime()) / 1000; //
        return playTime + seconds;
    }

    /**
     * Get points of the player dat
     *
     * @return points of the player dat
     */
    public int getPoints() {
        return points;
    }

    /**
     * Get wins of the player dat
     *
     * @return wins of the player dat
     */
    public int getWins() {
        return wins;
    }

    /**
     * Get losses of the player dat
     *
     * @return losses of the player dat
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Get the kills of the player dat
     *
     * @return kills of the player dat
     */
    public int getKills() {
        return kills;
    }

    /**
     * Get the deaths of the player dat
     *
     * @return deaths of the player dat
     */
    public int getDeaths() {
        return deaths;
    }

    /**
     * Get the status of the player dat
     *
     * @return status of the player dat
     * @see PlayerStatus
     */
    public PlayerStatus getStatus() {
        return status;
    }

    /**
     * Set the status of the player dat
     *
     * @param status new status of the player dat
     * @see PlayerStatus
     */
    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    /**
     * Add points to the player dat
     *
     * @param points points to add
     */
    public void addPoints(int points) {
        this.points += points;
    }

    /**
     * Add win to the player dat
     */
    public void addWin() {
        this.wins++;
    }

    /**
     * Add lose to the player dat
     */
    public void addLose() {
        this.losses++;
    }

    /**
     * Add kill to the player dat
     */
    public void addKill() {
        this.kills++;
    }

    /**
     * Add death to the player dat
     */
    public void addDeath() {
        this.deaths++;
    }
}
