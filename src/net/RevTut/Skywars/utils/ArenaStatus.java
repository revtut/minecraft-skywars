package net.RevTut.Skywars.utils;

/**
 * Created by João on 31/10/2014.
 */
public enum ArenaStatus {
    LOOKINGPLAYERS(999), // Waiting for players to join
    LOBBY (300), // Minimum players reached, waiting for new ones to join before starting
    WAITING(30), // Waiting time after the players are in the arena
    INGAME(1800), // Maximum game time
    FIREWORKS(30); // End of the game time

    /* Maximum Time of the Status (SECONDS) */
    private final int time;

    /* Construtor */
    ArenaStatus(int time){
        this.time = time;
    }

    /* Get's */
    int getTime() {
        return time;
    }
}
