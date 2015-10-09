package net.revtut.skywars;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.player.PlayerState;
import net.revtut.libraries.minecraft.scoreboard.InfoBoard;
import net.revtut.libraries.minecraft.scoreboard.label.BlankLabel;
import net.revtut.libraries.minecraft.scoreboard.label.ScrollingLabel;
import net.revtut.libraries.minecraft.scoreboard.label.StaticLabel;
import net.revtut.libraries.minecraft.text.scroller.ColorScroller;
import net.revtut.libraries.minecraft.text.scroller.TextScroller;
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
        infoBoard.setTitle(new ScrollingLabel("title", new ColorScroller("SKY WARS", 1, ChatColor.AQUA, ChatColor.YELLOW, ChatColor.GOLD), 0));

        // Body
        infoBoard.addLabel(new BlankLabel(11));
        infoBoard.addLabel(new ScrollingLabel("vip", new TextScroller("§fBecome §6VIP §fat our store! | ", 15), 10));
        infoBoard.addLabel(new BlankLabel(9));
        infoBoard.addLabel(new StaticLabel("session", "§6Session: §f" + arena.getId(), 8));
        infoBoard.addLabel(new StaticLabel("map", "§6Map: §fVoting!", 7));
        infoBoard.addLabel(new BlankLabel(6));
        infoBoard.addLabel(new StaticLabel("alive", "§aAlive: §f" + arena.getPlayers(PlayerState.ALIVE).size(), 5));
        infoBoard.addLabel(new StaticLabel("dead", "§cDead: §f" + arena.getPlayers(PlayerState.DEAD).size(), 4));
        infoBoard.addLabel(new BlankLabel(3));

        // Footer
        infoBoard.addLabel(new StaticLabel("separator", "§8----------------", 2));
        infoBoard.addLabel(new StaticLabel("check_us", "§fCheck us out", 1));
        infoBoard.addLabel(new StaticLabel("website", "§6www.revtut.net", 0));
        return infoBoard;
    }
}
