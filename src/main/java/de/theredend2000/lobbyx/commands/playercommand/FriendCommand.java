package de.theredend2000.lobbyx.commands.playercommand;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FriendCommand implements CommandExecutor {

    private Main plugin;

    public FriendCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(plugin.getLobbyWorlds().contains(player.getWorld())){
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("add")){
                        Player firendtoadd = Bukkit.getPlayer(args[1]);
                        if(firendtoadd == null){
                            player.sendMessage("§cNot Found");
                            return true;
                        }
                        plugin.yaml.set("Friends."+player.getUniqueId(),firendtoadd.getName());
                        plugin.saveData();
                        player.sendMessage("Friend added "+firendtoadd.getName());
                    }
                }
            }
        }
        return false;
    }
}
