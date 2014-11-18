package net.RevTut.Skywars.libraries.particles;

import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Random;

/**
 * Particle API.
 *
 * <P>Help make particles with simple methods</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class ParticlesAPI {

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
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles("fireworksSpark", (float) (loc.getX() + x), (float) (loc.getY() + y ), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
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
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(randomParticles(), (float) (loc.getX() + y), (float) (loc.getY() + x ), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
            for (Player online : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }

    /**
     * Make a random particle
     * @return return null
     */
    private static String randomParticles() {
        int rint = r.nextInt(5) + 1;
        if(rint == 1)
            return "fireworksSpark";
        else if(rint == 2)
            return "happyVillager";
        else if(rint == 3)
            return "witchMagic";
        else if(rint == 4)
            return "flame";
        else if(rint == 5) {
            return "blockcrack_152_0";
        }
        return "fireworksSpark";
    }
}
