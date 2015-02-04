package net.revtut.skywars.commands.teleport;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Teleport To Player Command.
 *
 * <P>Command to teleport to player.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class TeleportTo implements CommandExecutor {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of TeleportTo
     *
     * @param plugin main class
     */
    public TeleportTo(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Controls the execution of the TeleportTo command.
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
            if (!player.hasPermission("rev.teleport")) {
                player.sendMessage(Message.getMessage(Message.PLAYER_BLOCKED_COMMAND, player));
                return true;
            }

            if (args.length < 1 || args.length > 2) {
                player.sendMessage(Message.getMessage(Message.CORRECT_SINTAX, player) + "/teleport <player> [player]");
                return true;
            }

            Player alvo = Bukkit.getPlayer(args[0]);
            if (alvo == null) {
                player.sendMessage(Message.getMessage(Message.PLAYER_NOT_ONLINE, player));
                return true;
            }

            // Check arguments
            if (args.length == 1) {
                player.teleport(alvo);

                player.sendMessage(Message.getMessage(Message.COMMAND_TELEPORT, player) + args[0] + "!");
                return true;
            } else {
                Player segundoAlvo = Bukkit.getPlayer(args[1]);
                if (segundoAlvo == null) {
                    player.sendMessage(Message.getMessage(Message.PLAYER_NOT_ONLINE, player));
                    return true;
                }

                alvo.teleport(segundoAlvo);

                alvo.sendMessage(Message.getMessage(Message.COMMAND_TELEPORT, player) + args[1] + "! (" + player.getName() + ")");
                player.sendMessage(args[0] + " -> " + Message.getMessage(Message.COMMAND_TELEPORT, player) + args[1] + "!");
                return true;
            }
        } else {
            sender.sendMessage("§4Console blocked command!");
            return true;
        }
    }
}
