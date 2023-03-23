package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class LobbyXMenuManager {

    private Main plugin;
    private HashMap<ItemStack, Integer> items;

    public LobbyXMenuManager(Main plugin){
        this.plugin = plugin;
        items = new HashMap<>();
    }

    public void createMainInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Inventory.MainInventoryTitle")).replaceAll("&","§")));
        items.clear();
        inventory.clear();
        int[] ornageglass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53};
        for (int i = 0; i<ornageglass.length;i++){inventory.setItem(ornageglass[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(11, new ItemBuilder(Material.COMPARATOR).setDisplayname("§4Settings").setLore("§7Change many settings to make","§7the plugin as good as possible for you.","","§eClick to configure.").setLocalizedName("lobbyx.settings").build());
        inventory.setItem(15, new ItemBuilder(Material.COMPASS).setDisplayname("§5Edit Navigator").setLore("§7Edit the navigator","§7to configure all locations.","","§eClick to edit.").setLocalizedName("lobbyx.editNavigator").build());
        inventory.setItem(38, new ItemBuilder(Material.LIGHT_WEIGHTED_PRESSURE_PLATE).setDisplayname("§3Jump and Run §e§lBETA").setLore("§7Configure the jump and run","§7change settings, blocks and more.","","§eClick to configure.").setLocalizedName("lobbyx.jnr").setSkullOwner(Main.getTexture("ZjY2YmM1MTljZDI2NjJiYmIwYmFjN2U2OWY4MDAyNjFhMTk4M2EzMmIzOWMxODlkM2M5OGJjMjk4YjUyNWJkZCJ9fX0=")).build());
        inventory.setItem(42, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§5Ranks").setLore("§7Click to get the link","§7for the Power rank webeditor.","","§eClick to get the link.").setLocalizedName("lobbyx.editRanks").setSkullOwner(Main.getTexture("ZjY2YmM1MTljZDI2NjJiYmIwYmFjN2U2OWY4MDAyNjFhMTk4M2EzMmIzOWMxODlkM2M5OGJjMjk4YjUyNWJkZCJ9fX0=")).build());
        player.openInventory(inventory);
    }

    public void createSettingsInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Inventory.MainSettingsInventoryTitle")).replaceAll("&","§")));
        int[] purpleGlass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26,27,35,36,44,45,46,47,48,50,51,52,53};
        for (int i = 0; i<purpleGlass.length;i++){inventory.setItem(purpleGlass[i], new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.BACK_SKULL_TEXTURE).setDisplayname("§eBack").setLocalizedName("lobbyx.settings.back").build());
        inventory.setItem(10, new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayname("§bLobby").setLore("§eClick to set the Lobby.","§4§lReplaces the old lobby!").setLocalizedName("lobbyx.settings.lobby").build());
        inventory.setItem(43, new ItemBuilder(Material.IRON_BOOTS).setDisplayname("§bLobby Speed").setLore("§7All Players have speed in lobbys.","","§7Currently: §6"+(plugin.getConfig().getBoolean("Settings.LobbySpeed") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.settings.lobbyspeed").withGlow(plugin.getConfig().getBoolean("Settings.LobbySpeed")).build());
        inventory.setItem(49, new ItemBuilder(Material.BARRIER).setDisplayname("§cClose").setLocalizedName("lobbyx.settings.close").build());
        player.openInventory(inventory);
    }
    public void createJnrInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Inventory.MainJnrInventoryTitle")).replaceAll("&","§")));
        int[] blueGlass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,50,51,52,53};
        for (int i = 0; i<blueGlass.length;i++){inventory.setItem(blueGlass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.BACK_SKULL_TEXTURE).setDisplayname("§eBack").setLocalizedName("lobbyx.jnr.back").build());
        inventory.setItem(10, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("MjRhZjhkNTc1ZjBhNTI0ZWY2NmEyMTU3M2U4YTRhOGFjN2Q2NDBiMDBkOThlMmU5Mzc0ZTYwNDcwZTdjODZmMCJ9fX0=")).setDisplayname("§6Coins").setLore("§7Player get coins when finishing","§7a parkour. (Double Coins when Highscore)","","§7Currently: §6"+(plugin.getConfig().getBoolean("JumpAndRun.jnrCoins") ? "§a§l✔ Enabled" : "§c§l❌ Disabled") ,"§eClick to toggle").setLocalizedName("lobbyx.jnr.coins").build());
        inventory.setItem(38, new ItemBuilder(Material.LIGHT_WEIGHTED_PRESSURE_PLATE).setDisplayname("§6Pressure plate action").setLore("§7Player can start the parkour","§7with an pressure plate.","","§8Available Types:","§b- LIGHT WEIGHTED PRESSURE PLATE","§b- HEAVY WEIGHTED PRESSURE PLATE","§b- STONE PRESSURE PLATE","","§7Currently: §6"+(plugin.getConfig().getBoolean("JumpAndRun.pressurePlateStart") ? "§a§l✔ Enabled" : "§c§l❌ Disabled") ,"§eClick to toggle").setLocalizedName("lobbyx.jnr.pressure").build());
        inventory.setItem(16, new ItemBuilder(Material.PAPER).setDisplayname("§9Actionbar").setLore("§7Player can see the points in the actionbar.","","§7Currently: §6"+(plugin.getConfig().getBoolean("JumpAndRun.Actionbar.enabled") ? "§a§l✔ Enabled" : "§c§l❌ Disabled") ,"§eClick to toggle").setLocalizedName("lobbyx.jnr.actionbar").build());
        inventory.setItem(49, new ItemBuilder(Material.BARRIER).setDisplayname("§cClose").setLocalizedName("lobbyx.jnr.close").build());
        inventory.setItem(22, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture(plugin.getConfig().getBoolean("JumpAndRun.enabled") ? "ZmY0MDRjMWI3M2E3Y2JmYzg2MDU3OGNlMDQ0ZTI3MWQxM2I3OTE2NmVhZDgwYjM2NDUxOGNmZjIzNGQ2MTIxMiJ9fX0=" : "ZTYwMTViNDgwYWMyY2U0ODM0YzlkOGYyY2E1ZDE1YzZjYWMzOGE1MjE0NzA0MGExYzRjMDk1YTIzMTk4MTZmNSJ9fX0=")).setDisplayname("§3§lJUMP AND RUN").setLore("§e§lBETA FEATURE!","§7Player can start the parkour","§7with /jnr or the pressure plate if enabled.","","§7Currently: §6"+(plugin.getConfig().getBoolean("JumpAndRun.enabled") ? "§a§l✔ Enabled" : "§c§l❌ Disabled") ,"§eClick to toggle").setLocalizedName("lobbyx.jnr.enabled").build());
        player.openInventory(inventory);
    }

}
