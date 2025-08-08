package com.github.javanti.chatgame.integration;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class EconomyAPI {
    private static Economy economy;

    public void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) {
            economy = rsp.getProvider();
        }
    }

    public boolean isEconomyEnabled() {
        return economy != null;
    }

    public void deposit(Player player, double amount) {
        if (!isEconomyEnabled()) return;
        economy.depositPlayer(player, amount).transactionSuccess();
    }
}
