package net.RevTut.Skywars.anticheat;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by João Silva on 23-11-2014.
 */

public class Fly implements Listener {

    private final Map<String, Integer> antiHackFly = new HashMap();

    @EventHandler
    public void flyMove(PlayerMoveEvent e) {
        Location from = e.getFrom();
        double fromX = from.getX();
        double fromZ = from.getZ();
        double fromY = from.getY();

        Location to = e.getTo();
        double toX = to.getX();
        double toZ = to.getZ();
        double toY = to.getY();

        if ((fromX != toX) || (fromZ != toZ) || (fromY != toY))
        {
            Player p = e.getPlayer();
            if (p.isOp()) {
                return;
            }
            Location toLoc = e.getTo();
            Location fromLoc = e.getFrom();
            if ((toLoc.distance(fromLoc) > 6.0D) &&
                    (!p.getGameMode().equals(GameMode.CREATIVE)))
            {
                if (p.getAllowFlight()) {
                    return;
                }
                if (p.hasPermission("revtut.antihack.fly")) {
                    return;
                }
                if (e.getPlayer().getVehicle() == null)
                {
                    if(this.antiHackFly.containsKey(p.getName())){
                        this.antiHackFly.put(p.getName(), 0);
                    }
                    this.antiHackFly.put(p.getName(), ((Integer) this.antiHackFly.get(p.getName())).intValue() + 1);
                    p.kickPlayer("§4Foste kickado por uso de fly hack!");
                }
            }
        }
    }
}
