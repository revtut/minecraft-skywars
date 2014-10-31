package net.RevTut.Skywars.kits;


import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

/**
 * Created by waxcoder on 31-10-2014.
 */

public class Engineer implements Listener {

    ItemStack mine = new ItemStack(Material.STONE);{
        ItemMeta mineMeta = mine.getItemMeta();
        mineMeta.setDisplayName("§3Land Mine");
        ArrayList<String> lore =
    }
}
