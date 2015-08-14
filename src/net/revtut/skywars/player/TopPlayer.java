package net.revtut.skywars.player;

/**
 * Top Player class.
 * Contains everything related to a given top player.
 */
public class TopPlayer {

    /**
     * Nickname of the player
     */
    private String nickname;

    /**
     * Points (calculated accordingly wins, loses, kills and deaths) of the player
     */
    private int points;

    /**
     * Number of wins
     */
    private int wins;

    /**
     * Number of loses
     */
    private int loses;

    /**
     * Number of kills
     */
    private int kills;

    /**
     * Number of deaths
     */
    private int deaths;

    /**
     * Constructor of TopPlayer
     * @param nickname nickname of the player
     * @param points calculated points
     * @param wins number of wins
     * @param loses number of loses
     * @param kills number of kills
     * @param deaths number of deaths
     */
    public TopPlayer(String nickname, int points, int wins, int loses, int kills, int deaths) {
        this.nickname = nickname;
        this.points = points;
        this.wins = wins;
        this.loses = loses;
        this.kills = kills;
        this.deaths = deaths;
    }

    /**
     * Get the nickname of the player
     * @return nickname of the player
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Get the points of the player
     * @return points of the player
     */
    public int getPoints() {
        return points;
    }

    /**
     * Get the wins of the player
     * @return number of wins of the player
     */
    public int getWins() {
        return wins;
    }

    /**
     * Get the loses of the player
     * @return number of loses of the player
     */
    public int getLoses() {
        return loses;
    }

    /**
     * Get the kills of the player
     * @return number of kills of the player
     */
    public int getKills() {
        return kills;
    }

    /**
     * Get the deaths of the player
     * @return number of deaths of the player
     */
    public int getDeaths() {
        return deaths;
    }
}
