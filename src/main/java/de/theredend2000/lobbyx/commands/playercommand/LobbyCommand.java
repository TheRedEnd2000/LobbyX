package de.theredend2000.lobbyx.commands.playercommand;

import de.theredend2000.lobbyx.Main;
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
                    player.sendMessage("§8Sending you to the lobby...");
                }else
                    player.sendMessage("§cThe lobby isn't set yet. Please contact an admin.");
            }else
                player.sendMessage("§7Usage: §b/lobby");
        }else
            sender.sendMessage("§cOnly players can use this command.");
        return false;
    }
}
