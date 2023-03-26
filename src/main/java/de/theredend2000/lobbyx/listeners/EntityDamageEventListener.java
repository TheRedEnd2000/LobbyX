package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEventListener implements Listener {

    private Main plugin;

    public EntityDamageEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDamageEntity(EntityDamageEvent event){
        if(plugin.getLobbyWorlds().contains(event.getEntity().getWorld())){
            if (event.getEntity() instanceof Villager) {
                Villager villager = (Villager) event.getEntity();
                if (villager.getCustomName().equals("§6Daily")) {
                    event.setCancelled(true);
                }
            }
            if(event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (!plugin.getBuildPlayers().contains(player)) {
                    event.setCancelled(true);
                }
            }
            if (event.getEntity() instanceof Minecart || event.getEntity() instanceof Boat || event.getEntity() instanceof ChestBoat) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamageEntitybyEntity(EntityDamageByEntityEvent event){
        if(!plugin.getConfig().getBoolean("Settings.MobDamageInLobbys")){
            Entity entity = event.getEntity();
            if(event.getDamager() instanceof Player){
                Player player = (Player) event.getDamager();
                if(plugin.getLobbyWorlds().contains(player.getWorld()) && plugin.getLobbyWorlds().contains(entity.getWorld())){
                    if(!plugin.getBuildPlayers().contains(player)){
                        event.setCancelled(true);
                    }
                }
            }
            if (event.getEntity() instanceof Minecart || event.getEntity() instanceof Boat || event.getEntity() instanceof ChestBoat) {
                if (plugin.getLobbyWorlds().contains(event.getEntity().getWorld())) {
                    if(!plugin.getBuildPlayers().contains((Player) event.getDamager())) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

}
