package net.RevTut.Skywars.libraries.world;

import net.minecraft.server.v1_7_R4.*;
import org.bukkit.generator.ChunkGenerator;
import sun.misc.Unsafe;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by João on 31/10/2014.
 */
public class WorldServerNMS extends WorldServer {
    private Set N;
    private SortedSet M;
    private final UnsafeLock lock = new UnsafeLock(this);

    public WorldServerNMS(MinecraftServer minecraftserver, IDataManager idatamanager, String s, int i,
                          WorldSettings worldsettings, MethodProfiler methodprofiler, org.bukkit.World.Environment env, ChunkGenerator gen) {
        super(minecraftserver, idatamanager, s, i, worldsettings, methodprofiler, env, gen);
        N = (Set) WorldAPI.acquireField(WorldServerNMS.this, "M", WorldServer.class);
        M = (TreeSet) WorldAPI.acquireField(WorldServerNMS.this, "N", WorldServer.class);
    }

    @Override
    public void a(int i, int j, int k, Block block, int l, int i1) {
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

    @Override
    public void b(int i, int j, int k, Block block, int l, int i1) {
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

    public static class UnsafeLock {
        private static final Unsafe UNSAFE = (Unsafe) WorldAPI.acquireField(null, "theUnsafe", Unsafe.class);

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
}