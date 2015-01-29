package net.revtut.skywars.commands;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaDat;
import net.revtut.skywars.libraries.converters.ConvertersAPI;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Stats Command.
 *
 * <P>Command to get the stats of the player.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Stats implements CommandExecutor {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of Stats
     *
     * @param plugin main class
     */
    public Stats(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Controls the execution of the Stats command.
     *
     * @param sender entity who sent the command
     * @param cmd command sent
     * @param commandLabel label of the command
     * @param args arguments sent
     */
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
            if(playerDat == null)
                return true;

            Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
            if(arena == null)
                return true;

            ArenaDat arenaDat = arena.getArenaDat();
            if(arenaDat == null)
                return true;

            player.sendMessage("§6<====== §3Sky Wars §6======>");
            player.sendMessage("§6Nickname: §7" + player.getName());
            player.sendMessage(Message.getMessage(Message.WINS, player) + ": §7" + playerDat.getWins());
            player.sendMessage(Message.getMessage(Message.LOSSES, player) + ": §7" + playerDat.getLosses());
            player.sendMessage(Message.getMessage(Message.KILLS, player) + ": §7" + playerDat.getKills());
            player.sendMessage(Message.getMessage(Message.DEATHS, player) + ": §7" + playerDat.getDeaths());
            player.sendMessage(Message.getMessage(Message.PLAYTIME, player) + ": §7" + ConvertersAPI.convertSecondsToDHMS(playerDat.getPlayTime()));
            player.sendMessage("§6<=====================>");

            return true;
        } else {
            sender.sendMessage("§4Console blocked command!");
            return true;
        }
    }
}
