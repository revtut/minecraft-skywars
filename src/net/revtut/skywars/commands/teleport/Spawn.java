package net.revtut.skywars.commands.teleport;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Spawn Command.
 *
 * <P>Command to teleport back to the server spawn.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Spawn implements CommandExecutor {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of Spawn
     *
     * @param plugin main class
     */
    public Spawn(final SkyWars plugin) {
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
            player.sendMessage(Message.getMessage(Message.PLAYER_BLOCKED_COMMAND, player));
            return true;
        } else {
            sender.sendMessage("§4Console blocked command!");
            return true;
        }
    }
}
