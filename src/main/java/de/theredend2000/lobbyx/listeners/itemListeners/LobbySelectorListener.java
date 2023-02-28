package de.theredend2000.lobbyx.listeners.itemListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class LobbySelectorListener implements Listener {

    private Main plugin;

    public LobbySelectorListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onInteractWithGadgetsEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLocalizedName()){
                    if(Objects.requireNonNull(event.getItem().getItemMeta()).getLocalizedName().equals("lobbyx.lobbyselector")) {
                        if (plugin.getConfig().getBoolean("LobbySelector.enabled")) {
                            plugin.getLobbySelectorManager().createLobbySelector(player);
                        } else
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "LobbySelectorDisabled"));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.LobbySelectorInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    World world = Bukkit.getWorld(event.getCurrentItem().getItemMeta().getLocalizedName());
                    if(world == null){
                        player.sendMessage("§4§lThere was an error. Please try again.");
                        player.closeInventory();
                        return;
                    }
                    if(world.getName().equalsIgnoreCase(player.getWorld().getName())){
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"LobbySelectorAlreadyInThisWorld"));
                        player.closeInventory();
                        return;
                    }
                    ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin,"Locations.Lobby."+world.getName());
                    if(locationUtil.loadLocation() != null){
                        player.teleport(locationUtil.loadLocation());
                    }else{
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NoLobbySpawn"));
                        return;
                    }
                    plugin.getSetPlayerLobbyManager().setPlayerInLobby(player);
                }
            }
        }
    }

}
