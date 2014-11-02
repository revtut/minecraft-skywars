package net.RevTut.Skywars.arena;

/**
 * Created by João on 31/10/2014.
 */
public enum ArenaStatus {
    LOBBY(15), // Waiting for new ones to join
    PREGAME(15), // Time before the game actually starts
    INGAME(15), // Maximum game time
    ENDGAME(15); // End of the game time

    /* Maximum Time of the Status (SECONDS) */
    private final int time;

    /* Construtor */
    ArenaStatus(int time) {
        this.time = time;
    }

    /* Get's */
    int getTime() {
        return time;
    }
}
