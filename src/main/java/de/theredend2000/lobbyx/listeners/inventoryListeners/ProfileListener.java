package de.theredend2000.lobbyx.listeners.inventoryListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.File;
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
        if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.ProfileInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                    case "MainInventory.Profil":
                        break;
                    case "MainInventory.Stats":
                        break;
                    case"MainInventory.Settings":
                        plugin.getProfileMenuManager().createLanguageInventory(player);
                        break;
                    case"MainInventory.Social":
                        break;
                    default:
                        break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.LanguageInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "Settings.Language.Back":
                            plugin.getProfileMenuManager().createProfileInventory(player);
                            break;
                        case "Settings.Language.Deutsch":
                            player.closeInventory();
                            File file1 = new File(plugin.getDataFolder()+"/locales","de.yml");
                            Util.setLocale(player, file1);
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "LangSelectEnglish"));
                            break;
                        case"Settings.Language.Englisch":
                            player.closeInventory();
                            File file2 = new File(plugin.getDataFolder()+"/locales","en.yml");
                            Util.setLocale(player, file2);
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "LangSelectEnglish"));
                            break;
                        case"Settings.Language.Spanisch":
                            player.closeInventory();
                            player.sendMessage("In Arbeit");
                            /*File file = new File(plugin.getDataFolder()+"/locales","sp.yml");
                            Util.setLocale(player, file);
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "LangSelectEnglish"));*/
                            break;
                    }
                }
            }
        }
    }

}
