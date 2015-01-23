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
 * Game Info Command.
 *
 * <P>Command to get the info about a game.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Info implements CommandExecutor {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of Spawn
     *
     * @param plugin main class
     */
    public Info(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Controls the execution of the Spawn command.
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
            player.sendMessage("§6Game ID: §7" + arenaDat.getGameNumber());
            player.sendMessage(Message.getMessage(Message.ALIVE, player) + " §7" + arena.getAlivePlayers().size());
            player.sendMessage(Message.getMessage(Message.DEAD, player) + " §7" + arena.getDeadPlayers().size());
            player.sendMessage(Message.getMessage(Message.REMAINING_TIME, player) + " §7" + ConvertersAPI.convertSecondsToMS(arena.getRemainingTime()));
            player.sendMessage("§6<==========================>");

            return true;
        } else {
            sender.sendMessage("§4Console blocked command!");
            return true;
        }
    }
}
