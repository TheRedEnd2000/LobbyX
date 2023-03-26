package de.theredend2000.lobbyx.listeners.inventoryListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.commands.FriendCommand;
import de.theredend2000.lobbyx.commands.LobbyXCommand;
import de.theredend2000.lobbyx.jumpandrun.JumpAndRun;
import de.theredend2000.lobbyx.listeners.PlayerInteractAtEntityEventListener;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ItemBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ProfileListener implements Listener {

    private Main plugin;
    private ArrayList<Player> friendSearchPlayers;
    public ProfileListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
        friendSearchPlayers = new ArrayList<>();
        checkPlayerSettings();
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
                        plugin.getProfileMenuManager().createPlayerSettingsInventory(player);
                        break;
                    case "MainInventory.Help":
                        new LobbyXCommand(plugin).helpMessages(player);
                        player.closeInventory();
                        break;
                    case "MainInventory.Friends":
                        plugin.getProfileMenuManager().createFriendInventory(player,true);
                        break;
                    case"MainInventory.Info":
                        break;
                    case"MainInventory.Social":
                        plugin.getProfileMenuManager().createSocialMenu(player);
                        break;
                    case "MainInventory.Clan":
                        if(!player.getName().equals("TheRedEnd2000")){
                            player.sendMessage("§4This feature is currently disabled!");
                            return;
                        }
                        if(plugin.yaml.contains("Clans.")) {
                            for (String clanOwner : plugin.yaml.getConfigurationSection("Clans.").getKeys(false)) {
                                for (String clanNames : plugin.yaml.getConfigurationSection("Clans." + clanOwner + ".").getKeys(false)) {
                                    if (plugin.getClanManager().hasClan(player) || plugin.getClanManager().isAlreadyInClan(UUID.fromString(clanOwner), player.getName(), clanNames)) {

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
                        case"Settings.Language.Spain":
                            player.closeInventory();
                            player.sendMessage("§cThis language has not yet been translated. Do you speak spanish? Then help us to translate this. Join the discord for more info. Discord: https://discord.gg/7x2fzYKucZ");
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
                            plugin.getProfileMenuManager().createFriendInventory(player,true);
                            break;
                        case "friendInventory.sort2":
                            plugin.yaml.set("Settings."+player.getUniqueId()+".FriendSort","Offline");
                            plugin.saveData();
                            plugin.getProfileMenuManager().createFriendInventory(player,true);
                            break;
                        case "friendInventory.sort3":
                            plugin.yaml.set("Settings."+player.getUniqueId()+".FriendSort","Bookmarked");
                            plugin.saveData();
                            plugin.getProfileMenuManager().createFriendInventory(player,true);
                            break;
                        case "friendInventory.sort4":
                            plugin.yaml.set("Settings."+player.getUniqueId()+".FriendSort","All");
                            plugin.saveData();
                            plugin.getProfileMenuManager().createFriendInventory(player,true);
                            break;
                        case "friendInventory.back":
                            plugin.getProfileMenuManager().createProfileInventory(player);
                            break;
                        case "friendInventory.main":
                            plugin.getProfileMenuManager().createProfileInventory(player);
                            break;
                        case "friendInventory.search":
                            friendSearchPlayers.add(player);
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SearchFriend"));
                            player.closeInventory();
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
                            if(!player.getName().equals("TheRedEnd2000")){
                                player.sendMessage("§cThis feature is coming soon.");
                            }
                            break;
                        case "Settings.Friends.Socials":
                            if(!player.getName().equals("TheRedEnd2000")){
                                player.sendMessage("§cThis feature is coming soon.");
                            }
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
                            plugin.getProfileMenuManager().createFriendInventory(player,true);
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
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.DailyRewardInventoryTitle").replaceAll("&","§")))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "daily.dailyReward":
                            player.closeInventory();
                            if(!plugin.getRewardManager().getAllowReward(player, "daily")) {
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyClaimed"));
                                long current = System.currentTimeMillis();
                                long release = plugin.getRewardManager().getTimeDaily(player);
                                long millis = release - current;
                                player.sendMessage(plugin.getRewardManager().getRemainingTime(millis,player));
                                return;
                            }
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"ClaimReward"));
                            plugin.getRewardManager().setRewardDaily(player);
                            int coins = plugin.getConfig().getInt("Settings.DailyRewardCoinsAmount");
                            plugin.getCoinManager().addCoins(player,coins);
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"CoinsLayout").replaceAll("%COINS_AMOUNT%", String.valueOf(coins)).replaceAll("%COINS_BALANCE%", String.valueOf(plugin.getCoinManager().getCoins(player))));
                            break;
                        case "daily.weeklyReward":
                            player.closeInventory();
                            if(!plugin.getRewardManager().getAllowReward(player, "weekly")) {
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyClaimed"));
                                long current = System.currentTimeMillis();
                                long release = plugin.getRewardManager().getTimeWeekly(player);
                                long millis = release - current;
                                player.sendMessage(plugin.getRewardManager().getRemainingTime(millis,player));
                                return;
                            }
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"ClaimReward"));
                            plugin.getRewardManager().setRewardWeekly(player);
                            int coins2 = plugin.getConfig().getInt("Settings.WeeklyRewardCoinsAmount");
                            plugin.getCoinManager().addCoins(player,coins2);
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"CoinsLayout").replaceAll("%COINS_AMOUNT%", String.valueOf(coins2)).replaceAll("%COINS_BALANCE%", String.valueOf(plugin.getCoinManager().getCoins(player))));
                            break;
                        case "daily.monthlyReward":
                            player.closeInventory();
                            if(!plugin.getRewardManager().getAllowReward(player, "monthly")) {
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyClaimed"));
                                long current = System.currentTimeMillis();
                                long release = plugin.getRewardManager().getTimeMonthly(player);
                                long millis = release - current;
                                player.sendMessage(plugin.getRewardManager().getRemainingTime(millis,player));
                                return;
                            }
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"ClaimReward"));
                            plugin.getRewardManager().setRewardMonthly(player);
                            int coins3 = plugin.getConfig().getInt("Settings.MonthlyRewardCoinsAmount");
                            plugin.getCoinManager().addCoins(player,coins3);
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"CoinsLayout").replaceAll("%COINS_AMOUNT%", String.valueOf(coins3)).replaceAll("%COINS_BALANCE%", String.valueOf(plugin.getCoinManager().getCoins(player))));
                            break;
                        case "daily.close":
                            player.closeInventory();
                            break;
                        case "daily.kill":
                            plugin.getRewardManager().removeDailyRewardVillager(player.getLocation());
                            player.playSound(player.getLocation(),Sound.ENTITY_GENERIC_EXPLODE,1,1);
                            player.closeInventory();
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.InteractWithPlayerInventory")).replaceAll("&", "§"))){
            event.setCancelled(true);
            if (event.getCurrentItem() !=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "playerInformation.addFriend":
                            if(!player.getName().equals("TheRedEnd2000")){
                                player.sendMessage("§cThis feature is currently disabled due to a bug.");
                                return;
                            }
                            Player friendtoadd = Bukkit.getPlayer(event.getInventory().getItem(13).getItemMeta().getLocalizedName());
                            if(friendtoadd == null){
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerNotFound"));
                                return;
                            }
                            new FriendCommand(plugin).addFriend(player,friendtoadd);
                            break;
                        case "playerInformation.inviteClan":
                            if(!player.getName().equals("TheRedEnd2000")){
                                player.sendMessage("§cThis feature is coming soon.");
                            }
                            break;
                        case "playerInformation.social":
                            if(!player.getName().equals("TheRedEnd2000")){
                                player.sendMessage("§cThis feature is coming soon.");
                            }
                            break;
                        case "playerInformation.message":
                            String players = event.getInventory().getItem(13).getItemMeta().getLocalizedName();
                            TextComponent c = new TextComponent(plugin.getConfig().getString("Prefix").replaceAll("&","§")+"§7Click to get the command usage: ");
                            TextComponent clickme = new TextComponent("§6§l[HERE]");

                            clickme.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " +players+" <Message>"));
                            clickme.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§aShow command usage.")));
                            c.addExtra(clickme);
                            player.spigot().sendMessage(c);
                            player.closeInventory();
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.SettingsInventoryTitle")).replaceAll("&", "§"))){
            event.setCancelled(true);
            if (event.getCurrentItem() !=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    plugin.getPlayerDataManager().setYaml(player);
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "playerSettings.back":
                            plugin.getProfileMenuManager().createProfileInventory(player);
                            break;
                        case "playerSettings.main":
                            plugin.getProfileMenuManager().createProfileInventory(player);
                            break;
                        case "playerSettings.chat_messages":
                            plugin.getPlayerDataManager().playerDataYaml.set("Settings.Chat_Messages", !plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Chat_Messages"));
                            plugin.getPlayerDataManager().save(player);
                            plugin.getProfileMenuManager().createPlayerSettingsInventory(player);
                            break;
                        case "playerSettings.friend_requests":
                            plugin.getPlayerDataManager().playerDataYaml.set("Settings.FriendRequests", !plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.FriendRequests"));
                            plugin.getPlayerDataManager().save(player);
                            plugin.getProfileMenuManager().createPlayerSettingsInventory(player);
                            break;
                        case "playerSettings.clan_requests":
                            plugin.getPlayerDataManager().playerDataYaml.set("Settings.ClanRequests", !plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.ClanRequests"));
                            plugin.getPlayerDataManager().save(player);
                            plugin.getProfileMenuManager().createPlayerSettingsInventory(player);
                            break;
                        case "playerSettings.join_messages":
                            plugin.getPlayerDataManager().playerDataYaml.set("Settings.Join_Messages", !plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Join_Messages"));
                            plugin.getPlayerDataManager().save(player);
                            plugin.getProfileMenuManager().createPlayerSettingsInventory(player);
                            break;
                        case "playerSettings.leave_messages":
                            plugin.getPlayerDataManager().playerDataYaml.set("Settings.Leave_Messages", !plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Leave_Messages"));
                            plugin.getPlayerDataManager().save(player);
                            plugin.getProfileMenuManager().createPlayerSettingsInventory(player);
                            break;
                        case "playerSettings.fly":
                            String permission = Objects.requireNonNull(plugin.getConfig().getString("Permissions.FlySettingPermission"));
                            if(player.hasPermission(permission)){
                                plugin.getPlayerDataManager().playerDataYaml.set("Settings.Fly", !plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Fly"));
                                plugin.getPlayerDataManager().save(player);
                                plugin.getProfileMenuManager().createPlayerSettingsInventory(player);
                            }else
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"NoPermissionMessageClick").replaceAll("%PERMISSION%",permission));
                            break;
                        case "playerSettings.coins_api":
                            plugin.getPlayerDataManager().playerDataYaml.set("Settings.CoinsAPI", !plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.CoinsAPI"));
                            plugin.getPlayerDataManager().save(player);
                            plugin.getProfileMenuManager().createPlayerSettingsInventory(player);
                            break;
                        case "playerSettings.year":
                            plugin.getPlayerDataManager().playerDataYaml.set("Settings.Year", !plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Year"));
                            plugin.getPlayerDataManager().save(player);
                            plugin.getProfileMenuManager().createPlayerSettingsInventory(player);
                            break;
                        case "playerSettings.msg_messages":
                            String msg_Messages = plugin.getPlayerDataManager().playerDataYaml.getString("Settings.Msg_Messages");
                            switch (msg_Messages){
                                case "EVERYONE":
                                    plugin.getPlayerDataManager().playerDataYaml.set("Settings.Msg_Messages", "FRIENDS");
                                    break;
                                case "FRIENDS":
                                    plugin.getPlayerDataManager().playerDataYaml.set("Settings.Msg_Messages", "NONE");
                                    break;
                                case "NONE":
                                    plugin.getPlayerDataManager().playerDataYaml.set("Settings.Msg_Messages", "EVERYONE");
                                    break;
                                default:
                                    plugin.getPlayerDataManager().playerDataYaml.set("Settings.Msg_Messages", "FRIENDS");
                                    break;
                            }
                            plugin.getPlayerDataManager().save(player);
                            plugin.getProfileMenuManager().createPlayerSettingsInventory(player);
                            break;
                    }
                }
            }
        }
    }

    @EventHandler
    public void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        if(friendSearchPlayers.contains(player)){
            event.setCancelled(true);
            if(event.getMessage().equalsIgnoreCase("cancel")){
                plugin.getProfileMenuManager().createFriendInventory(player,true);
                player.sendMessage(Util.getMessage(Util.getLocale(player),"SearchFriendCancel"));
                friendSearchPlayers.remove(player);
                return;
            }
            if(plugin.yaml.contains("Friends."+player.getUniqueId()+"."+event.getMessage())) {
                plugin.getProfileMenuManager().createFriendSettingsInventory(player, event.getMessage());
            }else{
                player.sendMessage(Util.getMessage(Util.getLocale(player),"SearchFriendNotFound").replaceAll("%PLAYER%",event.getMessage()));
                plugin.getProfileMenuManager().createFriendInventory(player,true);
            }
            friendSearchPlayers.remove(player);
        }
    }

    private void checkPlayerSettings(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(plugin.getLobbyWorlds().contains(player.getWorld())){
                        plugin.getPlayerDataManager().setYaml(player);
                        if(player.getGameMode().equals(GameMode.ADVENTURE) || player.getGameMode().equals(GameMode.SURVIVAL)){
                            String permission = Objects.requireNonNull(plugin.getConfig().getString("Permissions.FlySettingPermission"));
                            if(player.hasPermission(permission)){
                                if (JumpAndRun.getJumpAndRuns().containsKey(player.getUniqueId())) {
                                    player.setFlying(false);
                                    player.setAllowFlight(false);
                                    return;
                                }
                                player.setAllowFlight(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Fly"));
                            }
                            if(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Year") && !plugin.getBuildPlayers().contains(player)){
                                player.setLevel(Integer.parseInt(plugin.getDatetimeUtils().getNowYear()));
                                player.setExp(0);
                            }else{
                                player.setLevel(0);
                                player.setExp(0);
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin,0,10);
    }

}
