package net.RevTut.Skywars.utils;

import net.RevTut.Skywars.libraries.language.Language;
import net.RevTut.Skywars.libraries.language.LanguageAPI;
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
    ALLOWED_OPEN_CHESTS,
    DONT_LEAVE_ARENA,
    EMPTY_MESSAGE,
    ENGINEER_PLACED_MINE,
    GAME_MAP,
    GAME_REPORT,
    GAME_TIMEOUT,
    GAME_WINNER,
    GUARDIAN_ENABLED_SPEED,
    HACKER_RESPAWN,
    MININUM_PLAYERS_NOT_ACHIEVED,
    MINIMUM_PLAYERS_REDUCE_TIME_ACHIEVED,
    NOT_ENOUGH_PLAYERS_TO_CONTINUE,
    PLAYER_ADVERTISE,
    PLAYER_ARENA_NULL,
    PLAYER_BAD_LANGUAGE,
    PLAYER_COOLDOWN_MESSAGES,
    PLAYER_DEAD_PREFIX,
    PLAYER_DUPLICATED_MESSAGE,
    PLAYER_JOINED_GAME,
    PLAYER_LEFT_GAME,
    PLAYER_PROFILE_ERROR,
    PLAYER_SPAM,
    TACTICAL_ENABLED_INVISIBILITY;

    /**
     * Game prefix of messages
     */
    private static String gamePrefix = "§7[§3Sky Wars§7] ";

    /**
     * Inspector prefix of messages
     */
    private static String inspectorPrefix = "§7[§6Inspector§7]";

    public static String getMessage(Message message, Player player){
        switch (message){
            case ALLOWED_OPEN_CHESTS:
                return gamePrefix + getAllowedOpenChests(player);
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
            case MININUM_PLAYERS_NOT_ACHIEVED:
                return gamePrefix + getMinimumPlayersNotAchieved(player);
            case MINIMUM_PLAYERS_REDUCE_TIME_ACHIEVED:
                return gamePrefix + getMinimumPlayersReduceTimeAchieved(player);
            case NOT_ENOUGH_PLAYERS_TO_CONTINUE:
                return gamePrefix + getNotEnoughPlayersToContinue(player);
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
            case PLAYER_DUPLICATED_MESSAGE:
                return inspectorPrefix + getPlayerDuplicatedMessage(player);
            case PLAYER_JOINED_GAME:
                return gamePrefix + getPlayerJoinedGame(player);
            case PLAYER_LEFT_GAME:
                return gamePrefix + getPlayerLeftGame(player);
            case PLAYER_PROFILE_ERROR:
                return inspectorPrefix + getPlayerProfileError(player);
            case PLAYER_SPAM:
                return inspectorPrefix + getPlayerSpam(player);
            case TACTICAL_ENABLED_INVISIBILITY:
                return gamePrefix + getTaticalEnabledInvisibility(player);
            default:
                return "";
        }
    }

    /**
     * Get allowed to open chests message
     *
     * @param player player to get the translated message
     */
    private static String getAllowedOpenChests(Player player){
        Language language = LanguageAPI.getLanguage(player);
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
                return "§aAgora voce pode abrir os baús!";
            default:
                return "§aYou may now open the chests!";
        }
    }

    /**
     * Get dont leave the arena message
     *
     * @param player player to get the translated message
     */
    private static String getDontLeaveArena(Player player){
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
                return "§4O vencedor do jogo foi: ";
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
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
            default:
                return "§6You have hacked the death and respawned!";
        }
    }

    /**
     * Get minimum players not achieved message
     *
     * @param player player to get the translated message
     */
    private static String getMinimumPlayersNotAchieved(Player player){
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
                return "§6O tempo foi reduzido para §330 §6 segundos.\n" + gamePrefix + "§6Numero de jogadores: §3";
            case PORTUGUESE_BR:
                return "§6O tempo foi reduzido para §330 §6 segundos.\n" + gamePrefix + "§6Numero de jogadores: §3";
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
        Language language = LanguageAPI.getLanguage(player);
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
            default:
                return "§4Assigned to a new arena due to insufficient players!";
        }
    }

    /**
     * Get player advertisement is not allowed message
     *
     * @param player player to get the translated message
     */
    private static String getPlayerAdvertise(Player player){
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
            default:
                return "§7|§4DEAD§7| ";
        }
    }

    /**
     * Get player duplicated messages are not allowed message
     *
     * @param player player to get the translated message
     */
    private static String getPlayerDuplicatedMessage(Player player){
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
            default:
                return "§6Joined the game ";
        }
    }

    /**
     * Get player profile error message
     *
     * @param player player to get the translated message
     */
    private static String getPlayerProfileError(Player player){
        Language language = LanguageAPI.getLanguage(player);
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
        Language language = LanguageAPI.getLanguage(player);
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
            default:
                return "§4Please do not spam!";
        }
    }

    /**
     * Get tactical enabled invisibility message
     *
     * @param player player to get the translated message
     */
    private static String getTaticalEnabledInvisibility(Player player){
        Language language = LanguageAPI.getLanguage(player);
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
            default:
                return "§6You have activated ghost mode for ";
        }
    }
}
