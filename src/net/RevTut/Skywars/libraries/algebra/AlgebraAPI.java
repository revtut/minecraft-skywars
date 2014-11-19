package net.RevTut.Skywars.libraries.algebra;

import org.bukkit.Location;

/**
 * Algebra Library.
 *
 * <P>A library with methods which envolve to algebra and mathematics.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class AlgebraAPI {

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

}
