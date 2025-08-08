package com.github.javanti.chatgame.manager;

import com.github.javanti.chatgame.GameTask;
import com.github.javanti.chatgame.config.GameConfig;
import com.github.javanti.chatgame.integration.EconomyAPI;
import com.github.javanti.chatgame.model.GameQuestion;
import com.github.javanti.chatgame.util.GameUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class GameManager {
    private final @NonNull GameConfig gameConfig;
    private final @NonNull EconomyAPI economyAPI;
    private GameQuestion currentQuestion;
    private final @NonNull JavaPlugin plugin;
    public void startGame() {
        if(gameStarted()){
            stopGame(true);
        }
        currentQuestion = getRandomQuestion();
        if(currentQuestion == null) return;
        GameUtil.sendBroadcast(gameConfig.getBroadcastGameStartMessage(), Map.of(
                "{QUESTION}", currentQuestion.question(),
                "{PRIZE}", String.valueOf(currentQuestion.prize()))
        );
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(gameStarted()){
                stopGame(true);
            }
        },gameConfig.getGameTime()*20);
    }

    public void stopGame(boolean timeout) {
        if(timeout) GameUtil.sendBroadcast(gameConfig.getBroadcastTimeLeftMessage(), Map.of("{ANSWERS}", String.join(", ", currentQuestion.answer())));
        currentQuestion = null;
    }

    public boolean gameStarted() {
        return currentQuestion != null;
    }

    public boolean isTrueAnswer(@NonNull String message) {
        if(currentQuestion == null) return false;
        return currentQuestion.answer().stream().anyMatch(message::equalsIgnoreCase);
    }

    public void givePrize(@NonNull Player player, @NonNull String message) {
        if(currentQuestion == null) return;
        economyAPI.deposit(player, currentQuestion.prize());
        GameUtil.sendBroadcast(gameConfig.getBroadcastGameEndMessage(), Map.of(
                "{WINNER}", player.getName(),
                "{ANSWERS}", String.join(", ", currentQuestion.answer())
                ,"{ANSWER}",message)
        );
    }

    private GameQuestion getRandomQuestion() {
        List<GameQuestion> questions = gameConfig.getQuestions();
        return questions.isEmpty() ? null : questions.get((int) (Math.random() * questions.size()));
    }

    public void startTimerTask() {
        Bukkit.getScheduler().cancelTasks(plugin);
        Bukkit.getScheduler().runTaskTimer(plugin,new GameTask(this),
                gameConfig.getPeriodSeconds()*20,
                gameConfig.getPeriodSeconds()*20);
    }
}
