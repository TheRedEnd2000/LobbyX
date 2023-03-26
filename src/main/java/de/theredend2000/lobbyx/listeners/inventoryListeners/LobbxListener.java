package de.theredend2000.lobbyx.listeners.inventoryListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class LobbxListener implements Listener {

    private Main plugin;

    public LobbxListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        lobbySpeed();
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.MainInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "lobbyx.settings":
                            plugin.getLobbyXMenuManager().createSettingsInventory(player);
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
                        case "lobbyx.jnr":
                            plugin.getLobbyXMenuManager().createJnrInventory(player);
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.MainSettingsInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "lobbyx.settings.back":
                            plugin.getLobbyXMenuManager().createMainInventory(player);
                            break;
                        case"lobbyx.settings.lobby":
                            new ConfigLocationUtil(plugin,player.getLocation(),"Locations.Lobby."+player.getWorld().getName()).saveLocation();
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SetLobby").replaceAll("%WORLD_NAME%",player.getWorld().getName()));
                            player.closeInventory();
                            break;
                        case"lobbyx.settings.close":
                            player.closeInventory();
                            break;
                        case "lobbyx.settings.lobbyspeed":
                            plugin.getConfig().set("Settings.LobbySpeed", !plugin.getConfig().getBoolean("Settings.LobbySpeed"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createSettingsInventory(player);
                            break;
                        case "lobbyx.settings.mobDamage":
                            plugin.getConfig().set("Settings.MobDamageInLobbys", !plugin.getConfig().getBoolean("Settings.MobDamageInLobbys"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createSettingsInventory(player);
                            break;
                        case "lobbyx.settings.welcomeTitles":
                            plugin.getConfig().set("Titles.WelcomeTitle.enabled", !plugin.getConfig().getBoolean("Titles.WelcomeTitle.enabled"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createSettingsInventory(player);
                            break;
                        case "lobbyx.settings.lobbySelector":
                            plugin.getConfig().set("LobbySelector.enabled", !plugin.getConfig().getBoolean("LobbySelector.enabled"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createSettingsInventory(player);
                            break;
                        case "lobbyx.settings.broadcaster":
                            plugin.getConfig().set("Broadcaster.enabled", !plugin.getConfig().getBoolean("Broadcaster.enabled"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createSettingsInventory(player);
                            break;
                        case "lobbyx.settings.ylevel":
                            if(event.getAction().equals(InventoryAction.PICKUP_ALL)){
                                int currentLevel = plugin.getConfig().getInt("Settings.PlayerYLevelTeleport");
                                if(currentLevel == 100){
                                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_HURT,5,1);
                                    return;
                                }
                                plugin.getConfig().set("Settings.PlayerYLevelTeleport",currentLevel+1);
                                plugin.saveConfig();
                                plugin.getLobbyXMenuManager().createSettingsInventory(player);
                            }else if(event.getAction().equals(InventoryAction.PICKUP_HALF)){
                                int currentLevel = plugin.getConfig().getInt("Settings.PlayerYLevelTeleport");
                                if(currentLevel == -200){
                                    player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_HURT,5,1);
                                    return;
                                }
                                plugin.getConfig().set("Settings.PlayerYLevelTeleport",currentLevel-1);
                                plugin.saveConfig();
                                plugin.getLobbyXMenuManager().createSettingsInventory(player);
                            }
                            break;
                        case "lobbyx.settings.items":
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.MainJnrInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "lobbyx.jnr.back":
                            plugin.getLobbyXMenuManager().createMainInventory(player);
                            break;
                        case"lobbyx.jnr.close":
                            player.closeInventory();
                            break;
                        case"lobbyx.jnr.coins":
                            plugin.getConfig().set("JumpAndRun.jnrCoins", !plugin.getConfig().getBoolean("JumpAndRun.jnrCoins"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createJnrInventory(player);
                            break;
                        case"lobbyx.jnr.pressure":
                            plugin.getConfig().set("JumpAndRun.pressurePlateStart", !plugin.getConfig().getBoolean("JumpAndRun.pressurePlateStart"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createJnrInventory(player);
                            break;
                        case"lobbyx.jnr.enabled":
                            plugin.getConfig().set("JumpAndRun.enabled", !plugin.getConfig().getBoolean("JumpAndRun.enabled"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createJnrInventory(player);
                            break;
                        case"lobbyx.jnr.actionbar":
                            plugin.getConfig().set("JumpAndRun.Actionbar.enabled", !plugin.getConfig().getBoolean("JumpAndRun.Actionbar.enabled"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createJnrInventory(player);
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.MainIntractableItemsInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "lobbyx.items.back":
                            plugin.getLobbyXMenuManager().createSettingsInventory(player);
                            break;
                        case"lobbyx.items.close":
                            player.closeInventory();
                            break;
                        case "lobbyx.items.redstone":
                            plugin.getConfig().set("IntractableItems.RedStoneItems", !plugin.getConfig().getBoolean("IntractableItems.RedStoneItems"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.station":
                            plugin.getConfig().set("IntractableItems.WorkingStations", !plugin.getConfig().getBoolean("IntractableItems.WorkingStations"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.furnace":
                            plugin.getConfig().set("IntractableItems.Furnaces", !plugin.getConfig().getBoolean("IntractableItems.Furnaces"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.doors":
                            plugin.getConfig().set("IntractableItems.Doors", !plugin.getConfig().getBoolean("IntractableItems.Doors"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.buttons":
                            plugin.getConfig().set("IntractableItems.Buttons", !plugin.getConfig().getBoolean("IntractableItems.Buttons"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.boats":
                            plugin.getConfig().set("IntractableItems.Boats", !plugin.getConfig().getBoolean("IntractableItems.Boats"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.chests":
                            plugin.getConfig().set("IntractableItems.Chests", !plugin.getConfig().getBoolean("IntractableItems.Chests"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.fence":
                            plugin.getConfig().set("IntractableItems.Fence_Gates", !plugin.getConfig().getBoolean("IntractableItems.Fence_Gates"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.trapdoors":
                            plugin.getConfig().set("IntractableItems.Trapdoors", !plugin.getConfig().getBoolean("IntractableItems.Trapdoors"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.candles":
                            plugin.getConfig().set("IntractableItems.Candles", !plugin.getConfig().getBoolean("IntractableItems.Candles"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.other":
                            plugin.getConfig().set("IntractableItems.Other", !plugin.getConfig().getBoolean("IntractableItems.Other"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.minecarts":
                            plugin.getConfig().set("IntractableItems.Minecarts", !plugin.getConfig().getBoolean("IntractableItems.Minecarts"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.anvils":
                            plugin.getConfig().set("IntractableItems.Anvils", !plugin.getConfig().getBoolean("IntractableItems.Anvils"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                        case "lobbyx.items.shulkers":
                            plugin.getConfig().set("IntractableItems.Shulkers", !plugin.getConfig().getBoolean("IntractableItems.Shulkers"));
                            plugin.saveConfig();
                            plugin.getLobbyXMenuManager().createIntractableItemsInventory(player);
                            break;
                    }
                }
            }
        }
    }

    private void lobbySpeed(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player players : Bukkit.getOnlinePlayers()){
                    if(plugin.getLobbyWorlds().contains(players.getWorld())){
                        if(plugin.getConfig().getBoolean("Settings.LobbySpeed")){
                            players.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,21,0,false,false));
                        }
                    }
                }
            }
        }.runTaskTimer(plugin,0,20);
    }

}
