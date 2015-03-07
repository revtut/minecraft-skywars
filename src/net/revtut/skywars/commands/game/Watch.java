package net.revtut.skywars.commands.game;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.arena.Arena;
import net.revtut.skywars.player.PlayerDat;
import net.revtut.skywars.player.PlayerStatus;
import net.revtut.skywars.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Watch Game Command.
 *
 * <P>Command to watch a game.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class Watch implements CommandExecutor {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of Watch
     *
     * @param plugin main class
     */
    public Watch(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Controls the execution of the Watch command.
     *
     * @param sender entity who sent the command
     * @param cmd command sent
     * @param commandLabel label of the command
     * @param args arguments sent
     */
    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("rev.watch")) {
                player.sendMessage(Message.getMessage(Message.PLAYER_BLOCKED_COMMAND, player));
                return true;
            }

            if (args.length != 1) {
                player.sendMessage(Message.getMessage(Message.CORRECT_SINTAX, player) + "/watch <player>");
                return true;
            }

            Player alvo = Bukkit.getPlayer(args[0]);
            if (alvo == null) {
                player.sendMessage(Message.getMessage(Message.PLAYER_NOT_ONLINE, player));
                return true;
            }

            if(alvo.getUniqueId().equals(player.getUniqueId())) {
                player.sendMessage("§7[§6Inspector§7] §4Don't watch yourself...");
                return true;
            }

            // Remove from current arena
            PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
            if(playerDat == null) {
                player.sendMessage(Message.getMessage(Message.PLAYER_PROFILE_ERROR, player));
                return true;
            }
            plugin.arenaManager.removePlayer(playerDat, true);


            // Add as spectator to new arena
            PlayerDat targetDat = plugin.playerManager.getPlayerDatByUUID(alvo.getUniqueId());
            if(targetDat == null) {
                player.sendMessage("§7[§6Inspector§7] §4Error! The target profile is null!");
                return true;
            }

            Arena arenaTarget = plugin.arenaManager.getArenaByPlayer(targetDat);
            if(arenaTarget == null) {
                player.sendMessage("§7[§6Inspector§7] §4Error! The arena of the target is null!");
                return true;
            }

            arenaTarget.getPlayers().add(playerDat);

            // Update player
            playerDat.setStatus(PlayerStatus.SPECTATOR);
            player.setGameMode(GameMode.SPECTATOR);

            // Update ScoreBoard to player
            plugin.scoreBoardManager.updateAlive(arenaTarget, playerDat);
            plugin.scoreBoardManager.updateDeath(arenaTarget, playerDat);

            // Unhide to Arena (show all players to the player)
            plugin.arenaManager.unhideToArena(player, true);

            // Hide to Arena
            plugin.arenaManager.hideToArena(player, false);

            // Show to spectators
            plugin.arenaManager.showToSpectators(player, true);

            player.teleport(alvo);

            player.sendMessage("§aYou are watching " + args[0] + "!");
            return true;
        } else {
            sender.sendMessage("§4Console blocked command!");
            return true;
        }
    }
}
