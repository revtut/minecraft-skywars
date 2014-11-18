package net.RevTut.Skywars.managers;

import net.RevTut.Skywars.Main;
import net.RevTut.Skywars.kits.Engineer;
import net.RevTut.Skywars.kits.Hacker;
import net.RevTut.Skywars.kits.Ninja;

/**
 * Kit Manager.
 *
 * <P>Manages all all the kits in the server.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class KitManager {

    /**
     * Main class
     */
    private final Main plugin;

    /** Kit Engineer */
    public final Engineer engineer;

    /** Kit Hacker */
    public final Hacker hacker;

    /** Kit Ninja */
    public final Ninja ninja;

    /**
     * Constructor of Kit Manager
     *
     * @param plugin main class
     */
    public KitManager(final Main plugin) {
        this.plugin = plugin;
        // Initialize Kits
        engineer = new Engineer(plugin);
        hacker = new Hacker(plugin);
        ninja = new Ninja(plugin);
    }
}
