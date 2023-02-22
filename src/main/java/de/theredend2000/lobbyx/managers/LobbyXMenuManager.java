package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class LobbyXMenuManager {

    private Main plugin;

    public LobbyXMenuManager(Main plugin){
        this.plugin = plugin;
    }

    public void createMainInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Inventory.MainInventoryTitle")).replaceAll("&","§")));

        player.openInventory(inventory);
    }

}
