package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.permissions.PermissionAttachment;

import java.util.*;

public class ProfileMenuManager implements Listener {

    private Main plugin;

    public ProfileMenuManager(Main plugin) {
        this.plugin = plugin;
    }

    public void createProfileInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.ProfileInventoryTitle")).replaceAll("&", "§"));
        int[] blueglass = new int[]{1, 2, 3, 4, 5, 6, 7, 9, 11, 12, 13, 14, 15, 17, 18, 21, 23, 26, 27, 20, 31, 32, 35, 36, 39, 40, 41, 44, 46, 47, 48, 49, 50, 51, 52};
        int[] anderesglass = new int[]{0, 8, 10, 16, 19, 20, 24, 25, 28, 34, 37, 38, 42, 43, 45, 53};
        for (int i = 0; i < blueglass.length; i++) {
            inventory.setItem(blueglass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        for (int i = 0; i < anderesglass.length; i++) {
            inventory.setItem(anderesglass[i], new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        ItemStack playerhead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerhead.getItemMeta();
        skullMeta.setOwner(player.getName());
        skullMeta.setDisplayName("Profile");
        skullMeta.setLocalizedName("MainInventory.Profil");
        playerhead.setItemMeta(skullMeta);
        inventory.setItem(22, new ItemStack(playerhead));
        inventory.setItem(29, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§9Clan").setSkullOwner(Main.getTexture("NmE1MzYxYjUyZGFmNGYxYzVjNTQ4MGEzOWZhYWExMDg5NzU5NWZhNTc2M2Y3NTdiZGRhMzk1NjU4OGZlYzY3OCJ9fX0=")).setLocalizedName("MainInventory.Clan").build());
        inventory.setItem(33, new ItemBuilder(Material.COMPARATOR).setDisplayname("§Settings").setLocalizedName("MainInventory.Settings").build());
        inventory.setItem(40, new ItemBuilder(Material.DIAMOND).setDisplayname("Social").setLocalizedName("MainInventory.Social").build());
        player.openInventory(inventory);
    }

    public void createSozialInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.SocialInventoryTitle")).replaceAll("&", "§"));
        int[] blueglass = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 15, 16, 17, 18, 26};
        int[] whiteglass = new int[]{12, 13, 14, 39, 40, 41};
        int[] anderesblauglass = new int[]{27, 35, 36, 37, 38, 42, 43, 44, 46, 47, 48, 50, 51, 52, 53};
        for (int i = 0; i < blueglass.length; i++) {
            inventory.setItem(blueglass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        for (int i = 0; i < whiteglass.length; i++) {
            inventory.setItem(whiteglass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        for (int i = 0; i < anderesblauglass.length; i++) {
            inventory.setItem(anderesblauglass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        ItemStack playerhead = new ItemStack(Material.LEGACY_SKULL_ITEM);
        SkullMeta skullMeta = (SkullMeta) playerhead.getItemMeta();


        player.openInventory(inventory);
    }

    public void createFriendInventory(Player player) {
        Inventory inventory = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.FriendInventoryTitle")).replaceAll("&", "§"));
        inventory.clear();
        String sort = plugin.yaml.getString("Settings." + player.getUniqueId() + ".FriendSort");
        if (sort == null) {
            plugin.yaml.set("Settings." + player.getUniqueId() + ".FriendSort", "All");
            plugin.saveData();
            player.sendMessage("§4§lThere was an error. Please try again.");
            return;
        }
        int[] ornageglass = new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 46, 47, 51, 52, 53};
        for (int i = 0; i < ornageglass.length; i++) {
            inventory.setItem(ornageglass[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwner(player.getName());
        skullMeta.setDisplayName("§aYour Friends");
        playerHead.setItemMeta(skullMeta);
        inventory.setItem(4, playerHead);
        inventory.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§7Back").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("friendInventory.back").build());
        inventory.setItem(48, new ItemBuilder(Material.OAK_SIGN).setDisplayname("§6Search").setLocalizedName("friendInventory.search").build());
        inventory.setItem(49, new ItemBuilder(Material.NETHER_STAR).setDisplayname("§9Main Menu").setLocalizedName("friendInventory.main").build());
        inventory.setItem(50, new ItemBuilder(Material.HOPPER).setDisplayname("§aSort").setLore("§6➤ All Friends", "§7Online Friends", "§7Offline Friends", "§7Bookmarked").setLocalizedName("friendInventory.sort1").build());
        if (plugin.yaml.getString("Friends." + player.getUniqueId()) != null) {
            for (String friends : plugin.yaml.getConfigurationSection("Friends." + player.getUniqueId() + ".").getKeys(false)) {
                if (sort.equalsIgnoreCase("All")) {
                    ItemStack playerHead2 = new ItemStack(Material.PLAYER_HEAD);
                    SkullMeta skullMeta2 = (SkullMeta) playerHead2.getItemMeta();
                    skullMeta2.setOwner(friends);
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

    public void createSozailMenu(Player player) {
        Inventory Sozial = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.SocialInventoryTitle").replaceAll("&", "§")));
        int[] Blau1 = new int[]{0, 9, 14, 15, 16, 18, 27, 37, 38, 39, 40, 44, 53};
        int[] Blau2 = new int[]{1, 2, 3, 4, 5, 6, 7, 19, 25, 28, 34, 46, 47, 48, 50, 51, 52};
        int[] Blau3 = new int[]{8, 10, 11, 12, 13, 17, 26, 35, 36, 41, 42, 43};
        for (int i = 0; i < Blau1.length; i++) {
            Sozial.setItem(Blau1[i], new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        for (int i = 0; i < Blau2.length; i++) {
            Sozial.setItem(Blau2[i], new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        for (int i = 0; i < Blau3.length; i++) {
            Sozial.setItem(Blau3[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        Sozial.setItem(22, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayname("§c").build());
        Sozial.setItem(31, new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        Sozial.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§7Back").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("Sozial.Back").build());
        Sozial.setItem(49, new ItemBuilder(Material.NETHER_STAR).setDisplayname("§eMainMenu").setLocalizedName("Sozial.MainMenu").build());
        Sozial.setItem(20, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§bDiscord").setSkullOwner(Main.getTexture("Nzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19")).setLocalizedName("Sozial.Dc").build());
        Sozial.setItem(21, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§cYoutube").setSkullOwner(Main.getTexture("YjQzNTNmZDBmODYzMTQzNTM4NzY1ODYwNzViOWJkZjBjNDg0YWFiMDMzMWI4NzJkZjExYmQ1NjRmY2IwMjllZCJ9fX0=")).setLocalizedName("Sozial.Yt").build());
        Sozial.setItem(23, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§dInstagram").setSkullOwner(Main.getTexture("YWM4OGQ2MTYzZmFiZTdjNWU2MjQ1MGViMzdhMDc0ZTJlMmM4ODYxMWM5OTg1MzZkYmQ4NDI5ZmFhMDgxOTQ1MyJ9fX0=")).setLocalizedName("Sozial.Instagram").build());
        Sozial.setItem(24, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§9Facebook").setSkullOwner(Main.getTexture("ZGViNDYxMjY5MDQ0NjNmMDdlY2ZjOTcyYWFhMzczNzNhMjIzNTliNWJhMjcxODIxYjY4OWNkNTM2N2Y3NTc2MiJ9fX0=")).setLocalizedName("Sozial.Facebook").build());
        Sozial.setItem(29, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§5Twitch").setSkullOwner(Main.getTexture("YjI1ODA0ODliMmQ0NGU1ZDlhOWIzZjgzNmVmMjE5ZjAzMTI5OTJkNDBiMTRkOTlmNTZjNWFmMDVjNDBmNzE1In19fQ==")).setLocalizedName("Sozial.Twitch").build());
        Sozial.setItem(30, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§4Tik§bTok").setSkullOwner(Main.getTexture("YmNmMjEwNWJiNzM3NjM4ODMzMDMzZGQ4MjQ0MDcxZTc1ODcwZTJlMTFjMjYxN2U1NDJlODkyNGZiMmI5MDE4MCJ9fX0=")).setLocalizedName("Sozial.Tiktok").build());
        Sozial.setItem(32, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§bTwitter").setSkullOwner(Main.getTexture("NmFkNDZhNDIyYWU1OTYwM2ZkODg5YzI1MzQ0ZmY2N2JjODQzYWY4ZWU1MTg5MzJjMmUyYWQwN2NkYmY5MzliMyJ9fX0=")).setLocalizedName("Sozial.Twitter").build());
        Sozial.setItem(33, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§8Github").setSkullOwner(Main.getTexture("MjZlMjdkYTEyODE5YThiMDUzZGEwY2MyYjYyZGVjNGNkYTkxZGU2ZWVlYzIxY2NmM2JmZTZkZDhkNDQzNmE3In19fQ==")).setLocalizedName("Sozial.Github").build());
        player.openInventory(Sozial);
    }

    public void createClanChooseInventory(Player player) {
        Inventory ClanChoose = Bukkit.createInventory(player, 27, Objects.requireNonNull(plugin.getConfig().getString("Inventory.ClanInventory.ClanChooseInventoryTitle").replaceAll("&", "§")));
        int[] Orange = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 19, 20, 21, 22, 23, 24, 25, 26};
        int[] Black = new int[]{11, 13, 15};
        int[] red = new int[]{10, 16};
        for (int i = 0; i < Orange.length; i++) {
            ClanChoose.setItem(Orange[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        for (int i = 0; i < Black.length; i++) {
            ClanChoose.setItem(Black[i], new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        for (int i = 0; i < red.length; i++) {
            ClanChoose.setItem(red[i], new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        ClanChoose.setItem(18, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§7Back").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("ClanChoose.Back").build());
        //ClanChoose.setItem(11,new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§1Invite").setSkullOwner(Main.getTexture("Y2Q5MWY1MTI2NmVkZGM2MjA3ZjEyYWU4ZDdhNDljNWRiMDQxNWFkYTA0ZGFiOTJiYjc2ODZhZmRiMTdmNGQ0ZSJ9fX0=")).setLore("§eList all Invites you have gotten").setLocalizedName("ClanChoose.InvitesList").build());
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

    public void createLanguageInventory(Player player) {
        Inventory Language = Bukkit.createInventory(player, 27, Objects.requireNonNull(plugin.getConfig().getString("Inventory.LanguageInventoryTitle")).replaceAll("&", "§"));
        int[] Orange = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 14, 16, 17, 19, 20, 21, 22, 23, 24, 25, 26};
        for (int i = 0; i < Orange.length; i++) {
            Language.setItem(Orange[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        Language.setItem(11, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§cEnglish").setSkullOwner(Main.getTexture("ODgzMWM3M2Y1NDY4ZTg4OGMzMDE5ZTI4NDdlNDQyZGZhYTg4ODk4ZDUwY2NmMDFmZDJmOTE0YWY1NDRkNTM2OCJ9fX0=")).setLocalizedName("Settings.Language.English").build());
        Language.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("YzJkNzMwYjZkZGExNmI1ODQ3ODNiNjNkMDgyYTgwMDQ5YjVmYTcwMjI4YWJhNGFlODg0YzJjMWZjMGMzYThiYyJ9fX0=")).setDisplayname("§6Spanish").setLocalizedName("Settings.Language.Spanisch").build());
        Language.setItem(15, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§bDeutsch").setSkullOwner(Main.getTexture("NWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==")).setLocalizedName("Settings.Language.Deutsch").build());
        Language.setItem(18, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§7Back").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("Settings.Language.Back").build());
        player.openInventory(Language);
    }

    public void createFriendSettingsInventory(Player player, String friend) {
        Inventory Friend = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.FriendSettingsInventoryTitle")).replaceAll("&", "§"));
        int[] orange = new int[]{1, 2, 3, 5, 6, 7, 9, 17, 18, 26, 27, 35, 36, 44, 46, 47, 48, 50, 51, 52};
        int[] red = new int[]{0, 8, 45, 53};
        int[] white = new int[]{10, 11, 15, 16, 19, 25, 28, 34, 37, 38, 42, 43};
        int[] blue = new int[]{12, 13, 14, 20, 21, 22, 23, 24, 29, 30, 31, 32, 33, 39, 40, 41};
        for (int i = 0; i < orange.length; i++) {
            Friend.setItem(orange[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        for (int i = 0; i < red.length; i++) {
            Friend.setItem(red[i], new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        for (int i = 0; i < white.length; i++) {
            Friend.setItem(white[i], new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        for (int i = 0; i < blue.length; i++) {
            Friend.setItem(blue[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());
        }
        Friend.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setOwner(friend).setDisplayname("§6" + friend).setLore("§7You manage " + friend).build());
        Friend.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§7Back").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("Settings.Friends.Back").build());
        Friend.setItem(49, new ItemBuilder(Material.NETHER_STAR).setDisplayname("§9Main Menu").setLocalizedName("Settings.Friends.Main").build());
        Friend.setItem(12, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§dInformation").setSkullOwner(Main.getTexture("M2Y4MzEzNTYxN2NmNTllNjlmNDVjZDhkYzYzYmIxNzQ5NDU2NWI3MGJiYmI4MmE3MjViODkyYWVlZGY4MDA1In19fQ==")).setLore("").build());
        Friend.setItem(14, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§9Clan").setSkullOwner(Main.getTexture("NmE1MzYxYjUyZGFmNGYxYzVjNTQ4MGEzOWZhYWExMDg5NzU5NWZhNTc2M2Y3NTdiZGRhMzk1NjU4OGZlYzY3OCJ9fX0=")).setLocalizedName("Settings.Friends.Clan").build());
        if (plugin.yaml.getBoolean("Friends." + player.getUniqueId() + "." + friend + ".Bookmarked")) {
            Friend.setItem(39, new ItemBuilder(Material.BOOK).setDisplayname("§2Bookmark").setLore("§a§lThis Player is Bookmarked", "§7Click to unbookmark this player.").setLocalizedName("Settings.Friends.Bookmark").build());
        } else
            Friend.setItem(39, new ItemBuilder(Material.BOOK).setDisplayname("§2Bookmark").setLore("§c§lThis Player isn't Bookmarked", "§7Click to bookmark this player.").setLocalizedName("Settings.Friends.Bookmark").build());
        Friend.setItem(41, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§4Remove this Friend").setSkullOwner(Main.getTexture("YzY1ZjNiYWUwZDIwM2JhMTZmZTFkYzNkMTMwN2E4NmE2MzhiZTkyNDQ3MWYyM2U4MmFiZDlkNzhmOGEzZmNhIn19fQ==")).setLocalizedName("Settings.Friends.Remove").build());
        player.openInventory(Friend);
    }

    public void createDeleteFriendInventory(Player player, String friend, int count, boolean isFinsihed) {
        Inventory confirmInventory = Bukkit.createInventory(player, 27, Objects.requireNonNull(plugin.getConfig().getString("Inventory.FriendRemoveInventoryTitle")).replaceAll("&", "§"));
        confirmInventory.clear();
        if (isFinsihed) {
            confirmInventory.setItem(11, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("YzczZmIyYTI4ZjNiNjY1NDg2NDEyZGI3YjY2Y2RiN2ZjNWM3ZDMzZDc0ZTg1MGI5NDcyMmRkM2QxNGFhYSJ9fX0=")).setDisplayname("§4Confirm").setLore("§4You can not undo this action!", "§eClick to Confirm").setLocalizedName("Friend.remove").build());
        } else
            confirmInventory.setItem(11, new ItemBuilder(Material.BARRIER).setDisplayname("§4Confirm").setLore("§4You can not undo this action!", "§7Please wait " + count + " seconds to confirm.").build());
        confirmInventory.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setOwner(friend).setDisplayname("§6" + friend).setLore("§7Are you sure to remove this friend.").build());
        confirmInventory.setItem(15, new ItemBuilder(Material.RED_CONCRETE).setDisplayname("§4Cancel").setLocalizedName("Friend.cancel").build());
        player.openInventory(confirmInventory);
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

    }
    public void createRankInventory(Player player){
        Inventory Rank = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.Rank.RankInventory")).replaceAll("&", "§"));
        int[] Schwarz = new int[]{0,8,10,16,37,43,53};
        int[] Weiß = new int[]{1,2,3,5,6,7,9,17,18,19,25,27,28,34,36,44,46,47,48,50,51,52};
        for (int i = 0; i < Weiß.length; i++) {Rank.setItem(Weiß[i], new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < Schwarz.length; i++) {Rank.setItem(Schwarz[i], new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        Rank.setItem(4, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§5Ranks").setSkullOwner(Main.getTexture("ZDZjYzZiODM3NjNhNjdmY2FkYTFlYTE4NGMyZDE3NTJhZDI0MDc0NmM2YmUyNThhNzM5ODNkOGI2NTdmNGJiNSJ9fX0=")).build());
        Rank.setItem(35, new ItemBuilder(Material.GREEN_CONCRETE).setDisplayname("§aCreate Rank").setLocalizedName("RankSettings.create").build());
        Rank.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eBack").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("RankSettings.back").build());
        plugin.getRankManager().listRanks(Rank);
        player.openInventory(Rank);
    }
    public void createRankSettingsInventory(Player player, String rank){
        Inventory rankSettings = Bukkit.createInventory(player,54,Objects.requireNonNull(plugin.getConfig().getString("Inventory.Rank.RankSettingsInventory.RankSettingsInventory")).replaceAll("&", "§"));
        int[]  whiteGlass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,46,47,48,49,50,51,52,53};
        for (int i = 0; i < whiteGlass.length; i++) {rankSettings.setItem(whiteGlass[i], new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").setLocalizedName(rank).build());}
        String rankName = plugin.getRankManager().getName(rank);
        String rankPrefix = plugin.getRankManager().getPrefix(rank);
        boolean op = plugin.getRankManager().hasOp(rank);
        setHopperForColor(rankSettings);
        rankSettings.setItem(10, new ItemBuilder(Material.NAME_TAG).setDisplayname("§6Rank Name").setLore("§7Set the rank name that's ","§7shown on the scoreboard.","","§7Current: §3"+rankName,"§eClick to change.").setLocalizedName("rankSettings.name").build());
        rankSettings.setItem(13, new ItemBuilder(Material.NAME_TAG).setDisplayname("§6Rank Prefix").setLore("§7Set the rank prefix that's ","§7shown on the chat.","","§7Current: §3"+rankPrefix,"§eClick to change.").setLocalizedName("rankSettings.prefix").build());
        rankSettings.setItem(37, new ItemBuilder(Material.COMMAND_BLOCK).setDisplayname("§6Rank Operator").setLore("§7Set if the rank has ","§7operator or not.","",op ? "§7Current: §2true" : "§7Current: §4false","§eClick to change.").setLocalizedName("rankSettings.op").build());
        rankSettings.setItem(40, new ItemBuilder(Material.OAK_SIGN).setDisplayname("§6Rank Permissions").setLore("§eClick to manage.").setLocalizedName("rankSettings.permissions").build());
        rankSettings.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eBack").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("rankSettings.back").build());
        player.openInventory(rankSettings);
    }
    public void createRankCreateInventory(Player player){
        Inventory createRank = Bukkit.createInventory(player,54,Objects.requireNonNull(plugin.getConfig().getString("Inventory.Rank.RankSettingsInventory.CreateRankInventory")).replaceAll("&", "§"));
        int[] redGlass = new int[]{0,8,53};
        int[]  whiteGlass = new int[]{1,2,3,4,5,6,7,9,12,14,17,18,21,23,26,27,30,32,39,31,35,36,41,44,46,47,48,50,51,52};
        for (int i = 0; i < redGlass.length; i++) {createRank.setItem(redGlass[i], new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < whiteGlass.length; i++) {createRank.setItem(whiteGlass[i], new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        createRank.setItem(45,new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.BACK_SKULL_TEXTURE).setDisplayname("§eBack").setLocalizedName("createRank.back").build());
        player.openInventory(createRank);
    }
    public void createRankPermissionInventory(Player player, String rank){
        Inventory rankPermissions = Bukkit.createInventory(player,54,Objects.requireNonNull(plugin.getConfig().getString("Inventory.Rank.RankSettingsInventory.RankPermissionInventory")).replaceAll("&", "§"));
        int[]  whiteGlass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,46,47,48,49,50,51,52,53};
        for (int i = 0; i < whiteGlass.length; i++) {rankPermissions.setItem(whiteGlass[i], new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayname("§c").setLocalizedName(rank).build());}
        FileConfiguration config = YamlConfiguration.loadConfiguration(plugin.getRankManager().getRankFile());
        if(config.contains("Ranks."+rank+".Permissions.")) {
            for (String permissions : config.getConfigurationSection("Ranks." + rank + ".Permissions.").getKeys(false)) {
                String permission = config.getString("Ranks." + rank + ".Permissions."+permissions);
                rankPermissions.addItem(new ItemBuilder(Material.OAK_SIGN).setDisplayname("§6§l"+permission).setLore("§8Right-Click to delete this permission").setLocalizedName(permissions).build());
            }
        }
        rankPermissions.setItem(53,new ItemBuilder(Material.LIME_CONCRETE).setDisplayname("§a§lAdd Permission").setLocalizedName("permissionRank.add").build());
        rankPermissions.setItem(45,new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.BACK_SKULL_TEXTURE).setDisplayname("§eBack").setLocalizedName("permissionRank.back").build());
        player.openInventory(rankPermissions);
    }
    private void setHopperForColor(Inventory inventory){
        String rank = inventory.getItem(0).getItemMeta().getLocalizedName();
        String rankColor = plugin.getRankManager().getColor(rank);
        switch (rankColor){
            case "§0":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§6§l➤ §0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§9Blue","§aGreen","§bAqua","§cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§1":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§6§l➤ §1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§9Blue","§aGreen","§bAqua","§cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§2":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§6§l➤ §2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§9Blue","§aGreen","§bAqua","§cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§3":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§6§l➤ §3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§9Blue","§aGreen","§bAqua","§cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§4":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§6§l➤ §4Dark Red","§5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§9Blue","§aGreen","§bAqua","§cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§5":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§6§l➤ §5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§9Blue","§aGreen","§bAqua","§cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§6":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6§l➤ §6Gold","§7Gray","§8Dark Gray","§9Blue","§aGreen","§bAqua","§cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§7":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§6§l➤ §7Gray","§8Dark Gray","§9Blue","§aGreen","§bAqua","§cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§8":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§7Gray","§6§l➤ §8Dark Gray","§9Blue","§aGreen","§bAqua","§cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§9":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§6§l➤ §9Blue","§aGreen","§bAqua","§cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§a":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§9Blue","§6§l➤ §aGreen","§bAqua","§cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§b":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§9Blue","§aGreen","§6§l➤ §bAqua","§cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§c":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§9Blue","§aGreen","§bAqua","§6§l➤ §cRed","§dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§d":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§9Blue","§aGreen","§bAqua","§cRed","§6§l➤ §dLight Purple","§eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§e":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§9Blue","§aGreen","§bAqua","§cRed","§dLight Purple","§6§l➤ §eYellow","§fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
            case "§f":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§6Rank Colors").setLore("§0Black","§1Dark Blue","§2Dark Green","§3Dark Aqua","§4Dark Red","§5Dark Purple","§6Gold","§7Gray","§8Dark Gray","§9Blue","§aGreen","§bAqua","§cRed","§dLight Purple","§eYellow","§6§l➤ §fWhite","","§eClick to change.").setLocalizedName("rankSettings.select").build());
                break;
        }
    }

}
