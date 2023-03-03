package de.theredend2000.lobbyx.listeners.itemListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class NavigatorListener implements Listener {

    private Main plugin;

    public NavigatorListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }


    @EventHandler
    public void onInteractWithGadgetsEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLocalizedName()){
                    if(Objects.requireNonNull(event.getItem().getItemMeta()).getLocalizedName().equals("lobbyx.teleporter")) {
                        createTeleporterInventory(player);
                    }
                }
            }
        }
    }

    public void createTeleporterInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.TeleporterInventoryTitle")).replaceAll("&","§"));
        if(plugin.navigatorYaml.contains("Navigator.Slots")) {
            for (String slot : plugin.navigatorYaml.getConfigurationSection("Navigator.Slots").getKeys(false)) {
                String itemName = plugin.navigatorYaml.getString("Navigator.Slots."+slot+".name");
                String material = "Navigator.Slots."+slot+".item";
                int itemSlot = Integer.parseInt(slot);
                inventory.setItem(itemSlot ,new ItemBuilder(plugin.getNavigatorMaterial(material)).setDisplayname(itemName.replaceAll("&", "§")).setLocalizedName("null").build());
            }
        }else
            player.sendMessage("§cERROR! Please contact an admin.");
        player.openInventory(inventory);
    }


}
