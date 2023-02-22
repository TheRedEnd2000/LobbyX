package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeavesDecayEventListener implements Listener {

    private Main plugin;

    public LeavesDecayEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event){
        if(plugin.getLobbyWorlds().contains(event.getBlock().getWorld())){
            event.setCancelled(true);
        }
    }

}
