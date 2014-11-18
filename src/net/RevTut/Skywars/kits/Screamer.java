package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.libraries.particles.ParticlesAPI;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

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
            ParticlesAPI.helixPosX(p);
        }
    }

    public static void kitScreamer(Player p) {
        p.getInventory().addItem(arrowScreamer);
    }
}
