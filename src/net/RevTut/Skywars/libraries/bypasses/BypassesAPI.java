package net.RevTut.Skywars.libraries.bypasses;

import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.EnumClientCommand;
import net.minecraft.server.v1_7_R4.PacketPlayInClientCommand;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Bypasses Library.
 *
 * <P>A library with methods related to game bypasses.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class BypassesAPI {

    /**
     * Bypass player respawn screen.
     *
     * @param player player to bypass respawn menu
     */
    public static void respawnBypass(Player player) {
        final PacketPlayInClientCommand in = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN); // Gets the packet class
        final EntityPlayer cPlayer = ((CraftPlayer) player).getHandle(); // Gets the EntityPlayer class
        cPlayer.playerConnection.a(in); // Handles the rest of it
    }


}
