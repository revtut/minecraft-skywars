package net.RevTut.skywar.libraries.particles;

import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * Particle API.
 *
 * <P>Help make particles with simple methods</P>
 *
 * @author João Silva
 * @version 1.0
 */
public final class ParticlesAPI {

    /**
     * Constructor of ParticlesAPI
     */
    private ParticlesAPI() {}

    /**
     * Random class
     */
    private static final Random r = new Random();


    /**
     * Create helix particle effect follow X-Axis
     *
     * @param player player to create helix effect
     */
    public static void helixPosX(Player player) {
        Location loc = player.getLocation();
        int radius = 2;
        for(double y = 0; y <= 50; y+=0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, (float) (loc.getX() + x), (float) (loc.getY() + y ), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
            for(Player online : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    /**
     * Create helix particle effect follow Y-Axis
     *
     * @param player player to create helix effect
     */
    public static void helixPosY(Player player) {
        Location loc = player.getLocation();
        int radius = 4;
        for (double y = 0; y <= 50; y += 0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(randomParticles(), true, (float) (loc.getX() + y), (float) (loc.getY() + x ), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
            for (Player online : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    /**
     * Create helix particle effect follow Y-Axis
     *
     * @param location location to create helix effect
     */
    public static void helixPosY(Location location) {
        int radius = 4;
        for (double y = 0; y <= 50; y += 0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(randomParticles(), true, (float) (location.getX() + y), (float) (location.getY() + x ), (float) (location.getZ() + z), 0, 0, 0, 0, 1);
            for (Player online : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    /**
     * Make a random particle
     * @return particle effect
     */
    private static EnumParticle randomParticles() {
        int rint = r.nextInt(5) + 1;
        if(rint == 1)
            return EnumParticle.FIREWORKS_SPARK;
        else if(rint == 2)
            return EnumParticle.VILLAGER_HAPPY;
        else if(rint == 3)
            return EnumParticle.SPELL_WITCH;
        else if(rint == 4)
            return EnumParticle.FLAME;
        else if(rint == 5) {
            return EnumParticle.BLOCK_CRACK;
        }
        return EnumParticle.FIREWORKS_SPARK;
    }
}
