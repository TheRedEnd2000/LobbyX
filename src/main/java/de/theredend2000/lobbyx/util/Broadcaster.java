package de.theredend2000.lobbyx.util;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Broadcaster {

    private Main plugin;

    public Broadcaster(Main plugin){
        this.plugin = plugin;
    }

    public void startBroadcast(){
        int delay = plugin.getConfig().getInt("Broadcaster.delay") * 20;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(plugin.getLobbyWorlds().contains(player.getWorld())){
                        if(isEnabled()) {
                            player.sendMessage("§b§l--------------------------------------");
                            player.sendMessage("             §6§lANNOUNCEMENT             "); // 13 emty
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', pickMessage()));
                            player.sendMessage("§b§l--------------------------------------");
                        }
                    }
                }
            }
        },0,delay);
    }


    private String pickMessage(){
        List<String> messages = plugin.getConfig().getStringList("Broadcaster.messages");
        int random = new Random().nextInt(messages.size());
        return messages.get(random);
    }

    private boolean isEnabled(){
        return plugin.getConfig().getBoolean("Broadcaster.enabled");
    }

}
