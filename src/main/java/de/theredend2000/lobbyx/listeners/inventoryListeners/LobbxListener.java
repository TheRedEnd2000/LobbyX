package de.theredend2000.lobbyx.listeners.inventoryListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class LobbxListener implements Listener {

    private Main plugin;

    public LobbxListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.MainInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "lobbyx.locations":
                            plugin.getLobbyXMenuManager().createLocationsInventpry(player);
                            break;
                        case "MainInventory.Stats":
                            break;
                        case"MainInventory.Languages":
                            plugin.getProfileMenuManager().createFriendInventory(player);
                            break;
                        case"MainInventory.Social":
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.LocationInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "lobbyx.locations.back":
                            plugin.getLobbyXMenuManager().createMainInventory(player);
                            break;
                        case "lobbyx.locations.hub":
                            new ConfigLocationUtil(plugin,player.getLocation(),"Locations.Hub").saveLocation();
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SetHub"));
                            player.closeInventory();
                            break;
                        case"lobbyx.locations.lobby":
                            new ConfigLocationUtil(plugin,player.getLocation(),"Locations.Lobby").saveLocation();
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SetLobby"));
                            player.closeInventory();
                            break;
                        case"lobbyx.locations.close":
                            player.closeInventory();
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.LocationInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "player.":
                            plugin.getLobbyXMenuManager().createMainInventory(player);
                            break;
                        case "lobbyx.locations.hub":
                            new ConfigLocationUtil(plugin,player.getLocation(),"Locations.Hub").saveLocation();
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SetHub"));
                            player.closeInventory();
                            break;
                        case"lobbyx.locations.lobby":
                            new ConfigLocationUtil(plugin,player.getLocation(),"Locations.Lobby").saveLocation();
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SetLobby"));
                            player.closeInventory();
                            break;
                        case"lobbyx.locations.close":
                            player.closeInventory();
                            break;
                    }
                }
            }
        }
    }

}
