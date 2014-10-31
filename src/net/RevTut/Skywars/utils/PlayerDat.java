package net.RevTut.Skywars.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class PlayerDat {
    /* PlayerDat's List */
    private static List<PlayerDat> playersDat = new ArrayList<PlayerDat>();

    /* UUID */
    private UUID uuid;

    /* Currency */
    private int points;

    /* Statistics */
    private Date lastLogin;
    private long playTime;
    private int wins;
    private int losses;
    private int kills;
    private int deaths;

    /* Constructor */
    public PlayerDat(UUID uuid, Date lastLogin, long playTime, int points, int wins, int losses, int kills, int deaths) {
        this.uuid = uuid;
        this.lastLogin = lastLogin;
        this.playTime = playTime;
        this.points = points;
        this.wins = wins;
        this.losses = losses;
        this.kills = kills;
        this.deaths = deaths;
    }

    /* Static Methods */
    public static boolean addPlayerDat(PlayerDat playerDat) {
        if (getPlayerDatByUUID(playerDat.getUUID()) != null)
            return false;
        playersDat.add(playerDat);
        return true;
    }

    public static void removePlayerDat(PlayerDat playerDat) {
        playersDat.remove(playerDat);
    }

    public static PlayerDat getPlayerDatByUUID(UUID uuid) {
        for (int i = 0; i < PlayerDat.playersDat.size(); i++)
            if (PlayerDat.playersDat.get(i).getUUID() == uuid)
                return PlayerDat.playersDat.get(i);
        return null;
    }

    /* Get's */
    public UUID getUUID() {
        return uuid;
    }

    public long getPlayTime() {
        long seconds = (new Date().getTime() - lastLogin.getTime()) / 1000; //
        return playTime + seconds;
    }

    public int getPoints() {
        return points;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    /* Add's */
    public void addPoints(int points) {
        this.points += points;
    }

    public void addWins() {
        this.wins++;
    }

    public void addLosses() {
        this.losses++;
    }
}
