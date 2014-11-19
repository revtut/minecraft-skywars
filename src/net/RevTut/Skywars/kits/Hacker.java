package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaLocation;
import net.RevTut.Skywars.libraries.bypasses.BypassesAPI;
import net.RevTut.Skywars.libraries.particles.ParticlesAPI;
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
     * Map with all the inventory items of a player
     */
    private final Map<UUID, ItemStack[]> playerInventoryContent = new HashMap<UUID, ItemStack[]>();

    /**
     * List with players that already respawned
     */
    private final List<UUID> respawnedPlayers = new ArrayList<UUID>();

    /**
     * Black Leather Helmet
     */
    private ItemStack leatherHelmet = new ItemStack(Material.LEATHER_HELMET, 1);

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
    public void kitHacker(Player p) {
        p.getInventory().setHelmet(leatherHelmet);
    }

    /**
     * Save all the items of a player if the player has not respawned yet
     *
     * @param player player to save inventory
     * @param arena player's arena
     * @return true if player may respawn
     */


    /**
     * Restore a player's inventory and send him back to a random spawn. Before it checks if a player's inventory may be restored
     *
     * @param player player to restore inventory
     * @param arena  arena of the player
     * @return location to send the player
     */
    public Location restorePlayer(Player player, Arena arena) { // MAKES USE OF PLAYER RESPAWN EVENT
        if (!playerInventoryContent.containsKey(player.getUniqueId()))
                return null;
        ItemStack[] inventoryContent = playerInventoryContent.get(player.getUniqueId());
        player.getInventory().setContents(inventoryContent);
        // Remove from maps
        playerInventoryContent.remove(player.getUniqueId());
        // Teleport to a random spawn location
        ArenaLocation arenaLocation = arena.getArenaLocation();
        if (arenaLocation == null)
            return null;
        List<Location> spawnLocations = arenaLocation.getSpawnLocations();
        Location spawnLocation = spawnLocations.get(rand.nextInt(spawnLocations.size()));
        if (spawnLocation == null)
            return null;
        // Play particle effects
        ParticlesAPI.helixPosY(spawnLocation);
        // Message
        player.sendMessage("§7|" + "§3Sky Wars" + "§7| §6Uma nova oportunidade de viveres foi-te dada!");
        // Add to already respawned players
        BypassesAPI.respawnBypass(player);
        return spawnLocation;
    }
}
