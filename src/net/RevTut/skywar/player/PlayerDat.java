package net.RevTut.skywar.player;

import net.RevTut.skywar.SkyWars;
import net.RevTut.skywar.utils.Message;
import net.RevTut.skywar.libraries.language.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Date;
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
     * Main class
     */
    public static SkyWars plugin = null;

    /**
     * UUID of the player
     */
    private final UUID uuid;

    /**
     * Last login of the player
     */
    private final Date lastLogin;

    /**
     * Language of the player
     */
    private Language language;

    /**
     * Playtime of the player in this game
     */
    private final long playTime;

    /**
     * Points of the player
     */
    private int points;

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
     * Number of kills in this game
     */
    private int gameKills;

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
     * @param language  language of the player
     * @param playTime  play time of the player
     * @param points    total points of the player
     * @param wins      wins in this game of the player
     * @param losses    losses in this game of the player
     * @param kills     kills in this game of the player
     * @param deaths    deaths in this game of the player
     */
    public PlayerDat(UUID uuid, Date lastLogin, Language language, long playTime, int points, int wins, int losses, int kills, int deaths) {
        this.uuid = uuid;
        this.lastLogin = lastLogin;
        this.language = language;
        this.playTime = playTime;
        this.points = points;
        this.wins = wins;
        this.losses = losses;
        this.kills = kills;
        this.gameKills = 0;
        this.deaths = deaths;
        this.status = PlayerStatus.WAITING;
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
     * Get the language of the player dat
     *
     * @return language of the player dat
     */
    public Language getLanguage() { return language; }

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
     * Get the kills of the player dat in this game
     *
     * @return kills of the player dat in this game
     */
    public int getGameKills() {
        return gameKills;
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
     * Set the language of the player dat
     *
     * @param language new language of the player dat
     * @see Language
     */
    public void setLanguage(Language language) { this.language = language; }

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
        if(this.points < 0)
            this.points = 0;
        // Player
        Player player = Bukkit.getPlayer(uuid);
        if(player == null)
            return;
        // Message
        char signal = '+';
        if(points < 0)
            signal = '-';
        player.sendMessage(Message.getMessage(Message.POINTS, player) + signal + Math.abs(points));
        // Update points in ScoreBoard
        plugin.scoreBoardManager.updatePoints(this);
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
        this.gameKills++;
    }

    /**
     * Add death to the player dat
     */
    public void addDeath() {
        this.deaths++;
    }

    /**
     * Reset the game kills of a player
     */
    public void resetGameKills() { this.gameKills = 0; }
}
