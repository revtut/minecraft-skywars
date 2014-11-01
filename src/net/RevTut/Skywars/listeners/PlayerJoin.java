package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.libraries.nametag.NameTagAPI;
import net.RevTut.Skywars.libraries.tab.TabAPI;
import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.utils.ScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

/**
 * Created by waxcoder on 31-10-2014.
 */

public class PlayerJoin implements Listener {

    public Main plugin;

    public PlayerJoin(final Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();

        // MySQL Tasks
        final UUID uuid = p.getUniqueId();
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                // PlayerDat
                plugin.mysql.createPlayerDat(uuid);
            }
        });
        // Add to Arena
        PlayerDat playerDat = PlayerDat.getPlayerDatByUUID(uuid);
        if (playerDat == null) {
            /** Send him to Hub. Error in playerDat */
            return;
        }
        if (!Arena.addPlayer(playerDat)) {
            /** Send him to Hub. No arena available */
            return;
        }
        Arena arena = Arena.getArenaByPlayer(playerDat);
        if (arena == null) {
            /** Send him to Hub. Error in arena */
            return;
        }
        // New Arena if Needed
        if (Arena.getNumberAvailableArenas() <= 1) {
            Arena.createNewArena();
        }
        // Title
        TitleAPI.sendTimings(p, plugin.fadeIn, plugin.timeOnScreen, plugin.fadeOut);
        TitleAPI.sendTitle(p, plugin.titleMessage.replace("%gamenumber%", arena.getArenaDat().getGameNumber()));
        TitleAPI.sendSubTitle(p, plugin.subTitleMessage);
        // Tab List
        TabAPI.setTab(p, plugin.tabTitle, plugin.tabFooter);
        // ScoreBoard
        ScoreBoard.showScoreBoard(p);
        // NameTag
        Scoreboard board = ScoreBoard.getScoreBoardByPlayer(p.getUniqueId());
        if (board != null)
            NameTagAPI.setNameTag(board, p, true);
    }
}
