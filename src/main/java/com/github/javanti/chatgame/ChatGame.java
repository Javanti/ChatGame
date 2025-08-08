package com.github.javanti.chatgame;

import com.github.javanti.chatgame.command.ChatGameCMD;
import com.github.javanti.chatgame.config.GameConfig;
import com.github.javanti.chatgame.integration.EconomyAPI;
import com.github.javanti.chatgame.listener.GameListener;
import com.github.javanti.chatgame.manager.GameManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Objects;

public final class ChatGame extends JavaPlugin {
    private final BukkitScheduler bukkitScheduler = getServer().getScheduler();
    @Override
    public void onEnable() {
        EconomyAPI economyAPI = new EconomyAPI();
        economyAPI.setupEconomy();
        GameConfig config = new GameConfig(this, "config.yml");
        GameManager gameManager = new GameManager(config, economyAPI,this);
        getServer().getPluginManager().registerEvents(new GameListener(gameManager), this);
        Objects.requireNonNull(getCommand("chatGame")).setExecutor(new ChatGameCMD(config,gameManager));
        gameManager.startTimerTask();
    }

    @Override
    public void onDisable() {
        bukkitScheduler.cancelTasks(this);
    }
}
