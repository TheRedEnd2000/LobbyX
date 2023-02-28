package de.theredend2000.lobbyx.listeners.inventoryListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.listeners.itemListeners.GadgetsListener;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Objects;

public class GadgetsInventoryListener implements Listener {

    private Main plugin;

    public GadgetsInventoryListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.GadgetsMainMenu")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "gadgets.main.close":
                            player.closeInventory();
                            break;
                        case "gadgets.main.heads":
                            plugin.getGadgetsMenuManager().createHeadsGadgetsInventory(player);
                            break;
                        case"gadgets.main.specialItems":
                            plugin.getGadgetsMenuManager().createItemGadgetsInventory(player);
                            break;
                        case"MainInventory.Social":
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.HeadInventory")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "gadgets.head.close":
                            player.closeInventory();
                            break;
                        case "gadgets.heads.reset":
                            if(player.getInventory().getHelmet() != null){
                                player.getInventory().setHelmet(null);
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"ResetHead"));
                            }else{
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"FailedResetHead"));
                            }
                            break;
                        case "gadgets.heads.back":
                            plugin.getGadgetsMenuManager().createGadgetsInventory(player);
                            break;
                    }
                    for(String heads : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Heads").getKeys(false)){
                        if(event.getCurrentItem().getItemMeta().getLocalizedName().equals(heads)){
                            player.closeInventory();
                            String headName = plugin.gadgetsYaml.getString("Gadgets.Heads."+heads+".name");
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SelectHead").replaceAll("%HEAD%", headName.replaceAll("&","§")));
                            String headTexture = plugin.gadgetsYaml.getString("Gadgets.Heads."+heads+".texture");
                            player.getInventory().setHelmet(new ItemBuilder(Material.PLAYER_HEAD).setDisplayname(headName.replaceAll("&","§")).setSkullOwner(Main.getTexture(headTexture)).build());
                            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Head",heads);
                            plugin.saveData();
                        }
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.SpecialItemInventory")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem() != null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "gadgets.items.close":
                            player.closeInventory();
                            break;
                        case "gadgets.items.reset":
                            if(player.getInventory().getHelmet() != null){
                                player.getInventory().setHelmet(null);
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"ResetHead"));
                            }else{
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"FailedResetHead"));
                            }
                            break;
                        case "gadgets.items.back":
                            plugin.getGadgetsMenuManager().createGadgetsInventory(player);
                            break;
                    }
                    for(String items : plugin.gadgetsYaml.getConfigurationSection("Gadgets.SpecialItems").getKeys(false)){
                        if(event.getCurrentItem().getItemMeta().getLocalizedName().equals(items)){
                            player.closeInventory();
                            String gadgetName = plugin.gadgetsYaml.getString("Gadgets.SpecialItems."+items+".name");
                            Material material = plugin.getGadgetsMaterial("Gadgets.SpecialItems."+items+".item");
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SelectSpecialItems").replaceAll("%GADGET%", gadgetName.replaceAll("&","§")));
                            player.getInventory().setItem(5, new ItemBuilder(material).setDisplayname(gadgetName.replaceAll("&","§")).build());
                            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Inv",items);
                            plugin.saveData();
                        }
                    }
                }
            }
        }
    }

}
