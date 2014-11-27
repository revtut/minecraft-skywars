package net.RevTut.Skywars.libraries.appearance;

import net.RevTut.Skywars.SkyWars;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Appearance Library.
 *
 * <P>A library with methods related to game appearance.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class AppearanceAPI {

    /**
     * Constructor of AppearanceAPI
     */
    private AppearanceAPI() {}

    /**
     * Main class
     */
    public static SkyWars plugin = null;

    /**
     * Copy of random class
     */
    private static final Random r = new Random();

    /**
     * Launch firework on player
     *
     * @param player player to launch firework
     * @param amount amount of fireworks to launch
     * @param delay  delay between each firework
     */
    public static void launchFirework(final Player player, final int amount, final int delay) {
        if (null == plugin) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Main plugin is null inside AppearanceAPI!");
            return;
        }
        for (int i = 0; i < amount; i++) {
            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    final Firework fw = player.getWorld().spawn(player.getLocation(), Firework.class);
                    final FireworkMeta fm = fw.getFireworkMeta();

                    final int fType = r.nextInt(5) + 1;
                    FireworkEffect.Type type;
                    switch (fType) {
                        case 1:
                            type = FireworkEffect.Type.BALL;
                            break;
                        case 2:
                            type = FireworkEffect.Type.BALL_LARGE;
                            break;
                        case 3:
                            type = FireworkEffect.Type.CREEPER;
                            break;
                        case 4:
                            type = FireworkEffect.Type.STAR;
                            break;
                        case 5:
                            type = FireworkEffect.Type.BURST;
                            break;
                        default:
                            type = FireworkEffect.Type.BALL;
                            break;
                    }

                    final Color c1 = getColor(r.nextInt(16) + 1);
                    final Color c2 = getColor(r.nextInt(16) + 1);

                    final FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
                    fm.addEffect(effect);
                    fm.setPower(1);
                    fw.setFireworkMeta(fm);
                }
            }, delay);
        }
    }

    /**
     * Convert integer to color
     *
     * @param c integer to be converted
     * @return correspondent color
     */
    private static Color getColor(final int c) {
        switch (c) {
            case 1:
            default:
                return Color.AQUA;
            case 2:
                return Color.BLACK;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.FUCHSIA;
            case 5:
                return Color.GRAY;
            case 6:
                return Color.GREEN;
            case 7:
                return Color.LIME;
            case 8:
                return Color.MAROON;
            case 9:
                return Color.NAVY;
            case 10:
                return Color.OLIVE;
            case 11:
                return Color.ORANGE;
            case 12:
                return Color.PURPLE;
            case 13:
                return Color.RED;
            case 14:
                return Color.SILVER;
            case 15:
                return Color.TEAL;
            case 16:
                return Color.WHITE;
            case 17:
        }
        return Color.YELLOW;
    }
}
