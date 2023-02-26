package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class LobbyXMenuManager {

    private Main plugin;

    public LobbyXMenuManager(Main plugin){
        this.plugin = plugin;
    }

    public void createMainInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Inventory.MainInventoryTitle")).replaceAll("&","§")));
        int[] ornageglass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53};
        for (int i = 0; i<ornageglass.length;i++){inventory.setItem(ornageglass[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(13, new ItemBuilder(Material.END_PORTAL_FRAME).setDisplayname("§9Locations").setLore("§eClick to manage.").setLocalizedName("lobbyx.locations").build());
        player.openInventory(inventory);
    }

    public void createLocationsInventpry(Player player){
        Inventory inventory = Bukkit.createInventory(player,9, Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Inventory.LocationInventoryTitle")).replaceAll("&","§")));
        int[] ornageglass = new int[]{1,2,3,5,6,7};
        for (int i = 0; i<ornageglass.length;i++){inventory.setItem(ornageglass[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(0, new ItemBuilder(Material.ARROW).setDisplayname("§eBack").setLocalizedName("lobbyx.locations.back").build());
        inventory.setItem(4, new ItemBuilder(Material.DIAMOND_BLOCK).setDisplayname("§bLobby").setLore("§eClick to set the Lobby.","§4§lReplaces the old lobby!").setLocalizedName("lobbyx.locations.lobby").build());
        inventory.setItem(8, new ItemBuilder(Material.BARRIER).setDisplayname("§cClose").setLocalizedName("lobbyx.locations.close").build());
        player.openInventory(inventory);
    }

}
