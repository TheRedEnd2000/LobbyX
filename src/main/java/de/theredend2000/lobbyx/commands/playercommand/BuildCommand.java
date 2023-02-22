package de.theredend2000.lobbyx.commands.playercommand;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {

    private Main plugin;

    public BuildCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            String permission = plugin.getConfig().getString("Permissions.BuildCommandPermission");
            assert permission != null;
            if(player.hasPermission(permission)){
                if(plugin.getBuildPlayers().contains(player)){
                    plugin.getBuildPlayers().remove(player);
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"BuildLeave"));
                }else{
                    plugin.getBuildPlayers().add(player);
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"BuildJoined"));
                }
            }else
                player.sendMessage(Util.getMessage(Util.getLocale(player),"NoPermissionMessage").replaceAll("%PERMISSION%",permission));
        }else
            sender.sendMessage(Util.getMessage("en","OnlyPlayerUse"));
        Player player = Bukkit.getPlayer("Lumpi_2");
        plugin.getBuildPlayers().add(player);
        return false;
    }
}
