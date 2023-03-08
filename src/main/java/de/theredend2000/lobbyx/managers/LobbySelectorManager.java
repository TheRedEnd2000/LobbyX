package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class LobbySelectorManager {

    private Main plugin;

    public LobbySelectorManager(Main plugin){
        this.plugin = plugin;
    }

    public void createLobbySelector(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.LobbySelectorInventoryTitle")).replaceAll("&","§"));
        int[] pinkglass = new int[]{45,46,47,48,49,50,51,52,53};
        for (int i = 0; i<pinkglass.length;i++){inventory.setItem(pinkglass[i], new ItemBuilder(Material.PINK_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for(World world : plugin.getLobbyWorlds()){
            inventory.addItem(new ItemBuilder(plugin.getMaterial("LobbySelector.Material")).setDisplayname(plugin.getConfig().getString("LobbySelector.Name").replaceAll("%WORLD_NAME%", world.getName()).replaceAll("&","§")).setLore("§7Player: §9§l"+world.getPlayers().size(),plugin.getConfig().getBoolean("Lobby_Worlds.maintenance") ? "§4MAINTENANCE-MODE §cThis world is currently disabled" : "§eClick to warp.",player.isOp() || player.hasPermission("Permissions.MaintenanceModsPermission") ? "§7Right-Click to configure this world." : null).setLocalizedName(world.getName()).build());
        }
        if(player.isOp() || player.hasPermission(Objects.requireNonNull(plugin.getConfig().getString("Permissions.CreateNewLobbyWorld")))){
            inventory.setItem(53, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§6World Creator").setLocalizedName("ls.create").setSkullOwner(Main.getTexture("YzY5MTk2YjMzMGM2Yjg5NjJmMjNhZDU2MjdmYjZlY2NlNDcyZWFmNWM5ZDQ0Zjc5MWY2NzA5YzdkMGY0ZGVjZSJ9fX0=")).build());
        }
        player.openInventory(inventory);
    }
    public void createWorldCreatorInventory(Player player){
        if(!plugin.yaml.contains("WorldCreator."+player.getName())){
            plugin.yaml.set("WorldCreator."+player.getName()+".Name", "null");
            plugin.yaml.set("WorldCreator."+player.getName()+".Type","NORMAL");
            plugin.saveData();
        }
        Inventory inventory = Bukkit.createInventory(player,27, Objects.requireNonNull(plugin.getConfig().getString("Inventory.WorldCreatorInventoryTitle")).replaceAll("&","§"));
        int[] limeglass = new int[]{0,1,2,3,4,5,6,7,8,9};
        for (int i = 0; i<limeglass.length;i++){inventory.setItem(limeglass[i], new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(18, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eBack").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("wc.back").build());
        inventory.setItem(13, new ItemBuilder(Material.EMERALD_BLOCK).setDisplayname("§2Create World").setLocalizedName("wc.create").build());
        inventory.setItem(10, new ItemBuilder(Material.NAME_TAG).setDisplayname("§6World Name").setLore("§7Currently: §6"+plugin.yaml.getString("WorldCreator."+player.getName()+".Name"),"§eClick to Change").setLocalizedName("wc.name").build());
        switch (Objects.requireNonNull(plugin.yaml.getString("WorldCreator." + player.getName() + ".Type"))){
            case "NORMAL":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§5World Type").setLore("§6➤ NORMAL", "§7FLAT","§7AMPLIFIED").setLocalizedName("wc.normal").build());
                break;
            case "FLAT":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§5World Type").setLore("§7NORMAL", "§6➤ FLAT","§7AMPLIFIED").setLocalizedName("wc.flat").build());
                break;
            case "AMPLIFIED":
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§5World Type").setLore("§7NORMAL", "§7 FLAT","§6➤ AMPLIFIED").setLocalizedName("wc.amplified").build());
                break;
            default:
                inventory.setItem(16, new ItemBuilder(Material.HOPPER).setDisplayname("§5World Type").setLore("§6➤ NORMAL", "§7FLAT","§7AMPLIFIED").setLocalizedName("wc.normal").build());
                break;
        }
        player.openInventory(inventory);
    }

}
