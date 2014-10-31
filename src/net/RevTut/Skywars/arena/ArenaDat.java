package net.RevTut.Skywars.arena;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArenaDat {
    /* ArenaDat's List */
    private static List<ArenaDat> arenasDat = new ArrayList<ArenaDat>();

    /* ArenaDat's Configuration */
    public static final int minPlayers = 8; // Minimum players for the game starts
    public static final int minReduceTimePlayers = 16; // Minimum players for the lobby time reduces
    public static final int maxPlayers = 24; // Maximum players that can play in arena

    /* Arena Info */
    private final int arenaNumber;
    private String mapName;
    private String gameNumber;
    private int remainingTime; // Arena's remaining time. It depends on the arena's status, that means it can be the currentInGameTime, currentLobbyTime, etc.
    private ArenaStatus status; // Current status of the Arena

    /* Arena Location */
    private final Location lobbyLocation;
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
    public ArenaDat(int arenaNumber, String mapName, String gameNumber, Location lobbyLocation, Location deathSpawnLocation, Location firstCorner, Location secondCorner, List<Location> spawnLocations) {
        this.arenaNumber = arenaNumber;
        this.mapName = mapName;
        this.gameNumber = gameNumber;
        this.lobbyLocation = lobbyLocation;
        this.deathSpawnLocation = deathSpawnLocation;
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;
        this.spawnLocations = spawnLocations;
        this.remainingTime = ArenaStatus.LOBBY.getTime();
        this.status = ArenaStatus.LOBBY;
    }

    /* Static Methods */
    public static boolean addArena(ArenaDat arenaDat) {
        if (getArenaByNumber(arenaDat.getArenaNumber()) != null)
            return false;
        arenasDat.add(arenaDat);
        return true;
    }

    public static void removeArena(ArenaDat arenaDat) {
        arenasDat.remove(arenaDat);
    }

    public static ArenaDat getArenaByNumber(int number) {
        for (int i = 0; i < ArenaDat.arenasDat.size(); i++)
            if (ArenaDat.arenasDat.get(i).getArenaNumber() == number)
                return ArenaDat.arenasDat.get(i);
        return null;
    }

    /* Get's Info */
    public int getArenaNumber() {
        return arenaNumber;
    }

    public String getMapName() {
        return mapName;
    }

    public String getGameNumber() {
        return gameNumber;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public ArenaStatus getStatus() {
        return status;
    }

    /* Get's Locations */
    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public Location getDeathSpawnLocation() {
        return deathSpawnLocation;
    }

    public Location getFirstCorner() {
        return firstCorner;
    }

    public Location getSecondCorner() {
        return secondCorner;
    }

    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    /* Get's Game Info */
    public String getWinner() {
        return winner;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public List<String> getInitialPlayers() {
        return initialPlayers;
    }

    public List<String> getGameChat() {
        return gameChat;
    }

    public List<String> getGameEvents() {
        return gameEvents;
    }

    /* Set's Info */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public void setGameNumber(String gameNumber) {
        this.gameNumber = gameNumber;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setStatus(ArenaStatus status) {
        this.status = status;
    }

    /* Set's Locations */
    public void setDeathSpawnLocation(Location deathSpawnLocation) { this.deathSpawnLocation = deathSpawnLocation; }

    public void setFirstCorner(Location firstCorner) { this.firstCorner = firstCorner; }

    public void setSecondCorner(Location secondCorner) { this.secondCorner = secondCorner; }

    public void setSpawnLocations(List<Location> spawnLocations) { this.spawnLocations = spawnLocations; }

    /* Set's Game Info */
    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }

    public void setInitialPlayers(List<String> initialPlayers) {
        this.initialPlayers = initialPlayers;
    }

    public void setGameChat(List<String> gameChat) {
        this.gameChat = gameChat;
    }

    public void setGameEvents(List<String> gameEvents) {
        this.gameEvents = gameEvents;
    }

    /* Add's Game Info */
    public void addInitialPlayer(String player) {
        this.initialPlayers.add(player);
    }

    public void addGameChat(String message) {
        this.gameChat.add(message);
    }

    public void addGameEvent(String event) {
        this.gameEvents.add(event);
    }
}
