package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaLocation;
import net.RevTut.Skywars.libraries.particles.ParticlesAPI;
import net.RevTut.Skywars.managers.KitManager;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
     * List with players that already respawned
     */
    private final List<UUID> respawnedPlayers = new ArrayList<UUID>();

    /**
     * List with players to be respawned
     */
    private final List<UUID> toBeRespawnedPlayers = new ArrayList<UUID>();

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
     * Check if a player may respawn
     *
     * @param playerDat player to check it can b respawned
     * @param arena arena where player is currently playibg
     * @return true if player may respawn
     */
    public boolean canRespawn(PlayerDat playerDat, Arena arena){
        if(respawnedPlayers.contains(playerDat.getUUID()))
            return false;

        KitManager kitManager = arena.getKitManager();
        if(!kitManager.playerKit.containsKey(playerDat.getUUID()))
            return false;

        Kit kit = kitManager.playerKit.get(playerDat.getUUID());
        if(kit != Kit.HACKER)
            return false;

        toBeRespawnedPlayers.add(playerDat.getUUID());
        return true;
    }

    /**
     * Respawn a player and send back to a random spawn
     *
     * @param player player to be respawned
     * @param arena  arena of the player
     * @return location to send the player
     */
    public Location respawnPlayer(Player player, Arena arena) { // MAKES USE OF PLAYER RESPAWN EVENT
        if (!toBeRespawnedPlayers.contains(player.getUniqueId()))
                return null;
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
        respawnedPlayers.add(player.getUniqueId());
        toBeRespawnedPlayers.remove(player.getUniqueId());
        return spawnLocation;
    }
}
