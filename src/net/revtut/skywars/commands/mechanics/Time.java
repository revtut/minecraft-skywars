package net.revtut.skywars.commands.mechanics;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.utils.Message;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Time Command.
 *
 * <P>Command to change the time and weather in a world.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Time implements CommandExecutor {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of Time
     *
     * @param plugin main class
     */
    public Time(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Controls the execution of the Time command.
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
            if (!player.hasPermission("rev.time")) {
                player.sendMessage(Message.getMessage(Message.PLAYER_BLOCKED_COMMAND, player));
                return true;
            }

            if (args.length != 2) {
                player.sendMessage(Message.getMessage(Message.CORRECT_SINTAX, player) + "/weather <sun/rain/storm> <day/night/sunrise/sunset>");
                return true;
            }

            // Weather
            final World world = player.getWorld();
            if (args[0].equalsIgnoreCase("rain")) {
                world.setStorm(true);
                world.setThundering(false);
                world.setWeatherDuration(1000000);

                player.sendMessage(Message.getMessage(Message.COMMAND_TIME_CHANGED, player) + " rain.");
            } else if (args[0].equalsIgnoreCase("sun")) {
                world.setStorm(false);
                world.setThundering(false);
                world.setWeatherDuration(1000000);

                player.sendMessage(Message.getMessage(Message.COMMAND_TIME_CHANGED, player) + " sun.");
            } else if (args[0].equalsIgnoreCase("storm")) {
                world.setStorm(true);
                world.setThundering(true);
                world.setWeatherDuration(1000000);

                player.sendMessage(Message.getMessage(Message.COMMAND_TIME_CHANGED, player) + " storm.");
            }

            // Time
            if (args[1].equalsIgnoreCase("day")) {
                world.setTime(6000);

                player.sendMessage(Message.getMessage(Message.COMMAND_TIME_CHANGED, player) + world.getTime() + " ticks.");
            } else if (args[1].equalsIgnoreCase("night")) {
                world.setTime(14000);

                player.sendMessage(Message.getMessage(Message.COMMAND_TIME_CHANGED, player) + world.getTime() + " ticks.");
            } else if (args[1].equalsIgnoreCase("sunrise")) {
                world.setTime(0);

                player.sendMessage(Message.getMessage(Message.COMMAND_TIME_CHANGED, player) + world.getTime() + " ticks.");
            } else if (args[1].equalsIgnoreCase("sunset")) {
                world.setTime(12000);

                player.sendMessage(Message.getMessage(Message.COMMAND_TIME_CHANGED, player) + world.getTime() + " ticks.");
            }
            return true;
        } else {
            sender.sendMessage("§4Console blocked command!");
            return true;
        }
    }
}
