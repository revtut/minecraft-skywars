package net.revtut.skywars.listeners.player;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaDat;
import net.revtut.skywars.libraries.bypasses.BypassesAPI;
import net.revtut.skywars.libraries.camera.CameraAPI;
import net.revtut.skywars.libraries.converters.ConvertersAPI;
import net.revtut.skywars.libraries.titles.TitleAPI;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.player.PlayerStatus;
import net.revtut.skywars.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.logging.Level;

/**
 * Player Death.
 *
 * <P>Controls the death event.</P>
 *
 * @author João Silva
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
            plugin.getLogger().log(Level.WARNING, "ArenaDat is null when player dies!");
            return;
        }

        // Hacker
        if (alvoArena.getKitManager().hacker.canRespawn(alvoDat, alvoArena)){
            // Bypass respawn
            BypassesAPI.respawnBypass(alvo);
            return;
        }

        // Update player
        alvoDat.setStatus(PlayerStatus.DEAD);
        alvo.setGameMode(GameMode.SPECTATOR);

        // Hide to Arena
        plugin.arenaManager.hideToArena(alvo, false);

        // Scoreboard update alive players and dead
        plugin.scoreBoardManager.updateAlive(alvoArena);
        plugin.scoreBoardManager.updateDeath(alvoArena);

        Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
            @Override
            public void run() {
                // Respawn
                BypassesAPI.respawnBypass(alvo);
                // Restore camera
                CameraAPI.resetCamera(alvo);
            }
        }, 60);

        // Damager player
        Player damager = null;
        PlayerDat damagerDat = null;
        if (PlayerDamage.lastPlayerDamager.containsKey(alvo.getUniqueId())) {
            damager = Bukkit.getPlayer(PlayerDamage.lastPlayerDamager.get(alvo.getUniqueId()));
            if(damager != null){
                damagerDat = plugin.playerManager.getPlayerDatByUUID(damager.getUniqueId());
                if (damagerDat == null)
                    return;
                Arena damagerArena = plugin.arenaManager.getArenaByPlayer(damagerDat);
                if (damagerArena == null)
                    return;
                // Check if they are in the same arena
                if (alvoArena.getArenaNumber() != damagerArena.getArenaNumber())
                    return;
            }
            PlayerDamage.lastPlayerDamager.remove(alvo.getUniqueId());
        }

        // Messages
        if (damager != null) {
            // Message to arena
            alvoArena.sendMessage(Message.PLAYER_DIED, alvo.getName());
            alvoArena.sendMessage(Message.PLAYER_KILLED_BY, damager.getName());
            arenaDat.addGameEvent(ChatColor.stripColor(alvo.getName() + " foi morto por " + damager.getName() + ".")); // Add to event log
            // Multi Kills
            int numberKills = damagerDat.getGameKills();
            switch(numberKills) {
                case 1:
                    break;
                case 2:
                    alvoArena.sendMessage(Message.PLAYER_KILL_SECOND);
                    arenaDat.addGameEvent(ChatColor.stripColor(damager.getName() + " second kill!")); // Add to event log
                    break;
                case 3:
                    alvoArena.sendMessage(Message.PLAYER_KILL_THIRD);
                    arenaDat.addGameEvent(ChatColor.stripColor(damager.getName() + " third kill!")); // Add to event log
                    break;
                case 4:
                    break;
                case 5:
                    alvoArena.sendMessage(Message.PLAYER_KILL_FIFTH);
                    arenaDat.addGameEvent(ChatColor.stripColor(damager.getName() + " fifth kill!")); // Add to event log
                    break;
                case 6:
                    break;
                case 7:
                    alvoArena.sendMessage(Message.PLAYER_KILL_SEVENTH);
                    arenaDat.addGameEvent(ChatColor.stripColor(damager.getName() + " seventh kill!")); // Add to event log
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    alvoArena.sendMessage(Message.PLAYER_KILL_TENTH);
                    arenaDat.addGameEvent(ChatColor.stripColor(damager.getName() + " tenth kill!")); // Add to event log
                    break;
                default:
                    alvoArena.sendMessage(Message.PLAYER_KILL_UNBELIEVABLE);
                    arenaDat.addGameEvent(ChatColor.stripColor(damager.getName() + " unbelievable kill!")); // Add to event log
                    break;
            }
            // Send his damager camera
            CameraAPI.sendCamera(alvo, damager);
            // Title
            // Target
            TitleAPI.sendTimes(alvo, 5, 60, 5);
            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_DIED, alvo)));
            TitleAPI.sendSubTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_WERE_KILLED_BY, alvo) + damager.getName()));
            // Damager
            TitleAPI.sendTimes(damager, 5, 60, 5);
            TitleAPI.sendTitle(damager, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_KILLED, damager)));
            TitleAPI.sendSubTitle(damager, ConvertersAPI.convertToJSON("§7" + alvo.getName()));
        } else {
            // Message to arena
            alvoArena.sendMessage(Message.PLAYER_DIED, alvo.getName());
            arenaDat.addGameEvent(ChatColor.stripColor(alvo.getName() + " morreu.")); // Add to event log
            // Title
            // Target
            TitleAPI.sendTimes(alvo, 5, 60, 5);
            TitleAPI.sendTitle(alvo, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_DIED, alvo)));
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
