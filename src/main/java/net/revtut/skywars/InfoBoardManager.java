package net.revtut.skywars;


import net.revtut.libraries.minecraft.bukkit.games.arena.Arena;
import net.revtut.libraries.minecraft.bukkit.games.player.PlayerState;
import net.revtut.libraries.minecraft.bukkit.scoreboard.InfoBoard;
import net.revtut.libraries.minecraft.bukkit.scoreboard.InfoBoardLabel;
import net.revtut.libraries.minecraft.bukkit.scoreboard.label.BlankLabel;
import net.revtut.libraries.minecraft.bukkit.scoreboard.label.ScrollingLabel;
import net.revtut.libraries.minecraft.bukkit.scoreboard.label.StaticLabel;
import net.revtut.libraries.minecraft.common.animation.text.ColorScroller;
import net.revtut.libraries.minecraft.common.animation.text.TextScroller;

import java.util.HashMap;
import java.util.Map;

/**
 * Manager of the information boards
 */
public class InfoBoardManager {

    /**
     * Map with all the scoreboards
     */
    private final Map<Arena, InfoBoard> scoreboards;

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
    public InfoBoard getInfoBoard(final Arena arena) {
        return scoreboards.get(arena);
    }

    /**
     * Set the information board of a arena
     * @param arena arena to be set
     * @param infoBoard information board of the arena
     */
    public void setInfoBoard(final Arena arena, final InfoBoard infoBoard) {
        scoreboards.put(arena, infoBoard);
    }

    /**
     * Create the information board of a arena
     * @param arena arena to create the information board
     * @return created information board
     */
    public InfoBoard createInfoBoard(final Arena arena) {
        final InfoBoard infoBoard = new InfoBoard(5);

        // Title
        infoBoard.setTitle(new ScrollingLabel("title", new ColorScroller("SKY WARS", 1, "§b", "§e", "§6"), 0));

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

    /**
     * Update the map inside the information board
     * @param arena arena to be updated
     * @param map map name of the arena
     */
    public void updateMap(final Arena arena, final String map) {
        final InfoBoard infoBoard = getInfoBoard(arena);
        if(infoBoard == null)
            return;
        final InfoBoardLabel mapLabel = infoBoard.getLabel("map");
        if(mapLabel == null)
            return;

        mapLabel.setText("§6Map: §f" + map);
        infoBoard.updateLabel(mapLabel);
    }

    /**
     * Update alive players inside the information board
     * @param arena arena to be updated
     * @param numberPlayers number of players alive on the arena
     */
    public void updateAlive(final Arena arena, final int numberPlayers) {
        final InfoBoard infoBoard = getInfoBoard(arena);
        if(infoBoard == null)
            return;
        final InfoBoardLabel aliveLabel = infoBoard.getLabel("alive");
        if(aliveLabel == null)
            return;

        aliveLabel.setText("§aAlive: §f" + numberPlayers);
        infoBoard.updateLabel(aliveLabel);
    }

    /**
     * Update dead players inside the information board
     * @param arena arena to be updated
     * @param numberPlayers number of players dead on the arena
     */
    public void updateDead(final Arena arena, final int numberPlayers) {
        final InfoBoard infoBoard = getInfoBoard(arena);
        if(infoBoard == null)
            return;
        final InfoBoardLabel deadLabel = infoBoard.getLabel("dead");
        if(deadLabel == null)
            return;

        deadLabel.setText("§cDead: §f" + numberPlayers);
        infoBoard.updateLabel(deadLabel);
    }
}