package net.revtu.skywars.libraries.bypasses;

import net.revtu.skywars.SkyWars;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.EnumClientCommand;
import net.minecraft.server.v1_8_R1.PacketPlayInClientCommand;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Bypasses Library.
 *
 * <P>A library with methods related to game bypasses.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class BypassesAPI {

    /**
     * Constructor of BypassesAPI
     */
    private BypassesAPI() {}

    /**
     * Main class
     */
    public static SkyWars plugin = null;

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
