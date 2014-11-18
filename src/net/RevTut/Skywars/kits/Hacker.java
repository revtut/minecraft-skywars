package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaLocation;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.*;

/**
 * Hacker Kit.
 *
 * <P>Kit Hacker will alow a player to respawn one time if he is killed.</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class Hacker {

    /**
     * Random Class
     */
    private final Random rand = new Random();

    /**
     * Map with all the items of a player
     */
    private final Map<UUID, ItemStack[]> playerItems = new HashMap<UUID, ItemStack[]>();

    /**
     * List with players that already respawned
     */
    private final List<UUID> respawnedPlayers = new ArrayList<UUID>();

    /**
     * Black Leather Helmet
     */
    private static ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET, 1);

    {
        // Set helmet's color
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) leatherHelmet.getItemMeta();
        leatherArmorMeta.setColor(Color.BLACK);
        leatherHelmet.setItemMeta(leatherArmorMeta);
    }

    /**
     * Give kit hacker to a player
     *
     * @param p player to give the kit
     */
    public static void kitHacker(Player p) {
        p.getInventory().setHelmet(leatherHelmet);
    }

    /**
     * Save all the items of a player if the player has not respawned yet
     *
     * @param player player to save inventory
     * @return true if player may respawn
     */
    public boolean saveInventory(Player player) { // MAKES USE OF PLAYER DEATH EVENT
        if (respawnedPlayers.contains(player.getUniqueId()))
            return false;
        // Add content of his inventory to the map
        ItemStack[] content = player.getInventory().getContents();
        playerItems.put(player.getUniqueId(), content);
        return true;
    }

    /**
     * Restore a player's inventory and send him back to a random spawn. Before it checks if a player's inventory may be restored
     *
     * @param player player to restore inventory
     * @param arena  arena of the player
     * @return true if restored a player
     */
    public boolean restorePlayer(Player player, Arena arena) { // MAKES USE OF PLAYER RESPAWN EVENT
        if (!playerItems.containsKey(player.getUniqueId()))
            return false;
        ItemStack[] content = playerItems.get(player.getUniqueId());
        player.getInventory().setContents(content);
        // Teleport to a random spawn location
        ArenaLocation arenaLocation = arena.getArenaLocation();
        if (arenaLocation == null)
            return false;
        List<Location> spawnLocations = arenaLocation.getSpawnLocations();
        Location spawnLocation = spawnLocations.get(rand.nextInt(spawnLocations.size()));
        if (spawnLocation == null)
            return false;
        player.teleport(spawnLocation);
        // Message
        player.sendMessage("§7|" + "§3Sky Wars" + "§7| §6Uma nova oportunidade de viveres foi-te dada!");
        // Add to already respawned players
        respawnedPlayers.add(player.getUniqueId());
        return true;
    }
}
