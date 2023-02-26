package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.listeners.itemListeners.GadgetsListener;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class GadgetsMenuManager {

    private Main plugin;

    public GadgetsMenuManager(Main plugin){
        this.plugin = plugin;
    }

    public void createGadgetsInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(plugin.getConfig().getString("GadgetsInventory.GadgetsMainMenu")).replaceAll("&","§"));
        int[] orangeglass = new int[]{1,2,3,5,6,7};
        for (int i = 0; i<orangeglass.length;i++){inventory.setItem(orangeglass[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        player.openInventory(inventory);
    }

}
