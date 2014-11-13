package net.RevTut.Skywars.player;

/**
 * Player Status Enum.
 *
 * <P>All the status that a player might be</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public enum PlayerStatus {
    /** Waiting player in the lobby */
    WAITING,
    /** Alive player already ingame */
    ALIVE,
    /** Dead player already ingame */
    DEAD,
    /** Spectating the game */
    SPECTATOR
}
