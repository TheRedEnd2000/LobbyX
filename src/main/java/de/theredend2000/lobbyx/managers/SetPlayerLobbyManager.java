package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
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
            checkConfig(player);

            if(plugin.getConfig().getBoolean("Titles.WelcomeTitle.enabled"))
                player.sendTitle(plugin.getConfig().getString("Titles.WelcomeTitle.title").replaceAll("&","§").replaceAll("%PLAYER%",player.getName()),plugin.getConfig().getString("Titles.WelcomeTitle.subtitle").replaceAll("&","§").replaceAll("%PLAYER%",player.getName()),20,100,40);

            ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin,"Locations.Lobby");
            if(locationUtil.loadLocation() != null){
                player.teleport(locationUtil.loadLocation());
            }else{
                for(Player admins : Bukkit.getOnlinePlayers()){
                    if(admins.isOp()){
                        admins.sendMessage("§cThe Lobby is not set. Please do this now!");
                    }
                }
            }

        }
    }

    public void setItems(Player player) {
        player.getInventory().clear();
        if(plugin.getConfig().getBoolean("Items.Gadgets.enabled"))
            player.getInventory().setItem(plugin.getConfig().getInt("Items.Gadgets.slot"),new ItemBuilder(plugin.getMaterial("Items.Gadgets.material")).setDisplayname(plugin.getConfig().getString("Items.Gadgets.displayname").replaceAll("&","§")).build());
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwner(player.getName());
        skullMeta.setDisplayName(plugin.getConfig().getString("Items.Profile.displayname").replaceAll("&","§"));
        skullMeta.setLocalizedName("lobbyx.profile");
        playerHead.setItemMeta(skullMeta);
        if(plugin.getConfig().getBoolean("Items.Profile.enabled"))
            player.getInventory().setItem(plugin.getConfig().getInt("Items.Profile.slot"),playerHead);
        if(plugin.yaml.getBoolean("Settings."+player.getUniqueId()+".PlayerHidden")){
            if(plugin.getConfig().getBoolean("Items.Hider_OFF.enabled"))
                player.getInventory().setItem(plugin.getConfig().getInt("Items.Hider_OFF.slot"),new ItemBuilder(plugin.getMaterial("Items.Hider_OFF.material")).setDisplayname(plugin.getConfig().getString("Items.Hider_OFF.displayname").replaceAll("&","§")).setLocalizedName("lobbyx.player_hider").build());
        }else {
            if(plugin.getConfig().getBoolean("Items.Hider_ON.enabled"))
                player.getInventory().setItem(plugin.getConfig().getInt("Items.Hider_ON.slot"),new ItemBuilder(plugin.getMaterial("Items.Hider_ON.material")).setDisplayname(plugin.getConfig().getString("Items.Hider_ON.displayname").replaceAll("&","§")).setLocalizedName("lobbyx.player_hider").build());
        }
        if(plugin.getConfig().getBoolean("Items.Teleporter.enabled"))
            player.getInventory().setItem(plugin.getConfig().getInt("Items.Teleporter.slot"),new ItemBuilder(plugin.getMaterial("Items.Teleporter.material")).setDisplayname(plugin.getConfig().getString("Items.Teleporter.displayname").replaceAll("&","§")).build());
        if(plugin.getConfig().getBoolean("Items.Selector.enabled"))
            player.getInventory().setItem(plugin.getConfig().getInt("Items.Selector.slot"),new ItemBuilder(plugin.getMaterial("Items.Selector.material")).setDisplayname(plugin.getConfig().getString("Items.Selector.displayname").replaceAll("&","§")).build());
    }

    private void checkConfig(Player player){
        if(plugin.yaml.getString("Settings."+player.getUniqueId()+".FriendSort") == null){
            plugin.yaml.set("Settings."+player.getUniqueId()+".FriendSort","All");
            plugin.saveData();
        }
    }

}
