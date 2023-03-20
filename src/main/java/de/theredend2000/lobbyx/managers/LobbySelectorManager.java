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
            inventory.addItem(new ItemBuilder(plugin.getMaterial("LobbySelector.Material")).setDisplayname(plugin.getConfig().getString("LobbySelector.Name").replaceAll("%WORLD_NAME%", world.getName()).replaceAll("&","§")).setLore("§7Players: §9§l"+world.getPlayers().size(),plugin.getConfig().getBoolean("Lobby_Worlds."+world.getName()+".maintenance") ? "§4MAINTENANCE-MODE §cThis world is currently disabled" : "§eClick to warp.",player.hasPermission(Objects.requireNonNull(plugin.getConfig().getString("Permissions.MaintenanceModsPermission"))) ? "§7Right-Click to configure this world." : null).setLocalizedName(world.getName()).build());
        }
        if(player.isOp() || player.hasPermission(Objects.requireNonNull(plugin.getConfig().getString("Permissions.CreateNewLobbyWorld")))){
            inventory.setItem(53, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§6World Creator").setLocalizedName("ls.create").setSkullOwner(Main.getTexture("YzY5MTk2YjMzMGM2Yjg5NjJmMjNhZDU2MjdmYjZlY2NlNDcyZWFmNWM5ZDQ0Zjc5MWY2NzA5YzdkMGY0ZGVjZSJ9fX0=")).build());
        }
        player.openInventory(inventory);
    }
    public void createDeleteLobby(Player player, String worldName){
        Inventory inventory = Bukkit.createInventory(player,27, "§cConfirm deletion.");
        inventory.setItem(11,new ItemBuilder(Material.GREEN_CONCRETE).setDisplayname("§aConfirm").setLocalizedName("cl.confirm").build());
        inventory.setItem(13,new ItemBuilder(Material.GRASS_BLOCK).setDisplayname("§aWorld: §6"+worldName).setLore("§7Are you sure to delete this lobby world").setLocalizedName(worldName).build());
        inventory.setItem(15,new ItemBuilder(Material.RED_CONCRETE).setDisplayname("§cCancel").setLocalizedName("cl.cancel").build());
        player.openInventory(inventory);
    }
    public void createWorldCreatorInventory(Player player){
        if(!plugin.yaml.contains("WorldCreator."+player.getName())){
            plugin.yaml.set("WorldCreator."+player.getName()+".Name", "null");
            plugin.yaml.set("WorldCreator."+player.getName()+".Type","NORMAL");
            plugin.saveData();
        }
        Inventory inventory = Bukkit.createInventory(player,27, Objects.requireNonNull(plugin.getConfig().getString("Inventory.WorldCreatorInventoryTitle")).replaceAll("&","§"));
        int[] limeglass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26};
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

    public void createManageWorldSelector(Player player, String worldName){
        Inventory inventory = Bukkit.createInventory(player,27, Objects.requireNonNull(plugin.getConfig().getString("Inventory.WorldManageInventoryTitle")).replaceAll("&","§"));
        int[] cyanGlass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,19,20,21,22,23,24,25,26};
        for (int i = 0; i<cyanGlass.length;i++){inventory.setItem(cyanGlass[i], new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        boolean maintenance = plugin.getConfig().getBoolean("Lobby_Worlds."+worldName+".maintenance");
        inventory.setItem(4, new ItemBuilder(Material.GRASS_BLOCK).setDisplayname("§aWorld: §6"+worldName).setLore("§7You are in the edit menu of the world "+worldName).setLocalizedName(worldName).build());
        inventory.setItem(10, new ItemBuilder(Material.WOODEN_AXE).setDisplayname("§2Maintenance mode").setLore("§7If the maintenance mode is","§7enabled nobody can join.","","§7Currently: "+(maintenance ? "§a§l✔ Enabled" : "§c§l❌ Disabled"),"§eClick to change.").setLocalizedName("wm.maintenance").build());
        inventory.setItem(13, new ItemBuilder(Material.ENDER_PEARL).setDisplayname("§5Teleport").setLore("§7Teleport you to this world to","§7set the spawn or build there.","","§eClick to teleport.").setLocalizedName("wm.teleport").build());
        inventory.setItem(16, new ItemBuilder(Material.REDSTONE_BLOCK).setDisplayname("§4Delete").setLore("§7Delete this world","§4You can not undo this.","","§eClick to delete.").setLocalizedName("wm.delete").build());
        inventory.setItem(18, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eBack").setSkullOwner(Main.BACK_SKULL_TEXTURE).setLocalizedName("wm.back").build());
        player.openInventory(inventory);
    }

}
