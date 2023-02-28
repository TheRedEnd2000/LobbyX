package de.theredend2000.lobbyx.listeners.itemListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Objects;

public class PlayerHiderListener implements Listener {

    private Main plugin;
    private HashMap<String, Long> playerHiderCooldown;

    public PlayerHiderListener(Main plugin){
        this.plugin = plugin;
        playerHiderCooldown = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onInteractWithHiderEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(event.getItem() != null && event.getItem().hasItemMeta()){
                    if(Objects.requireNonNull(event.getItem().getItemMeta()).getLocalizedName().equals("lobbyx.player_hider")){
                        if(playerHiderCooldown.containsKey(player.getName())){
                            if(playerHiderCooldown.get(player.getName()) > System.currentTimeMillis()){
                                player.sendMessage(Util.getMessage(Util.getLocale(player), "PlayerHider_Wait"));
                                return;
                            }
                        }
                        if(plugin.yaml.getBoolean("Settings."+player.getUniqueId()+".PlayerHidden")){
                            for(Player lobbyPlayer : player.getWorld().getPlayers()){
                                player.showPlayer(lobbyPlayer);
                            }
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerHider_Show"));
                            plugin.yaml.set("Settings."+player.getUniqueId()+".PlayerHidden", false);
                        }else{
                            for(Player lobbyPlayer : player.getWorld().getPlayers()){
                                player.hidePlayer(lobbyPlayer);
                            }
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerHider_Hide"));
                            plugin.yaml.set("Settings."+player.getUniqueId()+".PlayerHidden", true);
                        }
                        plugin.saveData();
                        plugin.getSetPlayerLobbyManager().setPlayerHider(player);
                        playerHiderCooldown.put(player.getName(),System.currentTimeMillis()+2000);
                    }
                }
            }
        }
    }

}
