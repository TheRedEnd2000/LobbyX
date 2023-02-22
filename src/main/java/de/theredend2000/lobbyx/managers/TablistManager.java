package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import org.bukkit.entity.Player;

import java.util.ListIterator;

public class TablistManager {

    private Main plugin;

    public TablistManager(Main plugin){
        this.plugin = plugin;
    }

    public void setPlayerList(Player player){
        player.setPlayerListHeaderFooter(
                plugin.getConfig().getString("Tablist.Header").replaceAll("&","§")
                .replaceAll("%SERVER_ONLINE%",String.valueOf(player.getServer().getOnlinePlayers().size()))
                .replaceAll("%SERVER_MAX_PLAYERS%",String.valueOf(player.getServer().getMaxPlayers()))
                .replaceAll("%PLAYER_SERVER%",player.getServer().getName())
                .replaceAll("~","\n")
                ,plugin.getConfig().getString("Tablist.Footer").replaceAll("&","§")
                        .replaceAll("%SERVER_ONLINE%",String.valueOf(player.getServer().getOnlinePlayers().size()))
                        .replaceAll("%SERVER_MAX_PLAYERS%",String.valueOf(player.getServer().getMaxPlayers()))
                        .replaceAll("%PLAYER_SERVER%",player.getServer().getName())
                        .replaceAll("~","\n"));
    }

}
