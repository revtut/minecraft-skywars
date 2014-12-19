package net.RevTut.Skywars.libraries.nametag;

import net.RevTut.Skywars.SkyWars;
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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Name Tag Library.
 *
 * <P>A library with methods name tag related to.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class NameTagAPI implements Listener {

    /**
     * Main class
     */
    public static SkyWars plugin = null;

    /**
     * Hide name tag of a player.
     *
     * @param p player to hide the NameTag
     */
    public static void hideNametag(Player p) {
        LivingEntity entidade = p.getWorld().spawn(p.getLocation(), Squid.class);
        entidade.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
        p.setPassenger(entidade);
    }

    /**
     * Unhide the name tag of a player.
     *
     * @param p player to Unhide the NameTag
     */
    public static void unHideNametag(Player p) {
        LivingEntity entidade = (LivingEntity) p.getPassenger();
        p.eject();
        entidade.remove();
    }

    /**
     * Check if a player has name tag hidden.
     *
     * @param p player to see if has nametag visible
     * @return true he has it hidden
     */
    public static boolean isNameTagHidden(Player p) {
        return p.getPassenger() != null && p.getPassenger().getType() == EntityType.SQUID && ((LivingEntity) p.getPassenger()).hasPotionEffect(PotionEffectType.INVISIBILITY);
    }

    /**
     * Change the nametag of a player.
     *
     * @param board               scoreBoard of the Teams
     * @param p                   player to show the NameTag
     * @param perPlayerScoreBoard if multiple ScoreBoards
     */
    public static void setNameTag(Scoreboard board, Player p, boolean perPlayerScoreBoard) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Main plugin is null inside NameTagAPI!");
            return;
        }
        if (perPlayerScoreBoard) {
            Scoreboard alvoBoard;
            for (Player alvo : Bukkit.getOnlinePlayers()) {
                alvoBoard = plugin.scoreBoardManager.getScoreBoardByPlayer(alvo.getUniqueId());
                if (alvoBoard != null)
                    setNameTag(alvoBoard, p); // Adicionar "Player" a ScoreBoard do Alvo
                setNameTag(board, alvo); // Adicionar "Alvo" a ScoreBoard do Player
            }
            return;
        }
        setNameTag(board, p); // Only one ScoreBoard is in use
    }

    /**
     * Change the nametag of a player.
     *
     * @param board scoreBoard of the Teams
     * @param p     player to show the NameTag
     */
    private static void setNameTag(Scoreboard board, Player p) {
        String id = "DEFAULT";
        String prefix = "";
        String sufix = "";
        if (p.hasPermission("rev.tag.ceo")) {
            id = "CEO";
            prefix = "§5[CEO] ";
        } else if (p.hasPermission("rev.tag.admin")) {
            id = "ADM";
            prefix = "§4[ADM] ";
        } else if (p.hasPermission("rev.tag.developer")) {
            id = "DEV";
            prefix = "§6[DEV] ";
        } else if (p.hasPermission("rev.tag.moderator")) {
            id = "MOD";
            prefix = "§1[MOD] ";
        } else if (p.hasPermission("rev.tag.staff")) {
            id = "STF";
            prefix = "§2[STF] ";
        } else if (p.hasPermission("rev.tag.thaiberium")) {
            id = "THB";
            prefix = "§3[THB] ";
        } else if (p.hasPermission("rev.tag.designer")) {
            id = "DNZ";
            prefix = "§6[DZN] ";
        } else if (p.hasPermission("rev.tag.youtuber")) {
            id = "YT";
            prefix = "§9[YT] ";
        } else if (p.hasPermission("rev.tag.vipstar")) {
            id = "VIPSTAR";
            prefix = "§b[VIP★] ";
        } else if (p.hasPermission("rev.tag.vipplus")) {
            id = "VIPPLUS";
            prefix = "§a[VIP+] ";
        } else if (p.hasPermission("rev.tag.vip")) {
            id = "VIP";
            prefix = "§e[VIP] ";
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

        // Display name
        String name = prefix + p.getName();
        if (!p.getDisplayName().equals(name)) {
            p.setDisplayName(name);
            //p.setPlayerListName(StringUtils.abbreviate(name, 16));
        }
    }

    /**
     * Remove damage on invisible entity when player has name tag hidden.
     *
     * @param e entity damage event
     */
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity().getType() == EntityType.SQUID && ((LivingEntity) e.getEntity()).hasPotionEffect(PotionEffectType.INVISIBILITY))
            e.setCancelled(true);
    }
}
