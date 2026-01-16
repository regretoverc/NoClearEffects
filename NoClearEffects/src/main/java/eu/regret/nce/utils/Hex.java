package eu.regret.nce.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Hex {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static String format(String message) {
        Matcher hexMatcher = HEX_PATTERN.matcher(message);
        StringBuffer sb = new StringBuffer();

        while (hexMatcher.find()) {
            String hexColor = hexMatcher.group(1);
            String rgbColor = ChatColor.of("#" + hexColor).toString();
            hexMatcher.appendReplacement(sb, rgbColor);
        }
        hexMatcher.appendTail(sb);

        return ChatColor.translateAlternateColorCodes('&', sb.toString());
    }
}
