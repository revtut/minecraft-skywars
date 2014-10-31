package net.RevTut.Skywars.world;

/**
 * Created by João on 31/10/2014.
 */
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
import sun.misc.Unsafe;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class AsyncWorldGen {
    private static class UnsafeLock {
        private static final Unsafe UNSAFE = (Unsafe) acquireField(null, "theUnsafe", Unsafe.class);

        private final Object lock;
        private volatile boolean locked = false;

        public UnsafeLock(Object lock) {
            this.lock = lock;
        }

        public void lock() {
            if (locked) return;
            UNSAFE.monitorEnter(lock);
        }

        public void unlock() {
            if (!locked) return;
            UNSAFE.monitorExit(lock);
        }
    }

    // NMS code
    private static class ModifiedWorldServerCB extends WorldServer {
        private Set N;
        private SortedSet M;
        private final UnsafeLock lock = new UnsafeLock(this);

        public ModifiedWorldServerCB(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i,
                                     WorldSettings worldsettings, MethodProfiler methodprofiler, World.Environment env, ChunkGenerator gen) {
            super(minecraftserver, idatamanager, s, i, worldsettings, methodprofiler, env, gen);
            N = (Set) acquireField(ModifiedWorldServerCB.this, "M", WorldServer.class);
            M = (TreeSet) acquireField(ModifiedWorldServerCB.this, "N", WorldServer.class);
        }

        @Override public void a(int i, int j, int k, Block block, int l, int i1) {
            lock.lock();

            NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, block);
            byte b0 = 0;

            if (this.d && block.getMaterial() != Material.AIR) {
                if (block.L()) {
                    b0 = 8;
                    if (this.b(nextticklistentry.a - b0, nextticklistentry.b - b0, nextticklistentry.c - b0, nextticklistentry.a + b0, nextticklistentry.b + b0, nextticklistentry.c + b0)) {
                        Block block1 = this.getType(nextticklistentry.a, nextticklistentry.b, nextticklistentry.c);

                        if (block1.getMaterial() != Material.AIR && block1 == nextticklistentry.a())
                            block1.a(this, nextticklistentry.a, nextticklistentry.b, nextticklistentry.c, this.random);
                    }

                    return;
                }

                l = 1;
            }

            if (this.b(i - b0, j - b0, k - b0, i + b0, j + b0, k + b0)) {
                if (block.getMaterial() != Material.AIR) {
                    nextticklistentry.a((long) l + this.worldData.getTime());
                    nextticklistentry.a(i1);
                }

                if (!this.M.contains(nextticklistentry)) {
                    this.M.add(nextticklistentry);
                    this.N.add(nextticklistentry);
                }
            }

            lock.unlock();
        }

        @Override public void b(int i, int j, int k, Block block, int l, int i1) {
            lock.lock();

            NextTickListEntry nextticklistentry = new NextTickListEntry(i, j, k, block);

            nextticklistentry.a(i1);
            if (block.getMaterial() != Material.AIR)
                nextticklistentry.a((long) l + this.worldData.getTime());

            if (!this.M.contains(nextticklistentry)) {
                this.M.add(nextticklistentry);
                this.N.add(nextticklistentry);
            }

            lock.unlock();
        }
    }

    private static final WorldCreator WORLD_0 = new WorldCreator("world0");
    private static final WorldCreator WORLD_1 = new WorldCreator("world1");
    private static final WorldCreator WORLD_2 = new WorldCreator("world2");

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gen")) {
            Thread thread = new Thread(new Runnable() {
                @Override public void run() {
                    UnsafeLock lock = new UnsafeLock(AsyncWorldGen.this);
                    lock.lock();

                    createWorld(WORLD_0);
                    createWorld(WORLD_1);
                    createWorld(WORLD_2);

                    lock.unlock();
                }
            });
            thread.start();

            return true;
        }
        return false;
    }

    private static synchronized Object acquireField(Object owner, @Nonnull String field, @Nonnull Class<?> fallback) {
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

    // CB code
    public static org.bukkit.World createWorld(WorldCreator creator) {
        Validate.notNull(creator, "Creator may not be null");

        CraftServer server = (CraftServer) Bukkit.getServer();
        MinecraftServer console = (MinecraftServer) acquireField(server, "console", CraftServer.class);
        Map<String, org.bukkit.World> worlds = (Map<String, org.bukkit.World>) acquireField(server, "worlds", CraftServer.class);
        PluginManager pluginManager = (PluginManager) acquireField(server, "pluginManager", CraftServer.class);

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
        } while(used);
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
}