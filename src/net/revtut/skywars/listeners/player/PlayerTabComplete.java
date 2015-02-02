package net.revtut.skywars.listeners.player;

import net.revtut.skywars.SkyWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

/**
 * Player Tab Complete.
 *
 * <P>Controls the player tab complete event.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerTabComplete implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerFood
     *
     * @param plugin main class
     */
    public PlayerTabComplete(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Array with all the tab blocked commands
     */
    private final String[] blockedCommands = { "/pl", "/plugins", "/about", "/bukkit:", "/ver", "version", "/?", "/op", "/deop", "/server", "/lag", "/stop", "/start", "/restart", "/nocheatplus", "/ncp"};

    /**
     * Takes care of a the player tab complete event
     *
     * @param e player chat tab complete event
     * @see org.bukkit.event.player.PlayerChatTabCompleteEvent
     */
    @EventHandler
    public void onTabComplete(PlayerChatTabCompleteEvent e) {
        // Blocked commands
        String message = e.getChatMessage();
        for(String blockedCommand : blockedCommands) {
            if(message.contains(blockedCommand)) {
                e.getTabCompletions().clear();
                return;
            }
        }
    }

}
