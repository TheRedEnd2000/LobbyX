package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

public class HangingBreakByEntityEventListener implements Listener {

    private Main plugin;

    public HangingBreakByEntityEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onHangingBreakByEntityEvent(HangingBreakByEntityEvent event){
        if(event.getRemover() instanceof Player){
            Player player = (Player) event.getRemover();
            if(plugin.getLobbyWorlds().contains(player.getWorld())){
                event.setCancelled(true);
            }
        }
    }

}
