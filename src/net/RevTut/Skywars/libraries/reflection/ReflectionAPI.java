package net.RevTut.Skywars.libraries.reflection;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Reflection Library.
 *
 * <P>A library with methods related to reflection.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class ReflectionAPI {

    /**
     * Get version of Minecraft.
     *
     * @return version of the game
     */
    private static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf('.') + 1) + ".";
    }

    /**
     * Get a NMS class given a name.
     *
     * @param className className to get the NMS class
     * @return nms class
     */
    public static Class<?> getNMSClass(String className) {
        String fullName = "net.minecraft.server." + getVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    /**
     * Get a CraftBukkit class given its name.
     *
     * @param className className to get the CraftBukkit class
     * @return craftBukkit class
     */
    public static Class<?> getOBCClass(String className) {
        String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    /**
     * Get a object handle.
     *
     * @param obj object to get the handle
     * @return object handle
     */
    public static Object getHandle(Object obj) {
        try {
            return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a field from a class given its class and its name.
     *
     * @param clazz class to get the field
     * @param name  field name
     * @return field of the class
     */
    public static Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a method from a class.
     *
     * @param clazz class to get the method
     * @param name  method name
     * @param args  args of the method
     * @return method of the class
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... args) {
        for (Method m : clazz.getMethods()) {
            if ((m.getName().equals(name)) && ((args.length == 0) || (ClassListEqual(args, m.getParameterTypes())))) {
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }

    /**
     * Compare two classes
     *
     * @param l1 class one to compare
     * @param l2 class second to compare
     * @return true if they are equal
     */
    private static boolean ClassListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length) {
            return false;
        }
        for (int i = 0; i < l1.length; i++) {
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        }
        return equal;
    }
}
