package net.RevTut.Skywars.utils;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ArenaDat {
    /* ArenaDat's List */
    private static List<ArenaDat> arenasDat = new ArrayList<ArenaDat>();

    /* ArenaDat's Configuration */
    private static int gameTime = 1800; // Maximum time a game can take (SECONDS)
    private static int lobbyTime = 300; // After reaching minimum players, how much time to wait for new players to join before game starts
    private static int waitingTime = 30; // Time before the gam actually starts (when you are already in the map's spawn) (SECONDS)
    private static int minPlayers = 8; // Minimum players for the game starts
    private static int minReduceTimePlayers = 16; // Minimum players for the lobby time reduces
    private static int maxPlayers = 24; // Maximum players that can play in arena

    /* Arena Info */
    private int arenaNumber;
    private String mapName;
    private String gameNumber;
    private int currentTime; // Arena's time. It depends on the arena's status, that means it can be the currentGameTime, currentLobbyTime, etc.
    private ArenaStatus status; // Current status of the Arena

    /* Arena Location */
    private Location lobbyLocation;
    private Location deathSpawnLocation; // Location where death player spawn
    private Location firstCorner; // Limits of the arena
    private Location secondCorner; // Limits of the arena
    private List<Location> spawnLocations = new ArrayList<Location>(); // Spawn players locations

    /* Game Info */
    private String winner; // This string is in fact the UUID
    private Date startDate;
    private Date stopDate;
    private List<String> initialPlayers = new ArrayList<String>(); // This string is in fact the UUID of the players with which the game started
    private List<String> gameChat = new ArrayList<String>(); // Player's Chat
    private List<String> gameEvents = new ArrayList<String>(); // Game Events

    /* Constructor */
    public ArenaDat(int arenaNumber, String mapName, String gameNumber) {
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
    public static boolean addPlayerDat(ArenaDat playerDat) {
        if (getPlayerDatByUUID(playerDat.getUUID()) != null)
            return false;
        arenasDat.add(playerDat);
        return true;
    }

    public static void removePlayerDat(ArenaDat playerDat) {
        arenasDat.remove(playerDat);
    }

    public static ArenaDat getPlayerDatByUUID(UUID uuid) {
        for (int i = 0; i < ArenaDat.arenasDat.size(); i++)
            if (ArenaDat.arenasDat.get(i).getUUID() == uuid)
                return ArenaDat.arenasDat.get(i);
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
