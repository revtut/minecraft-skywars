package net.RevTut.Skywars.listeners.player;

import net.RevTut.Skywars.SkyWars;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaDat;
import net.RevTut.Skywars.libraries.bypasses.BypassesAPI;
import net.RevTut.Skywars.libraries.converters.ConvertersAPI;
import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
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
    private final SkyWars plugin;

    /**
     * Constructor of PlayerDeath
     *
     * @param plugin main class
     */
    public PlayerDeath(SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of a player when he dies
     *
     * @param e player death event
     * @see PlayerDeathEvent
     */
    @EventHandler
    public void onDeath(final PlayerDeathEvent e) {
        // Mensagem Death
        e.setDeathMessage(null);

        // Target player
        final Player alvo = e.getEntity();
        final PlayerDat alvoDat = plugin.playerManager.getPlayerDatByUUID(alvo.getUniqueId());
        if (alvoDat == null)
            return;

        // Arena target
        Arena alvoArena = plugin.arenaManager.getArenaByPlayer(alvoDat);
        if (alvoArena == null)
            return;

        // ArenaDat
        ArenaDat arenaDat = alvoArena.getArenaDat();
        if (arenaDat == null) {
            System.out.println("ArenaDat is null when player dies!");
            return;
        }

        // Hacker
        if (alvoArena.getKitManager().hacker.canRespawn(alvoDat, alvoArena)){
            // Bypass respawn
            BypassesAPI.respawnBypass(alvo);
            return;
        }

        // Hide to Arena
        plugin.arenaManager.hideToArena(alvo, false);

        // Config Player
        if (!plugin.playerManager.configPlayer(alvoDat, PlayerStatus.DEAD, GameMode.ADVENTURE, true, true, 0, 0, 20.0, 20, true, true, 0)) {
            System.out.println("Error while configuring the player.");
            return;
        }

        // Bypass respawn
        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                BypassesAPI.respawnBypass(alvo);
            }
        }, 5);

        // Damager player
        final Player damager;
        PlayerDat damagerDat = null;
        if (PlayerDamage.lastPlayerDamager.containsKey(alvo.getUniqueId())) {
            damager = Bukkit.getPlayer(PlayerDamage.lastPlayerDamager.get(alvo.getUniqueId()));
            if(damager == null)
                return;
            damagerDat = plugin.playerManager.getPlayerDatByUUID(damager.getUniqueId());
            if (damagerDat == null)
                return;
            Arena damagerArena = plugin.arenaManager.getArenaByPlayer(damagerDat);
            if (damagerArena == null)
                return;
            // Check if they are in the same arena
            if (alvoArena.getArenaNumber() != damagerArena.getArenaNumber())
                return;
        }else
            damager = null;

        // Scoreboard update alive players and dead
        plugin.scoreBoardManager.updateAlive(alvoArena);
        plugin.scoreBoardManager.updateDeath(alvoArena);

        // Messages
        if (damager != null) {
            // Message to arena
            alvoArena.sendMessage("§7|" + "§3Sky Wars" + "§7| §4" + alvo.getName() + " foi morto por " + damager.getName() + ".");
            arenaDat.addGameEvent(ChatColor.stripColor(alvo.getName() + " foi morto por " + damager.getName() + ".")); // Add to event log
            // Title
            // Target
            TitleAPI.sendTimings(alvo, 5, 60, 5);
            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§4MORRESTE"));
            TitleAPI.sendSubTitle(alvo, ConvertersAPI.convertToJSON("§7Morto por " + damager.getName()));
            // Damager
            TitleAPI.sendTimings(damager, 5, 60, 5);
            TitleAPI.sendTitle(damager, ConvertersAPI.convertToJSON("§aMATASTE"));
            TitleAPI.sendSubTitle(damager, ConvertersAPI.convertToJSON("§7" + alvo.getName()));
        } else {
            // Message to arena
            alvoArena.sendMessage("§7|" + "§3Sky Wars" + "§7| §4" + alvo.getName() + " morreu.");
            arenaDat.addGameEvent(ChatColor.stripColor(alvo.getName() + " morreu.")); // Add to event log
            // Title
            // Target
            TitleAPI.sendTimings(alvo, 5, 60, 5);
            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON("§4MORRESTE"));
            TitleAPI.sendSubTitle(alvo, ConvertersAPI.convertToJSON(""));
        }

        // Check if game ended
        if (alvoArena.getAlivePlayers().size() <= 1) {
            if(alvoArena.getAlivePlayers().size() == 1){
                // Winner Dat
                PlayerDat winnerDat = alvoArena.getAlivePlayers().get(0);
                if (winnerDat != null){
                    arenaDat.setWinner(winnerDat.getUUID().toString());
                    winnerDat.setStatus(PlayerStatus.WAITING); // Set status so he cannot die
                }
            }
            // Set Remaining Time
            alvoArena.setRemainingTime(0);
        }

        // Stats
        alvoDat.addDeath(); // Target stats
        alvoDat.addLose();
        if (damagerDat == null)
            return;
        // Points earned
        int poinsEarned = (int) (plugin.pointsPerKill + plugin.pointsPerKill * ((float) damagerDat.getGameKills() / arenaDat.getInitialPlayers().size()) + plugin.pointsPerKill * ((float) plugin.rand.nextInt(11) / 100));
        damagerDat.addPoints(poinsEarned);
        // Add kill
        damagerDat.addKill();

    }
}
