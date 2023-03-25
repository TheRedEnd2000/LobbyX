package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.jumpandrun.PlayerMoveListener;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveEventListener implements Listener {

    private Main plugin;

    public PlayerMoveEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onMoveEvent(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(!plugin.getBuildPlayers().contains(player)){
                int maxYLevel = plugin.getConfig().getInt("Settings.PlayerYLevelTeleport");
                if(player.getLocation().getY() <= maxYLevel){
                    plugin.getSetPlayerLobbyManager().setPlayerInLobby(player);
                    ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin,"Locations.Lobby."+player.getWorld().getName());
                    if(locationUtil.loadLocation() != null){
                        player.teleport(locationUtil.loadLocation());
                    }else
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"LobbyInNotSet"));
                }
            }
        }
    }

}
