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
                plugin.getPlayerDataManager().setYaml(lobbyPlayers);
                if(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Join_Messages"))
                    lobbyPlayers.sendMessage(Util.getMessage(Util.getLocale(lobbyPlayers),"ChangeLobbyJoinMessage").replaceAll("%PLAYER%",player.getName()));
            }
            for(Player lobbyPlayers : event.getFrom().getPlayers()){
                plugin.getPlayerDataManager().setYaml(lobbyPlayers);
                if(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Leave_Messages"))
                 lobbyPlayers.sendMessage(Util.getMessage(Util.getLocale(lobbyPlayers),"ChangeLobbyLeaveMessage").replaceAll("%PLAYER%",player.getName()));
            }
            plugin.getSetPlayerLobbyManager().setPlayerInLobby(player);
            plugin.getSetPlayerLobbyManager().updateLobbyInventory();
        }else{
            player.getInventory().clear();
            player.setPlayerListHeaderFooter(null,null);
            player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
            plugin.getBuildPlayers().remove(player);
        }
    }

}
