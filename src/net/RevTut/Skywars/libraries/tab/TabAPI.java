package net.RevTut.Skywars.libraries.tab;

import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector;

/**
 * Created by João on 25/10/2014.
 */
public class TabAPI {

    /**
     * Set Tab List
     *
     * @param p      Player to send the Tab to
     * @param title  Tab Title String
     * @param footer Tab Foot String
     */
    public static void setTab(Player p, String title, String footer) {
        if (!(TitleAPI.getVersion(p) >= TitleAPI.VERSION))
            return;
        IChatBaseComponent tabTitle = ChatSerializer.a(title);
        IChatBaseComponent tabFooter = ChatSerializer.a(footer);
        ProtocolInjector.PacketTabHeader header = new ProtocolInjector.PacketTabHeader(tabTitle, tabFooter);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(header);
    }
}