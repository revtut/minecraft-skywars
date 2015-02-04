package net.revtut.skywars.commands.teleport;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Teleport Here Command.
 *
 * <P>Command to teleport a player to sender's location.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class TeleportHere implements CommandExecutor {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of TeleportHere
     *
     * @param plugin main class
     */
    public TeleportHere(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Controls the execution of the TeleportHere command.
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
            if (!player.hasPermission("rev.teleporthere")) {
                player.sendMessage(Message.getMessage(Message.PLAYER_BLOCKED_COMMAND, player));
                return true;
            }

            if (args.length != 1) {
                player.sendMessage(Message.getMessage(Message.CORRECT_SINTAX, player) + "/teleporthere <player>");
                return true;
            }

            Player alvo = Bukkit.getPlayer(args[0]);
            if (alvo == null) {
                player.sendMessage(Message.getMessage(Message.PLAYER_NOT_ONLINE, player));
                return true;
            }

            alvo.teleport(player);

            alvo.sendMessage(Message.getMessage(Message.COMMAND_TELEPORT, player) + player.getName() + "! (" + player.getName() + ")");
            player.sendMessage(args[0] + " -> " + Message.getMessage(Message.COMMAND_TELEPORT, player) + player.getName() + "!");
            return true;
        } else {
            System.out.println("Comando bloqueado na consola.");
            return true;
        }
    }
}
