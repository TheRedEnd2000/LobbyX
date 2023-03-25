package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerDeathEventListener implements Listener {

    private Main plugin;

    public PlayerDeathEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            player.getWorld().setGameRule(GameRule.DO_IMMEDIATE_RESPAWN,false);
            if(!plugin.getBuildPlayers().contains(player))
                plugin.getSetPlayerLobbyManager().setPlayerInLobby(player);
            ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin,"Locations.Lobby."+player.getWorld().getName());
            if(locationUtil.loadLocation() != null){
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(!player.isDead()) {
                            cancel();
                            player.teleport(locationUtil.loadLocation());
                        }
                    }
                }.runTaskTimer(plugin,0,1);
            }else
                player.sendMessage(Util.getMessage(Util.getLocale(player),"LobbyInNotSet"));
        }
    }

}
