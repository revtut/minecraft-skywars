package net.revtut.skywars.libraries.tab;

import net.revtut.skywars.SkyWars;
import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tab List Library.
 *
 * <P>A library with methods tab list related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class TabAPI {

    /**
     * Constructor of TabAPI
     */
    private TabAPI() {}

    /**
     * Main class
     */
    public static SkyWars plugin = null;

    /**
     * Version of Minecraft which might receive these packets
     */
    private static final int VERSION = 47;

    /**
     * Set the tab list of a player.
     *
     * @param p      player to send the tab
     * @param title  tab title
     * @param footer tab foot
     */
    public static void setTab(final Player p, final String title, final String footer) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Main plugin is null inside TabAPI!");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (VERSION != VERSION)
                    return;
                IChatBaseComponent tabTitle = ChatSerializer.a(title);
                IChatBaseComponent tabFooter = ChatSerializer.a(footer);
                PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

                try {
                    Field headerField = packet.getClass().getDeclaredField("a");
                    headerField.setAccessible(true);
                    headerField.set(packet, tabTitle);
                    headerField.setAccessible(!headerField.isAccessible());

                    Field footerField = packet.getClass().getDeclaredField("b");
                    footerField.setAccessible(true);
                    footerField.set(packet, tabFooter);
                    footerField.setAccessible(!footerField.isAccessible());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            }
        });
    }
}