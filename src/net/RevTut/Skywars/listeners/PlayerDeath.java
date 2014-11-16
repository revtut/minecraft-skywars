package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaDat;
import net.RevTut.Skywars.libraries.bypasses.BypassesAPI;
import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import net.RevTut.Skywars.utils.Converters;
import net.RevTut.Skywars.utils.ScoreBoard;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        // Mensagem Death
        e.setDeathMessage(null);
        // Target player
        Player alvo = e.getEntity();
        PlayerDat alvoDat = PlayerDat.getPlayerDatByUUID(alvo.getUniqueId());
        if(alvoDat == null)
            return;

        // Config Player
        if(!PlayerDat.configPlayer(alvoDat, PlayerStatus.DEAD, GameMode.ADVENTURE, true, true, 0, 0, 20.0, 20, true, true, 0)){
            System.out.println("Error while configuring the player.");
            return;
        }

        // Hide to Arena
        plugin.arenaManager.hideToArena(alvo, false);

        // Arena target
        Arena alvoArena = plugin.arenaManager.getArenaByPlayer(alvoDat);
        if(alvoArena == null)
            return;

        // ArenaDat
        ArenaDat arenaDat = alvoArena.getArenaDat();
        if (arenaDat == null) {
            System.out.println("ArenaDat is null when player dies!");
            return;
        }

        // Damager player
        Player damager = null;
        PlayerDat damagerDat = null;
        if(PlayerDamage.lastPlayerDamager.containsKey(alvo.getUniqueId())){
            damager = Bukkit.getPlayer(PlayerDamage.lastPlayerDamager.get(alvo.getUniqueId()));
            damagerDat = PlayerDat.getPlayerDatByUUID(damager.getUniqueId());
            if(damagerDat == null)
                return;
            Arena damagerArena = plugin.arenaManager.getArenaByPlayer(damagerDat);
            if(damagerArena == null)
                return;
            // Check if they are in the same arena
            if(alvoArena.getArenaNumber() != damagerArena.getArenaNumber())
                return;
        }

        // Scoreboard update alive players and dead
        ScoreBoard.updateAlive(alvoArena);
        ScoreBoard.updateDeath(alvoArena);

        // Bypass respawn screen
        BypassesAPI.respawnBypass(alvo);

        // Messages & Title
        TitleAPI.sendTimings(alvo, 5, 60, 5);
        TitleAPI.sendTitle(alvo, Converters.convertToJSON("§4MORRESTE"));
        if(damager != null) {
            // Message to arena
            alvoArena.sendMessage("§7|" + "§3Sky Wars" + "§7| §4" + alvo.getName() + " foi morto por " + damager.getName() + ".");
            arenaDat.addGameEvent(ChatColor.stripColor(alvo.getName() + " foi morto por " + damager.getName() + ".")); // Add to event log
            // Subtitle
            // Target
            TitleAPI.sendSubTitle(alvo, Converters.convertToJSON("§7Morto por " + damager.getName()));
            // Title and SubTitle
            // Damager
            TitleAPI.sendTimings(damager, 5, 60, 5);
            TitleAPI.sendTitle(damager, Converters.convertToJSON("§aMATASTE"));
            TitleAPI.sendSubTitle(damager,Converters.convertToJSON("§7" + alvo.getName()));
        }else {
            // Message to arena
            alvoArena.sendMessage("§7|" + "§3Sky Wars" + "§7| §4" + alvo.getName() + " morreu.");
            arenaDat.addGameEvent(ChatColor.stripColor(alvo.getName() + " morreu.")); // Add to event log
            // Subtitle
            // Target
            TitleAPI.sendSubTitle(alvo, Converters.convertToJSON(""));
        }

        // Check if game ended
        if(alvoArena.getAlivePlayers().size() == 1){
            // Set Remaining Time
            alvoArena.setRemainingTime(0);
            // Winner Dat
            PlayerDat winnerDat = alvoArena.getAlivePlayers().get(0);
            if(winnerDat != null)
                arenaDat.setWinner(winnerDat.getUUID().toString());
        }

        // Stats
        alvoDat.addDeath(); // Target stats
        alvoDat.addLose();
        if(damagerDat == null)
            return;
        damagerDat.addKill(); // Damager stats
    }
}
