package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.commands.FriendCommand;
import de.theredend2000.lobbyx.messages.Util;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FriendManager {

    private Main plugin;

    public FriendManager(Main plugin){
        this.plugin = plugin;
    }

    public int getOnlineFriends(Player player){
        ArrayList<Player> ofriends = new ArrayList<>();
        ofriends.clear();
        if(plugin.yaml.contains("Friends")) {
            for (String friends : plugin.yaml.getConfigurationSection("Friends." + player.getUniqueId() + ".").getKeys(false)) {
                Player ofriend = Bukkit.getPlayer(friends);
                if (ofriend != null)
                    ofriends.add(player);
            }
        }
        return ofriends.size();
    }

    public boolean isFriend(Player player, Player friend){
        return plugin.yaml.contains("Friends." + player.getUniqueId() + "." + friend.getName());
    }
    public boolean isFriend(Player player, String friend){
        return plugin.yaml.contains("Friends." + player.getUniqueId() + "." + friend);
    }
    public boolean acceptFriendRequests(Player player){
        plugin.getPlayerDataManager().setYaml(player);
        return plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.FriendRequests");
    }

}
