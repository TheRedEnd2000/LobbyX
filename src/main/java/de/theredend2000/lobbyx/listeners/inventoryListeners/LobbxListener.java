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
