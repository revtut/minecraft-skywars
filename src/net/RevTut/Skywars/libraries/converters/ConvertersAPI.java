package net.RevTut.Skywars.libraries.converters;

import java.util.List;

/**
 * Converters Library.
 *
 * <P>Usefull converters to be used.</P>
 *
 * @author Joao Silva
 * @version 1.0
 */
public class ConvertersAPI {

    /**
     * Convert a text to JSON format
     *
     * @param text text to be converted
     * @return converted text to JSON
     */
    public static String convertToJSON(String text) {
        if (text == null || text.length() == 0) {
            return "\"\"";
        }
        char c;
        int i;
        int len = text.length();
        StringBuilder sb = new StringBuilder(len + 4);
        String t;
        sb.append('"');
        for (i = 0; i < len; i += 1) {
            c = text.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ') {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u").append(t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    /**
     * Replace some text with special characters in a text
     *
     * @param text text to be replaced
     * @return text with special characters
     */
    public static String convertSpecialCharacters(String text) {
        // First Section
        text = text.replace("%ALT1%", "☺");
        text = text.replace("%ALT2%", "☻");
        text = text.replace("%ALT3%", "♥");
        text = text.replace("%ALT4%", "♦");
        text = text.replace("%ALT5%", "♣");
        text = text.replace("%ALT6%", "♠");
        text = text.replace("%ALT7%", "•");
        text = text.replace("%ALT8%", "◘");
        text = text.replace("%ALT9%", "○");
        text = text.replace("%ALT10%", "◙");
        text = text.replace("%ALT11%", "♂");
        text = text.replace("%ALT12%", "♀");
        text = text.replace("%ALT13%", "♪");
        text = text.replace("%ALT14%", "♫");
        text = text.replace("%ALT15%", "☼");
        text = text.replace("%ALT16%", "►");
        text = text.replace("%ALT17%", "◄");
        text = text.replace("%ALT18%", "↕");
        text = text.replace("%ALT19%", "‼");
        text = text.replace("%ALT20%", "¶");
        text = text.replace("%ALT21%", "§");
        text = text.replace("%ALT22%", "▬");
        text = text.replace("%ALT23%", "↨");
        text = text.replace("%ALT24%", "↑");
        text = text.replace("%ALT25%", "↓");
        text = text.replace("%ALT26%", "→");
        text = text.replace("%ALT28%", "∟");
        text = text.replace("%ALT29%", "↔");
        text = text.replace("%ALT30%", "▲");
        text = text.replace("%ALT31%", "▼");
        // Second Section
        text = text.replace("%ALT127%", "⌂");
        // Third Section
        text = text.replace("%ALT0153%", "™");
        text = text.replace("%ALT0169%", "©");
        text = text.replace("%ALT0169%", "®");
        return text;
    }

    /**
     * Convert hours to days
     *
     * @param hours hours to be converted
     * @return converted days
     */
    private static long convertHoursToDays(long hours) {
        return hours / 24;
    }

    /**
     * Convert minutes to hours
     *
     * @param minutes minutes to be converted
     * @return converted hours
     */
    private static long convertMinutesToHours(long minutes) {
        return minutes / 60;
    }

    /**
     * Convert seconds to minutes
     *
     * @param seconds seconds to be converted
     * @return converted minutes
     */
    private static long convertSecondsToMinutes(long seconds) {
        return seconds / 60;
    }

    /**
     * Convert seconds to D : H : M : S format.
     * Days : Hours : Minutes : Seconds
     *
     * @param sec seconds to be converted
     * @return seconds converted to the format
     */
    public static String convertSecondsToDHMS(long sec) {

        long seconds = sec % 60;
        long minutes = convertSecondsToMinutes(seconds);
        long hours = convertMinutesToHours(minutes);
        long days = convertHoursToDays(hours);
        minutes = minutes % 60;
        hours = hours % 60;

        return days + "D : " + hours + "H : " + minutes + "M : " + seconds + "S";
    }

    /**
     * Convert a string list to a string
     *
     * @param list      list with all the strings
     * @param separator string to separe each string
     * @return string unified
     */
    public static String convertListToString(List<String> list, String separator) {
        String converted = "";
        for (int i = 0; i < list.size(); i++) {
            if (i < (list.size() - 1))
                converted += list.get(i).replaceAll("'", "") + separator;
            else
                converted += list.get(i);
        }
        return converted;
    }
}