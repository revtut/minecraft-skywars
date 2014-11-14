package net.RevTut.Skywars.libraries.titles;

import net.RevTut.Skywars.libraries.reflection.ReflectionAPI;
import net.minecraft.util.io.netty.channel.Channel;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector.PacketTitle;

/**
 * Title Library.
 *
 * <P>A library with methods title related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class TitleAPI {

    /**
     * Version of Minecraft which might receive these packets
     */
    public static final int VERSION = 47;

    /**
     * NMS Class
     */
    private static final Class<?> nmsChatSerializer = ReflectionAPI.getNMSClass("ChatSerializer");

    /**
     * Send a title to a player.
     *
     * @param p     player to send the title
     * @param title json title to send
     */
    public static void sendTitle(Player p, String title) {
        if (!(getVersion(p) >= VERSION)) return;
        try {
            final Object handle = ReflectionAPI.getHandle(p);
            final Object connection = ReflectionAPI.getField(handle.getClass(), "playerConnection").get(handle);
            final Object serialized = ReflectionAPI.getMethod(nmsChatSerializer, "a", String.class).invoke(null, title);
            Object packet = PacketTitle.class.getConstructor(PacketTitle.Action.class, ReflectionAPI.getNMSClass("IChatBaseComponent")).newInstance(PacketTitle.Action.TITLE, serialized);
            ReflectionAPI.getMethod(connection.getClass(), "sendPacket").invoke(connection, packet);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a subtitle to a player.
     *
     * @param p        player to send the subtitle
     * @param subtitle json subtitle to send
     */
    public static void sendSubTitle(Player p, String subtitle) {
        if (!(getVersion(p) >= VERSION)) return;
        try {
            final Object handle = ReflectionAPI.getHandle(p);
            final Object connection = ReflectionAPI.getField(handle.getClass(), "playerConnection").get(handle);
            final Object serialized = ReflectionAPI.getMethod(nmsChatSerializer, "a", String.class).invoke(null, subtitle);
            Object packet = PacketTitle.class.getConstructor(PacketTitle.Action.class, ReflectionAPI.getNMSClass("IChatBaseComponent")).newInstance(PacketTitle.Action.SUBTITLE, serialized);
            ReflectionAPI.getMethod(connection.getClass(), "sendPacket").invoke(connection, packet);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the title timings.
     *
     * @param p       player to update the timings
     * @param fadeIn  time the title should take to fade in
     * @param stay    time the title should stay on screen
     * @param fadeOut time the title should take to fade out
     */
    public static void sendTimings(Player p, int fadeIn, int stay, int fadeOut) {
        if (!(getVersion(p) >= VERSION)) return;
        try {
            final Object handle = ReflectionAPI.getHandle(p);
            final Object connection = ReflectionAPI.getField(handle.getClass(), "playerConnection").get(handle);
            Object packet = PacketTitle.class.getConstructor(PacketTitle.Action.class, int.class, int.class, int.class).newInstance(PacketTitle.Action.TIMES, fadeIn, stay, fadeOut);
            ReflectionAPI.getMethod(connection.getClass(), "sendPacket").invoke(connection, packet);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reset the players timing, title, subtitle.
     *
     * @param p player to be reseted
     */
    public static void reset(Player p) {
        if (!(getVersion(p) >= VERSION)) return;
        try {
            final Object handle = ReflectionAPI.getHandle(p);
            final Object connection = ReflectionAPI.getField(handle.getClass(), "playerConnection").get(handle);
            Object packet = PacketTitle.class.getConstructor(PacketTitle.Action.class).newInstance(PacketTitle.Action.RESET);
            ReflectionAPI.getMethod(connection.getClass(), "sendPacket").invoke(connection, packet);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Clear the players title.
     *
     * @param p player to be cleared
     */
    public static void clear(Player p) {
        if (!(getVersion(p) >= VERSION)) return;
        try {
            final Object handle = ReflectionAPI.getHandle(p);
            final Object connection = ReflectionAPI.getField(handle.getClass(), "playerConnection").get(handle);
            Object packet = PacketTitle.class.getConstructor(PacketTitle.Action.class).newInstance(PacketTitle.Action.CLEAR);
            ReflectionAPI.getMethod(connection.getClass(), "sendPacket").invoke(connection, packet);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the version of a player.
     *
     * @param p player to get the Minecraft version
     * @return game version number
     */
    public static int getVersion(Player p) {
        try {
            final Object handle = ReflectionAPI.getHandle(p);
            final Object connection = ReflectionAPI.getField(handle.getClass(), "playerConnection").get(handle);
            final Object network = ReflectionAPI.getField(connection.getClass(), "networkManager").get(connection);
            final Object channel = ReflectionAPI.getField(network.getClass(), "m").get(network);
            final Object version = ReflectionAPI.getMethod(network.getClass(), "getVersion", Channel.class).invoke(network, channel);
            return (Integer) version;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
