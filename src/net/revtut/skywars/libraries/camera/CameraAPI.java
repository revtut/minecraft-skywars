package net.revtut.skywars.libraries.camera;

import net.minecraft.server.v1_8_R1.*;
import net.revtut.skywars.SkyWars;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Camera Library.
 *
 * <P>A library with methods camera related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class CameraAPI {

    /**
     * Constructor of CameraAPI
     */
    private CameraAPI() {}

    /**
     * Main class
     */
    public static SkyWars plugin = null;

    /**
     * Send a camera to a player.
     *
     * @param player player to send the camera
     * @param alvo camera to be sent
     */
    public static void sendCamera(final Player player, final Player alvo) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Main plugin is null inside CameraAPI!");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                PacketPlayOutCamera camera = new PacketPlayOutCamera((Entity) alvo);
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(camera);
            }
        });
    }

    /**
     * Reset the camera of a player.
     *
     * @param player player to be reseated the camera
     */
    public static void resetCamera(final Player player) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Main plugin is null inside CameraAPI!");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                PacketPlayOutCamera camera = new PacketPlayOutCamera((Entity) player);
                ((CraftPlayer)player).getHandle().playerConnection.sendPacket(camera);
            }
        });
    }
}
