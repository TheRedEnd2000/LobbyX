package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ListIterator;

public class TablistManager {

    private Main plugin;

    public TablistManager(Main plugin){
        this.plugin = plugin;

        updateTabList();
    }

    public void setPlayerList(Player player){
        player.setPlayerListHeaderFooter(
                plugin.getConfig().getString("Tablist.Header").replaceAll("&","§")
                .replaceAll("%SERVER_ONLINE%",String.valueOf(player.getServer().getOnlinePlayers().size()))
                .replaceAll("%SERVER_MAX_PLAYERS%",String.valueOf(player.getServer().getMaxPlayers()))
                .replaceAll("%PLAYER_SERVER%",player.getServer().getName())
                .replaceAll("%PLAYER_PING%", String.valueOf(player.getPing()))
                .replaceAll("~","\n")
                ,plugin.getConfig().getString("Tablist.Footer").replaceAll("&","§")
                        .replaceAll("%SERVER_ONLINE%",String.valueOf(player.getServer().getOnlinePlayers().size()))
                        .replaceAll("%SERVER_MAX_PLAYERS%",String.valueOf(player.getServer().getMaxPlayers()))
                        .replaceAll("%PLAYER_SERVER%",player.getServer().getName())
                        .replaceAll("%PLAYER_PING%", String.valueOf(player.getPing()))
                        .replaceAll("~","\n"));
    }

    private void updateTabList(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getServer().getOnlinePlayers()){
                    if(plugin.getLobbyWorlds().contains(player.getWorld())){
                        setPlayerList(player);
                    }
                }
            }
        }.runTaskTimer(plugin,0,plugin.getConfig().getInt("Tablist.Update_Delay"));
    }

}
