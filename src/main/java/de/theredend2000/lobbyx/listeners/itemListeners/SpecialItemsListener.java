package de.theredend2000.lobbyx.listeners.itemListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;
import java.util.Random;

public class SpecialItemsListener implements Listener {

    private Main plugin;

    public SpecialItemsListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteractSpecialItemEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLocalizedName()){
                    for(String items : plugin.gadgetsYaml.getConfigurationSection("Gadgets.SpecialItems").getKeys(false)){
                        if(event.getItem().getItemMeta().getLocalizedName().equals(items)){
                            player.closeInventory();
                            String itemName = event.getItem().getItemMeta().getDisplayName();
                            Material itemMaterial = event.getMaterial();
                            String gadgetName = plugin.gadgetsYaml.getString("Gadgets.SpecialItems."+items+".name");
                            Material material = plugin.getGadgetsMaterial("Gadgets.SpecialItems."+items+".item");
                            if(itemName.equals(gadgetName) && itemMaterial.equals(material)){
                                if(items.equals("Paint_Ball")){
                                    Random r = new Random();
                                    int chance = r.nextInt(3);
                                    if(chance == 0){

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
