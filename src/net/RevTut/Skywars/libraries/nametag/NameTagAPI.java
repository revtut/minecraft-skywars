package net.RevTut.Skywars.libraries.nametag;

import net.RevTut.Skywars.utils.ScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Squid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

/**
 * Created by João on 25/10/2014.
 */
public class NameTagAPI implements Listener {

    /**
     * Hide NameTag
     *
     * @param p Player to hide the NameTag
     */
    public static void hideNametag(Player p) {
        LivingEntity entidade = p.getWorld().spawn(p.getLocation(), Squid.class);
        entidade.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        p.setPassenger(entidade);
    }

    /**
     * Unhide NameTag
     *
     * @param p Player to Unhide the NameTag
     */
    public static void unHideNametag(Player p) {
        LivingEntity entidade = (LivingEntity) p.getPassenger();
        p.eject();
        entidade.remove();
    }

    /**
     * Check if Player Has NameTag Hidden
     *
     * @param p Player to show the NameTag
     */
    public static boolean isNameTagHidden(Player p) {
        return p.getPassenger() != null && p.getPassenger().getType() == EntityType.SQUID && ((LivingEntity) p.getPassenger()).hasPotionEffect(PotionEffectType.INVISIBILITY);
    }

    /**
     * Change NameTag Color
     *
     * @param board               ScoreBoard of the Teams
     * @param p                   Player to show the NameTag
     * @param perPlayerScoreBoard If multiple ScoreBoards
     */
    public static void setNameTag(Scoreboard board, Player p, boolean perPlayerScoreBoard) {
        if (perPlayerScoreBoard) {
            Scoreboard alvoBoard;
            for (Player alvo : Bukkit.getOnlinePlayers()) {
                alvoBoard = ScoreBoard.getScoreBoardByPlayer(alvo.getUniqueId());
                if (alvoBoard != null)
                    setNameTag(alvoBoard, p); // Adicionar "Player" a ScoreBoard do Alvo
                setNameTag(board, alvo); // Adicionar "Alvo" a ScoreBoard do Player
            }
            return;
        }
        setNameTag(board, p); // Only one ScoreBoard is in use
    }

    /**
     * Change NameTag Color
     *
     * @param board ScoreBoard of the Teams
     * @param p     Player to show the NameTag
     */
    private static void setNameTag(Scoreboard board, Player p) {
        String id = "DEFAULT";
        String prefix = "";
        String sufix = "";
        if (p.hasPermission("rev.vip")) {
            id = "VIP";
            prefix = "§a[VIP] ";
        } else if (p.hasPermission("rev.developer")) {
            id = "DEV";
            prefix = "§6[DEV] ";
        } else if (p.hasPermission("rev.staff")) {
            id = "STAFF";
            prefix = "§2[STAFF] ";
        } else if (p.hasPermission("rev.moderator")) {
            id = "MOD";
            prefix = "§1[MOD] ";
        } else if (p.hasPermission("rev.admin")) {
            id = "ADMIN";
            prefix = "§4[ADMIN] ";
        } else if (p.hasPermission("rev.ceo")) {
            id = "CEO";
            prefix = "§5[CEO] ";
        } else if (p.hasPermission("rev.youtuber")) {
            id = "YT";
            prefix = "§b[YT] ";
        }
        Team team = board.getTeam(id);
        if (team == null) {
            team = board.registerNewTeam(id);
            team.setPrefix(prefix);
            team.setSuffix(sufix);
            team.setAllowFriendlyFire(true);
            team.setCanSeeFriendlyInvisibles(false);
        }
        team.addPlayer(p);

        // Display Name
        String name = prefix + p.getName();
        if (!p.getDisplayName().equals(name))
            p.setDisplayName(name);
    }

    /* Remover Dano da Entidade Invisivel */
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity().getType() == EntityType.SQUID && ((LivingEntity) e.getEntity()).hasPotionEffect(PotionEffectType.INVISIBILITY)) {
            e.setCancelled(true);
        }
    }
}
