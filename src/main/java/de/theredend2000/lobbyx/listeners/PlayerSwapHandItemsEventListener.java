package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerSwapHandItemsEventListener implements Listener {

    private Main plugin;

    public PlayerSwapHandItemsEventListener(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onSwapItems(PlayerSwapHandItemsEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(!plugin.getBuildPlayers().contains(player)){
                event.setCancelled(true);
            }
        }
    }

}
