package net.revtu.skywars.libraries.titles;

import net.revtu.skywars.SkyWars;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Title Library.
 *
 * <P>A library with methods title related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class TitleAPI {

    /**
     * Constructor of TitleAPI
     */
    private TitleAPI() {}
    
    /**
     * Main class
     */
    public static SkyWars plugin = null;

    /**
     * Version of Minecraft which might receive these packets
     */
    private static final int VERSION = 47;

    /**
     * Send a title to a player.
     *
     * @param p     player to send the title
     * @param title json title to send
     */
    public static void sendTitle(final Player p, final String title) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Main plugin is null inside TitleAPI!");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (VERSION != VERSION)
                    return;
                IChatBaseComponent titleSerializer = ChatSerializer.a(title);
                PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.TITLE, titleSerializer);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
        });
    }

    /**
     * Send a subtitle to a player.
     *
     * @param p        player to send the subtitle
     * @param subtitle json subtitle to send
     */
    public static void sendSubTitle(final Player p, final String subtitle) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Main plugin is null inside TitleAPI!");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (VERSION != VERSION)
                    return;
                IChatBaseComponent subTitleSerializer = ChatSerializer.a(subtitle);
                PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subTitleSerializer);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
        });
    }

    /**
     * Set the title times.
     *
     * @param p       player to update the times
     * @param fadeIn  time the title should take to fade in
     * @param stay    time the title should stay on screen
     * @param fadeOut time the title should take to fade out
     */
    public static void sendTimes(final Player p, final int fadeIn, final int stay, final int fadeOut) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Main plugin is null inside TitleAPI!");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (VERSION != VERSION)
                    return;
                PacketPlayOutTitle packet = new PacketPlayOutTitle(fadeIn, stay, fadeOut);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
        });
    }

    /**
     * Reset the players timing, title, subtitle.
     *
     * @param p player to be reseted
     */
    public static void reset(final Player p) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Main plugin is null inside TitleAPI!");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (VERSION != VERSION)
                    return;
                PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.RESET, null);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
        });
    }

    /**
     * Clear the players title.
     *
     * @param p player to be cleared
     */
    public static void clear(final Player p) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Main plugin is null inside TitleAPI!");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (VERSION != VERSION)
                    return;
                PacketPlayOutTitle packet = new PacketPlayOutTitle(EnumTitleAction.CLEAR, null);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
        });
    }
}
