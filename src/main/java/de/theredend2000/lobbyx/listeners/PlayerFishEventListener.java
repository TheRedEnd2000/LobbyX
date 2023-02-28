package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerFishEventListener implements Listener {

    private Main plugin;

    public PlayerFishEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(!plugin.getBuildPlayers().contains(player)){
                if(plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Inv").equals("Grappling_Hook")){
                    if(event.getState().equals(PlayerFishEvent.State.REEL_IN)){
                        Location playerLocation = player.getLocation();
                        Location hookLocation = event.getHook().getLocation();
                        Location vector = hookLocation.subtract(playerLocation);
                        player.setVelocity(vector.toVector().multiply(0.15));
                    }
                    return;
                }
                event.setCancelled(true);
            }else if(plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Inv").equals("Grappling_Hook")){
                if(event.getState().equals(PlayerFishEvent.State.REEL_IN)){
                    Location playerLocation = player.getLocation();
                    Location hookLocation = event.getHook().getLocation();
                    Location vector = hookLocation.subtract(playerLocation);
                    player.setVelocity(vector.toVector().multiply(0.15));
                }
            }
        }
    }

}
