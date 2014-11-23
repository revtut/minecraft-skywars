package net.RevTut.Skywars.listeners.player;

import net.RevTut.Skywars.SkyWars;
import net.RevTut.Skywars.arena.Arena;
import net.RevTut.Skywars.arena.ArenaDat;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.player.PlayerStatus;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Player Chat.
 *
 * <P>Controls the chat event.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class PlayerChat implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of PlayerChat
     *
     * @param plugin main class
     */
    public PlayerChat(final SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * IP address pattern
     */
    private final Pattern ip = Pattern.compile("((?<![0-9])(?:(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))(?![0-9]))");

    /**
     * Web address pattern
     */
    private final Pattern web = Pattern.compile("(http://)|(https://)?(www)?\\S{2,}((\\.com)|(\\.ru)|(\\.net)|(\\.org)|(\\.co\\.uk)|(\\.tk)|(\\.info)|(\\.es)|(\\.de)|(\\.arpa)|(\\.edu)|(\\.firm)|(\\.me)|(\\.pt)|(\\.int)|(\\.mil)|(\\.mobi)|(\\.nato)|(\\.to)|(\\.fr)|(\\.ms)|(\\.vu)|(\\.eu)|(\\.nl)|(\\.us)|(\\.dk))");

    /**
     * Web address exceptions
     */
    private final String[] excepcoes_publicidade = {"revtut.net", "youtube.com/RevTut", "facebook.com/RevTut", "twitter.com/RevTut"};

    /**
     * Blocked words
     */
    private final String[] blocked = {"***", "merda", "lixo", "cabrao", "puta", "fdp", "pariu", "foder", "sexo", "fornicar", "chupa", "caralho", "craftlandia", "cabra", "paneleiro", "gay", "lesbica", "shit", "fuck", "fodase", "foderte", "fodasse", "fdx", "vadia", "vadio", "azeiteiro", "veado", "veada", "boi", "camelo", "amor", "amote", "namorar", "cona", "vagina", "piça", "pissa", "pila", "rabo", "lamber", "coninha", "pilinha", "quilhoes", "culhoes", "quilhos", "culhões", "quilhões", "testiculos", "escroto", "karalho", "namo", "vaitomarno",};

    /**
     * Last message sent
     */
    private final Map<UUID, String> lastMessage = new HashMap<UUID, String>();

    /**
     * Player chat cooldown
     */
    private final Map<UUID, Long> cooldownMessage = new HashMap<UUID, Long>();

    /**
     * Control the chat of the server. Reformats the messages sent. Before sending
     * checks for bad words, advertisment, duplicated messages and spam
     *
     * @param e async player chat event
     * @see AsyncPlayerChatEvent
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();

        // Cancel event. You always do as you cannot speak in Global Chat so you just send a message to the arena
        e.setCancelled(true);

        // PlayerDat
        PlayerDat playerDat = plugin.playerManager.getPlayerDatByUUID(player.getUniqueId());
        if (null == playerDat) {
            player.sendMessage("§7[§6Inspetor§7] §4Ha um erro com o teu perfil! Reloga por favor.");
            return;
        }

        // Arena
        Arena playerArena = plugin.arenaManager.getArenaByPlayer(playerDat);
        if (null == playerArena) {
            player.sendMessage("§7[§6Inspetor§7] §4Nao te encontras em nenhuma arena! Reloga por favor.");
            return;
        }

        // ArenaDat
        ArenaDat arenaDat = playerArena.getArenaDat();
        if (null == arenaDat) {
            player.sendMessage("§7[§6Inspetor§7] §4Existe um erro na tua Arena! Reloga por favor.");
            return;
        }

        // Cooldown
        if (cooldownMessage.containsKey(player.getUniqueId())) {
            // Tempo passado desde a Ultima Mensagem
            final long diff = (System.currentTimeMillis() - cooldownMessage.get(player.getUniqueId())) / 1000;
            if (diff < 3) {
                player.sendMessage("§7[§6Inspetor§7] §4Por favor espera 3 segundos entre mensagens.");
                e.setCancelled(true);
                return;
            } else {
                cooldownMessage.remove(player.getUniqueId());
            }
        }

        // Verificacoes a Mensagem
        String mensagem = e.getMessage();
        // Mensagem Para Verificacoes
        String mensagem_verificacoes = mensagem;
        // Substituir Numeros Por Letras
        mensagem_verificacoes = mensagem_verificacoes.replaceAll("0", "o").replaceAll("1", "i").replaceAll("2", "s").replaceAll("3", "e").replaceAll("4", "a").replaceAll("5", "s").replaceAll("6", "g").replaceAll("@", "a").replaceAll("&", "e").replaceAll(" ", "");
        // Tirar Acentos Nas Letras
        mensagem_verificacoes = Normalizer.normalize(mensagem_verificacoes, Normalizer.Form.NFD);
        // Tirar Carateres Nao ALPHA-NUMERICOS
        mensagem_verificacoes = mensagem_verificacoes.replaceAll("[^A-Za-z0-9*]", "");

        // Publicidade
        if (!player.hasPermission("rev.advertisment")) {
            boolean verificar = true; // Variavel que diz se tem algum site permitido ou nao
            /** Verificar se o website pode ser dito **/
            for (final String block : excepcoes_publicidade) {
                if (mensagem.toLowerCase().contains(block.toLowerCase())) {
                    verificar = false;
                }
            }
            final Matcher matcherip = ip.matcher(mensagem);
            final Matcher matcherweb = web.matcher(mensagem);
            if (verificar)
                if (matcherip.find() || matcherweb.find()) {
                    player.sendMessage("§7[§6Inspetor§7] §4Publicidade é igual a banimento permanente!");
                    return;
                }
        }

        // Ma Linguagem
        if (!player.hasPermission("rev.badlanguage")) {
            for (final String block : blocked) {
                if (mensagem_verificacoes.toLowerCase().contains(block.toLowerCase())) {
                    player.sendMessage("§7[§6Inspetor§7] §4Por favor nao uses ma linguagem!");
                    return;
                }
            }
        }

        // Mensagem Duplicada
        if (!player.hasPermission("rev.doublemsg")) {
            if (lastMessage.containsKey(player.getUniqueId())) {
                if (lastMessage.get(player.getUniqueId()).equalsIgnoreCase(mensagem_verificacoes)) {
                    player.sendMessage("§7[§6Inspetor§7] §4Nao podes mandar mensagens duplicadas!");
                    return;
                } else
                    lastMessage.put(player.getUniqueId(), mensagem_verificacoes);
            } else
                lastMessage.put(player.getUniqueId(), mensagem_verificacoes);
        }

        // CapsLock
        if (!player.hasPermission("rev.capslock")) {
            char c;
            int upperCase = 0; // Numero Letras Em Maiuscula
            int total = 0; // Numero Total de Letras
            int numeroLetrasRepetidas = 0; // Numero de Letras Seguidas Repetidas
            char primeira = mensagem.charAt(0); // Letra a Verificar Se Repetida

            for (int i = 0; i < mensagem.length(); i++) {
                c = mensagem.charAt(i);
                if (Character.isLetter(c)) {
                    if (Character.isUpperCase(c)) {
                        upperCase++;
                    }
                    total++;
                }
                if (Character.toLowerCase(primeira) == Character.toLowerCase(c)) {
                    numeroLetrasRepetidas++;
                    if (numeroLetrasRepetidas >= 4) {
                        player.sendMessage("§7[§6Inspetor§7] §4Por favor nao faças spam!");
                        return;
                    }
                } else {
                    primeira = c;
                    numeroLetrasRepetidas = 1;
                }
            }

            double percentagem;
            if (0 == total || 0 == upperCase) {
                percentagem = 0;
            } else {
                percentagem = (double) upperCase / total * 100;
            }
            if (percentagem >= 70) {
                mensagem = mensagem.toLowerCase();
            }
        }

        if (!player.hasPermission("rev.cooldownbp")) {
            /** Adicionar ao Cooldown **/
            cooldownMessage.put(player.getUniqueId(), System.currentTimeMillis());
        }

        // Colorir Mensagem
        if (player.hasPermission("rev.msgcolors")) {
            mensagem = mensagem.replaceAll("&", "§");
        }

        // Formato da Mensagem
        if(playerDat.getStatus() == PlayerStatus.DEAD)
            playerArena.sendMessage("§7|§4MORTO§7| " + player.getDisplayName() + " §6» §f" + mensagem);
        else
            playerArena.sendMessage(player.getDisplayName() + " §6» §f" + mensagem);
        arenaDat.addGameChat(ChatColor.stripColor(player.getDisplayName() + " » " + mensagem)); // Add to chat log
    }
}
