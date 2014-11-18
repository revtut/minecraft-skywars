package net.RevTut.Skywars.managers;

import net.RevTut.Skywars.kits.*;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
     * Kit Guardian
     */
    public final Guardian guardian = new Guardian();

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
     * Kit Screamer
     */
    public final Screamer screamer = new Screamer();

    /**
     * Kit Menu Item
     */
    private final ItemStack kitMenuItem = new ItemStack(Material.NETHER_STAR, 1);

    {
        ItemMeta kitMenuMeta = kitMenuItem.getItemMeta();
        kitMenuMeta.setDisplayName("§3Kit Menu");
        kitMenuItem.setItemMeta(kitMenuMeta);
    }

    /**
     * Map with players and choosen kit
     */
    private final Map<UUID, Kit> playerKit = new HashMap<UUID, Kit>();

    /**
     * Give the kit menu item to player
     *
     * @param playerDat player to give the menu item
     */
    public final void giveKitMenuItem(PlayerDat playerDat){
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if(player == null)
            return;
        player.getInventory().setItem(0, kitMenuItem);
    }

    /**
     * Give the choosen player's kit to him
     *
     * @param playerDat player to give the choosen kit
     */
    public final void giveChoosenKit(PlayerDat playerDat){
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if(player == null)
            return;
        if(!playerKit.containsKey(playerDat.getUUID())){
            player.sendMessage("§7|" + "§3Sky Wars" + "§7| §4Nenhum kit te foi dado pois nao o escolheste!");
            return;
        }
        // Kit
        Kit kit = playerKit.get(playerDat.getUUID());
        if(kit == Kit.ENGINEER)
            engineer.kitEngineer(player);
        else if(kit == Kit.GUARDIAN)
            guardian.kitGuardian(player);
        else if(kit == Kit.HACKER)
            hacker.kitHacker(player);
        else if(kit == Kit.NINJA)
            ninja.kitNinja(player);
        else if(kit == Kit.TATICAL)
            tatical.kitTatical(player);
        else if(kit == Kit.SCREAMER)
            screamer.kitScreamer(player);
        // Message
        player.sendMessage("§7|" + "§3Sky Wars" + "§7| §aRecebeste o Kit " + ChatColor.stripColor(kit.getDisplayName())+ "!");
    }

    /**
     * Open kit menu to a player
     *
     * @return inventory menu with all kits
     */
    public final Inventory createKitMenu(ItemStack itemStack) { // MAKES USE OF PLAYER INTERACT EVENT
        if (itemStack == null)
            return null;
        if (itemStack.getType() == null)
            return null;
        if (itemStack.getType() != kitMenuItem.getType())
            return null;
        if (!itemStack.hasItemMeta())
            return null;
        if (!itemStack.getItemMeta().hasDisplayName())
            return null;
        if (!itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(kitMenuItem.getItemMeta().getDisplayName()))
            return null;

        // All the kits
        Kit[] kits = Kit.values();

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
        Kit kit;
        ItemMeta itemMeta;
        for(int i = 0; i < kits.length; i++){
            kit = kits[i];
            itemStack = new ItemStack(kit.getMaterial(), 1);
            itemMeta = itemStack.getItemMeta(); // ItemMeta
            itemMeta.setDisplayName(kit.getDisplayName()); // DisplayName
            itemMeta.setLore(Arrays.asList("§7Custo: §b" + kit.getCost())); // Lore
            itemStack.setItemMeta(itemMeta); // Set iteMeta
            inventory.setItem(i , itemStack);
        }

        return inventory;
    }

    /**
     * Set the kit of a player
     *
     * @param playerDat player to set the kit
     * @param position position of the kit
     */
    public final void setKit(PlayerDat playerDat, Inventory inventory, int position){ // MAKES USE OF PLAYER INVENTORY CLICK EVENT
        if(!inventory.getTitle().equalsIgnoreCase("§6Menu de Kits"))
            return;
        if(position < 0)
            return;
        if(position >= Kit.values().length)
            return;
        // Player
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if(player == null)
            return;
        // Check if player has not choose the kit already
        if(playerKit.containsKey(playerDat.getUUID())){
            player.sendMessage("§7|" + "§3Sky Wars" + "§7| §4O teu kit ja foi escolhido!");
            return;
        }
        Kit kit = Kit.values()[position];
        playerKit.put(playerDat.getUUID(), kit);
        // Close Inventory
        player.closeInventory();
        // Message
        player.sendMessage("§7|" + "§3Sky Wars" + "§7| §6Kit " + ChatColor.stripColor(kit.getDisplayName()) + " escolhido!");
    }
}
