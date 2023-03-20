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
        for(String friends : plugin.yaml.getConfigurationSection("Friends."+player.getUniqueId()+".").getKeys(false)){
            Player ofriend = Bukkit.getPlayer(friends);
            if(ofriend != null)
                ofriends.add(player);
        }
        return ofriends.size();
    }

    public boolean isFriend(Player player, Player friend){
        return plugin.yaml.contains("Friends." + player.getUniqueId() + "." + friend.getName());
    }
    public boolean acceptFriendRequests(Player player){
        plugin.getPlayerDataManager().setYaml(player);
        return plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.FriendRequests");
    }

    public void addFriend(Player player, Player friendtoadd){
        FriendCommand friendCommand = new FriendCommand(plugin);
        if(plugin.getFriendManager().acceptFriendRequests(friendtoadd)) {
            if (plugin.yaml.getString("Friends." + player.getUniqueId()) != null) {
                for (String friends : plugin.yaml.getConfigurationSection("Friends." + player.getUniqueId()).getKeys(false)) {
                    if (friends.equalsIgnoreCase(friendtoadd.getName())) {
                        player.sendMessage(Util.getMessage(Util.getLocale(player), "FriendAlreadyAdded").replaceAll("%PLAYER_REQUEST_RECEIVER%", friendtoadd.getName()));
                        return;
                    }
                }
            }

            player.sendMessage(Util.getMessage(Util.getLocale(player), "FriendRequestSender").replaceAll("%FRIEND_REQUEST_RECEIVER%", friendtoadd.getName()).replaceAll("%FRIEND_REQUEST_SENDER%", player.getName()));
            friendtoadd.sendMessage("§4§l-=-=-=-=-=-=-=-=-=-=-=-=-");
            friendtoadd.sendMessage(Util.getMessage(Util.getLocale(friendtoadd), "FriendRequestReceiver").replaceAll("%FRIEND_REQUEST_RECEIVER%", friendtoadd.getName()).replaceAll("%FRIEND_REQUEST_SENDER%", player.getName()));
            TextComponent c = new TextComponent("");
            TextComponent clickme = new TextComponent("§7|----------- §a§l[Accept] §5§l----- ");
            TextComponent clickme2 = new TextComponent("§c§l[Deny] §7-----------|");

            clickme.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend accept " + player.getName()));
            clickme.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§aAccept friend request.")));
            c.addExtra(clickme);
            clickme2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend deny " + player.getName()));
            clickme2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§cDeny friend request.")));
            c.addExtra(clickme2);
            friendtoadd.spigot().sendMessage(c);
            friendtoadd.sendMessage("§4§l-=-=-=-=-=-=-=-=-=-=-=-=-");
            friendCommand.getFriendRequest().put(player, friendtoadd);
            friendCommand.getFriendRequestTime().put(player, 300);
        }else
            player.sendMessage(Util.getMessage(Util.getLocale(player),"AcceptNoFriendRequests"));
    }

}
