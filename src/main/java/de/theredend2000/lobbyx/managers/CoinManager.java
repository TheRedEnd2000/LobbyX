package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CoinManager {

    private Main plugin;

    public CoinManager(Main plugin){
        this.plugin = plugin;
    }

    public File getCoinFile() {
        return new File(plugin.getDataFolder(), "coins.yml");
    }

    public void addCoins(Player player, int coins){
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getCoinFile());
        cfg.set(player.getUniqueId()+".Coins", cfg.getInt(player.getUniqueId()+".Coins")+coins);
        save(cfg);
    }
    public void setCoins(Player player, int coins){
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getCoinFile());
        cfg.set(player.getUniqueId()+".Coins", coins);
        save(cfg);
    }
    public void removeCoins(Player player, int coins){
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getCoinFile());
        cfg.set(player.getUniqueId()+".Coins", cfg.getInt(player.getUniqueId()+".Coins")-coins);
        save(cfg);
    }
    public void resetCoins(Player player){
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getCoinFile());
        cfg.set(player.getUniqueId()+".Coins", null);
        save(cfg);
    }
    public boolean haveEnoughCoins(Player player, int cost){
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getCoinFile());
        double coins = cfg.getInt(player.getUniqueId()+".Coins");
        return !(coins < cost);
    }
    public int getCoins(Player player){
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getCoinFile());
        return cfg.getInt(player.getUniqueId()+".Coins");
    }
    public boolean allowCoinsSee(Player player){
        plugin.getPlayerDataManager().setYaml(player);
        return plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.CoinsAPI");
    }

    private void save(FileConfiguration cfg){
        try {
            cfg.save(getCoinFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
