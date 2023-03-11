package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
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
        for(String friends : plugin.yaml.getConfigurationSection("Friends."+player.getUniqueId()+".").getKeys(false)){
            Player ofriend = Bukkit.getPlayer(friends);
            if(ofriend != null)
                ofriends.add(player);
        }
        return ofriends.size();
    }

}
