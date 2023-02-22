package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SetPlayerLobbyManager {

    private Main plugin;

    public SetPlayerLobbyManager(Main plugin){
        this.plugin = plugin;
    }

    public void setPlayerInLobby(Player player){
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(plugin.yaml.getBoolean("Settings."+player.getUniqueId()+".PlayerHidden")){
                for(Player lobbyPlayer : player.getWorld().getPlayers()){
                    player.hidePlayer(lobbyPlayer);
                }
            }
            setItems(player);
        }
    }

    public void setItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(0,new ItemBuilder(Material.CHEST).setDisplayname("Gadgets").setLore("§eRight click to open").build());
        if(plugin.yaml.getBoolean("Settings."+player.getUniqueId()+".PlayerHidden")){
            player.getInventory().setItem(7,new ItemBuilder(Material.GRAY_DYE).setDisplayname("§7Player: §cHidden").setLore("§7Right click to change.").setLocalizedName("lobbyx.player_hider").build());
        }else
            player.getInventory().setItem(7,new ItemBuilder(Material.LIME_DYE).setDisplayname("§7Player: §aShown").setLore("§7Right click to change.").setLocalizedName("lobbyx.player_hider").build());
    }

}
