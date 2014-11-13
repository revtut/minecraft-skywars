package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.player.PlayerDat;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Player Death.
 *
 * <P>Controls the death event.</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class PlayerDeath implements Listener {

    /** Main class */
    private final Main plugin;

    /**
     * Constructor
     *
     * @param plugin    main class
     */
    public PlayerDeath(Main plugin) {
        this.plugin = plugin;
    }

    /**
     *  Takes care of a player when he dies
     *
     * @param e     player death event
     * @see         PlayerDeathEvent
     */
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        p.sendMessage("You died!" + PlayerDat.getPlayerDatByUUID(p.getUniqueId()).getDeaths());
    }

}
