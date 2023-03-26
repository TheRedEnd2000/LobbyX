package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
        inventory.setItem(38, new ItemBuilder(Material.LIGHT_WEIGHTED_PRESSURE_PLATE).setDisplayname("§3Jump and Run §e§lBETA").setLore("§7Configure the jump and run","§7change settings, blocks and more.","","§eClick to configure.").setLocalizedName("lobbyx.jnr").build());
        inventory.setItem(42, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§5Ranks").setLore("§7Click to get the link","§7for the Power rank webeditor.","","§eClick to get the link.").setLocalizedName("lobbyx.editRanks").setSkullOwner(Main.getTexture("ZjY2YmM1MTljZDI2NjJiYmIwYmFjN2U2OWY4MDAyNjFhMTk4M2EzMmIzOWMxODlkM2M5OGJjMjk4YjUyNWJkZCJ9fX0=")).build());
        player.openInventory(inventory);
    }

    public void createSettingsInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Inventory.MainSettingsInventoryTitle")).replaceAll("&","§")));
        int[] purpleGlass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26,27,35,36,44,45,46,47,48,50,51,52,53};
        for (int i = 0; i<purpleGlass.length;i++){inventory.setItem(purpleGlass[i], new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(45, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.BACK_SKULL_TEXTURE).setDisplayname("§eBack").setLocalizedName("lobbyx.settings.back").build());
        inventory.setItem(10, new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayname("§bLobby").setLore("§eClick to set the Lobby.","§4§lReplaces the old lobby!").setLocalizedName("lobbyx.settings.lobby").build());
        inventory.setItem(11, new ItemBuilder(Material.LEATHER_BOOTS).setDisplayname("§3Max Y Level").setLore("§7Change the max y level that","§7players can go down before","§7teleporting back to lobby spawn.","","§7Currently: §6"+plugin.getConfig().getInt("Settings.PlayerYLevelTeleport"),"§eLEFT CLICK §8to add a Y level.","§eRIGHT CLICK §8to remove a Y level.").setLocalizedName("lobbyx.settings.ylevel").build());
        inventory.setItem(12, new ItemBuilder(Material.CHEST).setDisplayname("§eIntractable Items").setLore("§7Select which blocks the players","§7can interact with and which not.","","§eClick to view").setLocalizedName("lobbyx.settings.items").build());
        inventory.setItem(28, new ItemBuilder(Material.IRON_BOOTS).setDisplayname("§bLobby Speed").setLore("§7All Players have speed in lobbys.","","§7Currently: §6"+(plugin.getConfig().getBoolean("Settings.LobbySpeed") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.settings.lobbyspeed").withGlow(plugin.getConfig().getBoolean("Settings.LobbySpeed")).build());
        inventory.setItem(29, new ItemBuilder(Material.ZOMBIE_SPAWN_EGG).setDisplayname("§cMob Damage").setLore("§7Enable that no mobs can be damaged.","","§7Currently: §6"+(plugin.getConfig().getBoolean("Settings.MobDamageInLobbys") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.settings.mobDamage").withGlow(plugin.getConfig().getBoolean("Settings.MobDamageInLobbys")).build());
        inventory.setItem(30, new ItemBuilder(Material.OAK_DOOR).setDisplayname("§6Welcome Titles").setLore("§7Enable welcome titles for players","§7that joined the server.","§7Message can be changed in the config.yml file.","","§7Currently: §6"+(plugin.getConfig().getBoolean("Titles.WelcomeTitle.enabled") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.settings.welcomeTitles").withGlow(plugin.getConfig().getBoolean("Titles.WelcomeTitle.enabled")).build());
        inventory.setItem(31, new ItemBuilder(Material.FIREWORK_STAR).setDisplayname("§dLobby Selector").setLore("§7Disable that player can use","§7the lobby selector.","","§7Currently: §6"+(plugin.getConfig().getBoolean("LobbySelector.enabled") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.settings.lobbySelector").withGlow(plugin.getConfig().getBoolean("LobbySelector.enabled")).build());
        inventory.setItem(32, new ItemBuilder(Material.NAME_TAG).setDisplayname("§6Broadcaster").setLore("§7That every user in all lobbys gets a","§7broadcast message.","§7Message can be changed in the config.yml file.","","§7Currently: §6"+(plugin.getConfig().getBoolean("Broadcaster.enabled") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.settings.broadcaster").withGlow(plugin.getConfig().getBoolean("Broadcaster.enabled")).build());
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
    public void createIntractableItemsInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,36, Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Inventory.MainIntractableItemsInventoryTitle")).replaceAll("&","§")));
        int[] blueGlass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,28,29,30,31,32,33,34,35};
        for (int i = 0; i<blueGlass.length;i++){inventory.setItem(blueGlass[i], new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(27, new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.BACK_SKULL_TEXTURE).setDisplayname("§eBack").setLocalizedName("lobbyx.items.back").build());
        inventory.setItem(10, new ItemBuilder(Material.REDSTONE).setDisplayname("§cRedstone Items").setLore("§7If enabled no player can interact with redstone items.","","§6All redstone items:","§3- Redstone wire","§3- Lever","§3- Comparator","§3- Daylight sensor","§3- Dropper","§3- Dispenser","§3- Hopper","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.RedStoneItems") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.redstone").withGlow(plugin.getConfig().getBoolean("IntractableItems.RedStoneItems")).build());
        inventory.setItem(11, new ItemBuilder(Material.BELL).setDisplayname("§7Station Items").setLore("§7If enabled no player can interact with station items.","","§6All station items:","§3- Lodestone","§3- Bell","§3- Stone cutter","§3- Cartography table","§3- Grindstone","§3- Barrel","§3- Smithing table","§3- Loom","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.WorkingStations") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.station").withGlow(plugin.getConfig().getBoolean("IntractableItems.WorkingStations")).build());
        inventory.setItem(12, new ItemBuilder(Material.FURNACE).setDisplayname("§4Furnaces").setLore("§7If enabled no player can interact with furnaces.","","§6All furnaces:","§3- Furnace","§3- Smoker","§3- Blast Furnace","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.Furnaces") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.furnace").withGlow(plugin.getConfig().getBoolean("IntractableItems.Furnaces")).build());
        inventory.setItem(13, new ItemBuilder(Material.IRON_DOOR).setDisplayname("§8Doors").setLore("§7If enabled no player can interact with doors.","","§6§lAll types of doors.","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.Doors") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.doors").withGlow(plugin.getConfig().getBoolean("IntractableItems.Doors")).build());
        inventory.setItem(14, new ItemBuilder(Material.STONE_BUTTON).setDisplayname("§8Buttons").setLore("§7If enabled no player can interact with buttons.","","§6§lAll types of buttons.","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.Buttons") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.buttons").withGlow(plugin.getConfig().getBoolean("IntractableItems.Buttons")).build());
        inventory.setItem(15, new ItemBuilder(Material.OAK_BOAT).setDisplayname("§eBoats").setLore("§7If enabled no player can interact with boats.","","§6§lAll types of boats.","§6§lAll types of chest boats","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.Boats") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.boats").withGlow(plugin.getConfig().getBoolean("IntractableItems.Boats")).build());
        inventory.setItem(16, new ItemBuilder(Material.CHEST).setDisplayname("§eChests").setLore("§7If enabled no player can interact with chests.","","§6§lAll chest items:","§3- Chest","§3- Redstone Chest","§3- Ender Chest","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.Chests") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.chests").withGlow(plugin.getConfig().getBoolean("IntractableItems.Chests")).build());
        inventory.setItem(19, new ItemBuilder(Material.OAK_FENCE_GATE).setDisplayname("§eFence gates").setLore("§7If enabled no player can interact with fence gates.","","§6§lAll types of fences.","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.Fence_Gates") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.fence").withGlow(plugin.getConfig().getBoolean("IntractableItems.Fence_Gates")).build());
        inventory.setItem(20, new ItemBuilder(Material.OAK_TRAPDOOR).setDisplayname("§eTrapdoors").setLore("§7If enabled no player can interact with trapdoors.","","§6§lAll types of trapdoors.","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.Trapdoors") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.trapdoors").withGlow(plugin.getConfig().getBoolean("IntractableItems.Trapdoors")).build());
        if(plugin.getServer().getVersion().contains("1.17") | plugin.getServer().getVersion().contains("1.18") | plugin.getServer().getVersion().contains("1.19"))
            inventory.setItem(21, new ItemBuilder(Material.LIGHT_BLUE_CANDLE).setDisplayname("§bCandles").setLore("§7If enabled no player can interact with candles.","","§6§lAll types of candles.","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.Candles") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.candles").withGlow(plugin.getConfig().getBoolean("IntractableItems.Candles")).build());
        else
            inventory.setItem(21, new ItemBuilder(Material.BARRIER).setDisplayname("§bCandles §4NOT SUPPORTED").setLore("§cYour version is lower that 1.17","§cso candles are disabled").build());
        inventory.setItem(22, new ItemBuilder(Material.BEE_NEST).setDisplayname("§dOther Items").setLore("§7If enabled no player can interact with other items.","","§6All other items:","§3- Beacon","§3- Enchanting table","§3- Note block","§3- Jukebox","§3- Brewing stand","§3- Cauldron","§3- Flower pot","§3- Bee Hive","§3- Bee nest","§3- Respawn anchor","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.Other") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.other").withGlow(plugin.getConfig().getBoolean("IntractableItems.Other")).build());
        inventory.setItem(23, new ItemBuilder(Material.MINECART).setDisplayname("§8Minecarts").setLore("§7If enabled no player can interact with minecarts.","","§6All minecarts:","§3- Minecart","§3- Chests Minecart","§3- Hopper Minecart","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.Minecarts") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.minecarts").withGlow(plugin.getConfig().getBoolean("IntractableItems.Minecarts")).build());
        inventory.setItem(24, new ItemBuilder(Material.ANVIL).setDisplayname("§8Anvils").setLore("§7If enabled no player can interact with anvils.","","§6All anvils items:","§3- Anvil","§3- Damaged anvil","§3- Chipped Anvil","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.Anvils") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.anvils").withGlow(plugin.getConfig().getBoolean("IntractableItems.Anvils")).build());
        inventory.setItem(25, new ItemBuilder(Material.SHULKER_BOX).setDisplayname("§5Shulkers").setLore("§7If enabled no player can interact with shulkers.","","§6§lAll types of shulkers:","","§7Currently: §6"+(plugin.getConfig().getBoolean("IntractableItems.Shulkers") ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to toggle.").setLocalizedName("lobbyx.items.shulkers").withGlow(plugin.getConfig().getBoolean("IntractableItems.Shulkers")).build());
        inventory.setItem(31, new ItemBuilder(Material.BARRIER).setDisplayname("§cClose").setLocalizedName("lobbyx.items.close").build());
        player.openInventory(inventory);
    }


}
