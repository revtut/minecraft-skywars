package net.RevTut.Skywars.libraries.language;

import net.RevTut.Skywars.libraries.reflection.ReflectionAPI;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Language Library.
 *
 * <P>A library with methods related to the languages.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public final class LanguageAPI {

    /**
     * Map with all the languages of the players
     */
    private final static Map<UUID, Language> playerLanguage = new HashMap<UUID, Language>();

    /**
     * Save the language of a player to the map
     *
     * @param player player to save the language
     * @return true if successfull;
     */
    public static boolean saveLanguage(Player player){
        try {
            Object playerMethod = ReflectionAPI.getMethod(player.getClass(), "getHandle").invoke(player, (Object[]) null);
            Field field = ReflectionAPI.getField(playerMethod.getClass(), "locale");
            field.setAccessible(true);

            String lang = (String) field.get(playerMethod);
            field.setAccessible(false);

            Language language = getByCode(lang);
            if(null == language)
                return false;

            playerLanguage.put(player.getUniqueId(), language);
            return true;
        } catch (Throwable t) {
            Logger.getLogger("Minecraft").log(Level.WARNING, "Error while getting player language in LanguageAPI!");
            t.printStackTrace();
            return false;
        }
    }

    /**
     * Get the language of a player
     *
     * @param player player to get the language
     * @return language of the player
     */
    public static Language getLanguage(Player player) {
        if(!playerLanguage.containsKey(player.getUniqueId()))
            return null;
        return playerLanguage.get(player.getUniqueId());
    }

    /**
     * Get a language by it code
     *
     * @param code code of the language
     * @return language with that code
     */
    public static Language getByCode(String code) {
        for (Language language : Language.values())
            if (language.getCode().equalsIgnoreCase(code))
                return language;
        return null;
    }

    /**
     * Get a language by it name
     *
     * @param name name of the language
     * @return language with that name
     */
    public static Language getByName(String name) {
        for (Language language : Language.values())
            if (language.getName().equalsIgnoreCase(name))
                return language;
        return null;
    }
}
