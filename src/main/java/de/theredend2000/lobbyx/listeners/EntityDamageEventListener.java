package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
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
                if(!plugin.getBuildPlayers().contains(player)){
                    event.setCancelled(true);
                }
            }
        }
        /*if(!plugin.getConfig().getBoolean("Settings.MobDamageInLobbys")){
            Entity entity = event.getEntity();
            if(!(entity instanceof ItemFrame || entity instanceof ArmorStand)) {
                if (plugin.getLobbyWorlds().contains(entity.getWorld())) {
                    event.setCancelled(true);
                    entity.sendMessage(String.valueOf(event.getEntity().getType()));
                }
            }else
                Bukkit.getConsoleSender().sendMessage("ne");
        }*/
    }

}
