package net.revtut.skywars.commands.appearance;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.libraries.appearance.AppearanceAPI;
import net.revtut.skywars.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Launch Firework Command.
 *
 * <P>Command to launch a firework in the server.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Fireworks implements CommandExecutor {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of Fireworks
     *
     * @param plugin main class
     */
    public Fireworks(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Controls the execution of the Firework command.
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

            if (!player.hasPermission("rev.firework")) {
                player.sendMessage(Message.getMessage(Message.COMMAND_BUY_VIP, player));
                return true;
            }

            AppearanceAPI.launchFirework(player, 1, 0);
            return true;
        } else {
            sender.sendMessage("§4Console blocked command!");
            return true;
        }
    }
}
