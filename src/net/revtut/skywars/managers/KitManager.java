package net.revtut.skywars.managers;

import net.revtut.skywars.kits.*;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.utils.Message;
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
     * Constructor of KitManager
     */
    public KitManager() {
        engineer = new Engineer();
        guardian = new Guardian();
        hacker = new Hacker();
        ninja = new Ninja();
        tatical = new Tatical();
        lifestealer = new Lifestealer();
        screamer = new Screamer();
    }

    /**
     * Kit Engineer
     */
    public final Engineer engineer;

    /**
     * Kit Guardian
     */
    public final Guardian guardian;

    /**
     * Kit Hacker
     */
    public final Hacker hacker;

    /**
     * Kit Ninja
     */
    public final Ninja ninja;

    /**
     * Kit Tatical
     */
    public final Tatical tatical;

    /**
     * Kit Lifestealer
     */
    public final Lifestealer lifestealer;

    /**
     * Kit Screamer
     */
    public final Screamer screamer;

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
     * Compass
     */
    private final ItemStack compass = new ItemStack(Material.COMPASS, 1);

    {
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.setDisplayName("§6TARGET");
        compass.setItemMeta(compassMeta);
    }

    /**
     * Map with players and choosen kit
     */
    public final Map<UUID, Kit> playerKit = new HashMap<>();

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
     * Give the kit menu item to player
     *
     * @param playerDat player to give the menu item
     */
    public final void giveCompassItem(PlayerDat playerDat){
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if(player == null)
            return;

        player.getInventory().setItem(8, compass);
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

        // Check if player bought kit
        if(!playerKit.containsKey(playerDat.getUUID()))
            return;

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
        else if(kit == Kit.LIFESTEALER)
            lifestealer.kitLifestealer(player);
        //else if(kit == Kit.SCREAMER)
        //    screamer.kitScreamer(player);

        // Message
        player.sendMessage(Message.getMessage(Message.KIT_RECEIVED, player) + ChatColor.stripColor(kit.getDisplayName())+ "!");
    }

    /**
     * Create kit menu to a player
     *
     * @param playerDat player to create the menu
     * @param itemStack item stack used by the player
     * @return inventory menu with all kits
     */
    public final Inventory createKitMenu(PlayerDat playerDat, ItemStack itemStack) { // MAKES USE OF PLAYER INTERACT EVENT
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

        // Player
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if(player == null)
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
        Inventory inventory = Bukkit.createInventory(null, inventorySize, "§6Kits");

        // Create Menu
        Kit kit;
        ItemMeta itemMeta;
        for(int i = 0; i < kits.length; i++){
            kit = kits[i];
            itemStack = new ItemStack(kit.getMaterial(), 1);
            itemMeta = itemStack.getItemMeta(); // ItemMeta
            itemMeta.setDisplayName(kit.getDisplayName()); // DisplayName
            if(!player.hasPermission("rev.halfcost"))
                itemMeta.setLore(Arrays.asList("§7" + ChatColor.stripColor(Message.getMessage(Message.POINTS, player)) + " §b" + kit.getCost())); // Lore
            else
                itemMeta.setLore(Arrays.asList("§7" + ChatColor.stripColor(Message.getMessage(Message.POINTS, player)) + " §4§m" + kit.getCost() + " " + "§b" + (kit.getCost()/2))); // Lore
            itemStack.setItemMeta(itemMeta); // Set iteMeta
            inventory.setItem(i , itemStack);
        }

        return inventory;
    }

    /**
     * Set the kit of a player. Checks if player has enough points to buy it and if player
     * has not choosen the kit yet
     *
     * @param playerDat player to set the kit
     * @param inventory inventory of the player
     * @param position position of the kit
     */
    public final void setKit(PlayerDat playerDat, Inventory inventory, int position){ // MAKES USE OF PLAYER INVENTORY CLICK EVENT
        if(!inventory.getTitle().equalsIgnoreCase("§6Kits"))
            return;
        if(position < 0)
            return;
        if(position >= Kit.values().length)
            return;
        // Player
        Player player = Bukkit.getPlayer(playerDat.getUUID());
        if(player == null)
            return;
        // Close Inventory
        player.closeInventory();
        // Check if player has not choose the kit already
        if(playerKit.containsKey(playerDat.getUUID())){
            player.sendMessage(Message.getMessage(Message.KIT_ALREADY_CHOSEN, player));
            return;
        }
        // Choosen kit
        Kit kit = Kit.values()[position];
        // Cost
        int cost = 0;
        if(!player.hasPermission("rev.halfcost"))
            cost = kit.getCost();
        else
            cost = kit.getCost() / 2;
        // Check if player has enough points
        if(playerDat.getPoints() < cost){
            player.sendMessage(Message.getMessage(Message.NOT_ENOUGH_POINTS_FOR_KIT, player));
            return;
        }
        // Add to map
        playerKit.put(playerDat.getUUID(), kit);
        // Decrease points
        playerDat.addPoints(0 - cost);
        // Message
        player.sendMessage(Message.getMessage(Message.KIT_BOUGHT, player) + ChatColor.stripColor(kit.getDisplayName()) + "!");
    }
}
