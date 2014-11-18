package net.RevTut.Skywars.kits;

import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

/**
 * Created by waxcoder on 18-11-2014.
 */
public class Screamer implements Listener {

    private static ItemStack arrowScreamer = new ItemStack(Material.ARROW, 32);
    /* Create projectile with particle effect helix */
    @EventHandler
    public void projectile(ProjectileHitEvent e){
        Projectile proj = e.getEntity();
        Arrow arrow = (Arrow) proj;
        if(proj instanceof Arrow){
            Player p = (Player) arrow.getShooter();
            createHelix(p);
        }
    }

    public static void kitScreamer(Player p){
        p.getInventory().addItem(arrowScreamer);
    }

    /* Create helix effect */
    public void createHelix(Player player) {
        Location loc = player.getLocation();
        int radius = 2;
        for(double y = 0; y <= 50; y+=0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles("fireworksSpark", (float) (loc.getX() + x), (float) (loc.getY() + y), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
            for(Player online : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
            }
        }
    }
}
