package de.theredend2000.lobbyx.commands;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
                    plugin.getSetPlayerLobbyManager().setItems(player);
                    player.setGameMode(GameMode.SURVIVAL);
                }else{
                    plugin.getBuildPlayers().add(player);
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"BuildJoined"));
                    player.getInventory().clear();
                    player.setGameMode(GameMode.CREATIVE);
                }
            }else
                player.sendMessage(Util.getMessage(Util.getLocale(player),"NoPermissionMessage").replaceAll("%PERMISSION%",permission));
        }else
            sender.sendMessage(Util.getMessage("en","OnlyPlayerUse"));
        return false;
    }
}
