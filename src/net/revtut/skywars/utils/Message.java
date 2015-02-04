package net.revtut.skywars.utils;

import net.revtut.skywars.SkyWars;
import net.revtut.skywars.libraries.language.Language;
import net.revtut.skywars.player.PlayerDat;
import org.bukkit.entity.Player;

/**
 * Server Messages.
 *
 * <P>Class that includes all the messages of the game.</P>
 *
 * @author João Silva
 * @version 1.0
 */
public enum Message {
    /**
     * Scoreboard - Alive
     */
    ALIVE,

    /**
     * Players are now allowed to open chests
     */
    ALLOWED_OPEN_CHESTS,

    /**
     * Buy VIP message when trying to use a VIP command
     */
    COMMAND_BUY_VIP,

    /**
     * Command Game Mode Changed
     */
    COMMAND_GAMEMODE_CHANGED,

    /**
     * Command Teleported
     */
    COMMAND_TELEPORT,

    /**
     * Command Time Changed
     */
    COMMAND_TIME_CHANGED,

    /**
     * Command Speed Changed
     */
    COMMAND_SPEED_CHANGED,

    /**
     * Correct sintax message
     */
    CORRECT_SINTAX,

    /**
     * Scoreboard - Dead
     */
    DEAD,

    /**
     * Scoreboard - Deaths
     */
    DEATHS,

    /**
     * Tell the player to not leave the arena
     */
    DONT_LEAVE_ARENA,

    /**
     * Empty message
     */
    EMPTY_MESSAGE,

    /**
     * Engineer placed a mine
     */
    ENGINEER_PLACED_MINE,

    /**
     * Current game map
     */
    GAME_MAP,

    /**
     * Tell the player how to report the fame
     */
    GAME_REPORT,

    /**
     * Game as timed out
     */
    GAME_TIMEOUT,

    /**
     * Winner of the match
     */
    GAME_WINNER,

    /**
     * Guardian enabled speed
     */
    GUARDIAN_ENABLED_SPEED,

    /**
     * Hacker bypassed respawn death
     */
    HACKER_RESPAWN,

    /**
     * Scoreboard - Kills
     */
    KILLS,

    /**
     * Player has aleady chosen the kit
     */
    KIT_ALREADY_CHOSEN,

    /**
     * Player has bought the kit
     */
    KIT_BOUGHT,

    /**
     * Player received the kit
     */
    KIT_RECEIVED,

    /**
     * Scoreboard - Losses
     */
    LOSSES,

    /**
     * Minimum players to start the game has not been achieved
     */
    MININUM_PLAYERS_NOT_ACHIEVED,

    /**
     * Minimum players to reduce the time has been achieved
     */
    MINIMUM_PLAYERS_REDUCE_TIME_ACHIEVED,

    /**
     * Not enough players to continue the match
     */
    NOT_ENOUGH_PLAYERS_TO_CONTINUE,

    /**
     * Not enough points to buy the kit
     */
    NOT_ENOUGH_POINTS_FOR_KIT,

    /**
     * No winner of the match
     */
    NO_WINNER,

    /**
     * Player has tried to advertise
     */
    PLAYER_ADVERTISE,

    /**
     * Player arena is null
     */
    PLAYER_ARENA_NULL,

    /**
     * Player has tried to use bad language
     */
    PLAYER_BAD_LANGUAGE,

    /**
     * Player may not execute that command
     */
    PLAYER_BLOCKED_COMMAND,

    /**
     * Tell the player to wait between commands
     */
    PLAYER_COOLDOWN_COMMANDS,

    /**
     * Tell the player to wait between messages
     */
    PLAYER_COOLDOWN_MESSAGES,

    /**
     * Prefix of a player when he is dead
     */
    PLAYER_DEAD_PREFIX,

    /**
     * Player has died
     */
    PLAYER_DIED,

    /**
     * Player tried to sent duplicated messages
     */
    PLAYER_DUPLICATED_MESSAGE,

    /**
     * Player has joined the game
     */
    PLAYER_JOINED_GAME,

    /**
     * Player fifth kill
     */
    PLAYER_KILL_FIFTH,

    /**
     * Player second kill
     */
    PLAYER_KILL_SECOND,

    /**
     * Player seven kill
     */
    PLAYER_KILL_SEVENTH,

    /**
     * Player tenth kill
     */
    PLAYER_KILL_TENTH,

    /**
     * Player third kill
     */
    PLAYER_KILL_THIRD,

    /**
     * Player unbelievable kill
     */
    PLAYER_KILL_UNBELIEVABLE,

    /**
     * Player was killed by someone
     */
    PLAYER_KILLED_BY,

    /**
     * Player has left the game
     */
    PLAYER_LEFT_GAME,

    /**
     * Player not online message
     */
    PLAYER_NOT_ONLINE,

    /**
     * There is an error with the player's profile
     */
    PLAYER_PROFILE_ERROR,

    /**
     * Player tried to spam
     */
    PLAYER_SPAM,

    /**
     * Player tried to execute a non-existing command
     */
    PLAYER_UNKNOWN_COMMAND,

    /**
     * Play Time
     */
    PLAYTIME,

    /**
     * Scoreboard - Points
     */
    POINTS,

    /**
     * Remaining Time
     */
    REMAINING_TIME,

    /**
     * Tactical has enabled invisibility
     */
    TACTICAL_ENABLED_INVISIBILITY,

    /**
     * Game time out
     */
    TIME_OUT,

    /**
     * Please use integers message
     */
    USE_INTEGERS,

    /**
     * Winner of the match
     */
    WINNER,

    /**
     * Scoreboard - Wins
     */
    WINS,

    /**
     * You have died message
     */
    YOU_DIED,

    /**
     * You have killed message
     */
    YOU_KILLED,

    /**
     * You have lost message
     */
    YOU_LOST,

    /**
     * You were killed by someone message
     */
    YOU_WERE_KILLED_BY,

    /**
     * You have won message
     */
    YOU_WON;

    /**
     * Main class
     */
    public static SkyWars plugin = null;

    /**
     * Game prefix of messages
     */
    private static final String gamePrefix = "§7[§3Sky Wars§7] ";

    /**
     * Inspector prefix of messages
     */
    private static final String inspectorPrefix = "§7[§6Inspector§7] ";

    /**
     * Send message to player in his language
     *
     * @param message message type to be sent
     * @param player player to send the message
     * @return translated message type
     */
    public static String getMessage(Message message, Player player){
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if(playerDat == null)
            return "";

        Language language = playerDat.getLanguage();

        switch (message){
            case ALIVE:
                return getAlive(language);
            case ALLOWED_OPEN_CHESTS:
                return gamePrefix + getAllowedOpenChests(language);
            case COMMAND_BUY_VIP:
                return gamePrefix + getCommandBuyVIP(language);
            case COMMAND_GAMEMODE_CHANGED:
                return gamePrefix + getCommandGameModeChanged(language);
            case COMMAND_TELEPORT:
                return gamePrefix + getCommandTeleport(language);
            case COMMAND_TIME_CHANGED:
                return gamePrefix + getCommandTimeChanged(language);
            case COMMAND_SPEED_CHANGED:
                return gamePrefix + getCommandSpeedChanged(language);
            case CORRECT_SINTAX:
                return inspectorPrefix + getCorrectSintax(language);
            case DEAD:
                return getDead(language);
            case DEATHS:
                return getDeaths(language);
            case DONT_LEAVE_ARENA:
                return gamePrefix + getDontLeaveArena(language);
            case EMPTY_MESSAGE:
                return "";
            case ENGINEER_PLACED_MINE:
                return gamePrefix + getEngineerPlacedMine(language);
            case GAME_MAP:
                return gamePrefix + getGameMap(language);
            case GAME_REPORT:
                return gamePrefix + getGameReport(language);
            case GAME_TIMEOUT:
                return gamePrefix + getGameTimeout(language);
            case GAME_WINNER:
                return gamePrefix + getGameWinner(language);
            case GUARDIAN_ENABLED_SPEED:
                return gamePrefix + getGuardianEnabledSpeed(language);
            case HACKER_RESPAWN:
                return gamePrefix + getHackerRespawn(language);
            case KILLS:
                return getKills(language);
            case KIT_ALREADY_CHOSEN:
                return gamePrefix + getKitAlreadyChosen(language);
            case KIT_BOUGHT:
                return gamePrefix + getKitBought(language);
            case KIT_RECEIVED:
                return gamePrefix + getKitReceived(language);
            case LOSSES:
                return getLosses(language);
            case MININUM_PLAYERS_NOT_ACHIEVED:
                return gamePrefix + getMinimumPlayersNotAchieved(language);
            case MINIMUM_PLAYERS_REDUCE_TIME_ACHIEVED:
                return gamePrefix + getMinimumPlayersReduceTimeAchieved(language);
            case NOT_ENOUGH_PLAYERS_TO_CONTINUE:
                return gamePrefix + getNotEnoughPlayersToContinue(language);
            case NOT_ENOUGH_POINTS_FOR_KIT:
                return gamePrefix + getNotEnoughPointsForKit(language);
            case NO_WINNER:
                return getNoWinner(language);
            case PLAYER_ADVERTISE:
                return inspectorPrefix + getPlayerAdvertise(language);
            case PLAYER_ARENA_NULL:
                return inspectorPrefix + getPlayerArenaNull(language);
            case PLAYER_BAD_LANGUAGE:
                return inspectorPrefix + getPlayerBadLanguage(language);
            case PLAYER_BLOCKED_COMMAND:
                return inspectorPrefix + getPlayerBlockedCommand(language);
            case PLAYER_COOLDOWN_COMMANDS:
                return inspectorPrefix + getPlayerCooldownCommands(language);
            case PLAYER_COOLDOWN_MESSAGES:
                return inspectorPrefix + getPlayerCooldownMessages(language);
            case PLAYER_DEAD_PREFIX:
                return getPlayerDeadPrefix(language);
            case PLAYER_DIED:
                return gamePrefix + getPlayerDied(language);
            case PLAYER_DUPLICATED_MESSAGE:
                return inspectorPrefix + getPlayerDuplicatedMessage(language);
            case PLAYER_JOINED_GAME:
                return gamePrefix + getPlayerJoinedGame(language);
            case PLAYER_KILL_FIFTH:
                return gamePrefix + getPlayerKillFifth(language);
            case PLAYER_KILL_SECOND:
                return gamePrefix + getPlayerKillSecond(language);
            case PLAYER_KILL_SEVENTH:
                return gamePrefix + getPlayerKillSeventh(language);
            case PLAYER_KILL_TENTH:
                return gamePrefix + getPlayerKillTenth(language);
            case PLAYER_KILL_THIRD:
                return gamePrefix + getPlayerKillThird(language);
            case PLAYER_KILL_UNBELIEVABLE:
                return gamePrefix + getPlayerKillUnbelievable(language);
            case PLAYER_KILLED_BY:
                return gamePrefix + getPlayerKilledBy(language);
            case PLAYER_LEFT_GAME:
                return gamePrefix + getPlayerLeftGame(language);
            case PLAYER_NOT_ONLINE:
                return inspectorPrefix + getPlayerNotOnline(language);
            case PLAYER_PROFILE_ERROR:
                return inspectorPrefix + getPlayerProfileError(language);
            case PLAYER_SPAM:
                return inspectorPrefix + getPlayerSpam(language);
            case PLAYER_UNKNOWN_COMMAND:
                return inspectorPrefix + getPlayerUnknownCommand(language);
            case PLAYTIME:
                return getPlayTime(language);
            case POINTS:
                return getPoints(language);
            case REMAINING_TIME:
                return getRemainingTime(language);
            case TACTICAL_ENABLED_INVISIBILITY:
                return gamePrefix + getTaticalEnabledInvisibility(language);
            case TIME_OUT:
                return getTimeOut(language);
            case USE_INTEGERS:
                return inspectorPrefix + getUseIntegers(language);
            case WINNER:
                return getWinner(language);
            case WINS:
                return getWins(language);
            case YOU_DIED:
                return getYouDied(language);
            case YOU_KILLED:
                return getYouKilled(language);
            case YOU_LOST:
                return getYouLost(language);
            case YOU_WERE_KILLED_BY:
                return getYouWereKilledBy(language);
            case YOU_WON:
                return getYouWon(language);
            default:
                return "";
        }
    }

    /**
     * Get alive message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getAlive(Language language){
        switch(language){
            case ENGLISH:
                return "§aAlive";
            case FRENCH:
                return "§aVivant";
            case GERMAN:
                return "§aLebendig";
            case PORTUGUESE_PT:
                return "§aVivos";
            case PORTUGUESE_BR:
                return "§aVivos";
            case SPANISH:
                return "§aVivo";
            default:
                return "§aAlive";
        }
    }

    /**
     * Get allowed to open chests message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getAllowedOpenChests(Language language){
        switch(language){
            case ENGLISH:
                return "§aYou may now open the chests!";
            case FRENCH:
                return "§aVous pouvez maintenant ouvrir les coffres!";
            case GERMAN:
                return "§aSie können jetzt die kisten!";
            case PORTUGUESE_PT:
                return "§aPodes agora abrir os baús!";
            case PORTUGUESE_BR:
                return "§aAgora você pode abrir os baús!";
            case SPANISH:
                return "§aAhora puede abrir los cofres!";
            default:
                return "§aYou may now open the chests!";
        }
    }

    /**
     * Get the buy vip message when trying to use a vip command
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getCommandBuyVIP(Language language){
        switch(language){
            case ENGLISH:
                return "§4You must become VIP to do that!";
            case FRENCH:
                return "§4Vous devez devenir VIP pour faire ça!";
            case GERMAN:
                return "§4Sie müssen VIP, das zu tun zu werden!";
            case PORTUGUESE_PT:
                return "§4Para fazeres isso tens que adquirir o VIP!";
            case PORTUGUESE_BR:
                return "§4Você deve se tornar VIP para fazer isso!";
            case SPANISH:
                return "§4Usted debe convertirse en VIP a hacer eso!";
            default:
                return "§4You must become VIP to do that!";
        }
    }

    /**
     * Get the game mode changed message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getCommandGameModeChanged(Language language){
        switch(language){
            case ENGLISH:
                return "§aYour gamemode has been changed to ";
            case FRENCH:
                return "§aVotre mode de jeu a été changé pour ";
            case GERMAN:
                return "§aIhre Spielmodus wurde geändert ";
            case PORTUGUESE_PT:
                return "§aO modo de jogo foi alterado para ";
            case PORTUGUESE_BR:
                return "§aO seu modo de jogo foi alterado para ";
            case SPANISH:
                return "§aSu modo de juego se ha cambiado a ";
            default:
                return "§aYour gamemode has been changed to ";
        }
    }

    /**
     * Get the teleport to location message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getCommandTeleport(Language language){
        switch(language){
            case ENGLISH:
                return "§aYou have been teleported to ";
            case FRENCH:
                return "§aVous avez été téléporté ";
            case GERMAN:
                return "§aSie haben, um teleportiert ";
            case PORTUGUESE_PT:
                return "§aFoste teleportado para ";
            case PORTUGUESE_BR:
                return "§aVocê foi teleportado para ";
            case SPANISH:
                return "§aUsted ha sido teletransportado a ";
            default:
                return "§aYou have been teleported to ";
        }
    }

    /**
     * Get the time changed message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getCommandTimeChanged(Language language){
        switch(language){
            case ENGLISH:
                return "§aTime has been changed to ";
            case FRENCH:
                return "§aLe temps a été modifié pour ";
            case GERMAN:
                return "§aZeit wurde geändert ";
            case PORTUGUESE_PT:
                return "§aO tempo foi alterado para ";
            case PORTUGUESE_BR:
                return "§aO tempo foi alterado para ";
            case SPANISH:
                return "§aEl tiempo ha sido cambiado a ";
            default:
                return "§aTime has been changed to ";
        }
    }

    /**
     * Get the speed changed message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getCommandSpeedChanged(Language language){
        switch(language){
            case ENGLISH:
                return "§aYour speed has been changed to ";
            case FRENCH:
                return "§aVotre vitesse a été modifiée pour ";
            case GERMAN:
                return "§aIhre Geschwindigkeit wurde geändert ";
            case PORTUGUESE_PT:
                return "§aA sua velocidade foi alterada para ";
            case PORTUGUESE_BR:
                return "§aA sua velocidade foi alterada para ";
            case SPANISH:
                return "§aSu velocidad ha cambiado a ";
            default:
                return "§aYour speed has been changed to ";
        }
    }

    /**
     * Get correct sintax message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getCorrectSintax(Language language){
        switch(language){
            case ENGLISH:
                return "§cCorrect usage: ";
            case FRENCH:
                return "§cUtilisation correcte: ";
            case GERMAN:
                return "§cBestimmungsgemäßer Gebrauch: ";
            case PORTUGUESE_PT:
                return "§cSintaxe correta: ";
            case PORTUGUESE_BR:
                return "§cSintaxe correta: ";
            case SPANISH:
                return "§cUso correcto: ";
            default:
                return "§cCorrect usage: ";
        }
    }

    /**
     * Get dead message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getDead(Language language){
        switch(language){
            case ENGLISH:
                return "§cDead";
            case FRENCH:
                return "§cMorts";
            case GERMAN:
                return "§cTote";
            case PORTUGUESE_PT:
                return "§cMortos";
            case PORTUGUESE_BR:
                return "§cMortos";
            case SPANISH:
                return "§cMuerto";
            default:
                return "§cDead";
        }
    }

    /**
     * Get deaths message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getDeaths(Language language){
        switch(language){
            case ENGLISH:
                return "§6Deaths";
            case FRENCH:
                return "§6Deaths";
            case GERMAN:
                return "§6Deaths";
            case PORTUGUESE_PT:
                return "§6Deaths";
            case PORTUGUESE_BR:
                return "§6Deaths";
            case SPANISH:
                return "§6Deaths";
            default:
                return "§6Deaths";
        }
    }

    /**
     * Get dont leave the arena message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getDontLeaveArena(Language language){
        switch(language){
            case ENGLISH:
                return "§6Do not leave the arena, a new game will start soon!\n" + gamePrefix + "§6If you leave now, you will lose points...";
            case FRENCH:
                return "§6Ne pas quitter l'arène, un nouveau jeu bientôt!\n" + gamePrefix + "§6Si vous sortez maintenant, vous perdrez des points...";
            case GERMAN:
                return "§6Lassen Sie die Arena, ein neues Spiel zu früh.\n" + gamePrefix + "§6Wenn Sie zu Fuß jetzt verlierst du Punkte...";
            case PORTUGUESE_PT:
                return "§6Não saias da arena, um novo jogo em breve!\n" + gamePrefix + "§6Se saires agora, vais perder pontos...";
            case PORTUGUESE_BR:
                return "§6Não deixes a arena, um novo jogo em breve!\n" + gamePrefix + "§6Se você sair agora, irá perder pontos...";
            case SPANISH:
                return "§6No deje la arena, el nuevo juego se iniciará pronto!\n" + gamePrefix + "§6Si te vas ahora, perderá puntos...";
            default:
                return "§6Do not leave the arena, a new game will start soon!\n" + gamePrefix + "§6If you leave now, you will lose points...";
        }
    }

    /**
     * Get engineer placed a mine message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getEngineerPlacedMine(Language language){
        switch(language){
            case ENGLISH:
                return "§6You have placed a mine!";
            case FRENCH:
                return "§6Vous mettez en place une mine!";
            case GERMAN:
                return "§6Sie legte einen meiner!";
            case PORTUGUESE_PT:
                return "§6Colocaste-te uma mina!";
            case PORTUGUESE_BR:
                return "§6Você colocou uma mina!";
            case SPANISH:
                return "§6Has colocado una mina!";
            default:
                return "§6You have placed a mine!";
        }
    }

    /**
     * Get game map message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getGameMap(Language language){
        switch(language){
            case ENGLISH:
                return "§6Game Map: ";
            case FRENCH:
                return "§6Map du jeu: ";
            case GERMAN:
                return "§6Map des Spiels: ";
            case PORTUGUESE_PT:
                return "§6Mapa de Jogo: ";
            case PORTUGUESE_BR:
                return "§6Mapa do Jogo: ";
            case SPANISH:
                return "§6Mapa del Juego: ";
            default:
                return "§6Game Map: ";
        }
    }

    /**
     * Get game report message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getGameReport(Language language){
        switch(language){
            case ENGLISH:
                return "§6To report the game, attach the ID: ";
            case FRENCH:
                return "§6Pour signaler le jeu, fixez le ID: ";
            case GERMAN:
                return "§6Um das Spiel zu berichten, bringen Sie die ID: ";
            case PORTUGUESE_PT:
                return "§6Para reportares o jogo, anexa o ID: ";
            case PORTUGUESE_BR:
                return "§6Para denunciares o jogo, coloca o ID: ";
            case SPANISH:
                return "§6Para reportar el juego, atar la ID: ";
            default:
                return "§6To report the game, attach the ID: ";
        }
    }

    /**
     * Get game timeout message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getGameTimeout(Language language){
        switch(language){
            case ENGLISH:
                return "§4Time out. There was no winner.";
            case FRENCH:
                return "§4Temps libre. Il n'y avait pas de gagnant.";
            case GERMAN:
                return "§4Auszeit. Es gab keine gewinner.";
            case PORTUGUESE_PT:
                return "§4Tempo esgotado. Nao houve um vencedor.";
            case PORTUGUESE_BR:
                return "§4Tempo esgotado. Nao houve um vencedor.";
            case SPANISH:
                return "§4Se acabó el tiempo. No hubo ganador.";
            default:
                return "§4Time out. There was no winner.";
        }
    }

    /**
     * Get game winner message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getGameWinner(Language language){
        switch(language){
            case ENGLISH:
                return "§aThe winner of the match was: ";
            case FRENCH:
                return "§aLe gagnant du jeu était: ";
            case GERMAN:
                return "§aDas spiel gewonnen hat: ";
            case PORTUGUESE_PT:
                return "§aO vencedor do partida foi: ";
            case PORTUGUESE_BR:
                return "§aO vencedor do jogo foi: ";
            case SPANISH:
                return "§aEl ganador del juego fue: ";
            default:
                return "§aThe winner of the match was: ";
        }
    }

    /**
     * Get guardian enabled speed message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getGuardianEnabledSpeed(Language language){
        switch(language){
            case ENGLISH:
                return "§6You have activated rage mode for ";
            case FRENCH:
                return "§6Vous avez activé le mode de rage pour ";
            case GERMAN:
                return "§6Sie haben Wut-Modus aktiviert ";
            case PORTUGUESE_PT:
                return "§6Ativaste o modo de fúria por ";
            case PORTUGUESE_BR:
                return "§6Você ativou o modo de fúria por ";
            case SPANISH:
                return "§6Se activa el modo de rabia por ";
            default:
                return "§6You have activated rage mode for ";
        }
    }

    /**
     * Get hacker respawn message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getHackerRespawn(Language language){
        switch(language){
            case ENGLISH:
                return "§6You have hacked the death and respawned!";
            case FRENCH:
                return "§6Vous avez piraté la mort et de respawn!";
            case GERMAN:
                return "§6Sie haben den Tod und die Respawn gehackt!";
            case PORTUGUESE_PT:
                return "§6Hackeaste a morte e ressuscitaste!";
            case PORTUGUESE_BR:
                return "§6Você hackeou a morte e ressuscitou!";
            case SPANISH:
                return "§6Has hackeado la muerte y resucitado!";
            default:
                return "§6You have hacked the death and respawned!";
        }
    }

    /**
     * Get kills message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getKills(Language language){
        switch(language){
            case ENGLISH:
                return "§6Kills";
            case FRENCH:
                return "§6Kills";
            case GERMAN:
                return "§6Kills";
            case PORTUGUESE_PT:
                return "§6Kills";
            case PORTUGUESE_BR:
                return "§6Kills";
            case SPANISH:
                return "§6Kills";
            default:
                return "§6Kills";
        }
    }

    /**
     * Get kit already chosen message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getKitAlreadyChosen(Language language){
        switch(language){
            case ENGLISH:
                return "§4You have already chosen your kit!";
            case FRENCH:
                return "§4Vous avez déjà choisi votre kit!";
            case GERMAN:
                return "§4Sie haben Ihre Kit gewählt!";
            case PORTUGUESE_PT:
                return "§4O teu kit ja foi escolhido!";
            case PORTUGUESE_BR:
                return "§4Você já escolheu o seu kit!";
            case SPANISH:
                return "§4Usted ya ha elegido su kit!";
            default:
                return "§4You have already chosen your kit!";
            }
    }

    /**
     * Get kit bought message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getKitBought(Language language){
        switch(language){
            case ENGLISH:
                return "§6You have bought Kit ";
            case FRENCH:
                return "§6Vous avez acheté Kit ";
            case GERMAN:
                return "§6Sie haben Kit gekauft ";
            case PORTUGUESE_PT:
                return "§6Adquiriste o Kit ";
            case PORTUGUESE_BR:
                return "§6Você comprou o Kit ";
            case SPANISH:
                return "§6Usted ha comprado el Kit ";
            default:
                return "§6You have bought Kit ";
        }
    }

    /**
     * Get kit received message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getKitReceived(Language language){
        switch(language){
            case ENGLISH:
                return "§aYou have received Kit ";
            case FRENCH:
                return "§aVous avez reçu Kit ";
            case GERMAN:
                return "§aSie haben Kit erhalten";
            case PORTUGUESE_PT:
                return "§aRecebeste o Kit ";
            case PORTUGUESE_BR:
                return "§aVocê recebeu o Kit ";
            case SPANISH:
                return "§aHa recibido Kit ";
            default:
                return "§aYou have received Kit ";
        }
    }

    /**
     * Get losses message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getLosses(Language language){
        switch(language){
            case ENGLISH:
                return "§6Losses";
            case FRENCH:
                return "§6Pertes";
            case GERMAN:
                return "§6Verluste";
            case PORTUGUESE_PT:
                return "§6Derrotas";
            case PORTUGUESE_BR:
                return "§6Derrotas";
            case SPANISH:
                return "§6Derrotas";
            default:
                return "§6Losses";
        }
    }

    /**
     * Get minimum players not achieved message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getMinimumPlayersNotAchieved(Language language){
        switch(language){
            case ENGLISH:
                return "§4The minimum number of players has not been reached.\n" + gamePrefix + "§4Minimum players: §6";
            case FRENCH:
                return "§4Le nombre minimum de joueurs n'a pas été atteint.\n" + gamePrefix + "§4Minimum de joueurs: §6";
            case GERMAN:
                return "§4Die minimale anzahl von spielern noch nicht erreicht wurde.\n" + gamePrefix + "§4Minimale spieler: §6";
            case PORTUGUESE_PT:
                return "§4O numero minimo de jogadores nao foi alcançado.\n" + gamePrefix + "§4Minimo de jogadores: §6";
            case PORTUGUESE_BR:
                return "§4Nao foi atingido o numero minimo de jogadores.\n" + gamePrefix + "§4Jogadores minimos: §6";
            case SPANISH:
                return "§4No se ha alcanzado el número mínimo de jugadores.\n" + gamePrefix + "§4Jugadores mínimos: §6";
            default:
                return "§4The minimum number of players has not been reached.\n" + gamePrefix + "§4Minimum players: §6";
        }
    }

    /**
     * Get minimum players to reduce time message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getMinimumPlayersReduceTimeAchieved(Language language){
        switch(language){
            case ENGLISH:
                return "§6The time was reduced to §330 §6seconds.\n" + gamePrefix + "§6Number of players: §3";
            case FRENCH:
                return "§6Le temps a été réduit à §330 §6secondes.\n" + gamePrefix + "§6Nombre de joueurs: §3";
            case GERMAN:
                return "§6Die zeit wurde auf §330 §6sekunden reduziert.\n" + gamePrefix + "§6Anzahl der spieler: §3";
            case PORTUGUESE_PT:
                return "§6O tempo foi reduzido para §330 §6segundos.\n" + gamePrefix + "§6Numero de jogadores: §3";
            case PORTUGUESE_BR:
                return "§6O tempo foi reduzido para §330 §6segundos.\n" + gamePrefix + "§6Numero de jogadores: §3";
            case SPANISH:
                return "§6El tiempo se redujo a §330 §6segundos.\n" + gamePrefix + "§6Número de jugadores: §3";
            default:
                return "§6The time was reduced to §330 §6seconds.\n" + gamePrefix + "§6Number of players: §3";
        }
    }

    /**
     * Get not enough players to continue the game message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getNotEnoughPlayersToContinue(Language language){
        switch(language){
            case ENGLISH:
                return "§4Assigned to a new arena due to insufficient players!";
            case FRENCH:
                return "§4Assigné à un nouvel aréna en raison de l'insuffisance des joueurs!";
            case GERMAN:
                return "§4Zu einer neuen arena zugeordnet aufgrund unzureichender spieler!";
            case PORTUGUESE_PT:
                return "§4Assignado a uma nova arena devido a jogadores insuficientes!";
            case PORTUGUESE_BR:
                return "§4Designado para uma nova arena, devido a insuficiencia de jogadores!";
            case SPANISH:
                return "§4Asignado a una nueva arena debido a la insuficiencia de los jugadores!";
            default:
                return "§4Assigned to a new arena due to insufficient players!";
        }
    }

    /**
     * Get not enough points for kit message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getNotEnoughPointsForKit(Language language){
        switch(language){
            case ENGLISH:
                return "§4You do not have enough points for the kit!";
            case FRENCH:
                return "§4Vous ne avez pas assez de points pour le kit!";
            case GERMAN:
                return "§4Sie haben nicht genug Punkte für das Kit!";
            case PORTUGUESE_PT:
                return "§4Pontos insuficientes para o kit!";
            case PORTUGUESE_BR:
                return "§4Você não tem pontos suficientes para o kit!";
            case SPANISH:
                return "§4Usted no tiene suficientes puntos para el kit!";
            default:
                return "§4You do not have enough points for the kit!";
        }
    }

    /**
     * Get no winner message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getNoWinner(Language language){
          switch(language){
            case ENGLISH:
                return "§7NO WINNER";
            case FRENCH:
                return "§7PAS DE GAGNANT";
            case GERMAN:
                return "§7NO WINNER";
            case PORTUGUESE_PT:
                return "§7SEM VENCEDOR";
            case PORTUGUESE_BR:
                return "§7SEM VENCEDOR";
            case SPANISH:
                return "§7NO GANADOR";
            default:
                return "§7NO WINNER";
        }
    }

    /**
     * Get player advertisement is not allowed message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerAdvertise(Language language){
        switch(language){
            case ENGLISH:
                return "§4Advertisement will lead you to a permanent ban!";
            case FRENCH:
                return "§4Publicité vous mènera à une interdiction définitive!";
            case GERMAN:
                return "§4Werbung werden Sie zu einem permanenten Bann führen!";
            case PORTUGUESE_PT:
                return "§4Publicidade é igual a banimento permanente!";
            case PORTUGUESE_BR:
                return "§4Publicidade irá levá-lo a um banimento permanente!";
            case SPANISH:
                return "§4Publicidad le llevará a una prohibición permanente!";
            default:
                return "§4Advertisement will lead you to a permanent ban!";
        }
    }

    /**
     * Get player's arena is null message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerArenaNull(Language language){
        switch(language){
            case ENGLISH:
                return "§4You are not in any arena! Relog please.";
            case FRENCH:
                return "§4Vous n'êtes pas dans ne importe quel domaine! Reconnecter se il vous plaît.";
            case GERMAN:
                return "§4Sie sind nicht in irgendeiner Arena! Bitte verbinden.";
            case PORTUGUESE_PT:
                return "§4Não te encontras em nenhuma arena! Reloga por favor.";
            case PORTUGUESE_BR:
                return "§4Você não se encontra em nenhuma arena! Relogue por favor.";
            case SPANISH:
                return "§4Usted no está en cualquier arena! Relog favor.";
            default:
                return "§4You are not in any arena! Relog please.";
        }
    }

    /**
     * Get player bad language message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerBadLanguage(Language language){
        switch(language){
            case ENGLISH:
                return "§4Please do not use bad language!";
            case FRENCH:
                return "§4Se il vous plaît ne pas utiliser mauvaise langue!";
            case GERMAN:
                return "§4Bitte verwenden Sie keine schlechte Sprache!";
            case PORTUGUESE_PT:
                return "§4Por favor não uses má linguagem!";
            case PORTUGUESE_BR:
                return "§4Por favor não use linguagem ruim!";
            case SPANISH:
                return "§4Por favor, no use malas palabras!";
            default:
                return "§4Please do not use bad language!";
        }
    }

    /**
     * Get player blocked command message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerBlockedCommand(Language language){
        switch(language){
            case ENGLISH:
                return "§4Whooo! Many available commands, but not this one...";
            case FRENCH:
                return "§4Whooo! Beaucoup de commandes disponibles, mais pas celui-ci...";
            case GERMAN:
                return "§4Whooo! Viele verfügbaren Befehle, aber nicht diese...";
            case PORTUGUESE_PT:
                return "§4Whooo! Tantos comandos disponíveis, mas não este...";
            case PORTUGUESE_BR:
                return "§4Whooo! Muitos comandos disponíveis, mas não este...";
            case SPANISH:
                return "§4Whooo! Muchos de los comandos disponibles, pero no ésta...";
            default:
                return "§4Whooo! Many available commands, but not this one...";
        }
    }

    /**
     * Get player cooldown between commands message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerCooldownCommands(Language language){
         switch(language){
            case ENGLISH:
                return "§4You have to wait for 3 seconds between commands.";
            case FRENCH:
                return "§4Se il vous plaît attendre 3 secondes entre les commands.";
            case GERMAN:
                return "§4Bitte warten Sie 3 Sekunden zwischen Befehle.";
            case PORTUGUESE_PT:
                return "§4Por favor espera 3 segundos entre comands.";
            case PORTUGUESE_BR:
                return "§4Você tem que 3 segundos esperar entre comandos.";
            case SPANISH:
                return "§4Tienes que esperar 3 segundos entre comandos.";
            default:
                return "§4You have to wait for 3 seconds between commands.";
        }
    }

    /**
     * Get player cooldown between messages message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerCooldownMessages(Language language){
        switch(language){
            case ENGLISH:
                return "§4You have to wait for 3 seconds between messages.";
            case FRENCH:
                return "§4Se il vous plaît attendre 3 secondes entre les messages.";
            case GERMAN:
                return "§4Bitte warten Sie 3 Sekunden zwischen Nachrichten.";
            case PORTUGUESE_PT:
                return "§4Por favor espere 3 segundos entre mensagens.";
            case PORTUGUESE_BR:
                return "§4Você tem que 3 segundos esperar entre mensagens.";
            case SPANISH:
                return "§4Tienes que esperar 3 segundos entre mensajes.";
            default:
                return "§4You have to wait for 3 seconds between messages.";
        }
    }

    /**
     * Get player dead prefix
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerDeadPrefix(Language language){
        switch(language){
            case ENGLISH:
                return "§7|§4DEAD§7| ";
            case FRENCH:
                return "§7|§4MORT§7| ";
            case GERMAN:
                return "§7|§4TOTE§7| ";
            case PORTUGUESE_PT:
                return "§7|§4MORTO§7| ";
            case PORTUGUESE_BR:
                return "§7|§4MORTO§7| ";
            case SPANISH:
                return "§7|§4MUERTO§7| ";
            default:
                return "§7|§4DEAD§7| ";
        }
    }

    /**
     * Get player died message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerDied(Language language){
        switch(language){
            case ENGLISH:
                return "§4Died: ";
            case FRENCH:
                return "§4Mort: ";
            case GERMAN:
                return "§4Verstorben: ";
            case PORTUGUESE_PT:
                return "§4Morreu: ";
            case PORTUGUESE_BR:
                return "§4Morreu: ";
            case SPANISH:
                return "§4Murió: ";
            default:
                return "§4Died: ";
        }
    }

    /**
     * Get player duplicated messages are not allowed message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerDuplicatedMessage(Language language){
        switch(language){
            case ENGLISH:
                return "§4You can not send duplicated messages!";
            case FRENCH:
                return "§4Vous ne pouvez pas envoyer des messages en double!";
            case GERMAN:
                return "§4Sie können keine doppelten Nachrichten versenden können!";
            case PORTUGUESE_PT:
                return "§4Não podes mandar mensagens duplicadas!";
            case PORTUGUESE_BR:
                return "§4Você não pode mandar mensagens duplicadas!";
            case SPANISH:
                return "§4No puede enviar mensajes duplicados!";
            default:
                return "§4You can not send duplicated messages!";
        }
    }

    /**
     * Get player left the game message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerLeftGame(Language language){
        switch(language){
            case ENGLISH:
                return "§6Left the game ";
            case FRENCH:
                return "§6A quitté l'arène ";
            case GERMAN:
                return "§6Verließ die arena ";
            case PORTUGUESE_PT:
                return "§6Abandonou a arena ";
            case PORTUGUESE_BR:
                return "§6Saiu da arena ";
            case SPANISH:
                return "§6Ha dejado el juego ";
            default:
                return "§6Left the game ";
        }
    }

    /**
     * Get player not online message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerNotOnline(Language language){
        switch(language){
            case ENGLISH:
                return "§4That player is not online!";
            case FRENCH:
                return "§4Ce joueur ne est pas online!";
            case GERMAN:
                return "§4Dieser Spieler ist nicht online!";
            case PORTUGUESE_PT:
                return "§4Esse jogador não se encontra online!";
            case PORTUGUESE_BR:
                return "§4Esse jogador não está online!";
            case SPANISH:
                return "§4Ese jugador no está online!";
            default:
                return "§4That player is not online!";
        }
    }

    /**
     * Get player joined the game message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerJoinedGame(Language language){
        switch(language){
            case ENGLISH:
                return "§6Joined the game ";
            case FRENCH:
                return "§6Entré dans l'arène ";
            case GERMAN:
                return "§6In die arena ";
            case PORTUGUESE_PT:
                return "§6Entrou na arena ";
            case PORTUGUESE_BR:
                return "§6Entrou na arena ";
            case SPANISH:
                return "§6Se unió al juego ";
            default:
                return "§6Joined the game ";
        }
    }

    /**
     * Get player fifth kill message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerKillFifth(Language language){
         switch(language){
            case ENGLISH:
                return "§6§lFIFTH KILL! OMG!!!!!";
            case FRENCH:
                return "§6§lCINQUIÈME MORT! OMG !!!!!";
            case GERMAN:
                return "§6§lFÜNFTE KILL! OH MEIN GOTT!!!!!";
            case PORTUGUESE_PT:
                return "§6§lQUINTA MATANÇA! MEU DEUS!!!!!";
            case PORTUGUESE_BR:
                return "§6§lQUINTA MATANÇA! MEU DEUS!!!!!";
            case SPANISH:
                return "§6§lQUINTA MATANZA! DIOS MIO!!!!!";
            default:
                return "§6§lFIFTH KILL! OMG!!!!!";
        }
    }

    /**
     * Get player second kill message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerKillSecond(Language language){
        switch(language){
            case ENGLISH:
                return "§6Second kill!";
            case FRENCH:
                return "§6Deuxième mort!";
            case GERMAN:
                return "§6Zweiter Sieg!";
            case PORTUGUESE_PT:
                return "§6Segunda matança!";
            case PORTUGUESE_BR:
                return "§6Segunda matança!";
            case SPANISH:
                return "§6Segundo matanza!";
            default:
                return "§6Second kill!";
        }
    }

    /**
     * Get player seventh kill message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerKillSeventh(Language language){
        switch(language){
            case ENGLISH:
                return "§b§lDOMINATING WITH SEVEN KILLS!!!!";
            case FRENCH:
                return "§b§lDOMINANT AVEC SEPT MORTS!!!!";
            case GERMAN:
                return "§b§lDOMINIERT WIRD MIT SIEBEN KILLS!!!!";
            case PORTUGUESE_PT:
                return "§b§lA DOMINAR COM SETE MATANÇAS!!!!";
            case PORTUGUESE_BR:
                return "§b§lA DOMINAR COM SETE MATANÇAS!!!!";
            case SPANISH:
                return "§b§lDOMINANDO CON SIETE MATANZAS!!!!";
            default:
                return "§b§lDOMINATING WITH SEVEN KILLS!!!!";
        }
    }

    /**
     * Get player tenth kill message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerKillTenth(Language language){
        switch(language){
            case ENGLISH:
                return "§a§lTHIS IS GOD LIKE! TENTH KILL!!!!!!";
            case FRENCH:
                return "§a§lCE EST DIEU! KILL DIXIÈME!!!!!!";
            case GERMAN:
                return "§a§lDIES IST WIE GOTT! ZEHNTE KILL!!!!!!";
            case PORTUGUESE_PT:
                return "§a§lISTO É COMO DEUS! DÉCIMA MATANÇA!!!!!!";
            case PORTUGUESE_BR:
                return "§a§lISTO É COMO DEUS! DÉCIMA MATANÇA!!!!!!";
            case SPANISH:
                return "§a§lESTO ES COMO DIOS! DÉCIMO MATANZA!!!!!!";
            default:
                return "§a§lTHIS IS GOD LIKE! TENTH KILL!!!!!!";
        }
    }

    /**
     * Get player third kill message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerKillThird(Language language){
        switch(language){
            case ENGLISH:
                return "§6Third kill! This player is good!";
            case FRENCH:
                return "§6Troisième kill! Ce joueur est bon!";
            case GERMAN:
                return "§6Drittens töten! Dieser Spieler ist gut!";
            case PORTUGUESE_PT:
                return "§6Terceira matança! Este jogador é bom!";
            case PORTUGUESE_BR:
                return "§6Terceira matança! Este jogador é bom!";
            case SPANISH:
                return "§6Tercer kill! Este jugador es bueno!";
            default:
                return "§6Third kill! This player is good!";
        }
    }

    /**
     * Get player unbelievable kill message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerKillUnbelievable(Language language){
        switch(language){
            case ENGLISH:
                return "§4§k@ §e§lUNBELIEVABLE! HE IS §4§lUNSTOPPABLE§e§l!!!! §4§k@";
            case FRENCH:
                return "§4§k@ §e§lINCROYABLE! IL EST §4§lIMPARABLE§e§l!!!! §4§k@";
            case GERMAN:
                return "§4§k@ §e§lUNGLAUBLICH! ER IST NICHT §4§lAUFZUHALTEN§e§l!!!! §4§k@";
            case PORTUGUESE_PT:
                return "§4§k@ §e§lINACREDITAVEL! ELE É §4§lIMPARAVEL§e§l!!!! §4§k@";
            case PORTUGUESE_BR:
                return "§4§k@ §e§lINACREDITAVEL! ELE É §4§lIMPARAVEL§e§l!!!! §4§k@";
            case SPANISH:
                return "§4§k@ §e§lINCREIBLE! ÉL ES §4§lIMPARABLE§e§l!!!! §4§k@";
            default:
                return "§4§k@ §e§lUNBELIEVABLE! HE IS §4§lUNSTOPPABLE§e§l!!!! §4§k@";
        }
    }

    /**
     * Get player killed by message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerKilledBy(Language language){
        switch(language){
            case ENGLISH:
                return "§4Killed By: ";
            case FRENCH:
                return "§4Mort Pour: ";
            case GERMAN:
                return "§4Tote Für: ";
            case PORTUGUESE_PT:
                return "§4Morto Por: ";
            case PORTUGUESE_BR:
                return "§4Morto Por: ";
            case SPANISH:
                return "§4Matado Por: ";
            default:
                return "§4Killed By: ";
        }
    }

    /**
     * Get player profile error message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerProfileError(Language language){
        switch(language){
            case ENGLISH:
                return "§4There is an error with your profile! Relog please.";
            case FRENCH:
                return "§4Il ya une erreur avec votre profil! Reconnecter se il vous plaît.";
            case GERMAN:
                return "§4Es ist ein Fehler mit Ihrem Profil! Bitte verbinden.";
            case PORTUGUESE_PT:
                return "§4Há um erro com o teu perfil! Reloga por favor.";
            case PORTUGUESE_BR:
                return "§4Houve um erro com o seu perfil! Relogue por favor.";
            case SPANISH:
                return "§4Hay un error con tu perfil! Relog favor.";
            default:
                return "§4There is an error with your profile! Relog please.";
        }
    }

    /**
     * Get player spam is not allowed message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerSpam(Language language){
        switch(language){
            case ENGLISH:
                return "§4Please do not spam!";
            case FRENCH:
                return "§4Se il vous plaît ne pas le spam!";
            case GERMAN:
                return "§4Bitte kein Spam!";
            case PORTUGUESE_PT:
                return "§4Por favor nao faças spam!";
            case PORTUGUESE_BR:
                return "§4Por favor nao faça spam!";
            case SPANISH:
                return "§4Por favor, no spam!";
            default:
                return "§4Please do not spam!";
        }
    }

    /**
     * Get player unknown command message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayerUnknownCommand(Language language){
        switch(language){
            case ENGLISH:
                return "§4Whooo! Many available commands, but not this one...";
            case FRENCH:
                return "§4Whooo! Beaucoup de commandes disponibles, mais pas celui-ci...";
            case GERMAN:
                return "§4Whooo! Viele verfügbaren Befehle, aber nicht diese...";
            case PORTUGUESE_PT:
                return "§4Whooo! Tantos comandos disponíveis, mas não este...";
            case PORTUGUESE_BR:
                return "§4Whooo! Muitos comandos disponíveis, mas não este...";
            case SPANISH:
                return "§4Whooo! Muchos de los comandos disponibles, pero no ésta...";
            default:
                return "§4Whooo! Many available commands, but not this one...";
        }
    }

    /**
     * Get play time message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPlayTime(Language language){
        switch(language){
            case ENGLISH:
                return "§6Play Time";
            case FRENCH:
                return "§6Temps de Jeu";
            case GERMAN:
                return "§6Spielzeit";
            case PORTUGUESE_PT:
                return "§6Tempo de Jogo";
            case PORTUGUESE_BR:
                return "§6Tempo de Jogo";
            case SPANISH:
                return "§6Tiempo de Juego";
            default:
                return "§6Play Time";
        }
    }

    /**
     * Get points message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getPoints(Language language){
        switch(language){
            case ENGLISH:
                return "§6Points";
            case FRENCH:
                return "§6Points";
            case GERMAN:
                return "§6Punkte";
            case PORTUGUESE_PT:
                return "§6Pontos";
            case PORTUGUESE_BR:
                return "§6Pontos";
            case SPANISH:
                return "§6Puntos";
            default:
                return "§6Points";
        }
    }

    /**
     * Get the remaining time message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getRemainingTime(Language language){
        switch(language){
            case ENGLISH:
                return "§6Remaining Time";
            case FRENCH:
                return "§6Temps Restant";
            case GERMAN:
                return "§6Verbleibende Zeit";
            case PORTUGUESE_PT:
                return "§6Tempo Restante";
            case PORTUGUESE_BR:
                return "§6Tempo Restante";
            case SPANISH:
                return "§6Tiempo Restante";
            default:
                return "§6Remaining Time";
        }
    }

    /**
     * Get tactical enabled invisibility message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getTaticalEnabledInvisibility(Language language){
        switch(language){
            case ENGLISH:
                return "§6You have activated ghost mode for ";
            case FRENCH:
                return "§6Vous avez activé le mode fantôme pour ";
            case GERMAN:
                return "§6Sie haben Geistermodus aktiviert ";
            case PORTUGUESE_PT:
                return "§6Ativaste o modo fantasma por ";
            case PORTUGUESE_BR:
                return "§6Você ativou o modo fantasma por ";
            case SPANISH:
                return "§6Ha activado el modo de fantasma por ";
            default:
                return "§6You have activated ghost mode for ";
        }
    }

    /**
     * Get time out message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getTimeOut(Language language){
        switch(language){
            case ENGLISH:
                return "§4TIME OUT";
            case FRENCH:
                return "§4TEMPS LIBRE";
            case GERMAN:
                return "§4AUSZEIT";
            case PORTUGUESE_PT:
                return "§4TEMPO ESGOTADO";
            case PORTUGUESE_BR:
                return "§4TEMPO ESGOTADO";
            case SPANISH:
                return "§4SE ACABÓ EL TIEMPO";
            default:
                return "§4TIME OUT";
        }
    }

    /**
     * Get use integers message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getUseIntegers(Language language){
        switch(language){
            case ENGLISH:
                return "§4Please use integers.";
            case FRENCH:
                return "§4Se il vous plaît utiliser les nombres entiers.";
            case GERMAN:
                return "§4Bitte verwenden Sie ganze Zahlen.";
            case PORTUGUESE_PT:
                return "§4Por favor usar números inteiros.";
            case PORTUGUESE_BR:
                return "§4Você tem que usar números inteiros.";
            case SPANISH:
                return "§4Por favor, use números enteros.";
            default:
                return "§4Please use integers.";
        }
    }

    /**
     * Get winner message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getWinner(Language language){
        switch(language){
            case ENGLISH:
                return "§7Winner: ";
            case FRENCH:
                return "§7Gagnant: ";
            case GERMAN:
                return "§7Sieger: ";
            case PORTUGUESE_PT:
                return "§7Vencedor: ";
            case PORTUGUESE_BR:
                return "§7Vencedor: ";
            case SPANISH:
                return "§7Ganador: ";
            default:
                return "§7Winner: ";
        }
    }

    /**
     * Get wins message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getWins(Language language){
        switch(language){
            case ENGLISH:
                return "§6Wins";
            case FRENCH:
                return "§6Victoires";
            case GERMAN:
                return "§6Siege";
            case PORTUGUESE_PT:
                return "§6Vitórias";
            case PORTUGUESE_BR:
                return "§6Vitórias";
            case SPANISH:
                return "§6Victorias";
            default:
                return "§6Wins";
        }
    }

    /**
     * Get you died message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getYouDied(Language language){
        switch(language){
            case ENGLISH:
                return "§4YOU HAVE DIED";
            case FRENCH:
                return "§aVOUS ÊTES MORTS";
            case GERMAN:
                return "§4GESTORBEN";
            case PORTUGUESE_PT:
                return "§4MORRESTE";
            case PORTUGUESE_BR:
                return "§4VOCÊ MORREU";
            case SPANISH:
                return "§4USTED HA MORRIDO";
            default:
                return "§4YOU HAVE DIED";
        }
    }

    /**
     * Get you killed message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getYouKilled(Language language){
        switch(language){
            case ENGLISH:
                return "§aYOU HAVE KILLED";
            case FRENCH:
                return "§aVOUS AVEZ TUÉ";
            case GERMAN:
                return "§aSIE HABEN GETÖTET";
            case PORTUGUESE_PT:
                return "§aMATASTE";
            case PORTUGUESE_BR:
                return "§aVOCÊ MATOU";
            case SPANISH:
                return "§aUSTED HA MATADO";
            default:
                return "§aYOU HAVE KILLED";
        }
    }

    /**
     * Get you lost message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getYouLost(Language language){
        switch(language){
            case ENGLISH:
                return "§4YOU HAVE LOST";
            case FRENCH:
                return "§aVOUS AVEZ PERDU";
            case GERMAN:
                return "§4SIE VERLOREN";
            case PORTUGUESE_PT:
                return "§4PERDESTE";
            case PORTUGUESE_BR:
                return "§4VOCÊ PERDEU";
            case SPANISH:
                return "§4USTED HA PERDIDO";
            default:
                return "§4YOU HAVE LOST";
        }
    }

    /**
     * Get player you were killed by message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getYouWereKilledBy(Language language){
        switch(language){
            case ENGLISH:
                return "§7Killed By: ";
            case FRENCH:
                return "§7Mort Pour: ";
            case GERMAN:
                return "§7Tote Für: ";
            case PORTUGUESE_PT:
                return "§7Morto Por: ";
            case PORTUGUESE_BR:
                return "§7Morto Por: ";
            case SPANISH:
                return "§7Muerto Por: ";
            default:
                return "§7Killed By: ";
        }
    }

    /**
     * Get you won message
     *
     * @param language language to get the translation
     * @return translated message
     */
    private static String getYouWon(Language language){
        switch(language){
            case ENGLISH:
                return "§aYOU HAVE WON";
            case FRENCH:
                return "§aVOUS AVEZ GAGNÉ";
            case GERMAN:
                return "§aDU HAST GEWONNEN";
            case PORTUGUESE_PT:
                return "§aVENCESTE";
            case PORTUGUESE_BR:
                return "§aVOCÊ VENCEU";
            case SPANISH:
                return "§aUSTED HA GANADO";
            default:
                return "§aYOU HAVE WON";
        }
    }
}
