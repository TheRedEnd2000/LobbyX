package de.theredend2000.lobbyx.listeners.itemListeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class GadgetsListener implements Listener {

    private Main plugin;

    public GadgetsListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteractWithHiderEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(event.getItem() != null && event.getItem().hasItemMeta()){
                    if(Objects.requireNonNull(event.getItem().getItemMeta()).getLocalizedName().equals("lobbyx.gadgets")){
                        plugin.getProfileMenuManager().createProfileInventory(player);
                    }
                }
            }
        }
    }

}
