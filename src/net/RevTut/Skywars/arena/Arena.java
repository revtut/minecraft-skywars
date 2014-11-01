package net.RevTut.Skywars.arena;

import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by João on 31/10/2014.
 */
public class Arena {
    /* Arena's List */
    private static List<Arena> arenas = new ArrayList<Arena>();

    /* Arena's Configuration */
    public static final int minPlayers = 8; // Minimum player for the game starts
    public static final int minReduceTimePlayers = 16; // Minimum player for the lobby time reduces
    public static final int maxPlayers = 24; // Maximum player that can play in arena

    /* Arena Info */
    private final int arenaNumber;
    private String mapName;
    private int remainingTime; // Arena's remaining time. It depends on the arena's status, that means it can be the currentInGameTime, currentLobbyTime, etc.
    private ArenaStatus status; // Current status of the Arena
    private List<PlayerDat> players = new ArrayList<PlayerDat>();

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

    public static boolean addPlayer(PlayerDat playerDat) {
        if (getArenaByPlayer(playerDat) != null)
            return false;
        // Look for available arena
        int posArena = -1;
        int maxPlayers = -1;
        for (int i = 0; i < Arena.arenas.size(); i++)
            if (Arena.arenas.get(i).getStatus() == ArenaStatus.LOBBY) // Waiting For Players
                if (Arena.arenas.get(i).getPlayers().size() < Arena.maxPlayers) // Not Full
                    if (Arena.arenas.get(i).getPlayers().size() > maxPlayers) // Arena With Highest Amount of Players
                        posArena = i;
        // Check if an arena was found
        if (posArena == -1)
            return false;
        Arena arena =  Arena.arenas.get(posArena);
        // Add Player To Arena
        arena.getPlayers().add(playerDat);
        // Teleport To Lobby
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if(player == null){
            return false;
        }
        player.teleport(arena.getArenaLocation().getLobbyLocation());
        return true;
    }

    public static void removeArena(Arena arena) {
        arenas.remove(arena);
    }

    public static void removePlayer(PlayerDat playerDat) {
        Arena arena = getArenaByPlayer(playerDat);
        if (arena != null)
            arena.getPlayers().remove(playerDat);
    }

    public static Arena getArenaByNumber(int number) {
        for (int i = 0; i < Arena.arenas.size(); i++)
            if (Arena.arenas.get(i).getArenaNumber() == number)
                return Arena.arenas.get(i);
        return null;
    }

    public static Arena getArenaByPlayer(PlayerDat playerDat) {
        for (int i = 0; i < Arena.arenas.size(); i++)
            for (int j = 0; j < Arena.arenas.get(i).getPlayers().size(); j++)
                if (Arena.arenas.get(i).getPlayers().get(j).getUUID() == playerDat.getUUID())
                    return Arena.arenas.get(i);
        return null;
    }

    public static int getNumberAvailableArenas(){
        int numero = 0;
        for (int i = 0; i < Arena.arenas.size(); i++)
            if (Arena.arenas.get(i).getStatus() == ArenaStatus.LOBBY)
                numero++;
        return numero;
    }

    public static int nextArenaNumber(){
        List<Integer> arenasNumbers = new ArrayList<Integer>();
        // Get all arenas numbers
        for(int i = 0; i < arenas.size(); i++)
            arenasNumbers.add(arenas.get(i).getArenaNumber());
        /* Check if there is a missing number in arenas numbers
        If there are 10 arenas, they should be 0, 1, ...., 9.
        But because of the way it is made, it might be 0, 1, ...,8,10.
        So we can assign the nextArenaNumber to the missing "9". */
        for(int i = 0; i < arenas.size(); i++)
            if(!arenasNumbers.contains(i))
                return i;
        return arenas.size() + 1;
    }

    public static String nextGameNumber(){
        return "";
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

    public List<PlayerDat> getPlayers() {
        return players;
    }

    /* Set's */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setStatus(ArenaStatus status) {
        this.remainingTime = status.getTime();
        this.status = status;
    }

    public void setArenaLocation(ArenaLocation arenaLocation) {
        this.arenaLocation = arenaLocation;
    }

    public void setArenaDat(ArenaDat arenaDat) {
        this.arenaDat = arenaDat;
    }

    public void setPlayers(List<PlayerDat> players) {
        this.players = players;
    }
}
