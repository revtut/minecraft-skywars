package net.RevTut.Skywars.anticheat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;

/**
 * Created by waxcoder on 23-11-2014.
 */

public class Fly implements Listener {

    @EventHandler
    public void flyMove(PlayerMoveEvent e) {
        if(e.getTo().getX() - e.getFrom().getX() > 1 || e.getFrom().getX() - e.getTo().getX() > 1 || e.getTo().getZ() - e.getFrom().getZ() > 1 || e.getFrom().getZ() - e.getTo().getZ() > 1) {
            Location loc = e.getPlayer().getLocation();
            if(loc.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR) {
                e.getPlayer().teleport(loc);
                double vl = e.getFrom().distance(loc);
                for(Player p : Bukkit.getOnlinePlayers()) {
                    if(p.hasPermission("revtut.hack.fly")) {
                        p.sendMessage("§7|§3SkyWars Anti§7| " + ChatColor.GRAY + "Jogador " + e.getPlayer().getName() + " esta a andar demasiado rápido VL: " + vl);
                    }
                }
            }
        }
    }
}
