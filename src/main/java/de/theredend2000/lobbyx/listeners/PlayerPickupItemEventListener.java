package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItemEventListener implements Listener {

    private Main plugin;

    public PlayerPickupItemEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onPickupItem(PlayerPickupItemEvent event){
        Player player =  event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(!plugin.getBuildPlayers().contains(player)){
                event.setCancelled(true);
            }
        }
    }

}
