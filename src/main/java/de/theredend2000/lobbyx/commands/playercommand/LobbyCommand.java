package de.theredend2000.lobbyx.commands.playercommand;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {

    private Main plugin;

    public LobbyCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin,"Locations.Lobby");
                if(locationUtil.loadLocation() != null){
                    player.teleport(locationUtil.loadLocation());
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"TeleportToLobby"));
                }else
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"LobbyInNotSet"));
            }else
                player.sendMessage(Util.getMessage(Util.getLocale(player),"LobbyCommandUsage"));
        }else
            sender.sendMessage(Util.getMessage("en","OnlyPlayerUse"));
        return false;
    }
}
