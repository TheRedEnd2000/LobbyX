package de.theredend2000.lobbyx.jumpandrun;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class JnrCommand implements CommandExecutor {

    private Main plugin;

    public JnrCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if (JumpAndRun.getJumpAndRuns().containsKey(player.getUniqueId())) {
                player.sendMessage(Util.getMessage(Util.getLocale(player), "AlreadyStarted"));
                return true;
            }
            if(args[0].equalsIgnoreCase("lb")){
                new Leaderboard(plugin,player.getLocation());
                return true;
            }
            if (plugin.getLobbyWorlds().contains(player.getWorld())) {
                JumpAndRun jumpAndRun = new JumpAndRun(player, plugin);
                jumpAndRun.start(player);
                JumpAndRun.getJumpAndRuns().put(player.getUniqueId(), jumpAndRun);
            }
        }else
            sender.sendMessage(Util.getMessage("en","OnlyPlayerUse"));
        return false;
    }
}
