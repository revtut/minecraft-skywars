package net.RevTut.Skywars.libraries.world;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

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
     * Load a new world to the server.
     *
     * @param worldName name of the world to load
     * @return true if world was loaded
     */
    public static boolean loadWorld(String worldName) {
        // World Creator
        WorldCreator creator = new WorldCreator(worldName);
        World world = Bukkit.createWorld(creator);
        // Check if it is not Null
        if (world == null)
            return false;
        return true;
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
            player.kickPlayer("World is being deleted... and you were in it!");

        return Bukkit.unloadWorld(world, true);
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
                if (!trgDir.exists()) {
                    trgDir.mkdirs();
                }
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
            System.out.println("Error while trying to copy world folder from " + srcDir.getAbsolutePath() + " to " + trgDir.getAbsolutePath() + ".");
            System.out.println(e.getMessage());
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
            dir.delete();
        } catch (Exception e) {
            System.out.println("Error while trying to delete " + dir.getAbsolutePath() + ".");
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
