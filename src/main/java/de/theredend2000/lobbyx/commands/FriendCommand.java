package de.theredend2000.lobbyx.commands;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class FriendCommand implements CommandExecutor, TabCompleter {

    private Main plugin;
    private HashMap<Player, Player> friendRequest;
    private HashMap<Player, Integer> friendRequestTime;

    public FriendCommand(Main plugin){
        this.plugin = plugin;
        friendRequest = new HashMap<>();
        friendRequestTime = new HashMap<>();

        checkFriendRequestTimeOut();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(plugin.getLobbyWorlds().contains(player.getWorld())){
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("add")){
                        Player friendtoadd = Bukkit.getPlayer(args[1]);
                        if(friendtoadd == null){
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerNotFound"));
                            return true;
                        }
                        if(friendtoadd.equals(player)){
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"NotAddYourself"));
                            return true;
                        }
                        addFriend(player,friendtoadd);
                    }else if(args[0].equalsIgnoreCase("accept")){
                        Player friendAccept = Bukkit.getPlayer(args[1]);
                        if(friendAccept == null){
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerNotFound"));
                            return true;
                        }
                        if(friendRequest.containsKey(friendAccept) && friendRequest.containsValue(player)){
                            friendRequest.remove(friendAccept);
                            friendRequestTime.remove(friendAccept);
                            friendRequest.values().remove(player);
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "FriendRequestAcceptReceiver").replaceAll("%PLAYER_REQUEST_ACCEPT%", player.getName()).replaceAll("%PLAYER_REQUEST_SENDER%", friendAccept.getName()));
                            friendAccept.sendMessage(Util.getMessage(Util.getLocale(player), "FriendRequestAcceptSender").replaceAll("%PLAYER_REQUEST_ACCEPT%", player.getName()).replaceAll("%PLAYER_REQUEST_SENDER%", friendAccept.getName()));
                            new ConfigLocationUtil(plugin,"Friends."+friendAccept.getUniqueId()+"."+player.getName()+".").saveFriend(player);
                            new ConfigLocationUtil(plugin,"Friends."+player.getUniqueId()+"."+friendAccept.getName()+".").saveFriend(friendAccept);
                        }else
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "NotFriendRequest"));
                    }else if(args[0].equalsIgnoreCase("deny")){
                        Player friendDeny = Bukkit.getPlayer(args[1]);
                        if(friendDeny == null){
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerNotFound"));
                            return true;
                        }
                        if(friendRequest.containsKey(friendDeny) && friendRequest.containsValue(player)){
                            friendRequest.remove(friendDeny);
                            friendRequestTime.remove(friendDeny);
                            friendRequest.values().remove(player);
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "FriendRequestDenyReceiver").replaceAll("%PLAYER_REQUEST_ACCEPT%", player.getName()).replaceAll("%PLAYER_REQUEST_SENDER%", friendDeny.getName()));
                            friendDeny.sendMessage(Util.getMessage(Util.getLocale(player), "FriendRequestDenySender").replaceAll("%PLAYER_REQUEST_ACCEPT%", player.getName()).replaceAll("%PLAYER_REQUEST_SENDER%", friendDeny.getName()));
                        }else
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "NotFriendRequest"));
                    }else if(args[0].equalsIgnoreCase("remove")){
                        String friendtoremove = args[1];
                        if(plugin.yaml.getString("Friends."+player.getUniqueId()) != null) {
                            if (plugin.getFriendManager().isFriend(player,friendtoremove)) {
                                String friendtoremoveUUID = plugin.yaml.getString("Friends."+player.getUniqueId()+"."+friendtoremove+".UUID");
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"FriendRemoveSender").replaceAll("%PLAYER_REMOVED%",friendtoremove).replaceAll("%PLAYER_REMOVED_FRIEND%",player.getName()));
                                Player removed = Bukkit.getPlayer(friendtoremove);
                                if(removed != null)
                                    removed.sendMessage(Util.getMessage(Util.getLocale(removed),"FriendRemoveReceiver").replaceAll("%PLAYER_REMOVED%",friendtoremove).replaceAll("%PLAYER_REMOVED_FRIEND%",player.getName()));
                                plugin.yaml.set("Friends."+player.getUniqueId()+"."+friendtoremove, null);
                                plugin.yaml.set("Friends."+friendtoremoveUUID+"."+player.getName(), null);
                                plugin.saveData();
                                return true;
                            }else
                                player.sendMessage(Util.getMessage(Util.getLocale(player), "FriendRemoveNotFriend").replaceAll("%PLAYER_REMOVED%",friendtoremove).replaceAll("%PLAYER_REMOVED_FRIEND%",player.getName()));
                        }else
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "FriendRemoveNotFriend").replaceAll("%PLAYER_REMOVED%",friendtoremove).replaceAll("%PLAYER_REMOVED_FRIEND%",player.getName()));
                    }else
                        player.sendMessage(Util.getMessage(Util.getLocale(player), "FriendCommandUsage"));
                }else if(args.length == 1){
                    if(args[0].equalsIgnoreCase("list")){
                        plugin.getProfileMenuManager().createFriendInventory(player, false);
                    }else
                        player.sendMessage(Util.getMessage(Util.getLocale(player), "FriendCommandUsage"));
                }else
                    player.sendMessage(Util.getMessage(Util.getLocale(player), "FriendCommandUsage"));
            }
        }else
            sender.sendMessage(Util.getMessage("en","OnlyPlayerUse"));
        return false;
    }

    private void checkFriendRequestTimeOut(){
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(friendRequest.containsKey(player)){
                        if(friendRequestTime.get(player) == 0){
                            friendRequest.remove(player);
                            friendRequestTime.remove(player);
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "FriendRequestExpired"));
                            return;
                        }
                        int time = friendRequestTime.get(player);
                        time -=1;
                        friendRequestTime.remove(player);
                        friendRequestTime.put(player,time);
                    }
                }
            }.runTaskTimer(plugin,0,20);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length == 0){
            player.sendMessage(Util.getMessage(Util.getLocale(player), "FriendCommandUsage"));
        }
        if(args.length == 1){
            String[] completes = {"add","list","remove","accept","deny"};
            ArrayList<String> completeList = new ArrayList<>();
            Collections.addAll(completeList, completes);
            return completeList;
        }
        return null;
    }

    public void addFriend(Player player, Player friendtoadd){
        if(friendRequest.containsKey(player)){
            player.sendMessage(Util.getMessage(Util.getLocale(player), "AlreadySend").replaceAll("%FRIEND_REQUEST_RECEIVER%", friendtoadd.getName()).replaceAll("%FRIEND_REQUEST_SENDER%",player.getName()));
            return;
        }
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
            friendRequest.put(player, friendtoadd);
            friendRequestTime.put(player, 300);
        }else
            player.sendMessage(Util.getMessage(Util.getLocale(player),"AcceptNoFriendRequests"));
    }
}
