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
    GAME_MAP,
    GAME_REPORT,
    GAME_TIMEOUT,
    GAME_WINNER,
    MININUM_PLAYERS_NOT_ACHIEVED,
    MINIMUM_PLAYERS_REDUCE_TIME_ACHIEVED,
    NOT_ENOUGH_PLAYERS_TO_CONTINUE,
    PLAYER_JOINED_GAME,
    PLAYER_LEFT_GAME;

    /**
     * Prefix of game messages
     */
    private static String prefix = "§7| §3Sky Wars§7| ";

    public static String getMessage(Message message, Player player){
        switch (message){
            case GAME_MAP:
                return prefix + getGameMap(player);
            case GAME_REPORT:
                return prefix + getGameReport(player);
            case GAME_TIMEOUT:
                return prefix + getGameTimeout(player);
            case GAME_WINNER:
                return prefix + getGameWinner(player);
            case MININUM_PLAYERS_NOT_ACHIEVED:
                return prefix + getMinimumPlayersNotAchieved(player);
            case MINIMUM_PLAYERS_REDUCE_TIME_ACHIEVED:
                return prefix + getMinimumPlayersReduceTimeAchieved(player);
            case NOT_ENOUGH_PLAYERS_TO_CONTINUE:
                return prefix + getNotEnoughPlayersToContinue(player);
            case PLAYER_JOINED_GAME:
                return prefix + getPlayerJoinedGame(player);
            case PLAYER_LEFT_GAME:
                return prefix + getPlayerLeftGame(player);
            default:
                return "";
        }
    }

    /**
     * Game map message
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
     * Game report message
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
     * Game timeout message
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
     * Game winner message
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
     * Minimum players not achieved message
     *
     * @param player player to get the translated message
     */
    private static String getMinimumPlayersNotAchieved(Player player){
        Language language = LanguageAPI.getLanguage(player);
        if(null == language)
            language = Language.ENGLISH;

        switch(language){
            case ENGLISH:
                return "§4The minimum number of players has not been reached.\n" + prefix + "§4Minimum players: §6";
            case FRENCH:
                return "§4Le nombre minimum de joueurs n'a pas été atteint.\n" + prefix + "§4Minimum de joueurs: §6";
            case GERMAN:
                return "§4Die minimale anzahl von spielern noch nicht erreicht wurde.\n" + prefix + "§4Minimale spieler: §6";
            case PORTUGUESE_PT:
                return "§4O numero minimo de jogadores nao foi alcançado.\n" + prefix + "§4Minimo de jogadores: §6";
            case PORTUGUESE_BR:
                return "§4Nao foi atingido o numero minimo de jogadores.\n" + prefix + "§4Jogadores minimos: §6";
            default:
                return "§4The minimum number of players has not been reached.\n" + prefix + "§4Minimum players: §6";
        }
    }

    /**
     * Minimum players to reduce time message
     *
     * @param player player to get the translated message
     */
    private static String getMinimumPlayersReduceTimeAchieved(Player player){
        Language language = LanguageAPI.getLanguage(player);
        if(null == language)
            language = Language.ENGLISH;

        switch(language){
            case ENGLISH:
                return "§6The time was reduced to §330 §6seconds.\n" + prefix + "§6Number of players: §3";
            case FRENCH:
                return "§6Le temps a été réduit à §330 §6secondes.\n" + prefix + "§6Nombre de joueurs: §3";
            case GERMAN:
                return "§6Die zeit wurde auf §330 §6sekunden reduziert.\n" + prefix + "§6Anzahl der spieler: §3";
            case PORTUGUESE_PT:
                return "§6O tempo foi reduzido para §330 §6 segundos.\n" + prefix + "§6Numero de jogadores: §3";
            case PORTUGUESE_BR:
                return "§6O tempo foi reduzido para §330 §6 segundos.\n" + prefix + "§6Numero de jogadores: §3";
            default:
                return "§6The time was reduced to §330 §6seconds.\n" + prefix + "§6Number of players: §3";
        }
    }

    /**
     * Not enough players to continue the game message
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
     * Player left the game message
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
     * Player joined the game message
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
}
