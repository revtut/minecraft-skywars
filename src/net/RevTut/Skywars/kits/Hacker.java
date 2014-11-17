package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by waxcoder on 16-11-2014.
 */

public class Hacker implements Listener {
    public Main plugin;

    public Hacker(final Main plugin){
        this.plugin = plugin;
    }

    public HashMap<Player , ItemStack[]> items = new HashMap<Player , ItemStack[]>();

    @EventHandler()
    public void onDeath(PlayerDeathEvent event){
        ItemStack[] content = event.getEntity().getInventory().getContents();
        items.put(event.getEntity(), content);
        event.getEntity().getInventory().clear();
    }
}
