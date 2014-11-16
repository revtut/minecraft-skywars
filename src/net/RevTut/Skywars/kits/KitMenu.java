package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.Main;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_7_R4.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.entity.Player;

/**
 * Created by waxcoder on 16-11-2014.
 */
public class KitMenu implements Listener {
    public static Main plugin;
    Inventory menu = Bukkit.createInventory(null,18, "$6Escolha um kit!");

    public KitMenu(final Main plugin){
        KitMenu.plugin = plugin;
    }


    public static String vip = ChatColor.RED + "Kit Exclusivo Para VIP";

    @EventHandler
    public void InventoryClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if(e.getInventory().getTitle().contains("$6Escolha um kit!")){
            e.setCancelled(true); //Cancel the event so they cant take items out of the GUI

            if(e.getCurrentItem() == null){
                return;
            }

            else if(e.getCurrentItem().getType() == Material.NETHER_STAR){
                //gets called when the current item's type (The item the player clicked) is a diamond

                p.closeInventory(); //call if you want to close the inventory when they click it
            }
        }
    }
}
