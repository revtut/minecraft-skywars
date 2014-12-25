package net.revtut.skywars.libraries.actionbar;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;
import net.revtut.skywars.SkyWars;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Action Bar Library.
 *
 * <P>A library with methods action bar related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class ActionBarAPI {

    /**
     * Constructor of ActionBarAPI
     */
    private ActionBarAPI() {}

    /**
     * Main class
     */
    public static SkyWars plugin = null;

    /**
     * Version of Minecraft which might receive these packets
     */
    private static final int VERSION = 47;

    /**
     * Send a action bar to a player.
     *
     * @param p      player to send the action bar
     * @param message  message to be sent
     */
    public static void sendActionBar(final Player p, final String message) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Main plugin is null inside ActionBarAPI!");
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                if (VERSION != VERSION)
                    return;
                IChatBaseComponent actionMessage = ChatSerializer.a(message);
                PacketPlayOutChat ppoc = new PacketPlayOutChat(actionMessage, (byte) 2);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(ppoc);
            }
        });
    }
}
