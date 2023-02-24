package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class PlayerItemDamageEventListener implements Listener {

    private Main plugin;

    public PlayerItemDamageEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }


    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent e) {
        Player player = e.getPlayer();
        if (e.getItem().getType() == Material.FISHING_ROD && plugin.getLobbyWorlds().contains(player.getWorld())) {
            e.setCancelled(true);
        }
    }
}
