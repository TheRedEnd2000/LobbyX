package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ColorUtils;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;

public class ProfileMenuManager implements Listener {

    private Main plugin;

    public ProfileMenuManager(Main plugin) {
        this.plugin = plugin;
    }

    /*
        Main Inventory
     */

    public void createProfileInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.ProfileInventoryTitle")).replaceAll("&", "§"));
        int[] blauGlass = new int[]{2,6,10,16,18,20,24,26,27,29,33,35,37,43,45,53};
        int[] lightBlueGlass = new int[]{1,3,5,7,28,31,34,36,39,40,41,44,46,48,49,50,52};
        int[] cyanGlass = new int[]{9,17,21,22,23,47,51};
        int[] whiteGlass = new int[]{11,12,13,14,15,30,32};
        for (int i = 0; i < blauGlass.length; i++) {inventory.setItem(blauGlass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}for (int i = 0; i < cyanGlass.length; i++) {inventory.setItem(cyanGlass[i], new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < lightBlueGlass.length; i++) {inventory.setItem(lightBlueGlass[i], new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}for (int i = 0; i < whiteGlass.length; i++) {inventory.setItem(whiteGlass[i], new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(0, new ItemBuilder(Material.COMPARATOR).setDisplayname("§4Settings").setLore("§7Configure your messages,","§7friend or clan requests and more...","§eClick to configure.").setLocalizedName("MainInventory.Settings").build());
        inventory.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setOwner(player.getName()).setDisplayname(ColorUtils.format('&',"#A2FF00Y#A6FF0BO#A9FF13U#ACFF1CR #E9FF00P#EAFF10R#EBFF19O#ECFF25F#EDFF30I#EFFF43L#F1FF57E",false,false)).setLore("§7In this category you can edit","§7everything and customize it for yourself.").build());
        inventory.setItem(8, new ItemBuilder(Material.BOOK).setDisplayname("§9Help").setLore("§eClick to get help.").setLocalizedName("MainInventory.Help").build());
        inventory.setItem(19, new ItemBuilder(Material.WRITABLE_BOOK).setDisplayname("§3Your Friends").setLore("§7Manage your friendships and create new ones.","§eClick to manage friendships.").setLocalizedName("MainInventory.Friends").build());
        inventory.setItem(25, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§dSocials").setSkullOwner(Main.getTexture("ZmNjZDRkMWIxNGVhMTQyZDgxYWMwNTQyYmZiZGE3ZTdiZDU3ZWI0YzA0YjhkZmIyNzkwNDViYmY5N2NhNjBhMSJ9fX0=")).setLore("§7Show others your social media accounts","§eClick to manage.").setLocalizedName("MainInventory.Social").build());
        inventory.setItem(38, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§9Clan").setLore("§aClans are wonderful.","§7Create your own or join another","§7clan to meet new people.","§eShow clan.").setSkullOwner(Main.getTexture("NmE1MzYxYjUyZGFmNGYxYzVjNTQ4MGEzOWZhYWExMDg5NzU5NWZhNTc2M2Y3NTdiZGRhMzk1NjU4OGZlYzY3OCJ9fX0=")).setLocalizedName("MainInventory.Clan").build());
        switch (Util.getPlayerLanguage(player)){
            case"EN": inventory.setItem(42, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§1Lan§fgua§cges").setSkullOwner(Main.getTexture("ODgzMWM3M2Y1NDY4ZTg4OGMzMDE5ZTI4NDdlNDQyZGZhYTg4ODk4ZDUwY2NmMDFmZDJmOTE0YWY1NDRkNTM2OCJ9fX0=")).setLore("§7Current Language:§c English","","§eClick to change your language.").setLocalizedName("MainInventory.Languages").build());break;
            case "DE": inventory.setItem(42, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§0Lan§4gua§eges").setSkullOwner(Main.getTexture("NWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==")).setLore("§7Current Language:§b Deutsch","","§eClick to change your language.").setLocalizedName("MainInventory.Languages").build());break;
            case "SP": inventory.setItem(42, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§4Lan§6gua§4ges").setSkullOwner(Main.getTexture("YzJkNzMwYjZkZGExNmI1ODQ3ODNiNjNkMDgyYTgwMDQ5YjVmYTcwMjI4YWJhNGFlODg0YzJjMWZjMGMzYThiYyJ9fX0=")).setLore("§7Current Language:§6 Español","","§eClick to change your language.").setLocalizedName("MainInventory.Languages").build());break;
        }
        player.openInventory(inventory);
    }

    /*
        Friend Manage Inventory's
     */

    public void createFriendInventory(Player player, boolean backArrow) {
        Inventory inventory = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.FriendInventoryTitle")).replaceAll("&", "§"));
        inventory.clear();
        String sort = plugin.yaml.getString("Settings." + player.getUniqueId() + ".FriendSort");
        if (sort == null) {
            plugin.yaml.set("Settings." + player.getUniqueId() + ".FriendSort", "All");
            plugin.saveData();
            player.sendMessage("§4§lThere was an error. Please try again.");
            return;
        }
        int[] orangeGlass = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44,45, 46, 47, 51, 52, 53};
        for (int i = 0; i < orangeGlass.length; i++) {
            inventory.setItem(orangeGlass[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        inventory.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setOwner(player.getName()).setDisplayname("§aYour Friends").build());
        if(backArrow)
            inventory.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eBack").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("friendInventory.back").build());
        inventory.setItem(48, new ItemBuilder(Material.OAK_SIGN).setDisplayname("§6Search").setLocalizedName("friendInventory.search").build());
        inventory.setItem(49, new ItemBuilder(Material.NETHER_STAR).setDisplayname("§9Main Menu").setLocalizedName("friendInventory.main").build());
        inventory.setItem(50, new ItemBuilder(Material.HOPPER).setDisplayname("§aSort").setLore("§6➤ All Friends", "§7Online Friends", "§7Offline Friends", "§7Bookmarked").setLocalizedName("friendInventory.sort1").build());
        if (plugin.yaml.getString("Friends." + player.getUniqueId()) != null) {
            for (String friends : plugin.yaml.getConfigurationSection("Friends." + player.getUniqueId() + ".").getKeys(false)) {
                if (sort.equalsIgnoreCase("All")) {
                    ItemStack playerHead2 = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMeta2 = (SkullMeta) playerHead2.getItemMeta();
                    Objects.requireNonNull(skullMeta2).setOwner(friends);
                    skullMeta2.setDisplayName("§5" + friends);
                    skullMeta2.setLocalizedName("friend." + friends);
                    Player online = Bukkit.getPlayer(friends);
                    if (online != null) {
                        skullMeta2.setLore(Arrays.asList(plugin.yaml.getBoolean("Friends." + player.getUniqueId() + "." + friends + ".Bookmarked") ? "§a§lBookmarked" : null, "§aOnline", "§7Friends since:", "§6" + plugin.yaml.getString("Friends." + player.getUniqueId() + "." + friends + ".Date") + " §f§l- §6" + plugin.yaml.getString("Friends." + player.getUniqueId() + "." + friends + ".Time"), "", "§eClick to manage friendship."));
                    } else
                        skullMeta2.setLore(Arrays.asList(plugin.yaml.getBoolean("Friends." + player.getUniqueId() + "." + friends + ".Bookmarked") ? "§a§lBookmarked" : null, "§cOffline", "§7Friends since:", "§6" + plugin.yaml.getString("Friends." + player.getUniqueId() + "." + friends + ".Date") + " §f§l- §6" + plugin.yaml.getString("Friends." + player.getUniqueId() + "." + friends + ".Time"), "", "§eClick to manage friendship."));
                    playerHead2.setItemMeta(skullMeta2);
                    inventory.addItem(playerHead2);
                    inventory.setItem(50, new ItemBuilder(Material.HOPPER).setDisplayname("§aSort").setLore("§6➤ All Friends", "§7Online Friends", "§7Offline Friends", "§7Bookmarked").setLocalizedName("friendInventory.sort1").build());
                } else if (sort.equalsIgnoreCase("Online")) {
                    Player friendOnline = Bukkit.getPlayer(friends);
                    if (friendOnline != null) {
                        ItemStack playerHead2 = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta skullMeta2 = (SkullMeta) playerHead2.getItemMeta();
                        skullMeta2.setOwner(friends);
                        skullMeta2.setDisplayName("§5" + friends);
                        skullMeta2.setLocalizedName("friend." + friends);
                        skullMeta2.setLore(Arrays.asList(plugin.yaml.getBoolean("Friends." + player.getUniqueId() + "." + friends + ".Bookmarked") ? "§a§lBookmarked" : null, "§aOnline", "§7Friends since:", "§6" + plugin.yaml.getString("Friends." + player.getUniqueId() + "." + friends + ".Date") + " §f§l- §6" + plugin.yaml.getString("Friends." + player.getUniqueId() + "." + friends + ".Time"), "", "§eClick to manage friendship."));
                        playerHead2.setItemMeta(skullMeta2);
                        inventory.addItem(playerHead2);
                    }
                    inventory.setItem(50, new ItemBuilder(Material.HOPPER).setDisplayname("§aSort").setLore("§7All Friends", "§6➤ Online Friends", "§7Offline Friends", "§7Bookmarked").setLocalizedName("friendInventory.sort2").build());
                } else if (sort.equalsIgnoreCase("Offline")) {
                    Player friendOnline = Bukkit.getPlayer(friends);
                    if (friendOnline == null) {
                        ItemStack playerHead2 = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta skullMeta2 = (SkullMeta) playerHead2.getItemMeta();
                        skullMeta2.setOwner(friends);
                        skullMeta2.setDisplayName("§5" + friends);
                        skullMeta2.setLocalizedName("friend." + friends);
                        skullMeta2.setLore(Arrays.asList(plugin.yaml.getBoolean("Friends." + player.getUniqueId() + "." + friends + ".Bookmarked") ? "§a§lBookmarked" : null, "§cOffline", "§7Friends since:", "§6" + plugin.yaml.getString("Friends." + player.getUniqueId() + "." + friends + ".Date") + " §f§l- §6" + plugin.yaml.getString("Friends." + player.getUniqueId() + "." + friends + ".Time"), "", "§eClick to manage friendship."));
                        playerHead2.setItemMeta(skullMeta2);
                        inventory.addItem(playerHead2);
                    }
                    inventory.setItem(50, new ItemBuilder(Material.HOPPER).setDisplayname("§aSort").setLore("§7All Friends", "§7Online Friends", "§6➤ Offline Friends", "§7Bookmarked").setLocalizedName("friendInventory.sort3").build());
                } else if (sort.equalsIgnoreCase("Bookmarked")) {
                    if (plugin.yaml.getBoolean("Friends." + player.getUniqueId() + "." + friends + ".Bookmarked")) {
                        ItemStack playerHead2 = new ItemStack(Material.PLAYER_HEAD);
                        SkullMeta skullMeta2 = (SkullMeta) playerHead2.getItemMeta();
                        skullMeta2.setOwner(friends);
                        skullMeta2.setDisplayName("§5" + friends);
                        skullMeta2.setLocalizedName("friend." + friends);
                        skullMeta2.setLore(Arrays.asList(plugin.yaml.getBoolean("Friends." + player.getUniqueId() + "." + friends + ".Bookmarked") ? "§a§lBookmarked" : null, "§cOffline", "§7Friends since:", "§6" + plugin.yaml.getString("Friends." + player.getUniqueId() + "." + friends + ".Date") + " §f§l- §6" + plugin.yaml.getString("Friends." + player.getUniqueId() + "." + friends + ".Time"), "", "§eClick to manage friendship."));
                        playerHead2.setItemMeta(skullMeta2);
                        inventory.addItem(playerHead2);
                    }
                    inventory.setItem(50, new ItemBuilder(Material.HOPPER).setDisplayname("§aSort").setLore("§7All Friends", "§7Online Friends", "§7Offline Friends", "§6➤ Bookmarked").setLocalizedName("friendInventory.sort4").build());
                }
            }
        }
        player.openInventory(inventory);
    }

    public void createFriendSettingsInventory(Player player, String friend) {
        Inventory friendSettingsInventory = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.FriendSettingsInventoryTitle")).replaceAll("&", "§"));
        int[] orange = new int[]{1, 2, 3, 5, 6, 7, 9, 17, 18, 26, 27, 35, 36, 44, 46, 47, 48, 50, 51, 52};
        int[] red = new int[]{0, 8, 45, 53};
        int[] white = new int[]{10, 11, 15, 16, 19, 25, 28, 34, 37, 38, 42, 43};
        int[] blue = new int[]{12, 13, 14, 20, 21, 22, 23, 24, 29, 30, 31, 32, 33, 39, 40, 41};
        for (int i = 0; i < orange.length; i++) {friendSettingsInventory.setItem(orange[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < red.length; i++) {friendSettingsInventory.setItem(red[i], new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < white.length; i++) {friendSettingsInventory.setItem(white[i], new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < blue.length; i++) {friendSettingsInventory.setItem(blue[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        friendSettingsInventory.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setOwner(friend).setDisplayname("§6" + friend).setLore("§7Manage your friend " + friend).build());
        friendSettingsInventory.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eBack").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("Settings.Friends.Back").build());
        friendSettingsInventory.setItem(49, new ItemBuilder(Material.NETHER_STAR).setDisplayname("§9Main Menu").setLocalizedName("Settings.Friends.Main").build());
        friendSettingsInventory.setItem(12, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§dSocials").setSkullOwner(Main.getTexture("ZmNjZDRkMWIxNGVhMTQyZDgxYWMwNTQyYmZiZGE3ZTdiZDU3ZWI0YzA0YjhkZmIyNzkwNDViYmY5N2NhNjBhMSJ9fX0=")).setLore("§7Look at all social media profiles of "+friend,"§eClick to see.").setLocalizedName("Settings.Friends.Socials").build());
        friendSettingsInventory.setItem(14, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§9Clan").setSkullOwner(Main.getTexture("NmE1MzYxYjUyZGFmNGYxYzVjNTQ4MGEzOWZhYWExMDg5NzU5NWZhNTc2M2Y3NTdiZGRhMzk1NjU4OGZlYzY3OCJ9fX0=")).setLocalizedName("Settings.Friends.Clan").build());
        if (plugin.yaml.getBoolean("Friends." + player.getUniqueId() + "." + friend + ".Bookmarked")) {
            friendSettingsInventory.setItem(39, new ItemBuilder(Material.BOOK).setDisplayname("§2Bookmark").setLore("§a§lThis Player is Bookmarked", "§7Click to unbookmark this player.").setLocalizedName("Settings.Friends.Bookmark").build());
        } else
            friendSettingsInventory.setItem(39, new ItemBuilder(Material.BOOK).setDisplayname("§2Bookmark").setLore("§c§lThis Player isn't Bookmarked", "§7Click to bookmark this player.").setLocalizedName("Settings.Friends.Bookmark").build());
        friendSettingsInventory.setItem(41, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§4Remove this Friend").setSkullOwner(Main.getTexture("YzY1ZjNiYWUwZDIwM2JhMTZmZTFkYzNkMTMwN2E4NmE2MzhiZTkyNDQ3MWYyM2U4MmFiZDlkNzhmOGEzZmNhIn19fQ==")).setLore("§cYou cannot undo this action.","§eClick to remove.").setLocalizedName("Settings.Friends.Remove").build());
        player.openInventory(friendSettingsInventory);
    }

    public void createDeleteFriendInventory(Player player, String friend, int count, boolean isFinsihed) {
        Inventory confirmInventory = Bukkit.createInventory(player, 27, Objects.requireNonNull(plugin.getConfig().getString("Inventory.FriendRemoveInventoryTitle")).replaceAll("&", "§"));
        confirmInventory.clear();
        if (isFinsihed)
            confirmInventory.setItem(11, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("YzczZmIyYTI4ZjNiNjY1NDg2NDEyZGI3YjY2Y2RiN2ZjNWM3ZDMzZDc0ZTg1MGI5NDcyMmRkM2QxNGFhYSJ9fX0=")).setDisplayname("§4Confirm").setLore("§4You can not undo this action!", "§eClick to Confirm").setLocalizedName("Friend.remove").build());
        else
            confirmInventory.setItem(11, new ItemBuilder(Material.BARRIER).setDisplayname("§4Confirm").setLore("§4You can not undo this action!", "§7Please wait " + count + " seconds to confirm.").build());
        confirmInventory.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setOwner(friend).setDisplayname("§6" + friend).setLore("§7Are you sure to remove this friend.").build());
        confirmInventory.setItem(15, new ItemBuilder(Material.RED_CONCRETE).setDisplayname("§4Cancel").setLocalizedName("Friend.cancel").build());
        player.openInventory(confirmInventory);
    }

    /*
      Player Settings Inventory
     */

    public void createPlayerSettingsInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.SettingsInventoryTitle")).replaceAll("&", "§"));
        inventory.clear();
        int[] purpleGlass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26,27,35,36,44,45,46,47,48,50,51,52,53};
        for (int i = 0; i < purpleGlass.length; i++) {inventory.setItem(purpleGlass[i], new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        plugin.getPlayerDataManager().setYaml(player);
        inventory.setItem(10, new ItemBuilder(Material.PAPER).setDisplayname("§bChat Messages").setLore("§7Enable all chat messages.","§7This does not include clan and msg messages.","§cThis action will lock the chat for you.","",plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Chat_Messages") ? "§a§l✔ Enabled" : "§c§l❌ Disabled","§eClick to toggle the chat.").setLocalizedName("playerSettings.chat_messages").withGlow(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Chat_Messages")).build());
        String msg_Messages = plugin.getPlayerDataManager().playerDataYaml.getString("Settings.Msg_Messages");
        switch (Objects.requireNonNull(msg_Messages)){
            case "FRIENDS":
                inventory.setItem(11,new ItemBuilder(Material.ORANGE_DYE).setDisplayname("§6Msg Messages").setLore("§7Switch between friends, everyone or none","","§7Everyone","§5➤ Friends","§7NONE","","§eClick to switch").setLocalizedName("playerSettings.msg_messages").build());
                break;
            case "NONE":
                inventory.setItem(11,new ItemBuilder(Material.RED_DYE).setDisplayname("§6Msg Messages").setLore("§7Switch between friends, everyone or none","","§7Everyone","§7Friends","§4➤ NONE","","§eClick to switch").setLocalizedName("playerSettings.msg_messages").build());
                break;
            case "EVERYONE":
                inventory.setItem(11,new ItemBuilder(Material.LIME_DYE).setDisplayname("§6Msg Messages").setLore("§7Switch between friends, everyone or none","","§2➤ Everyone","§7Friends","§7NONE","","§eClick to switch").setLocalizedName("playerSettings.msg_messages").build());
                break;
            default:
                inventory.setItem(11,new ItemBuilder(Material.LIME_DYE).setDisplayname("§6Msg Messages").setLore("§7Switch between friends, everyone or none","","§2➤ Everyone","§7Friends","§7NONE","","§eClick to switch").setLocalizedName("playerSettings.msg_messages").build());
                break;
        }
        inventory.setItem(12, new ItemBuilder(Material.WRITABLE_BOOK).setDisplayname("§2Friend Requests").setLore("§7Enable friend request form every user.","",plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.FriendRequests") ? "§a§l✔ Enabled" : "§c§l❌ Disabled","§eClick to toggle friend requests.").setLocalizedName("playerSettings.friend_requests").withGlow(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.FriendRequests")).build());
        inventory.setItem(13, new ItemBuilder(Material.BOOK).setDisplayname("§bClan Requests").setLore("§7Enable clan request form every user.","",plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.ClanRequests") ? "§a§l✔ Enabled" : "§c§l❌ Disabled","§eClick to toggle clan requests.").setLocalizedName("playerSettings.clan_requests").withGlow(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.ClanRequests")).build());
        inventory.setItem(14, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("OWEyZDg5MWM2YWU5ZjZiYWEwNDBkNzM2YWI4NGQ0ODM0NGJiNmI3MGQ3ZjFhMjgwZGQxMmNiYWM0ZDc3NyJ9fX0=")).setDisplayname("§6Join Messages").setLore("§7Disable join messages form you and other users.","",plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Join_Messages") ? "§a§l✔ Enabled" : "§c§l❌ Disabled","§eClick to toggle join messages.").setLocalizedName("playerSettings.join_messages").withGlow(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Join_Messages")).build());
        inventory.setItem(15, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("OTM1ZTRlMjZlYWZjMTFiNTJjMTE2NjhlMWQ2NjM0ZTdkMWQwZDIxYzQxMWNiMDg1ZjkzOTQyNjhlYjRjZGZiYSJ9fX0=")).setDisplayname("§6Leave Messages").setLore("§7Disable leave messages form you and other users.","",plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Leave_Messages") ? "§a§l✔ Enabled" : "§c§l❌ Disabled","§eClick to toggle leave messages.").setLocalizedName("playerSettings.leave_messages").withGlow(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Leave_Messages")).build());
        inventory.setItem(41, new ItemBuilder(Material.EXPERIENCE_BOTTLE).setDisplayname("§2Level = Year").setLore("§7Set your level to the year.","",plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Year") ? "§a§l✔ Enabled" : "§c§l❌ Disabled","§eClick to toggle level.").setLocalizedName("playerSettings.year").withGlow(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Year")).build());
        inventory.setItem(42, new ItemBuilder(Material.FEATHER).setDisplayname("§3Fly").setLore("§7Enable or disable fly for you.","§7Needs a special permission.","",plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Fly") ? "§a§l✔ Enabled" : "§c§l❌ Disabled","§eClick to toggle fly.").setLocalizedName("playerSettings.fly").withGlow(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Fly")).build());
        inventory.setItem(43, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("MjRhZjhkNTc1ZjBhNTI0ZWY2NmEyMTU3M2U4YTRhOGFjN2Q2NDBiMDBkOThlMmU5Mzc0ZTYwNDcwZTdjODZmMCJ9fX0=")).setDisplayname("§6Coins API").setLore("§7Disable that other users can see","§7how much money do you have","",plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.CoinsAPI") ? "§a§l✔ Enabled" : "§c§l❌ Disabled","§eClick to toggle the coins api.").setLocalizedName("playerSettings.coins_api").withGlow(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.CoinsAPI")).build());
        inventory.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eBack").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("playerSettings.back").build());
        inventory.setItem(49, new ItemBuilder(Material.NETHER_STAR).setDisplayname("§9Main Menu").setLocalizedName("playerSettings.main").build());
        player.openInventory(inventory);
    }

    /*
        Social Inventory
     */

    public void createSocialMenu(Player player) {
        Inventory socialInventory = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.SocialInventoryTitle").replaceAll("&", "§")));
        int[] Blau1 = new int[]{0, 9, 14, 15, 16, 18, 27, 37, 38, 39, 40, 44, 53};
        int[] Blau2 = new int[]{1, 2, 3, 4, 5, 6, 7, 19, 25, 28, 34, 46, 47, 48, 50, 51, 52};
        int[] Blau3 = new int[]{8, 10, 11, 12, 13, 17, 26, 35, 36, 41, 42, 43};
        for (int i = 0; i < Blau1.length; i++) {socialInventory.setItem(Blau1[i], new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < Blau2.length; i++) {socialInventory.setItem(Blau2[i], new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < Blau3.length; i++) {socialInventory.setItem(Blau3[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        socialInventory.setItem(22, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayname("§c").build());
        socialInventory.setItem(31, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        socialInventory.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eBack").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("Sozial.Back").build());
        socialInventory.setItem(49, new ItemBuilder(Material.NETHER_STAR).setDisplayname("§9Main Menu").setLocalizedName("Sozial.MainMenu").build());
        socialInventory.setItem(20, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§bDiscord").setSkullOwner(Main.getTexture("Nzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19")).setLocalizedName("social.discord").build());
        socialInventory.setItem(21, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§cYoutube").setSkullOwner(Main.getTexture("YjQzNTNmZDBmODYzMTQzNTM4NzY1ODYwNzViOWJkZjBjNDg0YWFiMDMzMWI4NzJkZjExYmQ1NjRmY2IwMjllZCJ9fX0=")).setLocalizedName("social.youtube").build());
        socialInventory.setItem(23, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§dInstagram").setSkullOwner(Main.getTexture("YWM4OGQ2MTYzZmFiZTdjNWU2MjQ1MGViMzdhMDc0ZTJlMmM4ODYxMWM5OTg1MzZkYmQ4NDI5ZmFhMDgxOTQ1MyJ9fX0=")).setLocalizedName("social.instagram").build());
        socialInventory.setItem(24, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§9Facebook").setSkullOwner(Main.getTexture("ZGViNDYxMjY5MDQ0NjNmMDdlY2ZjOTcyYWFhMzczNzNhMjIzNTliNWJhMjcxODIxYjY4OWNkNTM2N2Y3NTc2MiJ9fX0=")).setLocalizedName("social.facebook").build());
        socialInventory.setItem(29, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§5Twitch").setSkullOwner(Main.getTexture("YjI1ODA0ODliMmQ0NGU1ZDlhOWIzZjgzNmVmMjE5ZjAzMTI5OTJkNDBiMTRkOTlmNTZjNWFmMDVjNDBmNzE1In19fQ==")).setLocalizedName("social.twitch").build());
        socialInventory.setItem(30, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§4Tik§bTok").setSkullOwner(Main.getTexture("YmNmMjEwNWJiNzM3NjM4ODMzMDMzZGQ4MjQ0MDcxZTc1ODcwZTJlMTFjMjYxN2U1NDJlODkyNGZiMmI5MDE4MCJ9fX0=")).setLocalizedName("social.tiktok").build());
        socialInventory.setItem(32, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§bTwitter").setSkullOwner(Main.getTexture("NmFkNDZhNDIyYWU1OTYwM2ZkODg5YzI1MzQ0ZmY2N2JjODQzYWY4ZWU1MTg5MzJjMmUyYWQwN2NkYmY5MzliMyJ9fX0=")).setLocalizedName("social.twitter").build());
        socialInventory.setItem(33, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§8Github").setSkullOwner(Main.getTexture("MjZlMjdkYTEyODE5YThiMDUzZGEwY2MyYjYyZGVjNGNkYTkxZGU2ZWVlYzIxY2NmM2JmZTZkZDhkNDQzNmE3In19fQ==")).setLocalizedName("social.github").build());
        player.openInventory(socialInventory);
    }

    /*
        Clan Inventory's
     */

    public void createClanChooseInventory(Player player) {
        Inventory ClanChoose = Bukkit.createInventory(player, 27, Objects.requireNonNull(plugin.getConfig().getString("Inventory.ClanInventory.ClanChooseInventoryTitle").replaceAll("&", "§")));
        int[] Orange = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 19, 20, 21, 22, 23, 24, 25, 26};
        int[] Black = new int[]{11, 13, 15};
        int[] red = new int[]{10, 16};
        for (int i = 0; i < Orange.length; i++) {ClanChoose.setItem(Orange[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < Black.length; i++) {ClanChoose.setItem(Black[i], new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < red.length; i++) {ClanChoose.setItem(red[i], new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        ClanChoose.setItem(18, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§7Back").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("ClanChoose.Back").build());
        ClanChoose.setItem(12, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§3Create Clan").setSkullOwner(Main.getTexture("OWE5YTdmMDdkYTliZGEyODBiMGY5MDliNDk1ZWJjOWZiMWFlZTM1NTJjYjE3ZTM5YmExYjRjOTZkMDJhMjBjYSJ9fX0=")).setLore("§eCreate your own Clan").setLocalizedName("ClanChoose.CreateClan").build());
        ClanChoose.setItem(14, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§bJoin Clan").setSkullOwner(Main.getTexture("NDNlZDM2ODgyMWI0NjZkMzliZGY1N2U3OGZjMWRkZjc1ZWM5NjZjMTY5NTVlMDJlNTk0OGJlN2FkZmQxZmU1NSJ9fX0=")).setLore("§eJoin an already created Clan").setLocalizedName("ClanChoose.JoinClan").build());
        player.openInventory(ClanChoose);
    }

    public void createClanMemberInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, "");
        int[] blue = new int[]{1, 2, 3, 5, 6, 7, 9, 17, 18, 26, 27, 35, 36, 44, 46, 47, 48, 50, 51, 52};
        for (int i = 0; i < blue.length; i++) {
            inventory.setItem(blue[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
    }

    public void createClanGuiLeader(Player player) {
        Inventory Clan = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.ClanInventory.ClanLeaderInventoryTitle")).replaceAll("&", "§"));
        int[] whiteglass = new int[]{21, 22, 23, 29, 30, 32, 33, 37, 39, 40, 41, 43};
        int[] Blueglass = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44};
        int[] TurkischGlass = new int[]{10, 11, 12, 14, 15, 16, 19, 25, 28, 34};
        int[] HelleresTürkisGlass = new int[]{46, 47, 48, 50, 51, 52};
        for (int i = 0; i < whiteglass.length; i++) {Clan.setItem(whiteglass[i], new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < Blueglass.length; i++) {Clan.setItem(Blueglass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < TurkischGlass.length; i++) {Clan.setItem(TurkischGlass[i], new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < HelleresTürkisGlass.length; i++) {Clan.setItem(HelleresTürkisGlass[i], new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (String clanName : plugin.yaml.getConfigurationSection("Clans." + player.getUniqueId() + ".").getKeys(false)) {
            String clanOwnerName = plugin.yaml.getString("Clans." + player.getUniqueId() + "." + clanName + ".Owner");
            boolean privateBoolean = plugin.yaml.getBoolean("Clans." + player.getUniqueId() + "." + clanName + ".Private");
            String createdDate = plugin.yaml.getString("Clans." + player.getUniqueId() + "." + clanName + ".Date");
            String createdTime = plugin.yaml.getString("Clans." + player.getUniqueId() + "." + clanName + ".Time");
            Clan.setItem(4, new ItemBuilder(Material.BOOK).setDisplayname("§cIcon ").setLocalizedName("ClanMenu.Leader.ClanIcon").build());
            Clan.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§2§lClan Owner").setOwner(player.getName()).setLocalizedName("ClanMenu.Leader.ClanIcon.Leader").build());
            Clan.setItem(20, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eMembers").setSkullOwner(Main.getTexture("YjFlZDIwNTY3MDY4Y2IwY2MwNzJkNzMyZjUzMjJkNzM0YmY4NDllNjg4YzdmZTAxMWEzMDJlMWI5NDczZDIwYyJ9fX0=")).setLocalizedName("ClanMenu.Leader.Members").build());
            Clan.setItem(24, new ItemBuilder(Material.NETHERITE_SHOVEL).setDisplayname("§1Clan Rank´s").setLocalizedName("ClanMenu.Leader.Rank").build());//todo Rank system
            Clan.setItem(31, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§aInfo").setLore("§7ClanName: §6" + clanName, "§7ClanOwner: §6" + clanOwnerName, privateBoolean ? "§7Clan is §4Private" : "§7Clan is §2Public", "§7Created: §6" + createdDate + "§f§l - §6" + createdTime).setSkullOwner(Main.getTexture("M2Y4MzEzNTYxN2NmNTllNjlmNDVjZDhkYzYzYmIxNzQ5NDU2NWI3MGJiYmI4MmE3MjViODkyYWVlZGY4MDA1In19fQ==")).setLocalizedName("ClanMenu.Leader.ClanItems").build());//wichtig dat au
            Clan.setItem(38, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§dAdvancements").setSkullOwner(Main.getTexture("ZTM0YTU5MmE3OTM5N2E4ZGYzOTk3YzQzMDkxNjk0ZmMyZmI3NmM4ODNhNzZjY2U4OWYwMjI3ZTVjOWYxZGZlIn19fQ==")).setLocalizedName("ClanMenu.Leader.Achivements").build());//wichtig der quark muss rein
            Clan.setItem(42, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("ZWMyZmYyNDRkZmM5ZGQzYTJjZWY2MzExMmU3NTAyZGM2MzY3YjBkMDIxMzI5NTAzNDdiMmI0NzlhNzIzNjZkZCJ9fX0=")).setDisplayname("ClanSettings").setLocalizedName("ClanMenu.Leader.CLanSettings").build());//wth CLan Items Kommen in Gadget menu
            Clan.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§7Back").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("ClanMenu.Leader.Back").build());
            Clan.setItem(49, new ItemBuilder(Material.NETHER_STAR).setDisplayname("§9Main Menu").setLocalizedName("ClanMenu.Leader.MainMenu").build());
            Clan.setItem(53, new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayname("§4Delete Clan").setLocalizedName("ClanMenu.Leader.Clan.del").build());
        }
        player.openInventory(Clan);
    }
    public void CreateClanGuiMember1(Player player) {
        Inventory Clan = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.ClanInventory.ClanMember1InventoryTitle")).replaceAll("&", "§"));
        int[] whiteglass = new int[]{21, 22, 23, 29, 30, 32, 33, 37, 39, 40, 41, 43};
        int[] Blueglass = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44};
        int[] TurkischGlass = new int[]{10, 11, 12, 14, 15, 16, 19, 25, 28, 34};
        int[] HelleresTürkisGlass = new int[]{46, 47, 48, 50, 51, 52};
        for (int i = 0; i < whiteglass.length; i++) {Clan.setItem(whiteglass[i], new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < Blueglass.length; i++) {Clan.setItem(Blueglass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < TurkischGlass.length; i++) {Clan.setItem(TurkischGlass[i], new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < HelleresTürkisGlass.length; i++) {Clan.setItem(HelleresTürkisGlass[i], new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        Clan.setItem(4, new ItemBuilder(Material.BOOK).setDisplayname("§cIcon ").setLocalizedName("ClanMenu.Leader.ClanIcon").build());
        Clan.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§2§lClan Owner").setOwner(player.getName()).setLocalizedName("ClanMenu.Leader.ClanIcon.Leader").build());
        Clan.setItem(20, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eMembers").setSkullOwner(Main.getTexture("YjFlZDIwNTY3MDY4Y2IwY2MwNzJkNzMyZjUzMjJkNzM0YmY4NDllNjg4YzdmZTAxMWEzMDJlMWI5NDczZDIwYyJ9fX0=")).setLocalizedName("ClanMenu.Leader.Members").build());
        player.openInventory(Clan);
    }

    /*
        Language Inventory
     */


    public void createLanguageInventory(Player player) {
        Inventory language = Bukkit.createInventory(player, 27, Objects.requireNonNull(plugin.getConfig().getString("Inventory.LanguageInventoryTitle")).replaceAll("&", "§"));
        int[] Orange = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 17, 19, 20, 21, 22, 23, 24, 25, 26};
        for (int i = 0; i < Orange.length; i++) {language.setItem(Orange[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        language.setItem(11, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§cEnglish").setSkullOwner(Main.getTexture("ODgzMWM3M2Y1NDY4ZTg4OGMzMDE5ZTI4NDdlNDQyZGZhYTg4ODk4ZDUwY2NmMDFmZDJmOTE0YWY1NDRkNTM2OCJ9fX0=")).setLore("§7Translate all messages","§7of the plugin to english","","§eClick to change the language.").setLocalizedName("Settings.Language.English").withGlow(Util.getPlayerLanguage(player).equalsIgnoreCase("en")).build());
        language.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("YzJkNzMwYjZkZGExNmI1ODQ3ODNiNjNkMDgyYTgwMDQ5YjVmYTcwMjI4YWJhNGFlODg0YzJjMWZjMGMzYThiYyJ9fX0=")).setDisplayname("§6Español").setLore("§7Tradujo todos los mensajes para ti","§7del plugin en español.","","§eHaga clic para cambiar el idioma.").setLocalizedName("Settings.Language.Spain").withGlow(Util.getPlayerLanguage(player).equalsIgnoreCase("sp")).build());
        language.setItem(15, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§bDeutsch").setSkullOwner(Main.getTexture("NWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==")).setLore("§7Übersetzte dir alle Nachrichten","§7des Plugins auf Deutsch","","§eClicke um die Sprache zu wechseln.").setLocalizedName("Settings.Language.Deutsch").withGlow(Util.getPlayerLanguage(player).equalsIgnoreCase("de")).build());
        language.setItem(18, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eBack").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("Settings.Language.Back").build());
        player.openInventory(language);
    }


}
