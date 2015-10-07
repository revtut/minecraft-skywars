package net.revtut.skywars;

import net.revtut.libraries.games.arena.Arena;
import net.revtut.libraries.games.player.PlayerState;
import net.revtut.libraries.scoreboard.InfoBoard;
import net.revtut.libraries.scoreboard.label.BlankLabel;
import net.revtut.libraries.scoreboard.label.ScrollingLabel;
import net.revtut.libraries.scoreboard.label.StaticLabel;
import net.revtut.libraries.text.scroller.ColorScroller;
import net.revtut.libraries.text.scroller.TextScroller;
import org.bukkit.ChatColor;

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
        InfoBoard infoBoard = new InfoBoard(5);

        // Title
        infoBoard.setTitle(new ScrollingLabel(new ColorScroller("SKY WARS", 1, ChatColor.AQUA, ChatColor.GOLD)));

        // Body
        infoBoard.addLabel(new BlankLabel(12));
        infoBoard.addLabel(new ScrollingLabel(new TextScroller("§fBecome §6VIP §fat our store! | ", 15), 11));
        infoBoard.addLabel(new BlankLabel(10));
        infoBoard.addLabel(new StaticLabel("§6Session: §f" + arena.getId(), 9));
        infoBoard.addLabel(new StaticLabel("§6Map: §f" + arena.getWorld().getName().split("_")[2], 8));
        infoBoard.addLabel(new BlankLabel(7));
        infoBoard.addLabel(new StaticLabel("§aAlive: §f" + arena.getPlayers(PlayerState.ALIVE).size(), 6));
        infoBoard.addLabel(new StaticLabel("§cDead: §f" + arena.getPlayers(PlayerState.DEAD).size(), 5));
        infoBoard.addLabel(new BlankLabel(4));

        // Footer
        infoBoard.addLabel(new StaticLabel("§8----------------", 3));
        infoBoard.addLabel(new StaticLabel("§fCheck us out", 1));
        infoBoard.addLabel(new StaticLabel("§6www.revtut.net", 0));
        return infoBoard;
    }
}
