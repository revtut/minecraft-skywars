package net.revtut.skywars.listeners.player;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

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
     * Player command cooldown
     */
    private final Map<UUID, Long> cooldownCommand = new HashMap<>();

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

        // Unknown command
        HelpTopic helpTopic = Bukkit.getHelpMap().getHelpTopic(e.getMessage().split(" ")[0]);
        if (helpTopic == null) {
            player.sendMessage(Message.getMessage(Message.PLAYER_UNKNOWN_COMMAND, player));
            e.setCancelled(true);
            return;
        }

        // Add to cooldown list
        if (!player.hasPermission("rev.cooldownbp")) {
            cooldownCommand.put(player.getUniqueId(), System.currentTimeMillis());
        }
    }
}
