package net.revtut.skywars.listeners.arena;

import net.revtut.libraries.minecraft.games.arena.Arena;
import net.revtut.libraries.minecraft.games.arena.ArenaFlag;
import net.revtut.libraries.minecraft.games.arena.session.GameSession;
import net.revtut.libraries.minecraft.games.arena.session.GameState;
import net.revtut.libraries.minecraft.games.arena.types.ArenaSolo;
import net.revtut.libraries.minecraft.games.events.arena.ArenaLoadEvent;
import net.revtut.libraries.minecraft.maths.AlgebraAPI;
import net.revtut.libraries.minecraft.scoreboard.InfoBoard;
import net.revtut.skywars.Configuration;
import net.revtut.skywars.InfoBoardManager;
import net.revtut.skywars.SkyWars;
import net.revtut.skywars.Utils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Arena Load Listener
 */
public class ArenaLoadListener implements Listener {

    /**
     * Controls the arena load event
     * @param event arena load event
     */
    @EventHandler
    public void onArenaLoad(ArenaLoadEvent event) {
        // Check if the arena belongs to this game
        Arena arena = event.getArena();
        if(!SkyWars.getInstance().getGameController().hasArena(arena))
            return;

        if (!(arena instanceof ArenaSolo))
            return;

        ArenaSolo arenaSolo = (ArenaSolo) arena;
        initArena(arenaSolo);
        initWorld(arenaSolo, "Chopper Wars");
        initFlags(arenaSolo);
    }

    /**
     * Initialize a arena
     * @param arena arena to be initialized
     */
    private void initArena(ArenaSolo arena) {
        // Game Session
        SkyWars plugin = SkyWars.getInstance();
        Configuration configuration = plugin.getConfiguration();
        GameSession session = new GameSession(arena, configuration.getMinPlayers(), configuration.getMaxPlayers());

        // Initialize the arena
        arena.initArena(configuration.getLobby(), session);
        session.updateState(GameState.LOBBY, 30);

        // InfoBoard of the arena
        InfoBoardManager infoBoardManager = plugin.getInfoBoardManager();
        InfoBoard infoBoard = infoBoardManager.createInfoBoard(arena);
        infoBoardManager.setInfoBoard(arena, infoBoard);
    }

    /**
     * Initialize the flags of a arena
     * @param arena arena to be initialized
     */
    private void initFlags(ArenaSolo arena) {
        arena.updateFlag(ArenaFlag.BLOCK_BREAK, false);
        arena.updateFlag(ArenaFlag.BLOCK_PLACE, false);
        arena.updateFlag(ArenaFlag.BUCKET_EMPTY, false);
        arena.updateFlag(ArenaFlag.BUCKET_FILL, false);
        arena.updateFlag(ArenaFlag.CHAT, true);
        arena.updateFlag(ArenaFlag.DROP_ITEM, false);
        arena.updateFlag(ArenaFlag.DAMAGE, false);
        arena.updateFlag(ArenaFlag.HUNGER, false);
        arena.updateFlag(ArenaFlag.INVENTORY_CLICK, true);
        arena.updateFlag(ArenaFlag.INTERACT, true);
        arena.updateFlag(ArenaFlag.MOVE, true);
        arena.updateFlag(ArenaFlag.PICKUP_ITEM, false);
        arena.updateFlag(ArenaFlag.WEATHER, false);
    }

    /**
     * Initialize the world of a arena
     * @param arena arena to be initialized
     * @param worldName name of the world to be loaded
     */
    public void initWorld(ArenaSolo arena, String worldName) {
        SkyWars plugin = SkyWars.getInstance();

        World world = plugin.getGameController().loadWorld(plugin.getName() + "_" + arena.getId() + "_", worldName);

        // World arena locations
        File locationFile = new File(world.getWorldFolder() + File.separator + "location.yml");
        FileConfiguration locConfig = YamlConfiguration.loadConfiguration(locationFile);

        // Single locations
        Location spectator = Utils.parseLocation(locConfig, "Spectator", world),
                spectatorDeathMatch = Utils.parseLocation(locConfig, "SpectatorDeathMatch", world),
                dead = Utils.parseLocation(locConfig, "Dead", world),
                deadDeathMatch = Utils.parseLocation(locConfig, "DeadDeathMatch", world);

        // Array locations
        Location corners[] = new Location[] { Utils.parseLocation(locConfig, "Corners.First", world), Utils.parseLocation(locConfig, "Corners.Second", world) },
                cornersDeathMatch[] = new Location[] { Utils.parseLocation(locConfig, "CornersDeathMatch.First", world), Utils.parseLocation(locConfig, "CornersDeathMatch.Second", world) };

        // List locations
        List<Location> spawnLocations = new ArrayList<>();
        Location spawnLocation;
        for (final String spawnNumber : locConfig.getConfigurationSection("Spawns").getKeys(false)) {
            spawnLocation = Utils.parseLocation(locConfig, "Spawns." + spawnNumber, world);
            spawnLocations.add(AlgebraAPI.locationLookAt(spawnLocation, dead));
        }

        List<Location> deathMatchLocations = new ArrayList<>();
        Location deathMatchLocation;
        for (final String deathMatchSpawnNumber : locConfig.getConfigurationSection("DeathMatch").getKeys(false)) {
            deathMatchLocation = Utils.parseLocation(locConfig, "DeathMatch." + deathMatchSpawnNumber, world);
            deathMatchLocations.add(AlgebraAPI.locationLookAt(deathMatchLocation, deadDeathMatch));
        }

        // Initialize the world of the arena
        arena.initWorld(world, spectator, spectatorDeathMatch, corners, cornersDeathMatch, spawnLocations, dead, deadDeathMatch, deathMatchLocations);
    }
}
