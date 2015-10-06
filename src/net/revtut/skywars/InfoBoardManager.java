package net.revtut.skywars;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerState;
import net.revtut.libraries.scoreboard.InfoBoard;
import net.revtut.libraries.scoreboard.label.StaticLabel;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager of the information boards
 */
public class InfoBoardManager {

    /**
     * Map with all the scoreboards
     */
    private Map<Arena, InfoBoard> scoreboards;

    /**
     * Constructor of InfoBoardManager
     */
    public InfoBoardManager() {
        scoreboards = new HashMap<>();
    }

    /**
     * Get the information board of the arena
     * @param arena arena to get the information board
     * @return information board of the arena
     */
    public InfoBoard getInfoBoard(Arena arena) {
        return scoreboards.get(arena);
    }

    /**
     * Set the information board of a arena
     * @param arena arena to be set
     * @param infoBoard information board of the arena
     */
    public void setInfoBoard(Arena arena, InfoBoard infoBoard) {
        scoreboards.put(arena, infoBoard);
    }

    /**
     * Create the information board of a arena
     * @param arena arena to create the information board
     * @return created information board
     */
    public InfoBoard createInfoBoard(Arena arena) {
        InfoBoard infoBoard = new InfoBoard();

        // Title
        infoBoard.setTitle("Sky Wars\n" + arena.getId());

        // Body
        infoBoard.addLabel(new StaticLabel("Map", 1));
        infoBoard.addLabel(new StaticLabel(arena.getWorld().getName().split("_")[2], 10));
        infoBoard.addLabel(new StaticLabel(" ", 9));
        infoBoard.addLabel(new StaticLabel("Alive", 8));
        infoBoard.addLabel(new StaticLabel("" + arena.getPlayers(PlayerState.ALIVE).size(), 7));
        infoBoard.addLabel(new StaticLabel("  ", 6));
        infoBoard.addLabel(new StaticLabel("Spectators", 5));
        infoBoard.addLabel(new StaticLabel(" " + (arena.getPlayers(PlayerState.SPECTATOR).size() + arena.getPlayers(PlayerState.DEAD).size()), 4));
        infoBoard.addLabel(new StaticLabel("   ", 3));

        // Footer
        infoBoard.addLabel(new StaticLabel("----------", 2));
        infoBoard.addLabel(new StaticLabel("See us at", 1));
        infoBoard.addLabel(new StaticLabel("revtut.net", 0));
        return infoBoard;
    }
}
