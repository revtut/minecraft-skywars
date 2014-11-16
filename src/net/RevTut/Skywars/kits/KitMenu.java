package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.Main;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_7_R4.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

/**
 * Created by waxcoder on 16-11-2014.
 */
public class KitMenu implements Listener {
    public static Main plugin;
    Inventory menu = Bukkit.createInventory(null,18, "$6Escolha um kit!");

    public KitMenu(final Main plugin){
        KitMenu.plugin = plugin;
        KitMenu.this.criarMenu(KitMenu.this.menu);
    }


    public static String vip = ChatColor.RED + "Kit Exclusivo Para VIP";

    /* Cria o menu de kits */
    public void criarMenu(final Inventory inv){
        /* Engineer */

    }
}
