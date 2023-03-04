package de.theredend2000.lobbyx.listeners.itemListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class NavigatorListener implements Listener {

    private Main plugin;
    private HashMap<Player, Integer> materialPlayer;
    private HashMap<Player, Integer> namePlayer;

    public NavigatorListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
        namePlayer = new HashMap<>();
        materialPlayer = new HashMap<>();
    }


    @EventHandler
    public void onInteractWithGadgetsEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLocalizedName()){
                    if(Objects.requireNonNull(event.getItem().getItemMeta()).getLocalizedName().equals("lobbyx.teleporter")) {
                        plugin.getNavigatorMenuManager().createTeleporterInventory(player);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.TeleporterInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if(event.getCurrentItem().getItemMeta().hasLocalizedName()) {
                    int slot = Integer.parseInt(event.getCurrentItem().getItemMeta().getLocalizedName());
                    String action = plugin.navigatorYaml.getString("Navigator.Slots." + slot + ".action");
                    switch (Objects.requireNonNull(action)){
                        case "close":
                            player.closeInventory();
                            break;
                        case "teleport":
                            String name = plugin.navigatorYaml.getString("Navigator.Slots." + slot + ".name");
                            if(plugin.navigatorYaml.contains("Navigator.Slots." + slot + ".teleportLocation")){
                                if(loadLocation("Navigator.Slots." + slot + ".teleportLocation") != null)
                                    player.teleport(loadLocation("Navigator.Slots." + slot + ".teleportLocation"));
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"TeleportPlayerToLocation").replaceAll("%GAME%", name.replaceAll("&","§")));
                            }else
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"LocationNotSet"));
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals("§7Select a Slot")){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    int invSize = plugin.navigatorYaml.getInt("Navigator.Settings.size");
                    for(int i = 0; i < invSize; i++){
                        if(event.getCurrentItem().getItemMeta().getLocalizedName().equals(String.valueOf(i))){
                            plugin.getNavigatorMenuManager().createSlotEditInventory(player,i);
                        }
                    }
                }
            }
        }else if (event.getView().getTitle().equals("§7Edit Navigator Slot")){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    int slot = Integer.parseInt(event.getInventory().getItem(0).getItemMeta().getLocalizedName());
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "back":
                            plugin.getNavigatorMenuManager().createSelectSlotInventory(player);
                            break;
                        case "action.none":
                            plugin.navigatorYaml.set("Navigator.Slots." + slot + ".action", "teleport");
                            plugin.saveNavigator();
                            plugin.getNavigatorMenuManager().createSlotEditInventory(player, slot);
                            break;
                        case "action.teleport":
                            plugin.navigatorYaml.set("Navigator.Slots." + slot + ".action", "close");
                            plugin.saveNavigator();
                            plugin.getNavigatorMenuManager().createSlotEditInventory(player, slot);
                            break;
                        case "action.close":
                            plugin.navigatorYaml.set("Navigator.Slots." + slot + ".action", "none");
                            plugin.saveNavigator();
                            plugin.getNavigatorMenuManager().createSlotEditInventory(player, slot);
                            break;
                        case "name":
                            player.closeInventory();
                            namePlayer.put(player,slot);
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"EnterNewNameInChat"));
                            break;
                        case "material":
                            player.closeInventory();
                            materialPlayer.put(player,slot);
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"EnterNewMaterialInChat"));
                            break;
                        case "location":
                            saveLocation("Navigator.Slots." + slot + ".teleportLocation",player.getLocation());
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"LocationSaved"));
                            break;
                    }
                }
            }
        }
    }

    public void saveLocation(String root, Location location) {
        FileConfiguration config = plugin.navigatorYaml;
        config.set(root + ".World", location.getWorld().getName());
        config.set(root + ".X", location.getX());
        config.set(root + ".Y", location.getY());
        config.set(root + ".Z", location.getZ());
        config.set(root + ".Yaw", location.getYaw());
        config.set(root + ".Pitch", location.getPitch());
        plugin.saveNavigator();
    }

    public Location loadLocation(String root) {
        FileConfiguration config = plugin.navigatorYaml;
        if (config.contains(root)) {
            World world = Bukkit.getWorld(config.getString(root + ".World"));
            double x = config.getDouble(root + ".X"),
                    y = config.getDouble(root + ".Y"),
                    z = config.getDouble(root + ".Z");
            float yaw = (float) config.getDouble(root + ".Yaw"),
                    pitch = (float) config.getDouble(root + ".Pitch");
            return new Location(world, x, y, z, yaw, pitch);
        } else
            return null;
    }

    @EventHandler
    public void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        if(namePlayer.containsKey(player)){
            event.setCancelled(true);
            int slot = namePlayer.get(player);
            if(event.getMessage().equalsIgnoreCase("cancel")){
                plugin.getNavigatorMenuManager().createSlotEditInventory(player,slot);
                player.sendMessage(Util.getMessage(Util.getLocale(player),"EnterInChatCanceled"));
                return;
            }
            plugin.navigatorYaml.set("Navigator.Slots." + slot + ".name",event.getMessage());
            plugin.saveNavigator();
            plugin.getNavigatorMenuManager().createSlotEditInventory(player,slot);
            namePlayer.remove(player);
        }
        if(materialPlayer.containsKey(player)){
            event.setCancelled(true);
            int slot = materialPlayer.get(player);
            if(event.getMessage().equalsIgnoreCase("cancel")){
                plugin.getNavigatorMenuManager().createSlotEditInventory(player,slot);
                player.sendMessage(Util.getMessage(Util.getLocale(player),"EnterInChatCanceled"));
                return;
            }
            Material material = Material.getMaterial(event.getMessage().toUpperCase());
            if(material == null){
                player.sendMessage(Util.getMessage(Util.getLocale(player),"NotAMaterial").replaceAll("%MATERIAL%",event.getMessage()));
                plugin.getNavigatorMenuManager().createSlotEditInventory(player, slot);
                materialPlayer.remove(player);
                return;
            }
            plugin.navigatorYaml.set("Navigator.Slots." + slot + ".item", event.getMessage().toUpperCase());
            plugin.saveNavigator();
            plugin.getNavigatorMenuManager().createSlotEditInventory(player, slot);
            materialPlayer.remove(player);
        }
    }
}
