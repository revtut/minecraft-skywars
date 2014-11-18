package net.RevTut.Skywars.managers;

import net.RevTut.Skywars.kits.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

/**
 * Kit Manager.
 *
 * <P>Manages all all the kits in the server.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class KitManager {

    /**
     * Kit Engineer
     */
    public final Engineer engineer = new Engineer() ;

    /**
     * Kit Hacker
     */
    public final Hacker hacker = new Hacker();

    /**
     * Kit Ninja
     */
    public final Ninja ninja = new Ninja();

    /**
     * Kit Tatical
     */
    public final Tatical tatical = new Tatical();

    /**
     * Kit Menu Item
     */
    private final ItemStack kitMenuItem = new ItemStack(Material.NETHER_STAR, 1);

    {
        kitMenuItem.getItemMeta().setDisplayName("§3Kit Menu");
    }

    /**
     * Open kit menu to a player
     *
     * @param player player to open the menu
     * @return inventory menu with all kits
     */
    public final Inventory openKitMenu(Player player) {
        // All the kits
        Kits[] kits = Kits.values();

        // Size of Inventory
        int inventorySize;
        if(kits.length <= 9)
            inventorySize = 9;
        else if(kits.length <= 18)
            inventorySize = 18;
        else if(kits.length <= 27)
            inventorySize = 27;
        else if(kits.length <= 36)
            inventorySize = 36;
        else if(kits.length <= 45)
            inventorySize = 45;
        else
            inventorySize = 54;
        Inventory inventory = Bukkit.createInventory(null, inventorySize, "§6Menu de Kits");

        // Create Menu
        ItemStack itemStack;
        Kits kit;
        for(int i = 0; i < kits.length; i++){
            kit = kits[i];
            itemStack = new ItemStack(kit.getMaterial(), 1);
            itemStack.getItemMeta().setDisplayName(kit.getDisplayName());
            itemStack.getItemMeta().setLore(Arrays.asList("§7Custo: §b" + kit.getCost()));
            inventory.setItem(i , itemStack);
        }

        return inventory;
    }

    /**
     * Give the kit menu item to player
     *
     * @param player player to give the menu item
     */
    public final void giveKitMenuItem(Player player){
        player.getInventory().setItem(0, kitMenuItem);
    }
}
