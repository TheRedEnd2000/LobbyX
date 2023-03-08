package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class RewardManager {

    private Main plugin;

    public RewardManager(Main plugin){
        this.plugin = plugin;
    }

    public boolean getAllowReward(Player player) {
        long current = System.currentTimeMillis();
        long millis = getTime(player);
        return current > millis;
    }

    public File getRewardFile() {
        return new File(plugin.getDataFolder(), "rewards.yml");
    }

    public void setReward(Player player) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getRewardFile());
        long toSet = System.currentTimeMillis()+86400000;
        cfg.set(player.getUniqueId() + ".millis", toSet);
        try {
            cfg.save(getRewardFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getTime(Player player) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getRewardFile());
        return cfg.getLong(player.getUniqueId() + ".millis");
    }

    public String getRemainingTime(long millis, Player player) {
        long seconds = millis/1000;
        long minutes = 0;
        while(seconds > 60) {
            seconds-=60;
            minutes++;
        }
        long hours = 0;
        while(minutes > 60) {
            minutes-=60;
            hours++;
        }
        return Util.getMessage(Util.getLocale(player),"RewardTimeLayOut").replaceAll("%HOURS%", String.valueOf(hours)).replaceAll("%MINUTES%", String.valueOf(minutes)).replaceAll("%SECONDS%", String.valueOf(seconds));
    }

}
