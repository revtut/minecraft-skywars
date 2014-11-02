package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.entity.Player;
/**
 * Created by waxcoder on 01-11-2014.
 */

public class PlayerDeath implements Listener {
    public Main plugin;

    public PlayerDeath(Main plugin){
        this.plugin = plugin;
    }

    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        p.sendMessage("You died!" + PlayerDat.getPlayerDatByUUID(p.getUniqueId()).getDeaths());
     }

}
