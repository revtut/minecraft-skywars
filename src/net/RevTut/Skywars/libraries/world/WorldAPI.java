package net.RevTut.Skywars.libraries.world;

import net.RevTut.Skywars.libraries.reflection.ReflectionAPI;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * World Library.
 *
 * <P>Library with several methods world related to such as loadWorld, unloadWorld, copyDirectory and so on.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class WorldAPI {

    /**
     * Map with the needed classes for working with worlds and NMS/OBC
     */
    public static final Map<String, Class<?>> classes = new HashMap<String, Class<?>>();

    /**
     * Map with the needed methods for working with worlds and NMS/OBC
     */
    public static final HashMap<String, Method> methods = new HashMap<String, Method>();

    /**
     * Map with the needed fields for working with worlds and NMS/OBC
     */
    public static final HashMap<String, Field> fields = new HashMap<String, Field>();

    /**
     * Map with the files regions
     */
    private static HashMap regionfiles;

    /**
     *  Hashmap with the fields of the regions
     */
    private static Field rafField;

    /**
     * Load a new world to the server.
     *
     * @param worldName name of the world to load
     * @return true if world was loaded
     */
    public static boolean loadWorld(String worldName) {
        // World Creator
        WorldCreator creator = new WorldCreator(worldName);
        creator.generateStructures(false);
        World world = creator.createWorld();
        // Check if it is not Null
        return world != null;
    }

    /**
     * Unload a world from the server.
     *
     * @param worldName name of the world to load
     * @return true if world was unloaded
     */
    public static boolean unloadWorld(String worldName) {
        World world = Bukkit.getWorld(worldName);
        if (world == null)
            return false;
        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);

        // Kick remaining players
        for (Player player : world.getPlayers())
            player.kickPlayer("§4A resetar o mundo... nao e suposto estares aqui!");

        // Unload all the chunks
        for(Chunk chunk : world.getLoadedChunks())
            world.unloadChunk(chunk);

        return Bukkit.unloadWorld(world, true);
    }

    /**
     * Unload a world from the server.
     *
     * @param worldName name of the world to load
     * @return true if world was unloaded
     */
    public static boolean unloadWorldNMS(String worldName) {
        // Bind region files
        if(!bindRegionFiles())
            return false;
        // Force unload world
        if(!forceUnloadWorld(worldName))
            return false;
        // Clear references to the world
        if(!clearWorldReference(worldName))
            return false;
        // Unbind region files;
        unbindRegionFiles();

        return true;
    }

    /**
     * Forces the unload of a world from the server.
     *
     * @param worldName name of the world to load
     * @return true if world was unloaded
     */
    public static boolean forceUnloadWorld(String worldName)  {
        World world = Bukkit.getWorld(worldName);
        if (world == null)
            return false;
        world.setAutoSave(false);
        world.setKeepSpawnInMemory(false);

        // Kick remaining players
        for ( Player player : world.getPlayers() )
            player.kickPlayer("§4A resetar o mundo... nao e suposto estares aqui!");

        // Unload all the chunks
        for(Chunk chunk : world.getLoadedChunks())
            world.unloadChunk(chunk);

        // Access fields in NMS
        try
        {
            Field f = fields.get("CraftServer.worlds");
            f.setAccessible(true);
            Map<String, World> worlds = (Map<String, World>)f.get(Bukkit.getServer());
            worlds.remove(world.getName().toLowerCase());
            f.setAccessible(false);
        } catch (IllegalAccessException ex ) {
            return false;
        }

        // Remove world from worlds list
        Object minecraftServer = getMinecraftServer();
        List<Object> worldList;
        try {
            worldList = (List<Object>) fields.get("MinecraftServer.worlds").get(minecraftServer);
            int wid = worldList.indexOf(methods.get("CraftWorld.getHandle()").invoke(world));
            if(wid > -1) {
                worldList.remove(wid);
            }
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        } catch (InvocationTargetException e) {
            return false;
        }
    }

    /**
     * Delete world references from the server
     *
     * @param worldName name of the world
     * @return true if successfull
     */
    public static synchronized boolean clearWorldReference(String worldName) {
        if (regionfiles == null)
            return false;
        if (rafField == null)
            return false;

        ArrayList<Object> removedKeys = new ArrayList<Object>();
        try {
            for (Object o : regionfiles.entrySet()) {
                Map.Entry e = (Map.Entry) o;
                File f = (File) e.getKey();
                if (f.toString().startsWith("." + File.separator + worldName)) {
                    try {
                        RandomAccessFile raf = (RandomAccessFile) rafField.get(e.getValue());
                        raf.close();
                        removedKeys.add(f);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Exception while removing world reference for '" + worldName + "' ReflectionAPI!");
            e.printStackTrace();
            return false;
        }
        for (Object key : removedKeys)
            regionfiles.remove(key);
        return true;
    }

    /**
     * Bind region files and filds
     */
    public static boolean bindRegionFiles() {
        try {
            regionfiles = (HashMap) fields.get("RegionFileCache.regionsByFilename").get(null);
            rafField = fields.get("RegionFile.dataFile");
            return true;
        } catch (Throwable t) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Error binding to region file cache ReflectionAPI!");
            t.printStackTrace();
            return false;
        }
    }

    /**
     * Unbind region files and filds
     */
    public static void unbindRegionFiles() {
        regionfiles = null;
        rafField = null;
    }

    /**
     * Saves needed classes to work with worlds in a map
     *
     * @return true if successfull
     */
    public static boolean getWorldClasses() {
        try {
            // org.bukkit.craftbukkit
            classes.put("CraftServer", ReflectionAPI.getOBCClass("CraftServer"));
            classes.put("CraftWorld", ReflectionAPI.getOBCClass("CraftWorld"));
            classes.put("CraftFallingSand", ReflectionAPI.getOBCClass("entity.CraftFallingSand"));
            // net.minecraft.server
            classes.put("MinecraftServer", ReflectionAPI.getNMSClass("MinecraftServer"));
            classes.put("RegionFile", ReflectionAPI.getNMSClass("RegionFile"));
            classes.put("RegionFileCache", ReflectionAPI.getNMSClass("RegionFileCache"));
            classes.put("WorldData", ReflectionAPI.getNMSClass("WorldData"));
            classes.put("WorldServer", ReflectionAPI.getNMSClass("WorldServer"));
            classes.put("EntityFallingBlock", ReflectionAPI.getNMSClass("EntityFallingBlock"));
            return true;
        } catch (Exception e) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Could not aquire a required class in ReflectionAPI!");
            return false;
        }
    }

    /**
     * Saves needed methods to work with worlds in a map
     *
     * @return true if successfull
     */
    public static boolean getWorldMethods() {
        try {
            // org.bukkit.craftbukkit
            methods.put("CraftWorld.getHandle()", ReflectionAPI.getMethod(classes.get("CraftWorld"), "getHandle"));
            methods.put("CraftServer.getServer()", ReflectionAPI.getMethod(classes.get("CraftServer"), "getServer"));
            methods.put("CraftFallingSand.getHandle()", ReflectionAPI.getMethod(classes.get("CraftFallingSand"), "getHandle"));
            // net.minecraft.server
            return true;
        } catch (Exception e) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Could not aquire a required method in ReflectionAPI!");
            return false;
        }
    }

    /**
     * Saves needed fields to work with worlds in a map
     *
     * @return true if successfull
     */
    public static boolean getWorldFields() {
        try {
            fields.put("RegionFileCache.regionsByFilename", ReflectionAPI.getField(classes.get("RegionFileCache"), "a")); // obfuscated - regionsByFilename in RegionFileCache
            fields.put("RegionFile.dataFile", ReflectionAPI.getField(classes.get("RegionFile"), "c")); // obfuscated - dataFile in RegionFile
            fields.put("WorldData.seed", ReflectionAPI.getField(classes.get("WorldData"), "seed"));
            fields.put("EntityFallingBlock.hurtEntities", ReflectionAPI.getField(classes.get("EntityFallingBlock"), "hurtEntities"));
            fields.put("EntityFallingBlock.fallHurtAmount", ReflectionAPI.getField(classes.get("EntityFallingBlock"), "fallHurtAmount"));
            fields.put("EntityFallingBlock.fallHurtMax", ReflectionAPI.getField(classes.get("EntityFallingBlock"), "fallHurtMax"));
            fields.put("MinecraftServer.worlds", ReflectionAPI.getField(classes.get("MinecraftServer"), "worlds"));
            fields.put("CraftServer.worlds", ReflectionAPI.getField(classes.get("CraftServer"), "worlds"));
            fields.put("RegionFile.dataFile", ReflectionAPI.getField(classes.get("RegionFile"), "c"));
            return true;
        } catch (Exception e) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Could not find a field class in ReflectionAPI!");
            return false;
        }
    }

    /**
     * Get the minecraft server object
     *
     * @return the mnecraft server object
     */
    public static Object getMinecraftServer() {
        try {
            return methods.get("CraftServer.getServer()").invoke(Bukkit.getServer());
        } catch (IllegalArgumentException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    /**
     * Change damage of falling blocks
     *
     * @param block falling block to change damage
     * @param damage amout of damage
     * @param max max damge applied
     * @return true if successfull
     */
    public static boolean setFallingBlockHurtEntities(FallingBlock block, float damage, int max) {
        try {
            Object efb = methods.get("CraftFallingSand.getHandle()").invoke(block);
            Field field = fields.get("EntityFallingBlock.hurtEntities");
            field.setAccessible(true);
            field.setBoolean(efb, true);
            field = fields.get("EntityFallingBlock.fallHurtAmount");
            field.setAccessible(true);
            field.setFloat(efb, damage);
            field = fields.get("EntityFallingBlock.fallHurtMax");
            field.setAccessible(true);
            field.setInt(efb, max);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Copy existing directory to new location.
     *
     * @param srcDir source of the folder to copy
     * @param trgDir target of the folder
     * @return true if successfull
     */
    public static boolean copyDirectory(final File srcDir, final File trgDir) {
        try {
            if (srcDir.isDirectory()) {
                // Check if target folder exists
                if (!trgDir.exists())
                    if(!trgDir.mkdirs())
                        return false;
                // List of files inside source directory
                String[] fList = srcDir.list();
                for (String aFList : fList) {
                    File dest = new File(trgDir, aFList);
                    File source = new File(srcDir, aFList);

                    // Copy that file / directory
                    copyDirectory(source, dest);
                }
            } else {
                // Copy the file
                // Open a file for read and write (copy)
                FileInputStream fInStream = new FileInputStream(srcDir);
                FileOutputStream fOutStream = new FileOutputStream(trgDir);
                // Read 2K at a time from the file
                byte[] buffer = new byte[2048];
                int iBytesReads;
                // In each successful read, write back to the source
                while ((iBytesReads = fInStream.read(buffer)) >= 0) {
                    fOutStream.write(buffer, 0, iBytesReads);
                }
                // Safe exit
                fInStream.close();
                fOutStream.close();
            }
        } catch (Exception e) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Error while trying to copy world folder from " + srcDir.getAbsolutePath() + " to " + trgDir.getAbsolutePath() + ".");
            Logger.getLogger("Minecraft").log(Level.WARNING, e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Delete directory. Sub-files and sub-directories will be deleted to.
     *
     * @param dir folder to remove
     * @return true it successfull when removing directory
     */
    public static boolean removeDirectory(final File dir) {
        try {
            if (dir.isDirectory()) {
                if (dir.listFiles() != null)
                    for (File c : dir.listFiles())
                        removeDirectory(c);
            }
            if(!dir.delete())
                Logger.getLogger("Minecraft").log(Level.WARNING, "Error while trying to delete " + dir.getName() + ".");
        } catch (Exception e) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Error while trying to delete " + dir.getAbsolutePath() + ".");
            Logger.getLogger("Minecraft").log(Level.WARNING, e.getMessage());
            return false;
        }
        return true;
    }
}
