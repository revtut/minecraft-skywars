package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Created by waxcoder on 16-11-2014.
 */
public class KitMenu implements Listener {
    public static Main plugin;
    public Inventory menu;

    ItemStack eng = new ItemStack(Material.ENDER_PEARL);{
        ItemMeta engMeta = eng.getItemMeta();
        engMeta.setDisplayName("§6Engineer ");
    }

    ItemStack start = new ItemStack(Material.ENDER_PEARL);{
        ItemMeta startMeta = eng.getItemMeta();
        startMeta.setDisplayName("§2Kits");
    }

    public KitMenu(final Main plugin){
        KitMenu.plugin = plugin;
    }


    public static String vip = ChatColor.RED + "Kit Exclusivo Para VIP";

    /* Event create menu with 18 slots */
    @EventHandler
    public void criarMenu (PlayerInteractEvent e){
        Player p = e.getPlayer();
        menu = Bukkit.createInventory(null,18, "$6Escolha um kit!");

        if(p.getInventory().getItemInHand().getType() == Material.NETHER_STAR){
            if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                p.openInventory(menu);
            }
        }
    }

    @EventHandler
    public void InventoryClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if(e.getInventory().getName().equals(menu.getName())){
            e.setCancelled(true);
        }

        if(e.getCurrentItem() == null){
            return;
        }

        if(e.getCurrentItem().getType() == Material.ENDER_PEARL){

        }
    }
}
