package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.*;

/**
 * Hacker Kit.
 *
 * <P>Kit Hacker will alow a player to respawn one time if he is killed.</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class Hacker implements Listener {
    /**
     * Main Class
     */
    private final Main plugin;

    /**
     * Constructor of Kit Hacker
     *
     * @param plugin main class
     */
    public Hacker(Main plugin) {
        this.plugin = plugin;
    }

    /** Map with all the items of a player */
    private final Map<UUID, ItemStack[]> playerItems = new HashMap<UUID , ItemStack[]>();

    /** List with players that already respawned */
    private final List<UUID> respawnedPlayers = new ArrayList<UUID>();

    /**
     * Save all the items of a player if the player has not respawned yet
     *
     * @param player player to save inventory
     * @return true if player may respawn
     */
    public boolean saveInventory(Player player){ // MAKES USE OF PLAYER DEATH EVENT
        if(respawnedPlayers.contains(player.getUniqueId()))
            return false;
        // Add content of his inventory to the map
        ItemStack[] content = player.getInventory().getContents();
        playerItems.put(player.getUniqueId(), content);
        return true;
    }

    /**
     * Restore a player's inventory. Before it checks if a player's inventory may be restored
     *
     * @param player player to restore inventory
     * @return true if restored inventory of a player
     */
    public boolean restoreInventory(Player player){ // MAKES USE OF PLAYER RESPAWN EVENT
        if(!playerItems.containsKey(player.getUniqueId()))
            return false;
        ItemStack[] content = playerItems.get(player.getUniqueId());
        player.getInventory().setContents(content);
        // Add to already respawned players
        respawnedPlayers.add(player.getUniqueId());
        return true;
    }
}
