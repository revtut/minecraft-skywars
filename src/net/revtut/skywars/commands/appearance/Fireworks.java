package net.revtut.skywars.commands.appearance;

import net.revtut.libraries.appearance.AppearanceAPI;
import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaStatus;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
     * Player firework cooldown
     */
    private final Map<UUID, Long> cooldownFirework = new HashMap<>();

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

            // Cooldown
            if (cooldownFirework.containsKey(player.getUniqueId())) {
                // Tempo passado desde a Ultima Mensagem
                final long diff = (System.currentTimeMillis() - cooldownFirework.get(player.getUniqueId())) / 1000;
                if (diff < 10) {
                    player.sendMessage(Message.getMessage(Message.WAIT_BEFORE_USE_AGAIN, player));
                    return true;
                } else {
                    cooldownFirework.remove(player.getUniqueId());
                }
            }

            // Launch firework
            AppearanceAPI.launchFirework(player, 1, 0);

            // Add to cooldown list
            cooldownFirework.put(player.getUniqueId(), System.currentTimeMillis());

            return true;
        } else {
            sender.sendMessage("§4Console blocked command!");
            return true;
        }
    }
}
