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
        infoBoard.setTitle("�bSKY WARS");

        // Body
        infoBoard.addLabel(new StaticLabel("�6Session: " + arena.getId(), 9));
        infoBoard.addLabel(new StaticLabel("�6Map: " + arena.getWorld().getName().split("_")[2], 8));
        infoBoard.addLabel(new StaticLabel("   ", 7));
        infoBoard.addLabel(new StaticLabel("�aAlive: �f" + arena.getPlayers(PlayerState.ALIVE).size(), 6));
        infoBoard.addLabel(new StaticLabel("�cDead: �f" + arena.getPlayers(PlayerState.DEAD).size(), 5));
        infoBoard.addLabel(new StaticLabel("  ", 4));

        // Footer
        infoBoard.addLabel(new StaticLabel("�7----------------", 4));
        infoBoard.addLabel(new StaticLabel(" ", 3));
        infoBoard.addLabel(new StaticLabel("�fCheck us out", 1));
        infoBoard.addLabel(new StaticLabel("�6www.revtut.net", 0));
        return infoBoard;
    }
}
