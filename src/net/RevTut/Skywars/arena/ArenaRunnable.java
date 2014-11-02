package net.RevTut.Skywars.arena;

import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.Bukkit;
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
            if(remainingTime > 0){
                arena.setRemainingTime(remainingTime--);
                if(arena.getStatus() == ArenaStatus.LOBBY)
                    onLobby(arena);
            }
        }
    }

    public void onLobby(Arena arena){
        int remainingTime = arena.getRemainingTime();
        for(PlayerDat alvoDat : arena.getPlayers()){
            Player alvo = Bukkit.getPlayer(alvoDat.getUUID());
            if(alvo == null)
                break;
            alvo.setLevel(remainingTime);
        }
    }

   //Jogar task = new Jogar(this);
   //task.setId(Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, 20, 20));
}
