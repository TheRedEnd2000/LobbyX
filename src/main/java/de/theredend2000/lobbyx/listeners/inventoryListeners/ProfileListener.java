package de.theredend2000.lobbyx.listeners.inventoryListeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ProfileListener implements Listener {

    private Main plugin;

    public ProfileListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){

    }

}
