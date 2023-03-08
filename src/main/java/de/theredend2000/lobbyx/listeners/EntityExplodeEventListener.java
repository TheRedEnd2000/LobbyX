package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntityExplodeEventListener implements Listener {

    private Main plugin;

    public EntityExplodeEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event){
        if(plugin.getLobbyWorlds().contains(event.getEntity().getWorld())){
            event.setCancelled(true);
            event.blockList().clear();
        }
    }

}
