package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeEventListener implements Listener {

    private Main plugin;

    public WeatherChangeEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onWeatherChangeEvent(WeatherChangeEvent event){
        if(plugin.getLobbyWorlds().contains(event.getWorld())){
            event.setCancelled(true);
        }
    }

}
