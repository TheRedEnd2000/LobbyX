package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
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
            inventory.addItem(new ItemBuilder(plugin.getMaterial("LobbySelector.Material")).setDisplayname(plugin.getConfig().getString("LobbySelector.Name").replaceAll("%WORLD_NAME%", world.getName()).replaceAll("&","§")).setLore("§7Player: §9§l"+world.getPlayers().size(),"§eClick to warp.").setLocalizedName(world.getName()).build());
        }
        player.openInventory(inventory);
    }

}
