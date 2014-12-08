package net.RevTut.Skywars.utils;

import net.RevTut.Skywars.SkyWars;
import net.RevTut.Skywars.libraries.language.Language;
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
     * Scoreboard - Dead
     */
    DEAD,

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
     * Hackr bypassed respawn death
     */
    HACKER_RESPAWN,

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
     * Player was killed by someone
     */
    PLAYER_KILLED_BY,

    /**
     * Player has left the game
     */
    PLAYER_LEFT_GAME,

    /**
     * There is an error with the player's profile
     */
    PLAYER_PROFILE_ERROR,

    /**
     * Player tried to spam
     */
    PLAYER_SPAM,

    /**
     * Points of the player
     */
    POINTS,

    /**
     * Tactical has enabled invisibility
     */
    TACTICAL_ENABLED_INVISIBILITY,

    /**
     * Game time out
     */
    TIME_OUT,

    /**
     * Winner of the match
     */
    WINNER,

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
    private static final String inspectorPrefix = "§7[§6Inspector§7]";

    /**
     * Send message to player in his language
     *
     * @param message message type to be sent
     * @param player player to send the message
     * @return translated message type
     */
    public static String getMessage(Message message, Player player){
        switch (message){
            case ALIVE:
                return getAlive(player);
            case ALLOWED_OPEN_CHESTS:
                return gamePrefix + getAllowedOpenChests(player);
            case DEAD:
                return getDead(player);
            case DONT_LEAVE_ARENA:
                return gamePrefix + getDontLeaveArena(player);
            case EMPTY_MESSAGE:
                return "";
            case ENGINEER_PLACED_MINE:
                return gamePrefix + getEngineerPlacedMine(player);
            case GAME_MAP:
                return gamePrefix + getGameMap(player);
            case GAME_REPORT:
                return gamePrefix + getGameReport(player);
            case GAME_TIMEOUT:
                return gamePrefix + getGameTimeout(player);
            case GAME_WINNER:
                return gamePrefix + getGameWinner(player);
            case GUARDIAN_ENABLED_SPEED:
                return gamePrefix + getGuardianEnabledSpeed(player);
            case HACKER_RESPAWN:
                return gamePrefix + getHackerRespawn(player);
            case KIT_ALREADY_CHOSEN:
                return gamePrefix + getKitAlreadyChosen(player);
            case KIT_BOUGHT:
                return gamePrefix + getKitBought(player);
            case KIT_RECEIVED:
                return gamePrefix + getKitReceived(player);
            case MININUM_PLAYERS_NOT_ACHIEVED:
                return gamePrefix + getMinimumPlayersNotAchieved(player);
            case MINIMUM_PLAYERS_REDUCE_TIME_ACHIEVED:
                return gamePrefix + getMinimumPlayersReduceTimeAchieved(player);
            case NOT_ENOUGH_PLAYERS_TO_CONTINUE:
                return gamePrefix + getNotEnoughPlayersToContinue(player);
            case NOT_ENOUGH_POINTS_FOR_KIT:
                return gamePrefix + getNotEnoughPointsForKit(player);
            case NO_WINNER:
                return getNoWinner(player);
            case PLAYER_ADVERTISE:
                return inspectorPrefix + getPlayerAdvertise(player);
            case PLAYER_ARENA_NULL:
                return inspectorPrefix + getPlayerArenaNull(player);
            case PLAYER_BAD_LANGUAGE:
                return inspectorPrefix + getPlayerBadLanguage(player);
            case PLAYER_COOLDOWN_MESSAGES:
                return inspectorPrefix + getPlayerCooldownMessages(player);
            case PLAYER_DEAD_PREFIX:
                return getPlayerDeadPrefix(player);
            case PLAYER_DIED:
                return gamePrefix + getPlayerDied(player);
            case PLAYER_DUPLICATED_MESSAGE:
                return inspectorPrefix + getPlayerDuplicatedMessage(player);
            case PLAYER_JOINED_GAME:
                return gamePrefix + getPlayerJoinedGame(player);
            case PLAYER_KILLED_BY:
                return gamePrefix + getPlayerKilledBy(player);
            case PLAYER_LEFT_GAME:
                return gamePrefix + getPlayerLeftGame(player);
            case PLAYER_PROFILE_ERROR:
                return inspectorPrefix + getPlayerProfileError(player);
            case PLAYER_SPAM:
                return inspectorPrefix + getPlayerSpam(player);
            case POINTS:
                return getPoints(player);
            case TACTICAL_ENABLED_INVISIBILITY:
                return gamePrefix + getTaticalEnabledInvisibility(player);
            case TIME_OUT:
                return getTimeOut(player);
            case WINNER:
                return getWinner(player);
            case YOU_DIED:
                return getYouDied(player);
            case YOU_KILLED:
                return getYouKilled(player);
            case YOU_LOST:
                return getYouLost(player);
            case YOU_WERE_KILLED_BY:
                return getYouWereKilledBy(player);
            case YOU_WON:
                return getYouWon(player);
            default:
                return "";
        }
    }

    /**
     * Get alive message
     *
     * @param player player to get the translated message
     */
    private static String getAlive(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

        switch(language){
            case ENGLISH:
                return "§aAlive: ";
            case FRENCH:
                return "§aVivant: ";
            case GERMAN:
                return "§aLebendig: ";
            case PORTUGUESE_PT:
                return "§aVivos: ";
            case PORTUGUESE_BR:
                return "§aVivos: ";
            case SPANISH:
                return "§aVivo: ";
            default:
                return "§aAlive: ";
        }
    }

    /**
     * Get allowed to open chests message
     *
     * @param player player to get the translated message
     */
    private static String getAllowedOpenChests(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * Get dead message
     *
     * @param player player to get the translated message
     */
    private static String getDead(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

        switch(language){
            case ENGLISH:
                return "§cDead: ";
            case FRENCH:
                return "§cMorts: ";
            case GERMAN:
                return "§cTote: ";
            case PORTUGUESE_PT:
                return "§cMortos: ";
            case PORTUGUESE_BR:
                return "§cMortos: ";
            case SPANISH:
                return "§cMuerto: ";
            default:
                return "§cDead: ";
        }
    }

    /**
     * Get dont leave the arena message
     *
     * @param player player to get the translated message
     */
    private static String getDontLeaveArena(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getEngineerPlacedMine(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getGameMap(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getGameReport(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getGameTimeout(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getGameWinner(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getGuardianEnabledSpeed(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getHackerRespawn(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * Get kit already chosen message
     *
     * @param player player to get the translated message
     */
    private static String getKitAlreadyChosen(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getKitBought(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getKitReceived(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * Get minimum players not achieved message
     *
     * @param player player to get the translated message
     */
    private static String getMinimumPlayersNotAchieved(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getMinimumPlayersReduceTimeAchieved(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getNotEnoughPlayersToContinue(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getNotEnoughPointsForKit(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getNoWinner(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getPlayerAdvertise(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getPlayerArenaNull(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getPlayerBadLanguage(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * Get player cooldown between messages message
     *
     * @param player player to get the translated message
     */
    private static String getPlayerCooldownMessages(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getPlayerDeadPrefix(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getPlayerDied(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getPlayerDuplicatedMessage(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getPlayerLeftGame(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * Get player joined the game message
     *
     * @param player player to get the translated message
     */
    private static String getPlayerJoinedGame(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * Get player killed by message
     *
     * @param player player to get the translated message
     */
    private static String getPlayerKilledBy(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getPlayerProfileError(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getPlayerSpam(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * Get points message
     *
     * @param player player to get the translated message
     */
    private static String getPoints(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

        switch(language){
            case ENGLISH:
                return "§6Points: ";
            case FRENCH:
                return "§6Points: ";
            case GERMAN:
                return "§6Punkte: ";
            case PORTUGUESE_PT:
                return "§6Pontos: ";
            case PORTUGUESE_BR:
                return "§6Pontos: ";
            case SPANISH:
                return "§6Puntos: ";
            default:
                return "§6Points: ";
        }
    }

    /**
     * Get tactical enabled invisibility message
     *
     * @param player player to get the translated message
     */
    private static String getTaticalEnabledInvisibility(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getTimeOut(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * Get winner message
     *
     * @param player player to get the translated message
     */
    private static String getWinner(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * Get you died message
     *
     * @param player player to get the translated message
     */
    private static String getYouDied(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getYouKilled(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getYouLost(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getYouWereKilledBy(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
     * @param player player to get the translated message
     */
    private static String getYouWon(Player player){
        Language language = plugin.playerManager.getPlayerLanguageByUUID(player.getUniqueId());
        if(null == language)
            language = Language.ENGLISH;

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
