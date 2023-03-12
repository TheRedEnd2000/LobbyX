package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEventListener implements Listener {

    private Main plugin;

    public PlayerChatEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            event.setCancelled(true);
            for(Player lobbyPlayer : player.getWorld().getPlayers()){
                lobbyPlayer.sendMessage("§7["+plugin.getRankManager().getColoredPrefix(player)+"§7] §3"+player.getName()+" §6>> §r"+event.getMessage());
            }
        }
    }

}
