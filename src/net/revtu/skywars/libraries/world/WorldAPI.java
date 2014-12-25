package net.revtu.skywars.libraries.world;

import net.revtu.skywars.libraries.reflection.ReflectionAPI;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
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
public final class WorldAPI {

    /**
     * Constructor of WorldAPI
     */
    private WorldAPI() {}

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
     * Change damage of falling blocks
     *
     * @param block falling block to change damage
     * @param damage amout of damage
     * @param max max damge applied
     * @return true if successfull
     */
    public static boolean changeFallingBlockDamage(FallingBlock block, float damage, int max) {
        try {
            // Falling block
            Class classzz = ReflectionAPI.getOBCClass("entity.CraftFallingSand");
            Object fallingBlock = ReflectionAPI.getMethod(classzz, "getHandle").invoke(block);

            // Enable falling block damage
            classzz = ReflectionAPI.getNMSClass("EntityFallingBlock");
            Field field = ReflectionAPI.getField(classzz, "hurtEntities");
            field.setAccessible(true);
            field.setBoolean(fallingBlock, true);
            field.setAccessible(false);

            // Set the hurt amount of a falling block
            field = ReflectionAPI.getField(classzz, "fallHurtAmount");
            field.setAccessible(true);
            field.setFloat(fallingBlock, damage);
            field.setAccessible(false);

            // Set the maximum hurt amount of a falling block
            field = ReflectionAPI.getField(classzz, "fallHurtMax");
            field.setAccessible(true);
            field.setInt(fallingBlock, max);
            field.setAccessible(false);

            return true;
        } catch (Exception e) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Error while trying to ser damage of falling blocks in ReflectionAPI!");
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
