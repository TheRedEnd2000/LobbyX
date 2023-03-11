package de.theredend2000.lobbyx.commands.playercommand;

import de.theredend2000.lobbyx.hologramms.Hologram;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class hologramCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("s")){
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("add")){
                        String name = args[1];
                        Hologram hologram = new Hologram(name, player.getLocation());
                        player.sendMessage("§aConfirm");
                    }
                }
            }
        }
        return false;
    }
}
