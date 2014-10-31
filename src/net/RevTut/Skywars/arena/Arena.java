package net.RevTut.Skywars.arena;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by João on 31/10/2014.
 */
public class Arena {
    /* Arena's List */
    private static List<Arena> arenas = new ArrayList<Arena>();

    /* Arena's Configuration */
    public static final int minPlayers = 8; // Minimum players for the game starts
    public static final int minReduceTimePlayers = 16; // Minimum players for the lobby time reduces
    public static final int maxPlayers = 24; // Maximum players that can play in arena

    /* Arena Info */
    private final int arenaNumber;
    private String mapName;
    private int remainingTime; // Arena's remaining time. It depends on the arena's status, that means it can be the currentInGameTime, currentLobbyTime, etc.
    private ArenaStatus status; // Current status of the Arena

    /* Arena Location */
    private ArenaLocation arenaLocation;

    /* Arena Dat */
    private ArenaDat arenaDat;

    /* Constructor */
    public Arena(int arenaNumber, String mapName, ArenaLocation arenaLocation) {
        this.arenaNumber = arenaNumber;
        this.mapName = mapName;
        this.arenaLocation = arenaLocation;
        this.arenaDat = new ArenaDat();
        this.remainingTime = ArenaStatus.LOBBY.getTime();
        this.status = ArenaStatus.LOBBY;
    }

    /* Static Methods */
    public static boolean addArena(Arena arena) {
        if (getArenaByNumber(arena.getArenaNumber()) != null)
            return false;
        arenas.add(arena);
        return true;
    }

    public static void removeArena(Arena arena) {
        arenas.remove(arena);
    }

    public static Arena getArenaByNumber(int number) {
        for (int i = 0; i < Arena.arenas.size(); i++)
            if (Arena.arenas.get(i).getArenaNumber() == number)
                return Arena.arenas.get(i);
        return null;
    }

    /* Get's */
    public int getArenaNumber() {
        return arenaNumber;
    }

    public String getMapName() {
        return mapName;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public ArenaStatus getStatus() {
        return status;
    }

    public ArenaLocation getArenaLocation() {
        return arenaLocation;
    }

    public ArenaDat getArenaDat() {
        return arenaDat;
    }

    /* Set's */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setStatus(ArenaStatus status) {
        this.status = status;
    }

    public void setArenaLocation(ArenaLocation arenaLocation) {
        this.arenaLocation = arenaLocation;
    }

    public void setArenaDat(ArenaDat arenaDat) {
        this.arenaDat = arenaDat;
    }
}
