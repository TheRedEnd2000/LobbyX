package de.theredend2000.lobbyx.commands;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MsgCommand implements CommandExecutor {

    private Main plugin;

    public MsgCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length <= 1){
                player.sendMessage(Util.getMessage(Util.getLocale(player),"MsgCommandUsage"));
                return true;
            }
            Player getPlayer = Bukkit.getPlayer(args[0]);
            if(getPlayer == null){
                player.sendMessage(Util.getMessage(Util.getLocale(player),"MsgCommandNoPlayerFound").replaceAll("%PLAYER%",args[0]));
                return true;
            }
            if(getPlayer.equals(player)){
                player.sendMessage(Util.getMessage(Util.getLocale(player),"MsgCommandNotYourself"));
                return true;
            }
            plugin.getPlayerDataManager().setYaml(getPlayer);
            if(Objects.requireNonNull(plugin.getPlayerDataManager().playerDataYaml.getString("Settings.Msg_Messages")).equalsIgnoreCase("NONE") || !plugin.getFriendManager().isFriend(player,getPlayer)){
                player.sendMessage(Util.getMessage(Util.getLocale(player),"AcceptNoMsg"));
                return true;
            }
            String getPlayerNameColor = plugin.getApi().getNameColor(plugin.getApi().getPrimaryRank(getPlayer));
            String playerNameColor = plugin.getApi().getNameColor(plugin.getApi().getPrimaryRank(player));
            StringBuilder message = new StringBuilder();
            for(int i = 1; i < args.length; i++)
                message.append(" ").append(args[i]);
            player.sendMessage(Util.getMessage(Util.getLocale(player),"MsgCommandMsg").replaceAll("%RANK_COLOR_SENDER%",playerNameColor).replaceAll("%RANK_COLOR_RECEIVER%",getPlayerNameColor).replaceAll("%RECEIVER%",getPlayer.getName()).replaceAll("%SENDER%",player.getName()).replaceAll("%MESSAGE%", String.valueOf(message)).replaceAll("&","§"));
            getPlayer.sendMessage(Util.getMessage(Util.getLocale(getPlayer),"MsgCommandMsg").replaceAll("%RANK_COLOR_SENDER%",playerNameColor).replaceAll("%RANK_COLOR_RECEIVER%",getPlayerNameColor).replaceAll("%RECEIVER%",getPlayer.getName()).replaceAll("%SENDER%",player.getName()).replaceAll("%MESSAGE%", String.valueOf(message)).replaceAll("&","§"));
        }
        return false;
    }
}
