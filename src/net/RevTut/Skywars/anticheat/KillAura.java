package net.RevTut.Skywars.anticheat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Created by waxcoder on 22-11-2014.
 */

public class KillAura implements CommandExecutor, Listener{


    public void runKillAuraCheck() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "aura");
            }
        },0, 20);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e){
        if(e.getEntity() instanceof  Villager){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onVillagerDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof  Player && e.getEntity() instanceof Villager){
            Villager villager = (Villager) e.getEntity();
            Player damager = (Player) e.getDamager();
            if(villager.getCustomName().equals(damager.getName())){
                for(Player online : Bukkit.getOnlinePlayers()){
                    if(online.hasPermission("revtut.antihack")){
                        online.sendMessage("§7|§3SkyWars Anti§7| " + damager.getName() + "poderá estar a usar ForceField(KillAura)");
                    }
                }
            }
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("aura")){
            for(Player online : Bukkit.getOnlinePlayers()){
                final Villager villager = (Villager) online.getLocation().getWorld().spawnEntity(online.getLocation().subtract(3, 0, 3), EntityType.VILLAGER);
                villager.setCustomName(online.getName());
                villager.setCustomNameVisible(true);
                villager.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
                Bukkit.getScheduler().runTaskLater(this,new Runnable(){
                    @Override
                    public void run() {
                       villager.remove();
                    }
                }, 10);
            }
        }
        return false;
    }
}
