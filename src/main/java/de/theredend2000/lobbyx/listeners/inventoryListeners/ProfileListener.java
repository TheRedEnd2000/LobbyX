package de.theredend2000.lobbyx.listeners.inventoryListeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class ProfileListener implements Listener {

    private Main plugin;

    public ProfileListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.ProfileInventoryTitle")).replaceAll("&","§"))){event.setCancelled(true);if (event.getCurrentItem()!=null){if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                    case "MainInventory.Profil":
                        plugin.getProfileMenuManager();
                        break;
                    case "MainInventory.Stats":
                        break;
                    case"MainInventory.Settings":
                        break;
                    case"MainInventory.Sozial":
                        break;

                }
            }
        }
        }



    }

}
