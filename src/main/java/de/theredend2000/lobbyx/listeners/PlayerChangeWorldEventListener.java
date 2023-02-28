package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class PlayerChangeWorldEventListener implements Listener {

    private Main plugin;

    public PlayerChangeWorldEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onChangeWorldEvent(PlayerChangedWorldEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            for(Player lobbyPlayers : player.getWorld().getPlayers()){
                lobbyPlayers.sendMessage(Util.getMessage(Util.getLocale(lobbyPlayers),"ChangeLobbyJoinMessage").replaceAll("%PLAYER%",player.getName()));
            }
            for(Player lobbyPlayers : event.getFrom().getPlayers()){
                lobbyPlayers.sendMessage(Util.getMessage(Util.getLocale(lobbyPlayers),"ChangeLobbyLeaveMessage").replaceAll("%PLAYER%",player.getName()));
            }
            plugin.getSetPlayerLobbyManager().setPlayerInLobby(player);
        }else{
            player.getInventory().clear();
            player.setPlayerListHeaderFooter(null,null);
            player.sendMessage("left loby");
        }
    }

}
