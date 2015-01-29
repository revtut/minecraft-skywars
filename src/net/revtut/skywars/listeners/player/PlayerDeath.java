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

        // Arena
        Arena arena = plugin.arenaManager.getArenaByPlayer(alvoDat);
        if (arena == null)
            return;

        // ArenaDat
        ArenaDat arenaDat = arena.getArenaDat();
        if (arenaDat == null) {
            plugin.getLogger().log(Level.WARNING, "ArenaDat is null when player dies!");
            return;
        }

        // Hacker
        if (arena.getKitManager().hacker.canRespawn(alvoDat, arena)){
            // Bypass respawn
            BypassesAPI.respawnBypass(alvo);
            return;
        }

        // Respawn
        Bukkit.getScheduler().runTaskLater(plugin, () -> BypassesAPI.respawnBypass(alvo), 20L);

        // Hide to Arena
        plugin.arenaManager.hideToArena(alvo, false);

        // Show to spectators
        plugin.arenaManager.showToSpectators(alvo, true);

        // Scoreboard update alive players and dead
        plugin.scoreBoardManager.updateAlive(arena);
        plugin.scoreBoardManager.updateDeath(arena);

        // Damager player
        Player damager = getLastDamager(alvo);

        // Messages
        if (damager != null) {
            killedByPlayer(arena, alvo, damager);
        } else {
            killedByNature(arena, alvo);
        }

        // Check if game ended
        if (arena.getAlivePlayers().size() <= 1) {
            if(arena.getAlivePlayers().size() == 1){
                // Winner Dat
                PlayerDat winnerDat = arena.getAlivePlayers().get(0);
                if (winnerDat != null){
                    arenaDat.setWinner(winnerDat.getUUID().toString());
                    winnerDat.setStatus(PlayerStatus.WAITING); // Set status so he cannot die
                }
            }
            // Set Remaining Time
            arena.setRemainingTime(0);
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

    private void updateKilledPlayer(Arena arena, Player target) {
        final PlayerDat targetDat = plugin.playerManager.getPlayerDatByUUID(target.getUniqueId());
        if (targetDat == null)
            return;

        // Update player
        targetDat.setStatus(PlayerStatus.DEAD);
        target.setGameMode(GameMode.SPECTATOR);

        // Update target stats
        targetDat.addDeath();
        targetDat.addLose();

        // Update stats
        plugin.scoreBoardManager.updateWinsLosses(targetDat);
        plugin.scoreBoardManager.updateKillsDeaths(targetDat);
    }

    /**
     * Take care of what to do when a player is killed by another player
     *
     * @param arena arena where player was killed
     * @param target player that was killed
     * @param damager player that killed
     */
    private void killedByPlayer(Arena arena, Player target, Player damager) {
        PlayerDat damagerDat = plugin.playerManager.getPlayerDatByUUID(damager.getUniqueId());
        if(damagerDat == null)
            return;

        // Points earned
        int poinsEarned = (int) (plugin.pointsPerKill + plugin.pointsPerKill * ((float) damagerDat.getGameKills() / arena.getArenaDat().getInitialPlayers().size()) + plugin.pointsPerKill * ((float) plugin.rand.nextInt(11) / 100));
        damagerDat.addPoints(poinsEarned);

        // Add kill
        damagerDat.addKill();

        // Update stats
        plugin.scoreBoardManager.updateKillsDeaths(damagerDat);

        // Message to arena
        arena.sendMessage(Message.PLAYER_DIED, target.getName());
        arena.sendMessage(Message.PLAYER_KILLED_BY, damager.getName());
        arena.getArenaDat().addGameEvent(ChatColor.stripColor(target.getName() + " was killed by " + damager.getName() + ".")); // Add to event log

        // Multi Kills
        int numberKills = damagerDat.getGameKills();
        sendMultiKillMessage(numberKills, arena, damager);

        // Target
        TitleAPI.sendTimes(target, 5, 60, 5);
        TitleAPI.sendTitle(target, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_DIED, target)));
        TitleAPI.sendSubTitle(target, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_WERE_KILLED_BY, target) + damager.getName()));

        // Damager
        TitleAPI.sendTimes(damager, 5, 60, 5);
        TitleAPI.sendTitle(damager, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_KILLED, damager)));
        TitleAPI.sendSubTitle(damager, ConvertersAPI.convertToJSON("§7" + target.getName()));
    }

    /**
     * Take care of what to do when a player is killed by nature
     *
     * @param arena arena where player was killed
     * @param target killed player
     */
    private void killedByNature(Arena arena, Player target) {
        // Message to arena
        arena.sendMessage(Message.PLAYER_DIED, target.getName());
        arena.getArenaDat().addGameEvent(ChatColor.stripColor(target.getName() + " died.")); // Add to event log

        // Target
        TitleAPI.sendTimes(target, 5, 60, 5);
        TitleAPI.sendTitle(target, ConvertersAPI.convertToJSON(Message.getMessage(Message.YOU_DIED, target)));
        TitleAPI.sendSubTitle(target, ConvertersAPI.convertToJSON(""));
    }

    /**
     * Get the last damager of a player
     *
     * @param target player to get the last damager
     * @return last damager
     */
    private Player getLastDamager(Player target) {
        final PlayerDat alvoDat = plugin.playerManager.getPlayerDatByUUID(target.getUniqueId());
        if (alvoDat == null)
            return null;

        Player damager = null;
        PlayerDat damagerDat;
        if (PlayerDamage.lastPlayerDamager.containsKey(target.getUniqueId())) {
            damager = Bukkit.getPlayer(PlayerDamage.lastPlayerDamager.get(target.getUniqueId()));
            if(damager != null){
                damagerDat = plugin.playerManager.getPlayerDatByUUID(damager.getUniqueId());
                if (damagerDat != null)
                    if(!plugin.arenaManager.inSameArena(alvoDat, damagerDat))
                        return null;
            }
            PlayerDamage.lastPlayerDamager.remove(target.getUniqueId());
        }
        return damager;
    }
}