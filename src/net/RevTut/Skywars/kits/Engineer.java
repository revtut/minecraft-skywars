package net.RevTut.Skywars.kits;

import net.RevTut.Skywars.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Engineer implements Listener {
    public Main plugin;

    public Engineer(Main plugin) {
        this.plugin = plugin;
    }

    private List<Location> mines = new ArrayList<Location>();

    ItemStack mine = new ItemStack(Material.IRON_BLOCK, 2);

    {
        ItemMeta mineMeta = mine.getItemMeta();
        mineMeta.setDisplayName("§3Land Mine");
        mine.setItemMeta(mineMeta);
    }

    ItemStack ironArmor1 = new ItemStack(Material.IRON_HELMET, 1);

    {
        ironArmor1.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 2);
    }

    ItemStack ironArmor2 = new ItemStack(Material.IRON_CHESTPLATE, 1);

    {
        ironArmor2.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
    }

    ItemStack ironArmor3 = new ItemStack(Material.IRON_LEGGINGS, 1);

    {
        ironArmor3.addEnchantment(Enchantment.PROTECTION_EXPLOSIONS, 1);
    }

    ItemStack ironArmor4 = new ItemStack(Material.IRON_BOOTS, 1);

    {
        ironArmor4.addEnchantment(Enchantment.PROTECTION_FALL, 2);
    }

    public void kitEngineer(Player p) {
        p.getInventory().addItem(mine);
        p.getInventory().addItem(ironArmor1);
        p.getInventory().addItem(ironArmor2);
        p.getInventory().addItem(ironArmor3);
        p.getInventory().addItem(ironArmor4);

    }

    @EventHandler
    public void onActivate(PlayerInteractEvent e) {
        if (e.getAction().equals(Action.PHYSICAL)) {
            if (e.getClickedBlock().getType().equals(Material.IRON_BLOCK)) {
                if (mines.contains(e.getClickedBlock().getLocation())) {
                    e.getClickedBlock().setType(Material.AIR);
                    mines.remove(e.getClickedBlock().getLocation());
                    e.getClickedBlock().getWorld().createExplosion(e.getClickedBlock().getLocation(), 2, false);
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.IRON_BLOCK)) {
            mines.add(e.getBlock().getLocation());
            e.getPlayer().sendMessage(ChatColor.GOLD + "Crias te uma" + ChatColor.DARK_RED + " mina!");
        }
    }
}
