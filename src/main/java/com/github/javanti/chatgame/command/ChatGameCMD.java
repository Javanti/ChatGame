package com.github.javanti.chatgame.command;

import com.github.javanti.chatgame.config.GameConfig;
import com.github.javanti.chatgame.manager.GameManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Collections;
import java.util.List;
@RequiredArgsConstructor
public class ChatGameCMD implements CommandExecutor, TabExecutor {
    private final @NonNull GameConfig config;
    private final @NonNull GameManager gameManager;
    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if(args.length > 0 && args[0].equalsIgnoreCase("reload")){
            config.reloadConfig();
            gameManager.startTimerTask();
            sender.sendMessage(ChatColor.GREEN+"[ChatGame] "+ChatColor.GOLD+"конфиг успешно перезагружен");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, String @NonNull [] args) {
        if(args.length == 1) {
            return List.of("reload");
        }
        return Collections.emptyList();
    }
}
