package net.RevTut.Skywars.libraries.appearance;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

/**
 * Created by João on 02/11/2014.
 */
public class Fireworks {
    static final Random r = new Random();

    /**
     * Launch FireWork on Player
     *
     * @param player Player to launch firework
     */
    public static void shootFireworks(final Player player) {
        final Firework fw = player.getWorld().spawn(player.getLocation(), Firework.class);
        final FireworkMeta fm = fw.getFireworkMeta();

        final int fType = r.nextInt(5) + 1;
        FireworkEffect.Type type = null;
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
        }

        final Color c1 = getColor(r.nextInt(16) + 1);
        final Color c2 = getColor(r.nextInt(16) + 1);

        final FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
        fm.addEffect(effect);
        fm.setPower(1);
        fw.setFireworkMeta(fm);
    }

    /**
     * Convert Integer to Color
     *
     * @param c Integer to be converted
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
