package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.utils.PlayerDat;
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


public class PlayerChat implements Listener {
    /* Padroes */
    private final Pattern ip = Pattern.compile("((?<![0-9])(?:(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2})[ ]?[., ][ ]?(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9]{1,2}))(?![0-9]))");
    private final Pattern web = Pattern.compile("(http://)|(https://)?(www)?\\S{2,}((\\.com)|(\\.ru)|(\\.net)|(\\.org)|(\\.co\\.uk)|(\\.tk)|(\\.info)|(\\.es)|(\\.de)|(\\.arpa)|(\\.edu)|(\\.firm)|(\\.int)|(\\.mil)|(\\.mobi)|(\\.nato)|(\\.to)|(\\.fr)|(\\.ms)|(\\.vu)|(\\.eu)|(\\.nl)|(\\.us)|(\\.dk))");
    /* Palavras Bloqueadas */
    private final String[] excepcoes_publicidade = {"revtut.net", "youtube.com/RevTut", "facebook.com/RevTut", "twitter.com/RevTut"};
    private final String[] blocked = {"***", "merda", "lixo", "cabrao", "puta", "fdp", "pariu", "foder", "sexo", "fornicar", "chupa", "caralho", "craftlandia", "cabra", "paneleiro", "gay", "lesbica", "shit", "fuck", "fodase", "foderte", "fodasse", "fdx", "vadia", "vadio", "azeiteiro", "veado", "veada", "boi", "camelo", "amor", "amote", "namorar", "cona", "vagina", "piça", "pissa", "pila", "rabo", "lamber", "coninha", "pilinha", "quilhoes", "culhoes", "quilhos", "culhões", "quilhões", "testiculos", "escroto", "karalho", "namo", "vaitomarno",};
    /* Mensagem Anterior */
    private Map<UUID, String> lastMessage = new HashMap<UUID, String>();
    /* Cooldown */
    private Map<UUID, Long> cooldownMessage = new HashMap<UUID, Long>();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        PlayerDat playerDat = PlayerDat.getPlayerDatByUUID(player.getUniqueId());
        if (null == playerDat) {
            player.sendMessage("§7[§6Inspetor§7] §4Ha um erro com o teu perfil! Reloga por favor.");
            e.setCancelled(true);
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
        mensagem = mensagem.replaceAll("%", "%%");
        // Mensagem Para Verificacoes
        String mensagem_verificacoes = mensagem;
        // Substituir Numeros Por Letras
        mensagem_verificacoes = mensagem_verificacoes.replaceAll("0", "o").replaceAll("1", "i").replaceAll("2", "s").replaceAll("3", "e").replaceAll("4", "a").replaceAll("6", "g").replaceAll("@", "a").replaceAll("&", "e").replaceAll(" ", "");
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
                    e.setCancelled(true);
                    return;
                }
        }

        // Ma Linguagem
        if (!player.hasPermission("rev.badlanguage")) {
            for (final String block : blocked) {
                if (mensagem_verificacoes.toLowerCase().contains(block.toLowerCase())) {
                    player.sendMessage("§7[§6Inspetor§7] §4Por favor nao uses ma linguagem!");
                    e.setCancelled(true);
                    return;
                }
            }
        }

        // Mensagem Duplicada
        if (!player.hasPermission("rev.doublemsg")) {
            if (lastMessage.containsKey(player.getUniqueId())) {
                if (lastMessage.get(player.getUniqueId()).equalsIgnoreCase(mensagem_verificacoes)) {
                    player.sendMessage("§7[§6Inspetor§7] §4Nao podes mandar mensagens duplicadas!");
                    e.setCancelled(true);
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
                        e.setCancelled(true);
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
        e.setFormat(player.getDisplayName() + " §6» §f" + mensagem);
    }
}