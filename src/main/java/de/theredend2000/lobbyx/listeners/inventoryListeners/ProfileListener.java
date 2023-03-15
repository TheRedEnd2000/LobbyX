package de.theredend2000.lobbyx.listeners.inventoryListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ProfileListener implements Listener {

    private Main plugin;
    private ArrayList<Player> rankNamePlayers;
    private ArrayList<Player> rankPrefixPlayers;
    private ArrayList<Player> rankPermissionPlayers;

    public ProfileListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        rankPrefixPlayers = new ArrayList<>();
        rankNamePlayers = new ArrayList<>();
        rankPermissionPlayers = new ArrayList<>();
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.ProfileInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                    case "MainInventory.Settings":
                        break;
                    case "MainInventory.Help":
                        break;
                    case "MainInventory.Friends":
                        plugin.getProfileMenuManager().createFriendInventory(player);
                        break;
                    case"MainInventory.Info":
                        break;
                    case"MainInventory.Social":
                        plugin.getProfileMenuManager().createSozailMenu(player);
                        break;
                    case "MainInventory.Clan":
                        if(plugin.yaml.contains("Clans.")) {
                            for (String clanOwner : plugin.yaml.getConfigurationSection("Clans.").getKeys(false)) {
                                for (String clanNames : plugin.yaml.getConfigurationSection("Clans." + clanOwner + ".").getKeys(false)) {
                                    if (plugin.getClanManager().hasClan(player) || plugin.getClanManager().isAlreadyInClan(UUID.fromString(clanOwner), player.getName(), clanNames)) {
                                        if(plugin.getClanManager().hasClan(player)){
                                            plugin.getProfileMenuManager().createClanGuiLeader(player,true);
                                        }else
                                            plugin.getProfileMenuManager().CreateClanGuiMember(player,true);
                                    } else
                                        plugin.getProfileMenuManager().createClanChooseInventory(player);
                                }
                            }
                        }else
                            plugin.getProfileMenuManager().createClanChooseInventory(player);
                        break;
                    case "MainInventory.Languages":
                        plugin.getProfileMenuManager().createLanguageInventory(player);
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
                        case"Settings.Language.English":
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
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.FriendInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                for(String friends : plugin.yaml.getConfigurationSection("Friends."+player.getUniqueId()+".").getKeys(false)){
                    if(event.getCurrentItem().getItemMeta().getLocalizedName().equals("friend."+friends)){
                        plugin.getProfileMenuManager().createFriendSettingsInventory(player,friends);
                    }
                }
                if(event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "friendInventory.sort1":
                            plugin.yaml.set("Settings."+player.getUniqueId()+".FriendSort","Online");
                            plugin.saveData();
                            plugin.getProfileMenuManager().createFriendInventory(player);
                            break;
                        case "friendInventory.sort2":
                            plugin.yaml.set("Settings."+player.getUniqueId()+".FriendSort","Offline");
                            plugin.saveData();
                            plugin.getProfileMenuManager().createFriendInventory(player);
                            break;
                        case "friendInventory.sort3":
                            plugin.yaml.set("Settings."+player.getUniqueId()+".FriendSort","Bookmarked");
                            plugin.saveData();
                            plugin.getProfileMenuManager().createFriendInventory(player);
                            break;
                        case "friendInventory.sort4":
                            plugin.yaml.set("Settings."+player.getUniqueId()+".FriendSort","All");
                            plugin.saveData();
                            plugin.getProfileMenuManager().createFriendInventory(player);
                            break;
                        case "friendInventory.back":
                            player.sendMessage("need setup");
                            break;
                        case "friendInventory.main":
                            plugin.getProfileMenuManager().createProfileInventory(player);
                            break;
                        case "friendInventory.search":
                            player.sendMessage("SOON");
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.FriendSettingsInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if(event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    String friend = ChatColor.stripColor(event.getInventory().getItem(4).getItemMeta().getDisplayName());
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "Settings.Friends.Bookmark":
                            if(plugin.yaml.getBoolean("Friends."+player.getUniqueId()+"."+friend+".Bookmarked")){
                                plugin.yaml.set("Friends."+player.getUniqueId()+"."+friend+".Bookmarked",false);
                            }else
                                plugin.yaml.set("Friends."+player.getUniqueId()+"."+friend+".Bookmarked",true);
                            plugin.saveData();
                            plugin.getProfileMenuManager().createFriendSettingsInventory(player,friend);
                            break;
                        case "Settings.Friends.Clan":
                            player.sendMessage("need setup");
                            break;
                        case "Settings.Friends.Remove":
                            plugin.getProfileMenuManager().createDeleteFriendInventory(player, friend, 5, false);
                            new BukkitRunnable() {
                                int seconds = 5;
                                @Override
                                public void run() {
                                    if (player.getOpenInventory().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.FriendRemoveInventoryTitle")).replaceAll("&","§"))) {
                                        if (seconds == 0) {
                                            cancel();
                                            plugin.getProfileMenuManager().createDeleteFriendInventory(player, friend, seconds, true);
                                            return;
                                        }
                                        plugin.getProfileMenuManager().createDeleteFriendInventory(player, friend, seconds, false);
                                        seconds--;
                                    }else{
                                        cancel();
                                    }
                                }
                            }.runTaskTimer(plugin,0,20);
                            break;
                        case "Settings.Friends.Back":
                            plugin.getProfileMenuManager().createFriendInventory(player);
                            break;
                        case "Settings.Friends.Main":
                            plugin.getProfileMenuManager().createProfileInventory(player);
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.FriendRemoveInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if(event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    String friend = ChatColor.stripColor(event.getInventory().getItem(13).getItemMeta().getDisplayName());
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "Friend.remove":
                            player.closeInventory();
                            for (String friends : plugin.yaml.getConfigurationSection("Friends." + player.getUniqueId()).getKeys(false)) {
                                if (friends.equalsIgnoreCase(friend) && plugin.yaml.contains("Friends." + player.getUniqueId()+"."+friends)) {
                                    String friendtoremoveUUID = plugin.yaml.getString("Friends."+player.getUniqueId()+"."+friend+".UUID");
                                    player.sendMessage(Util.getMessage(Util.getLocale(player),"FriendRemoveSender").replaceAll("%PLAYER_REMOVED%",friend).replaceAll("%PLAYER_REMOVED_FRIEND%",player.getName()));
                                    Player removed = Bukkit.getPlayer(friend);
                                    if(removed != null)
                                        removed.sendMessage(Util.getMessage(Util.getLocale(removed),"FriendRemoveReceiver").replaceAll("%PLAYER_REMOVED%",friend).replaceAll("%PLAYER_REMOVED_FRIEND%",player.getName()));
                                    plugin.yaml.set("Friends."+player.getUniqueId()+"."+friend, null);
                                    plugin.yaml.set("Friends."+friendtoremoveUUID+"."+player.getName(), null);
                                    plugin.saveData();
                                    return;
                                }
                            }
                            break;
                        case "Friend.cancel":
                            plugin.getProfileMenuManager().createFriendSettingsInventory(player,friend);
                            break;
                    }
                }
            }//kp
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.SocialInventoryTitle").replaceAll("&","§")))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case"Sozial.Back":
                            plugin.getProfileMenuManager().createProfileInventory(player);
                            break;
                        case"Sozial.MainMenu":
                            plugin.getProfileMenuManager().createProfileInventory(player);
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.ClanInventory.ClanChooseInventoryTitle").replaceAll("&","§")))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case"ClanChoose.Back":
                            plugin.getProfileMenuManager().createProfileInventory(player);
                            break;
                        case ":":
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.InteractwithplayerInventory")).replaceAll("&", "§"))){
            event.setCancelled(true);
            if (event.getCurrentItem() !=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "":
                            break;
                    }
                }
            }
        }
    }



}
