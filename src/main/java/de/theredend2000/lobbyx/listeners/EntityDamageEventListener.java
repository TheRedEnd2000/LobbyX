package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEventListener implements Listener {

    private Main plugin;

    public EntityDamageEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDamageEntity(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if(plugin.getLobbyWorlds().contains(player.getWorld())){
                event.setCancelled(true);
            }
        }
        if(!plugin.getConfig().getBoolean("Settings.MobDamageInLobbys")){
            Entity entity = event.getEntity();
            if(plugin.getLobbyWorlds().contains(entity.getWorld())){
                if(event.getEntity().getType() == EntityType.ITEM_FRAME || event.getEntity().getType() == EntityType.ARMOR_STAND){
                    return;
                }
                event.setCancelled(true);
            }
        }
    }

}
