package net.RevTut.Skywars.listeners.environment;

import net.RevTut.Skywars.SkyWars;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Weather.
 *
 * <P>Controls the weather events.</P>
 *
 * @author WaxCoder
 * @version 1.0
 */
public class Weather implements Listener {

    /**
     * Main class
     */
    private final SkyWars plugin;

    /**
     * Constructor of Weather
     *
     * @param plugin main class
     */
    public Weather(SkyWars plugin) {
        this.plugin = plugin;
    }

    /**
     * Takes care of changes on weather
     *
     * @param e weather change event
     * @see WeatherChangeEvent
     */
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    /**
     * Takes care of changes on thunders
     *
     * @param e thunder change event
     * @see ThunderChangeEvent
     */
    @EventHandler
    public void onThunderChange(ThunderChangeEvent e) {
        e.setCancelled(true);
    }

    /**
     * Takes care of lightning strike
     *
     * @param e lightning strike event
     * @see LightningStrikeEvent
     */
    @EventHandler
    public void onLightningStrike(LightningStrikeEvent e) {
        e.setCancelled(true);
    }
}
