package net.RevTut.Skywars.anticheat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

/**
 * Created by waxcoder on 23-11-2014.
 */
public class Fly implements Listener {

    @EventHandler
    public void onFly(PlayerKickEvent e){
        if(e.getReason().equalsIgnoreCase("Flying is not enabled on this server")){
            e.setReason("Banido devido a fly hack!");
            e.getPlayer().setBanned(true);
        }
    }
}
