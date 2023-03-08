package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class PlayerDataManager {

    private Main plugin;
    public YamlConfiguration playerDataYaml;

    public PlayerDataManager(Main plugin){
        this.plugin = plugin;
    }

    public void checkPlayerData(Player player){
        File playerDataFolder = new File(plugin.getDataFolder()+ "/playerData");
        if(!playerDataFolder.exists()){
            playerDataFolder.mkdirs();
        }
        File playerdata = new File(plugin.getDataFolder()+ "/playerData",player.getUniqueId()+".yml");
        try {
            if(!playerdata.exists()){
                playerdata.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File getDataFile(Player player){
        File playerdata = new File(plugin.getDataFolder()+ "/playerData",player.getUniqueId()+".yml");
        if(!playerdata.exists()){
            return null;
        }
        return playerdata;
    }
    public void setYaml(Player player){
        playerDataYaml = YamlConfiguration.loadConfiguration(getDataFile(player));
        save(player);
    }
    public void save(Player player) {
        try {
            this.playerDataYaml.save(this.getDataFile(player));
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

}
