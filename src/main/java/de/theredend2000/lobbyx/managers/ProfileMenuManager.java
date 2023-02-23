package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.listeners.itemListeners.ProfileListeners;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Skull;

import java.util.*;

public class ProfileMenuManager implements Listener {

    private Main plugin;

    public ProfileMenuManager(Main plugin){
        this.plugin = plugin;
    }

    public void createProfileInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.ProfileInventoryTitle")).replaceAll("&","§"));
        int[] blueglass = new int[]{1,2,3,4,5,6,7,9,11,12,13,14,15,17,18,21,23,26,27,20,31,32,35,36,39,40,41,44,46,47,48,49,50,51,52};
        int [] anderesglass = new int[]{0,8,10,16,19,20,24,25,28,34,37,38,42,43,45,53};
        for (int i = 0; i<blueglass.length;i++){inventory.setItem(blueglass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i<anderesglass.length;i++){inventory.setItem(anderesglass[i], new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        ItemStack playerhead = new ItemStack(Material.PLAYER_HEAD);SkullMeta skullMeta = (SkullMeta) playerhead.getItemMeta();skullMeta.setOwner(player.getName());skullMeta.setDisplayName("Profile");skullMeta.setLocalizedName("MainInventory.Profil");playerhead.setItemMeta(skullMeta);
        inventory.setItem(22,new ItemStack(playerhead));
        inventory.setItem(29, new ItemBuilder(Material.NETHERITE_SWORD).setDisplayname("§Stats").setLocalizedName("MainInventory.Stats").build());
        inventory.setItem(33, new ItemBuilder(Material.WRITABLE_BOOK).setDisplayname("§6Languages").setLocalizedName("MainInventory.Languages").build());
        inventory.setItem(40, new ItemBuilder(Material.DIAMOND).setDisplayname("Social").setLocalizedName("MainInventory.Social").build());
        player.openInventory(inventory);
    }
    public void createSozialInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54,Objects.requireNonNull(plugin.getConfig().getString("Inventory.SocialInventoryTitle")).replaceAll("&","§"));
        int[] blueglass = new int[]{0,1,2,3,5,6,7,8,9,10,11,15,16,17,18,26};
        int[] whiteglass = new int[]{12,13,14,39,40,41};
        int[] anderesblauglass = new int[]{27,35,36,37,38,42,43,44,46,47,48,50,51,52,53};
        for (int i = 0; i<blueglass.length;i++){inventory.setItem(blueglass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i<whiteglass.length;i++){inventory.setItem(whiteglass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i<anderesblauglass.length;i++){inventory.setItem(anderesblauglass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        player.openInventory(inventory);
    }

    public void createFriendInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54,Objects.requireNonNull(plugin.getConfig().getString("Inventory.FriendInventoryTitle")).replaceAll("&","§"));
        int[] ornageglass = new int[]{0,1,2,3,5,6,7,8,9,17,18,26,27,35,36,44,46,47,52,53};
        for (int i = 0; i<ornageglass.length;i++){inventory.setItem(ornageglass[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwner(player.getName());
        skullMeta.setDisplayName("§aYour Friends");
        playerHead.setItemMeta(skullMeta);
        inventory.setItem(4, playerHead);
        inventory.setItem(45, new ItemBuilder(Material.ARROW).setDisplayname("§eBack").setLocalizedName("friendInventory.back").build());
        inventory.setItem(48, new ItemBuilder(Material.OAK_SIGN).setDisplayname("§6Search").setLocalizedName("friendInventory.search").build());
        inventory.setItem(49, new ItemBuilder(Material.NETHER_STAR).setDisplayname("§9Main Menu").setLocalizedName("friendInventory.main").build());
        inventory.setItem(50, new ItemBuilder(Material.HOPPER).setDisplayname("§aSort").setLocalizedName("friendInventory.sort").build());
        inventory.setItem(51, new ItemBuilder(Material.BOOK).setDisplayname("§7Bookmark").setLocalizedName("friendInventory.bookmark").build());
        if(plugin.yaml.getString("Friends."+player.getUniqueId()) != null) {
            for (String friends : plugin.yaml.getConfigurationSection("Friends." + player.getUniqueId()).getKeys(false)) {
                ItemStack playerHead2 = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta2 = (SkullMeta) playerHead2.getItemMeta();
                skullMeta2.setOwner(friends);
                skullMeta2.setDisplayName("§6" + friends);
                Player friend = Bukkit.getPlayer(friends);
                if (friend != null) {
                    skullMeta2.setLore(Arrays.asList("§aOnline", "§eClick to manage."));
                } else
                    skullMeta2.setLore(Arrays.asList("§cOffline", "§eClick to manage."));
                playerHead2.setItemMeta(skullMeta2);
                inventory.addItem(playerHead2);
            }
        }
        player.openInventory(inventory);
    }

}
