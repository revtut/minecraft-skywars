package net.RevTut.Skywars.listeners;

import net.RevTut.Skywars.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by waxcoder on 01-11-2014.
 */

public class Weather implements Listener {
    private final Main plugin;

    public Weather(Main plugin) {
        this.plugin = plugin;
    }

    /* Ao mudar o tempo cancela o evento*/
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    /* Ao alterar o trovejar cancela o evento */
    @EventHandler
    public void onThunderChange(ThunderChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onLightningStrike(LightningStrikeEvent e) {
        e.setCancelled(true);
    }
}
