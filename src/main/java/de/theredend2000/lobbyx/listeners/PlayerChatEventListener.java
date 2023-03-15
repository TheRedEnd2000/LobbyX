package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerChatEventListener implements Listener {

    private Main plugin;

    public PlayerChatEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(event.isCancelled()){
                return;
            }
            event.setCancelled(true);
            for(Player lobbyPlayer : player.getWorld().getPlayers()){
                if(plugin.getServer().getPluginManager().isPluginEnabled("PowerRanks")) {
                    String rank = plugin.getApi().getPrimaryRank(player);
                    String prefix = plugin.getApi().getPrefix(rank);
                    String nameColor = plugin.getApi().getNameColor(rank);
                    String chatColor = plugin.getApi().getChatColor(rank);
                    lobbyPlayer.sendMessage(plugin.getConfig().getString("ChatFormat.ChatFormatRank").replaceAll("%RANK%", ColorUtils.format('&', prefix, true, true)).replaceAll("%CHAT_COLOR%",chatColor).replaceAll("%PLAYER%",player.getName()).replaceAll("%MESSAGE%",event.getMessage()).replaceAll("%NAME_COLOR%",nameColor).replaceAll("&","§"));
                }else
                    lobbyPlayer.sendMessage(plugin.getConfig().getString("ChatFormat.ChatFormatWithout").replaceAll("%PLAYER%",player.getName()).replaceAll("%MESSAGE%",event.getMessage()).replaceAll("&","§"));
            }
        }
    }

}
