package com.github.javanti.chatgame;

import com.github.javanti.chatgame.manager.GameManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameTask implements Runnable{
    private final @NonNull GameManager gameManager;
    @Override
    public void run() {
        if(gameManager.gameStarted()) return;
        gameManager.startGame();
    }
}
