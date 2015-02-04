package net.revtut.skywars.commands.building;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Game Mode Command.
 *
 * <P>Command to change the game mode of a player.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class GameMode implements CommandExecutor {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of GameMode
     *
     * @param plugin main class
     */
    public GameMode(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Controls the execution of the GameMode command.
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
            if (!player.hasPermission("rev.gamemode")) {
                player.sendMessage(Message.getMessage(Message.PLAYER_BLOCKED_COMMAND, player));
                return true;
            }

            if (args.length > 1) {
                player.sendMessage(Message.getMessage(Message.CORRECT_SINTAX, player) + "/gamemode <player>");
                return true;
            }

            if (args.length == 0) {
                if (player.getGameMode() == org.bukkit.GameMode.ADVENTURE)
                    player.setGameMode(org.bukkit.GameMode.SURVIVAL);
                else if (player.getGameMode() == org.bukkit.GameMode.SURVIVAL)
                    player.setGameMode(org.bukkit.GameMode.CREATIVE);
                else if (player.getGameMode() == org.bukkit.GameMode.CREATIVE)
                    player.setGameMode(org.bukkit.GameMode.ADVENTURE);

                player.sendMessage(Message.getMessage(Message.COMMAND_GAMEMODE_CHANGED, player) + player.getGameMode().name());
                return true;
            } else {
                Player alvo = Bukkit.getPlayer(args[0]);
                if (alvo == null) {
                    player.sendMessage(Message.getMessage(Message.PLAYER_NOT_ONLINE, player));
                    return true;
                }

                if (alvo.getGameMode() == org.bukkit.GameMode.ADVENTURE)
                    alvo.setGameMode(org.bukkit.GameMode.SURVIVAL);
                else if (alvo.getGameMode() == org.bukkit.GameMode.SURVIVAL)
                    alvo.setGameMode(org.bukkit.GameMode.CREATIVE);
                else if (alvo.getGameMode() == org.bukkit.GameMode.CREATIVE)
                    alvo.setGameMode(org.bukkit.GameMode.ADVENTURE);

                alvo.sendMessage(Message.getMessage(Message.COMMAND_GAMEMODE_CHANGED, player) + player.getGameMode().name() + " (" + player.getName() + ")");
                player.sendMessage(Message.getMessage(Message.COMMAND_GAMEMODE_CHANGED, player) + player.getGameMode().name() + " (" + args[0] + ")");
                return true;
            }
        } else {
            sender.sendMessage("§4Console blocked command!");
            return true;
        }
    }
}
