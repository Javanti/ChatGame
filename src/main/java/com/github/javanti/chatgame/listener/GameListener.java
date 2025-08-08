package com.github.javanti.chatgame.listener;

import com.github.javanti.chatgame.manager.GameManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class GameListener implements Listener {
    private final @NonNull GameManager gameManager;
    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if(!gameManager.gameStarted()) return;
        if(!gameManager.isTrueAnswer(message)) return;
        gameManager.givePrize(event.getPlayer(), message);
        gameManager.stopGame(false);

    }
}
