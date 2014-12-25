package net.RevTut.skywar.listeners.player;

import net.RevTut.skywar.SkyWars;
import net.RevTut.skywar.player.PlayerStatus;
import net.RevTut.skywar.arena.Arena;
import net.RevTut.skywar.arena.ArenaStatus;
import net.RevTut.skywar.player.PlayerDat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Player Inventory Click.
 *
 * <P>Controls when a player clicks in his inventory.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerInventoryClick implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerInventoryClick
     *
     * @param plugin main class
     */
    public PlayerInventoryClick(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of what to do when player clicks in his inventory
     *
     * @param e inventory click event
     * @see InventoryClickEvent
     */
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        // Player Dat
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if (playerDat == null)
            return;
        // Arena
        Arena arena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (null == arena)
            return;

        // Kit Menu
        arena.getKitManager().setKit(playerDat, e.getInventory(), e.getSlot());

        // Compass Menu
        arena.getKitManager().compassTeleport(playerDat, arena, e.getInventory(), e.getSlot());

        // Check status
        if (arena.getStatus() != ArenaStatus.INGAME)
            e.setCancelled(true);
        if (playerDat.getStatus() != PlayerStatus.ALIVE)
            e.setCancelled(true);
    }
}
