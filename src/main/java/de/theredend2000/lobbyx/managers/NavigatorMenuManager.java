package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class NavigatorMenuManager {

    private Main plugin;

    public NavigatorMenuManager(Main plugin){
        this.plugin = plugin;
    }

    public void createTeleporterInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.TeleporterInventoryTitle")).replaceAll("&","§"));
        int invSize = plugin.navigatorYaml.getInt("Navigator.Settings.size");
        if(plugin.navigatorYaml.contains("Navigator.Slots")) {
            for (String slot : plugin.navigatorYaml.getConfigurationSection("Navigator.Slots").getKeys(false)) {
                int itemSlot = Integer.parseInt(slot);
                if(itemSlot <= invSize) {
                    String itemName = plugin.navigatorYaml.getString("Navigator.Slots." + slot + ".name");
                    String material = "Navigator.Slots." + slot + ".item";
                    String texture = plugin.navigatorYaml.getString("Navigator.Slots." + slot + ".texture");
                    if (plugin.getNavigatorMaterial(material).equals(Material.PLAYER_HEAD)) {
                        inventory.setItem(itemSlot, new ItemBuilder(plugin.getNavigatorMaterial(material)).setSkullOwner(Main.getTexture(texture)).setDisplayname(itemName.replaceAll("&", "§")).setLocalizedName(String.valueOf(itemSlot)).build());
                    } else
                        inventory.setItem(itemSlot, new ItemBuilder(plugin.getNavigatorMaterial(material)).setDisplayname(itemName.replaceAll("&", "§")).setLocalizedName(String.valueOf(itemSlot)).build());
                }
            }
        }else
            player.sendMessage("§cERROR! Please contact an admin.");
        player.openInventory(inventory);
    }

    public void createSelectSlotInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,plugin.navigatorYaml.getInt("Navigator.Settings.size"),"§7Select a Slot");
        inventory.clear();
        int invSize = plugin.navigatorYaml.getInt("Navigator.Settings.size");
        if(plugin.navigatorYaml.contains("Navigator.Slots")) {
            for (String slot : plugin.navigatorYaml.getConfigurationSection("Navigator.Slots").getKeys(false)) {
                int itemSlot = Integer.parseInt(slot);
                if(itemSlot <= invSize) {
                    String itemName = plugin.navigatorYaml.getString("Navigator.Slots." + slot + ".name");
                    String material = "Navigator.Slots." + slot + ".item";
                    String texture = plugin.navigatorYaml.getString("Navigator.Slots." + slot + ".texture");
                    if (plugin.getNavigatorMaterial(material).equals(Material.PLAYER_HEAD)) {
                        inventory.setItem(itemSlot, new ItemBuilder(plugin.getNavigatorMaterial(material)).setSkullOwner(Main.getTexture(texture)).setDisplayname(itemName.replaceAll("&", "§")+" §f- §9SLOT: §6"+itemSlot).setLore("§eClick to edit this Slot!").setLocalizedName(String.valueOf(itemSlot)).build());
                    } else
                        inventory.setItem(itemSlot, new ItemBuilder(plugin.getNavigatorMaterial(material)).setDisplayname(itemName.replaceAll("&", "§")+" §f- §9SLOT: §6"+itemSlot).setLore("§eClick to edit this Slot!").setLocalizedName(String.valueOf(itemSlot)).build());
                }
            }
        }else
            player.sendMessage("§cERROR! Please contact an admin.");
        player.openInventory(inventory);
    }

    public void createSlotEditInventory(Player player, int slot){
        Inventory inventory = Bukkit.createInventory(player,45,"§7Edit Navigator Slot");
        int[] glass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44};
        for (int i = 0; i<glass.length;i++){inventory.setItem(glass[i], new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").setLocalizedName(String.valueOf(slot)).build());}
        String itemName = plugin.navigatorYaml.getString("Navigator.Slots." + slot + ".name");
        String material = "Navigator.Slots." + slot + ".item";
        String materialName = plugin.navigatorYaml.getString("Navigator.Slots." + slot + ".item");
        String texture = plugin.navigatorYaml.getString("Navigator.Slots." + slot + ".texture");
        for(String slots : plugin.navigatorYaml.getConfigurationSection("Navigator.Slots").getKeys(false)){
            if(slots.equals(String.valueOf(slots))){
                if(plugin.getNavigatorMaterial(material).equals(Material.PLAYER_HEAD)){
                    inventory.setItem(22,new ItemBuilder(plugin.getNavigatorMaterial(material)).setSkullOwner(Main.getTexture(texture)).setDisplayname(itemName.replaceAll("&","§")).setLore("§eCurrent layout.").build());
                }else
                    inventory.setItem(22,new ItemBuilder(plugin.getNavigatorMaterial(material)).setDisplayname(itemName.replaceAll("&","§")).setLore("§eCurrent layout.").build());
            }
        }
        inventory.setItem(36,new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eBack").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("back").build());
        inventory.setItem(28, new ItemBuilder(Material.GRASS_BLOCK).setDisplayname("§2Material").setLore("§7Currently: §6"+materialName.toUpperCase(),"§eClick to change.").setLocalizedName("material").build());
        inventory.setItem(10, new ItemBuilder(Material.NAME_TAG).setDisplayname("§2Item Name").setLore("§7Currently: §r"+itemName.replaceAll("&","§"),"§eClick to change.").setLocalizedName("name").build());
        String action = plugin.navigatorYaml.getString("Navigator.Slots." + slot + ".action");
        assert action != null;
        switch (action) {
            case "none":
                inventory.setItem(25, new ItemBuilder(Material.HOPPER).setDisplayname("§2Actions").setLore("§6➤ None", "§7Teleport", "§7Close").setLocalizedName("action.none").build());
                break;
            case "teleport":
                inventory.setItem(25, new ItemBuilder(Material.HOPPER).setDisplayname("§2Actions").setLore("§7None", "§6➤ Teleport", "§7Close").setLocalizedName("action.teleport").build());
                inventory.setItem(34, new ItemBuilder(Material.BEACON).setDisplayname("§3Location").setLore("§7Click to set the location for the slot.","§cIt will use your current coordinates.").setLocalizedName("location").build());
                break;
            case "close":
                inventory.setItem(25, new ItemBuilder(Material.HOPPER).setDisplayname("§2Actions").setLore("§7 None", "§7Teleport", "§6➤ Close").setLocalizedName("action.close").build());
                break;
            default:
                plugin.navigatorYaml.set("Navigator.Slots." + slot + ".action", "none");
                plugin.saveNavigator();
                inventory.setItem(25, new ItemBuilder(Material.HOPPER).setDisplayname("§2Actions").setLore("§6➤ None", "§7Teleport", "§7Close").setLocalizedName("action.none").build());
                break;
        }
        player.openInventory(inventory);
    }

}
