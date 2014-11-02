package net.RevTut.Skywars.arena;

import net.RevTut.Skywars.libraries.titles.TitleAPI;
import net.RevTut.Skywars.player.PlayerDat;
import net.RevTut.Skywars.utils.Converters;
import net.minecraft.util.com.google.common.base.Converter;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

/**
 * Created by João on 02/11/2014.
 */
public class ArenaRunnable implements Runnable {

    private static int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
       this.id = id;
    }

    public static void cancel() {
        Bukkit.getScheduler().cancelTask(id);
    }

    @Override
    public void run(){
        int remainingTime;
        for(Arena arena : Arena.getArenas()){
            remainingTime = arena.getRemainingTime();
            // Time is over ZERO
            if(remainingTime >= 0){
                if(arena.getStatus() == ArenaStatus.LOBBY)
                    onLobby(arena);
                else if(arena.getStatus() == ArenaStatus.PREGAME)
                    onPreGame(arena);
            }
            arena.setRemainingTime(remainingTime--);
        }
    }

    /* Lobby */
    public void onLobby(Arena arena){
        int remainingTime = arena.getRemainingTime();
        for(PlayerDat alvoDat : arena.getPlayers()){
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if(alvo == null)
                break;
            alvo.setLevel(remainingTime);
        }
    }

    /* PreGame */
    public void onPreGame(Arena arena){
        int remainingTime = arena.getRemainingTime();
        for(PlayerDat alvoDat : arena.getPlayers()){
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if(alvo == null)
                break;
            alvo.setLevel(remainingTime);
            switch (remainingTime){
                case 10:
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§b10"));
                    break;
                case 5:
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§a5"));
                    break;
                case 4:
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§e4"));
                    break;
                case 3:
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§63"));
                    break;
                case 2:
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§c2"));
                    break;
                case 1:
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§41"));
                    break;
                case 0:
                    TitleAPI.sendTitle(alvo, Converters.convertToJSON("§aGO"));
                    alvo.playSound(alvo.getLocation(), Sound.ORB_PICKUP, 1, 1);
                    break;
            }
        }
    }

   //Jogar task = new Jogar(this);
   //task.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, 20, 20));
}
