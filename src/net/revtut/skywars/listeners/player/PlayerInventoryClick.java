package net.revtut.skywars.listeners.player;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaStatus;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.player.PlayerStatus;
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
