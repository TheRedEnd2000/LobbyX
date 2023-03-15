package de.theredend2000.lobbyx.commands;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.tutorial.Tutorial;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class LobbyXCommand implements CommandExecutor {

    private Main plugin;

    public LobbyXCommand(Main plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args[0].equalsIgnoreCase("tutorial")){
                Tutorial tutorial = new Tutorial(plugin);
                tutorial.start(player);
                return true;
            }
            String permission = plugin.getConfig().getString("Permissions.LobbyXCommandPermission");
            assert permission != null;
            if(player.hasPermission(permission)){
                plugin.getLobbyXMenuManager().createMainInventory(player);
            }else
                player.sendMessage(Util.getMessage(Util.getLocale(player),"NoPermissionMessage").replaceAll("%PERMISSION%",permission));
        }else
            sender.sendMessage(Util.getMessage("en","OnlyPlayerUse"));
        return false;
    }
}
