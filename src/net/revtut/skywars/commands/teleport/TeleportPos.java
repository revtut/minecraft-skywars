package net.revtut.skywars.commands.teleport;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.utils.Message;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Teleport To A Location Command.
 *
 * <P>Command to teleport to a location.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class TeleportPos implements CommandExecutor {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of TeleportPos
     *
     * @param plugin main class
     */
    public TeleportPos(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Controls the execution of the TeleportPos command.
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
            if (!player.hasPermission("rev.teleportpos")) {
                player.sendMessage(Message.getMessage(Message.PLAYER_BLOCKED_COMMAND, player));
                return true;
            }

            if (args.length != 3) {
                player.sendMessage(Message.getMessage(Message.CORRECT_SINTAX, player) + "/teleportpos <x> <y> <z>");
                return true;
            }

            try {
                Location localizacao = new Location(player.getWorld(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                player.teleport(localizacao);

                player.sendMessage(Message.getMessage(Message.COMMAND_TELEPORT, player) + args[0] + ", " + args[1] + ", " + args[2] + "!");
            } catch (Exception e) {
                player.sendMessage(Message.getMessage(Message.USE_INTEGERS, player));
            }
            return true;
        } else {
            sender.sendMessage("§4Console blocked command!");
            return true;
        }
    }
}
