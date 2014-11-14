package net.RevTut.Skywars.arena;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * ArenaLocation Object.
 * <p/>
 * <P>A complement to Arena Object, which saves all the information regarding game locations.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class ArenaLocation {

    /**
     * Lobby location
     */
    private final Location lobbyLocation;

    /**
     * Location where dead players will spawn
     */
    private Location deathSpawnLocation;

    /**
     * Inferior limit of the arena (minimum coords)
     */
    private Location firstCorner;

    /**
     * Superior limit of the arena (maximum coords)
     */
    private Location secondCorner;

    /**
     * List with all the spawn locations of the game
     */
    private List<Location> spawnLocations = new ArrayList<Location>();

    /**
     * Constructor of ArenaLocation
     *
     * @param lobbyLocation      lobby location
     * @param deathSpawnLocation death spawn location
     * @param firstCorner        first corner of the arena (min coords)
     * @param secondCorner       second corner of the arena (max coords)
     * @param spawnLocations     spawn locations of the game
     */
    public ArenaLocation(Location lobbyLocation, Location deathSpawnLocation, Location firstCorner, Location secondCorner, List<Location> spawnLocations) {
        this.lobbyLocation = lobbyLocation;
        this.deathSpawnLocation = deathSpawnLocation;
        this.firstCorner = firstCorner;
        this.secondCorner = secondCorner;
        this.spawnLocations = spawnLocations;
    }

    /**
     * Returns the lobby location of the arena
     *
     * @return location of the lobby of the arena
     */
    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    /**
     * Returns the death spawn location of the arena
     *
     * @return death spawn location
     */
    public Location getDeathSpawnLocation() {
        return deathSpawnLocation;
    }

    /**
     * Returns the first corner of the arena (minimum coords)
     *
     * @return first corner location
     */
    public Location getFirstCorner() {
        return firstCorner;
    }

    /**
     * Returns the second corner of the arena (maximum coords)
     *
     * @return second corner location
     */
    public Location getSecondCorner() {
        return secondCorner;
    }

    /**
     * Returns list with the spawn locations of the game
     *
     * @return spawn locations of the game
     */
    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    /**
     * Set the death spawn location of the arena
     *
     * @param deathSpawnLocation the death spawn location
     */
    public void setDeathSpawnLocation(Location deathSpawnLocation) {
        this.deathSpawnLocation = deathSpawnLocation;
    }

    /**
     * Set the first corner location of the arena
     *
     * @param firstCorner the first corner of the arena
     */
    public void setFirstCorner(Location firstCorner) {
        this.firstCorner = firstCorner;
    }

    /**
     * Set the second corner location of the arena
     *
     * @param secondCorner the second corner of the arena
     */
    public void setSecondCorner(Location secondCorner) {
        this.secondCorner = secondCorner;
    }

    /**
     * Set the spawn locations of the arena
     *
     * @param spawnLocations list with all the spawn locations
     */
    public void setSpawnLocations(List<Location> spawnLocations) {
        this.spawnLocations = spawnLocations;
    }
}
