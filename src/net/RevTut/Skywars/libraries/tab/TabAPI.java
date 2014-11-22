package net.RevTut.Skywars.libraries.tab;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector;

/**
 * Tab List Library.
 *
 * <P>A library with methods tab list related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class TabAPI {

    /**
     * Main class
     */
    public static Main plugin = null;

    /**
     * Version of Minecraft which might receive these packets
     */
    public static final int VERSION = 47;

    /**
     * Set the tab list of a player.
     *
     * @param p      player to send the tab
     * @param title  tab title
     * @param footer tab foot
     */
    public static void setTab(final Player p, final String title, final String footer) {
        if (null == plugin) {
            System.out.println("Main plugin is null inside TabAPI!");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (!(TitleAPI.getVersion(p) >= TabAPI.VERSION))
                    return;
                IChatBaseComponent tabTitle = ChatSerializer.a(title);
                IChatBaseComponent tabFooter = ChatSerializer.a(footer);
                ProtocolInjector.PacketTabHeader header = new ProtocolInjector.PacketTabHeader(tabTitle, tabFooter);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(header);
            }
        });
    }
}