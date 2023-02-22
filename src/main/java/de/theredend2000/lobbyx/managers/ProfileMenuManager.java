package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.listeners.itemListeners.ProfileListeners;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class ProfileMenuManager implements Listener {

    private Main plugin;

    public ProfileMenuManager(Main plugin){
        this.plugin = plugin;
    }

    public void createProfileInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player, 54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.ProfileInventoryTitle")).replaceAll("&","§"));
        player.openInventory(inventory);
    }

}
