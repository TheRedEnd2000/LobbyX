package de.theredend2000.lobbyx.listeners.inventoryListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
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
                        case "lobbyx.editNavigator":
                            plugin.getNavigatorMenuManager().createSelectSlotInventory(player);
                            break;
                        case "lobbyx.editRanks":
                            player.closeInventory();
                            TextComponent c = new TextComponent("§7You can edit all the ranks in the webeditor of power ranks. Click here to get the link. ");
                            TextComponent clickme = new TextComponent("§6§l[HERE]");

                            clickme.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/pr webeditor start"));
                            clickme.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§aClick to generate webeditor link.")));
                            c.addExtra(clickme);
                            player.spigot().sendMessage(c);
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
                        case"lobbyx.locations.lobby":
                            new ConfigLocationUtil(plugin,player.getLocation(),"Locations.Lobby."+player.getWorld().getName()).saveLocation();
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SetLobby").replaceAll("%WORLD_NAME%",player.getWorld().getName()));
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
