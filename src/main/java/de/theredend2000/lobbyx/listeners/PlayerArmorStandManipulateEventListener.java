package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;

public class PlayerArmorStandManipulateEventListener implements Listener {

    private Main plugin;

    public PlayerArmorStandManipulateEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerArmorStandManipulate(PlayerArmorStandManipulateEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(!plugin.getBuildPlayers().contains(player)){
                event.setCancelled(true);
            }
        }
    }

}
