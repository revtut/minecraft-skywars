package net.RevTut.Skywars.world;

/**
 * Created by João on 31/10/2014.
 */

import net.minecraft.server.v1_7_R4.*;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.util.Map;

public class ASyncWorld {

    // CB code
    public static org.bukkit.World createWorld(WorldCreator creator) {
        Validate.notNull(creator, "Creator may not be null");

        CraftServer server = (CraftServer) Bukkit.getServer();
        MinecraftServer console = (MinecraftServer) ModifiedWorldServerCB.acquireField(server, "console", CraftServer.class);
        Map<String, org.bukkit.World> worlds = (Map<String, org.bukkit.World>) ModifiedWorldServerCB.acquireField(server, "worlds", CraftServer.class);
        PluginManager pluginManager = (PluginManager) ModifiedWorldServerCB.acquireField(server, "pluginManager", CraftServer.class);

        String name = creator.name();
        ChunkGenerator generator = creator.generator();
        File folder = new File(server.getWorldContainer(), name);
        org.bukkit.World world = server.getWorld(name);
        WorldType type = WorldType.getType(creator.type().getName());
        boolean generateStructures = creator.generateStructures();

        if (world != null)
            return world;

        if ((folder.exists()) && (!folder.isDirectory()))
            throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");

        if (generator == null)
            generator = server.getGenerator(name);

        Convertable converter = new WorldLoaderServer(server.getWorldContainer());
        if (converter.isConvertable(name)) {
            System.out.println("Converting world '" + name + "'");
            converter.convert(name, new ConvertProgressUpdater(console));
        }

        int dimension = CraftWorld.CUSTOM_DIMENSION_OFFSET + console.worlds.size();
        boolean used = false;
        do {
            for (WorldServer server1 : console.worlds) {
                used = server1.dimension == dimension;
                if (used) {
                    dimension++;
                    break;
                }
            }
        } while (used);
        boolean hardcore = false;

        WorldServer internal = new ModifiedWorldServerCB(
                console,
                new ServerNBTManager(server.getWorldContainer(), name, true),
                name, dimension,
                new WorldSettings(creator.seed(), EnumGamemode.getById(server.getDefaultGameMode().getValue()), generateStructures,
                        hardcore, type), console.methodProfiler, creator.environment(), generator
        );

        if (!(worlds.containsKey(name.toLowerCase())))
            return null;

        internal.scoreboard = server.getScoreboardManager().getMainScoreboard().getHandle();

        internal.tracker = new EntityTracker(internal);
        internal.addIWorldAccess(new WorldManager(console, internal));
        internal.difficulty = EnumDifficulty.EASY;
        internal.setSpawnFlags(true, true);
        console.worlds.add(internal);

        if (generator != null)
            internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));

        pluginManager.callEvent(new WorldInitEvent(internal.getWorld()));
        System.out.print("Preparing start region for level " + (console.worlds.size() - 1) + " (Seed: " + internal.getSeed() + ")");

        if (internal.getWorld().getKeepSpawnInMemory()) {
            short short1 = 196;
            long i = System.currentTimeMillis();
            for (int j = -short1; j <= short1; j += 16) {
                for (int k = -short1; k <= short1; k += 16) {
                    long l = System.currentTimeMillis();

                    if (l < i) {
                        i = l;
                    }

                    if (l > i + 1000L) {
                        int i1 = (short1 * 2 + 1) * (short1 * 2 + 1);
                        int j1 = (j + short1) * (short1 * 2 + 1) + k + 1;

                        System.out.println("Preparing spawn area for " + name + ", " + (j1 * 100 / i1) + "%");
                        i = l;
                    }

                    ChunkCoordinates chunkcoordinates = internal.getSpawn();
                    internal.chunkProviderServer.getChunkAt(chunkcoordinates.x + j >> 4, chunkcoordinates.z + k >> 4);
                }
            }
        }
        pluginManager.callEvent(new WorldLoadEvent(internal.getWorld()));

        return internal.getWorld();
    }

    // NMS code
}