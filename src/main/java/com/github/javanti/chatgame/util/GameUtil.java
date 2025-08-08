package com.github.javanti.chatgame.util;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GameUtil {
    private GameUtil(){}
    
    public static String toColorText(String from) {
        if(from == null) return "";
        Pattern pattern = Pattern.compile("&#[a-fA-F\0-9]{6}");
        Matcher matcher = pattern.matcher(from);
        while (matcher.find()) {
            String hexCode = from.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace("&#", "x");
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch)
                builder.append("&").append(c);
            from = from.replace(hexCode, builder.toString());
            matcher = pattern.matcher(from);
        }
        return ChatColor.translateAlternateColorCodes('&', from);
    }

    public static void sendBroadcast(@NonNull List<String> messages, @NonNull Map<String, String> placeholders) {
        for (String msg : messages) {
            for (Map.Entry<String, String> entry : placeholders.entrySet()) {
                msg = msg.replace(entry.getKey(), entry.getValue());
            }
            msg = GameUtil.toColorText(msg);
            Bukkit.broadcastMessage(msg); 
        }
    }

}
