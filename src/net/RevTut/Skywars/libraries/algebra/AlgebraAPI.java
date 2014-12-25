package net.revtut.skywars.libraries.algebra;

import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.player.PlayerDat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Algebra Library.
 *
 * <P>A library with methods which envolve to algebra and mathematics.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class AlgebraAPI {

    /**
     * Constructor of AlgebraAPI
     */
    private AlgebraAPI() {}

    /**
     * Make a location look at another location, in other words, change the pitch/yaw in such manner that,
     * if applied to a player or entity, it will look at the specified location
     *
     * @param location location to set pitch and yaw
     * @param lookAt location to look at
     * @return location that will look lookAt
     */
    public static Location locationLookAt(Location location, Location lookAt) {
        //Clone the loc to prevent applied changes to the input loc
        location = location.clone();

        // Values of change in distance (make it relative)
        double deltaX = lookAt.getX() - location.getX();
        double deltaY = lookAt.getY() - location.getY();
        double deltaZ = lookAt.getZ() - location.getZ();

        // Set yaw
        if (deltaX != 0) {
            // Set yaw start value based on deltaX
            if (deltaX < 0)
                location.setYaw((float) (1.5 * Math.PI));
            else
                location.setYaw((float) (0.5 * Math.PI));
            location.setYaw(location.getYaw() - (float) Math.atan(deltaZ / deltaX));
        } else if (deltaZ < 0)
            location.setYaw((float) Math.PI);

        // Get the distance from deltaX/deltaZ
        double dxz = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaZ, 2));

        // Set pitch
        location.setPitch((float) -Math.atan(deltaY / dxz));

        // Set values, convert to degrees (invert the yaw since Bukkit uses a different yaw dimension format)
        location.setYaw(-location.getYaw() * 180f / (float) Math.PI);
        location.setPitch(location.getPitch() * 180f / (float) Math.PI);

        return location;
    }

    /**
     * Get the closest player to a given player
     *
     * @param arena arena where player is currently playing
     * @param player player to get the closest player
     * @return closest player
     */
    public static Player closestPlayer(Arena arena, Player player) {
        Player closest = null;
        double minDistance = Integer.MAX_VALUE;
        for(PlayerDat alvoDat : arena.getAlivePlayers()) {
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if(null == alvo)
                continue;

            if(alvo.getUniqueId().equals(player.getUniqueId()))
                continue;

            if(alvo.getWorld() != player.getWorld())
                continue;

            double distance = distanceBetween(player.getLocation(), alvo.getLocation());

            if(distance < minDistance){
                minDistance = distance;
                closest = alvo;
            }
        }
        return closest;
    }

    /**
     * Distance between two locations in meters
     *
     * @param initial initial location
     * @param target target location
     * @return distance in meters
     */
    public static double distanceBetween(Location initial, Location target) {
        int xI = initial.getBlockX(), xF = target.getBlockX();
        int yI = initial.getBlockY(), yF = target.getBlockY();
        int zI = initial.getBlockZ(), zF = target.getBlockZ();

        int deltaX = xF - xI, deltaY = yF - yI, deltaZ = zF - zI;

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }
}
