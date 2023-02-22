package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

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
                    if(plugin.yaml.getBoolean("Settings."+lobbyPlayer.getUniqueId()+".PlayerHidden")){
                        lobbyPlayer.hidePlayer(player);
                    }
                }
            }
            player.setFoodLevel(20);
            //HEAL
            setItems(player);
            plugin.getTablistManager().setPlayerList(player);
        }
    }

    public void setItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(0,new ItemBuilder(Material.CHEST).setDisplayname("§1Gadgets").setLore("§eRight click to open").build());
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwner(player.getName());
        skullMeta.setDisplayName("§2Your Profile");
        skullMeta.setLocalizedName("lobbyx.profile");
        playerHead.setItemMeta(skullMeta);
        player.getInventory().setItem(1,playerHead);
        if(plugin.yaml.getBoolean("Settings."+player.getUniqueId()+".PlayerHidden")){
            player.getInventory().setItem(7,new ItemBuilder(Material.GRAY_DYE).setDisplayname("§7Player: §cHidden").setLore("§7Right click to change.").setLocalizedName("lobbyx.player_hider").build());
        }else
            player.getInventory().setItem(7,new ItemBuilder(Material.LIME_DYE).setDisplayname("§7Player: §aShown").setLore("§7Right click to change.").setLocalizedName("lobbyx.player_hider").build());
        player.getInventory().setItem(4,new ItemBuilder(Material.COMPASS).setDisplayname("§5Navigator").setLore("§eRight click to open").build());
        player.getInventory().setItem(8,new ItemBuilder(Material.FIREWORK_STAR).setDisplayname("§fLobby Selector").setLore("§eRight click to open").build());
    }

}
