package com.github.javanti.chatgame.config;

import com.github.javanti.chatgame.model.GameQuestion;
import com.github.javanti.chatgame.util.ConfigFile;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GameConfig {
    private final ConfigFile configFile;
    private List<GameQuestion> questions;
    private List<String> broadcastGameStartMessage;
    private List<String> broadcastGameEndMessage;
    private List<String> broadcastTimeLeftMessage;
    private long periodSeconds;
    private long gameTime;


    public GameConfig(@NonNull JavaPlugin plugin, @NonNull String configPath) {
        configFile = new ConfigFile(plugin, configPath);
        configFile.saveDefaultConfig();
        loadConfig();
    }

    public void loadConfig(){
        questions = new ArrayList<>();
        broadcastGameEndMessage = new ArrayList<>();
        broadcastGameStartMessage = new ArrayList<>();
        broadcastTimeLeftMessage = new ArrayList<>();
        ConfigurationSection questionsSection = configFile.getConfig().getConfigurationSection("questions");
        if(questionsSection != null) {
            for (String questionKey : questionsSection.getKeys(false)) {
                ConfigurationSection questionSection = questionsSection.getConfigurationSection(questionKey);
                if (questionSection != null) {
                    GameQuestion question = new GameQuestion(questionKey, questionSection.getString("question",""),
                            questionSection.getStringList("answers"), questionSection.getInt("prize"));
                    questions.add(question);
                }
            }
        }
        gameTime = configFile.getConfig().getLong("question-time", 30);
        broadcastGameStartMessage = configFile.getConfig().getStringList("broadcast.start-game");
        broadcastGameEndMessage = configFile.getConfig().getStringList("broadcast.end-game");
        broadcastTimeLeftMessage = configFile.getConfig().getStringList("broadcast.time-left");
        periodSeconds = configFile.getConfig().getLong("period", 1200);
    }

    public void reloadConfig(){
        configFile.reloadConfig();
        loadConfig();
    }
}
