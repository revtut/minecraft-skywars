package net.RevTut.Skywars.arena;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by João on 31/10/2014.
 */
public class ArenaLocation {

    /* Arena Location */
    private final Location lobbyLocation;
    private Location deathSpawnLocation; // Location where death player spawn
    private Location firstCorner; // Limits of the arena
    private Location secondCorner; // Limits of the arena
    private List<Location> spawnLocations = new ArrayList<Location>(); // Spawn player locations

    /* Constructor */
    public ArenaLocation(Location lobbyLocation, Location deathSpawnLocation, Location firstCorner, Location secondCorner, List<Location> spawnLocations) {
        this.lobbyLocation = lobbyLocation;
        this.deathSpawnLocation = deathSpawnLocation;
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;
        this.spawnLocations = spawnLocations;
    }

    /* Get's */
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

    /* Set's */
    public void setDeathSpawnLocation(Location deathSpawnLocation) {
        this.deathSpawnLocation = deathSpawnLocation;
    }

    public void setFirstCorner(Location firstCorner) {
        this.firstCorner = firstCorner;
    }

    public void setSecondCorner(Location secondCorner) {
        this.secondCorner = secondCorner;
    }

    public void setSpawnLocations(List<Location> spawnLocations) {
        this.spawnLocations = spawnLocations;
    }
}
