package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by waxcoder on 16-11-2014.
 */

public class Hacker implements Listener {
    /**
     * Main Class
     */
    private final Main plugin;

    /**
     * Constructor of Kit Hacker
     *
     * @param plugin main class
     */
    public Hacker(Main plugin) {
        this.plugin = plugin;
    }

    public HashMap<Player , ItemStack[]> items = new HashMap<Player , ItemStack[]>();

    public void onDeath(Player player){
        ItemStack[] content = player.getInventory().getContents();
        items.put(player, content);
        player.getInventory().clear();
    }
}
