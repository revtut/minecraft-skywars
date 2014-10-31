package net.RevTut.Skywars.utils;

/**
 * Created by João on 25/10/2014.
 */
public class Converters {

    /* Convert From Text to JSON */
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
                        sb.append("\\u" + t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    /* Replace With Special Characters */
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
        text = text.replace("%ALT27%", "←");
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

    /* Convert Hours to Days */
    public static long convertHoursToDays(long hours) {
        return hours / 24;
    }

    /* Convert Minutes to Hours */
    public static long convertMinutesToHours(long minutes) {
        return minutes / 60;
    }

    /* Convert Seconds to Minutes */
    public static long convertSecondsToMinutes(long seconds) {
        return seconds / 60;
    }

    /* Convert Seconds to Days, Hours, Minutes and Seconds */
    public static String convertSecondsToDHMS(long sec) {

        long seconds = sec % 60;
        long minutes = convertSecondsToMinutes(seconds);
        long hours = convertMinutesToHours(minutes);
        long days = convertHoursToDays(hours);
        minutes = minutes % 60;
        hours = hours % 60;

        return days + "D : " + hours + "H : " + minutes + "M : " + seconds + "S";
    }
}