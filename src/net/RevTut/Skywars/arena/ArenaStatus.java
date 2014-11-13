package net.RevTut.Skywars.arena;

/**
 * Arena Status Enum.
 *
 * <P>All the status that a arena might be</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public enum ArenaStatus {
    /** Waiting for new players to join the game */
    LOBBY(15),
    /** Waiting for game start. No players can join now. */
    PREGAME(15),
    /** Players are already playing the game. */
    INGAME(15),
    /** The game has ended. */
    ENDGAME(15);

    /** Maximum Time of the Status (SECONDS) */
    private final int time;

    /**
     * Constructor of ArenaStatus
     *
     * @param time          time of that status
     */
    ArenaStatus(int time) {
        this.time = time;
    }

    /** Returns the time of that status */
    int getTime() {
        return time;
    }
}
