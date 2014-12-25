package net.revtu.skywars.listeners.player;

import net.revtu.skywars.SkyWars;
import net.revtu.skywars.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Player Command.
 *
 * <P>Controls the command process event.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerCommand implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerCommand
     *
     * @param plugin main class
     */
    public PlayerCommand(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Array with all the blocked commands
     */
    private String[] blockedCommands = { "/pl", "/plugins", "/about", "/bukkit:", "/ver", "version", "/?", "/op", "/deop", "/server", "/lag", "/stop", "/start", "/restart", "/nocheatplus", "/ncp", "/pex", "/permissionsex"};

    /**
     * Player command cooldown
     */
    private final Map<UUID, Long> cooldownCommand = new HashMap<UUID, Long>();

    /**
     * Control when a player try to execute a command. Check if it is invalid
     * or if it is blocked.
     *
     * @param e player command pre-process event event
     * @see PlayerCommandPreprocessEvent
     */
    @EventHandler
    public void onPlayerCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();

        // Cooldown
        if (cooldownCommand.containsKey(player.getUniqueId())) {
            // Tempo passado desde a Ultima Mensagem
            final long diff = (System.currentTimeMillis() - cooldownCommand.get(player.getUniqueId())) / 1000;
            if (diff < 3) {
                player.sendMessage(Message.getMessage(Message.PLAYER_COOLDOWN_COMMANDS, player));
                e.setCancelled(true);
                return;
            } else {
                cooldownCommand.remove(player.getUniqueId());
            }
        }

        // Blocked commands
        String message = e.getMessage();
        for(String blockedCommand : blockedCommands) {
            if(message.contains(blockedCommand)) {
                e.setCancelled(true);
                player.sendMessage(Message.getMessage(Message.PLAYER_BLOCKED_COMMAND, player));
                return;
            }
        }

        // Unknown command
        Command comando = Bukkit.getServer().getPluginCommand(e.getMessage().split(" ")[0]);
        if (comando == null) {
            player.sendMessage(Message.getMessage(Message.PLAYER_UNKNOWN_COMMAND, player));
            e.setCancelled(true);
            return;
        }

        // Permission for the command
        if (!comando.testPermissionSilent(player)) {
            player.sendMessage(Message.getMessage(Message.PLAYER_BLOCKED_COMMAND, player));
            e.setCancelled(true);
            return;
        }

        // Add to cooldown list
        if (!player.hasPermission("rev.cooldownbp")) {
            cooldownCommand.put(player.getUniqueId(), System.currentTimeMillis());
        }
    }
}
