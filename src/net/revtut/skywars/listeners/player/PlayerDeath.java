package net.revtut.skywars.listeners.player;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.arena.ArenaDat;
import net.revtut.skywars.libraries.bypasses.BypassesAPI;
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

        // Update target stats
        alvoDat.addDeath();
        alvoDat.addLose();

        // Hide to Arena
        plugin.arenaManager.hideToArena(alvo, false);

        // Show to spectators
        plugin.arenaManager.showToSpectators(alvo, true);

        // Scoreboard update alive players and dead
        plugin.scoreBoardManager.updateAlive(alvoArena);
        plugin.scoreBoardManager.updateDeath(alvoArena);

        // Update stats
        plugin.scoreBoardManager.updateWinsLosses(alvoDat);
        plugin.scoreBoardManager.updateKillsDeaths(alvoDat);

        // Respawn
        Bukkit.getScheduler().runTaskLater(plugin, () -> BypassesAPI.respawnBypass(alvo), 20L);

        // Damager player
        Player damager = null;
        PlayerDat damagerDat = null;
        if (PlayerDamage.lastPlayerDamager.containsKey(alvo.getUniqueId())) {
            damager = Bukkit.getPlayer(PlayerDamage.lastPlayerDamager.get(alvo.getUniqueId()));
            if(damager != null){
                damagerDat = plugin.playerManager.getPlayerDatByUUID(damager.getUniqueId());
                if (damagerDat != null)
                    if(!plugin.arenaManager.inSameArena(alvoDat, damagerDat))
                        return;
            }
            PlayerDamage.lastPlayerDamager.remove(alvo.getUniqueId());
        }

        // Messages
        if (damager != null && damagerDat != null) {
            // Points earned
            int poinsEarned = (int) (plugin.pointsPerKill + plugin.pointsPerKill * ((float) damagerDat.getGameKills() / arenaDat.getInitialPlayers().size()) + plugin.pointsPerKill * ((float) plugin.rand.nextInt(11) / 100));
            damagerDat.addPoints(poinsEarned);

            // Add kill
            damagerDat.addKill();

            // Update stats
            plugin.scoreBoardManager.updateKillsDeaths(damagerDat);

            // Message to arena
            alvoArena.sendMessage(Message.PLAYER_DIED, alvo.getName());
            alvoArena.sendMessage(Message.PLAYER_KILLED_BY, damager.getName());
            arenaDat.addGameEvent(ChatColor.stripColor(alvo.getName() + " was killed by " + damager.getName() + ".")); // Add to event log

            // Multi Kills
            int numberKills = damagerDat.getGameKills();
            sendMultiKillMessage(numberKills, alvoArena, damager);

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
            arenaDat.addGameEvent(ChatColor.stripColor(alvo.getName() + " died.")); // Add to event log

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
    }

    /**
     * Send the multikill message to the arena
     *
     * @param numberKills number of kills of the damager
     * @param arena arena to send the message
     * @param damager damager that made those kills
     */
    private void sendMultiKillMessage(int numberKills, Arena arena, Player damager) {
        ArenaDat arenaDat = arena.getArenaDat();
        if(arenaDat == null)
            return;

        switch(numberKills) {
            case 1:
                break;
            case 2:
                arena.sendMessage(Message.PLAYER_KILL_SECOND);
                arenaDat.addGameEvent(ChatColor.stripColor(damager.getName() + " second kill!")); // Add to event log
                break;
            case 3:
                arena.sendMessage(Message.PLAYER_KILL_THIRD);
                arenaDat.addGameEvent(ChatColor.stripColor(damager.getName() + " third kill!")); // Add to event log
                break;
            case 4:
                break;
            case 5:
                arena.sendMessage(Message.PLAYER_KILL_FIFTH);
                arenaDat.addGameEvent(ChatColor.stripColor(damager.getName() + " fifth kill!")); // Add to event log
                break;
            case 6:
                break;
            case 7:
                arena.sendMessage(Message.PLAYER_KILL_SEVENTH);
                arenaDat.addGameEvent(ChatColor.stripColor(damager.getName() + " seventh kill!")); // Add to event log
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                arena.sendMessage(Message.PLAYER_KILL_TENTH);
                arenaDat.addGameEvent(ChatColor.stripColor(damager.getName() + " tenth kill!")); // Add to event log
                break;
            default:
                arena.sendMessage(Message.PLAYER_KILL_UNBELIEVABLE);
                arenaDat.addGameEvent(ChatColor.stripColor(damager.getName() + " unbelievable kill!")); // Add to event log
                break;
        }
    }
}
