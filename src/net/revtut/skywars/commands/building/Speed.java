package net.revtut.skywars.commands.building;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Speed Command.
 *
 * <P>Command to change the ingame speed of a player.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Speed implements CommandExecutor {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of Speed
     *
     * @param plugin main class
     */
    public Speed(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Controls the execution of the Speed command.
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
            if (!player.hasPermission("rev.speed")) {
                player.sendMessage(Message.getMessage(Message.PLAYER_BLOCKED_COMMAND, player));
                return true;
            }

            if (args.length > 2) {
                player.sendMessage(Message.getMessage(Message.CORRECT_SINTAX, player) + "/speed <speed> [player]");
                return true;
            }

            if (args.length == 1) {
                try {
                    if (player.isFlying())
                        player.setFlySpeed(Float.parseFloat(args[0]) / 10);
                    else
                        player.setWalkSpeed(Float.parseFloat(args[0]) / 10);

                    player.sendMessage(Message.getMessage(Message.COMMAND_SPEED_CHANGED, player) + args[0]);
                } catch (Exception e) {
                    player.sendMessage(Message.getMessage(Message.USE_INTEGERS, player));
                }
                return true;
            } else if(args.length == 2) {
                Player alvo = Bukkit.getPlayer(args[1]);
                if (alvo == null) {
                    player.sendMessage(Message.getMessage(Message.PLAYER_NOT_ONLINE, player));
                    return true;
                }

                try {
                    if (alvo.isFlying())
                        alvo.setFlySpeed(Float.parseFloat(args[0]) / 10);
                    else
                        alvo.setWalkSpeed(Float.parseFloat(args[0]) / 10);

                    alvo.sendMessage(Message.getMessage(Message.COMMAND_SPEED_CHANGED, player) + args[0] + " (" + player.getName() + ")");
                    player.sendMessage(Message.getMessage(Message.COMMAND_SPEED_CHANGED, player) + args[0] + " (" + args[1] + ")");
                } catch (Exception e) {
                    player.sendMessage(Message.getMessage(Message.USE_INTEGERS, player));
                }
                return true;
            } else {
                player.sendMessage(Message.getMessage(Message.CORRECT_SINTAX, player) + "/speed <speed> [player]");
                return true;
            }
        } else {
            sender.sendMessage("§4Console blocked command!");
            return true;
        }
    }
}
