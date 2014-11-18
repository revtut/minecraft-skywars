package net.RevTut.Skywars.managers;

import net.RevTut.Skywars.kits.Engineer;
import net.RevTut.Skywars.kits.Hacker;
import net.RevTut.Skywars.kits.Ninja;
import net.RevTut.Skywars.kits.Tatical;

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
     * Kit Engineer
     */
    public final Engineer engineer = new Engineer() ;

    /**
     * Kit Hacker
     */
    public final Hacker hacker = new Hacker();

    /**
     * Kit Ninja
     */
    public final Ninja ninja = new Ninja();

    /**
     * Kit Tatical
     */
    public final Tatical tatical = new Tatical();
}
