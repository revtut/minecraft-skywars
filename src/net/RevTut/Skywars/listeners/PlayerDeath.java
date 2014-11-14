package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.libraries.bypasses.BypassesAPI;
import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import net.RevTut.Skywars.utils.Converters;
import net.RevTut.Skywars.utils.ScoreBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Player Death.
 *
 * <P>Controls the death event.</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class PlayerDeath implements Listener {

    /**
     * Main class
     */
    private final Main plugin;

    /**
     * Constructor of PlayerDeath
     *
     * @param plugin main class
     */
    public PlayerDeath(Main plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of a player when he dies
     *
     * @param e player death event
     * @see PlayerDeathEvent
     */
    public void onDeath(PlayerDeathEvent e) {
        // Target player
        Player alvo = e.getEntity();
        PlayerDat alvoDat = PlayerDat.getPlayerDatByUUID(alvo.getUniqueId());
        if(alvoDat == null)
            return;
        Arena alvoArena = Arena.getArenaByPlayer(alvoDat);
        if(alvoArena == null)
            return;
        // Damager player
        Player damager = null;
        PlayerDat damagerDat = null;
        if(PlayerDamage.lastPlayerDamager.containsKey(alvo.getUniqueId())){
            damager = Bukkit.getPlayer(PlayerDamage.lastPlayerDamager.get(alvo.getUniqueId()));
            damagerDat = PlayerDat.getPlayerDatByUUID(damager.getUniqueId());
            if(damagerDat == null)
                return;
            Arena damagerArena = Arena.getArenaByPlayer(damagerDat);
            if(damagerArena == null)
                return;
            // Check if they are in the same arena
            if(alvoArena.getArenaNumber() != damagerArena.getArenaNumber())
                return;
        }
        // Messages & Title
        TitleAPI.sendTimings(alvo, Main.fadeIn, Main.timeOnScreen, Main.fadeOut);
        TitleAPI.sendTitle(alvo, Converters.convertToJSON("§4MORRESTE"));
        if(damager != null) {
            // Message to arena
            alvoArena.sendMessageToArena("§4" + alvo.getName() + " foi morto por " + damager.getName() + ".");
            // Subtitle
            // Target
            TitleAPI.sendSubTitle(alvo,Converters.convertToJSON("§7" + damager.getName()));
            // Title and SubTitle
            // Damager
            TitleAPI.sendTimings(damager, Main.fadeIn, Main.timeOnScreen, Main.fadeOut);
            TitleAPI.sendTitle(damager, Converters.convertToJSON("§aMATASTE"));
            TitleAPI.sendSubTitle(damager,Converters.convertToJSON("§7" + alvo.getName()));
        }else {
            // Message to arena
            alvoArena.sendMessageToArena("§4" + alvo.getName() + " morreu.");
        }
        // Scoreboard update alive players and dead
        ScoreBoard.updateAlive(alvoArena);
        ScoreBoard.updateDeath(alvoArena);
        // Bypass respawn screen
        BypassesAPI.respawnBypass(alvo);
        // Set status to DEAD
        alvoDat.setStatus(PlayerStatus.DEAD);
        // Stats
        alvoDat.addDeath(); // Target stats
        alvoDat.addLose();
        if(damagerDat == null)
            return;
        damagerDat.addKill(); // Damager stats
    }
}
