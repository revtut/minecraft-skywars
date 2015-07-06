package net.revtut.skywars.commands.building;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaStatus;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Fly Command.
 *
 * <P>Command to switch the fly mode of a player.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Fly implements CommandExecutor {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of Fly
     *
     * @param plugin main class
     */
    public Fly(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Controls the execution of the Fly command.
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
            if (!player.hasPermission("rev.fly")) {
                player.sendMessage(Message.getMessage(Message.COMMAND_BUY_VIP, player));
                return true;
            }

            // Player Dat
            PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
            if (playerDat == null)
                return true;

            // Arena
            Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
            if (null == arena)
                return true;

            if (arena.getStatus() != ArenaStatus.LOBBY) {
                player.sendMessage(Message.getMessage(Message.COMMAND_BLOCKED_INGAME, player));
                return true;
            }

            if (args.length > 1 && player.hasPermission("rev.fly.others")) {
                player.sendMessage(Message.getMessage(Message.CORRECT_SINTAX, player) + "/fly <player>");
                return true;
            }

            if (args.length == 0 || !player.hasPermission("rev.fly.others")) {
                player.setAllowFlight(!playerDat.isFlyingMode());
                playerDat.setFlyingMode(!playerDat.isFlyingMode());

                if (player.getAllowFlight())
                    player.sendMessage(Message.getMessage(Message.COMMAND_FLY_ENABLED, player));
                else
                    player.sendMessage(Message.getMessage(Message.COMMAND_FLY_DISABLED, player));
                return true;
            } else {
                Player alvo = Bukkit.getPlayer(args[0]);
                if (alvo == null) {
                    player.sendMessage(Message.getMessage(Message.PLAYER_NOT_ONLINE, player));
                    return true;
                }

                PlayerDat alvoDat = plugin.playerManager.getPlayerDatByUUID(alvo.getUniqueId());
                if(alvoDat == null)
                    return true;

                alvo.setAllowFlight(!alvoDat.isFlyingMode());
                alvoDat.setFlyingMode(!alvoDat.isFlyingMode());

                if (alvo.getAllowFlight()) {
                    alvo.sendMessage(Message.getMessage(Message.COMMAND_FLY_ENABLED, player) + " (" + player.getName() + ")");
                    player.sendMessage(Message.getMessage(Message.COMMAND_FLY_ENABLED, player) + " (" + args[0] + ")");
                } else {
                    alvo.sendMessage(Message.getMessage(Message.COMMAND_FLY_DISABLED, player) + " (" + player.getName() + ")");
                    player.sendMessage(Message.getMessage(Message.COMMAND_FLY_DISABLED, player) + " (" + args[0] + ")");
                }
                return true;
            }
        } else {
            sender.sendMessage("§4Console blocked command!");
            return true;
        }
    }
}
