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
    MINIMUM_PLAYERS_REDUCE_TIME_ACHIEVED;

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
                return "§4The minimum number of players has not been reached.\n§4Minimum players: §6";
            case FRENCH:
                return "§4Le nombre minimum de joueurs n'a pas été atteint.\n§4Minimum de joueurs: §6";
            case GERMAN:
                return "§4Die minimale anzahl von spielern noch nicht erreicht wurde.\n§4Minimale spieler: §6";
            case PORTUGUESE_PT:
                return "§4O numero minimo de jogadores nao foi alcançado.\n§4Minimo de jogadores: §6";
            case PORTUGUESE_BR:
                return "§4Nao foi atingido o numero minimo de jogadores.\n§4Jogadores minimos: §6";
            default:
                return "§4The minimum number of players has not been reached.\n§4Minimum players: §6";
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
                return "§6The time was reduced to §330 §6seconds.\n§6Number of players: §3";
            case FRENCH:
                return "§6Le temps a été réduit à §330 §6secondes.\n§6Nombre de joueurs: §3";
            case GERMAN:
                return "§6Die zeit wurde auf §330 §6sekunden reduziert.\n§6Anzahl der spieler: §3";
            case PORTUGUESE_PT:
                return "§6O tempo foi reduzido para §330 §6 segundos.\n§6Numero de jogadores: §3";
            case PORTUGUESE_BR:
                return "§6O tempo foi reduzido para §330 §6 segundos.\n§6Numero de jogadores: §3";
            default:
                return "§6The time was reduced to §330 §6seconds.\n§6Number of players: §3";
        }
    }
}
