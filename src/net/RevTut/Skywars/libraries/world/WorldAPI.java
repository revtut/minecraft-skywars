package net.RevTut.Skywars.libraries.world;

import net.minecraft.server.v1_7_R4.*;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginManager;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by João on 31/10/2014.
 */
public class WorldAPI {

    /**
     * Load World ASync
     *
     * @param worldName Name of the world to load
     */
    public static org.bukkit.World loadWorldAsync(String worldName) {
        // World Creator
        WorldCreator creator = new WorldCreator(worldName);

        Validate.notNull(creator, "Creator may not be null");

        // Fields to Get from Main Classes
        CraftServer server = (CraftServer) Bukkit.getServer();
        MinecraftServer console = (MinecraftServer) acquireField(server, "console", CraftServer.class);
        Map<String, World> worlds = (Map<String, org.bukkit.World>) acquireField(server, "worlds", CraftServer.class);
        PluginManager pluginManager = (PluginManager) acquireField(server, "pluginManager", CraftServer.class);

        // World
        String name = creator.name();
        org.bukkit.World world = server.getWorld(name);
        if (world != null)
            return world;

        // Folder of the World
        File folder = new File(server.getWorldContainer(), name);
        if ((folder.exists()) && (!folder.isDirectory()))
            throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");

        // Chunk Generator
        ChunkGenerator generator = creator.generator();
        if (generator == null)
            generator = server.getGenerator(name);

        // Convert Existing World
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

        boolean generateStructures = creator.generateStructures();
        WorldType type = WorldType.getType(creator.type().getName());

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

    /**
     * Get object from class
     *
     * @param owner    Owner object
     * @param field    Declared field to get
     * @param fallback Class of the field
     */
    public static synchronized Object acquireField(Object owner, @Nonnull String field, @Nonnull Class<?> fallback) {
        try {
            Field f = fallback.getDeclaredField(field);

            if (f == null)
                return null;

            f.setAccessible(true);
            return f.get(owner);
        } catch (NoSuchFieldException x) {
            x.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        }
        return null;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gen")) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // UnsafeLock lock = new UnsafeLock(new ASyncWorld());
                    //lock.lock();

                    //  createWorld(WORLD_0);
                    // createWorld(WORLD_1);
                    //  createWorld(WORLD_2);

                    //  lock.unlock();
                    System.out.println("Ola");
                }
            });
            thread.start();

            return true;
        }
        return false;
    }
}
