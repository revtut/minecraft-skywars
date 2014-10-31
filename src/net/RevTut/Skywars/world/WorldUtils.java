package net.RevTut.Skywars.world;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

/**
 * Created by João on 31/10/2014.
 */
public class WorldUtils {
    public static synchronized Object acquireField(Object owner, @Nonnull String field, @Nonnull Class<?> fallback) {
        try {
            Field f = fallback.getDeclaredField(field);

            if (f == null)
                return null;

            f.setAccessible(true);
            return f.get(owner);
        } catch (NoSuchFieldException x) {
            x.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        }
        return null;
    }
}
