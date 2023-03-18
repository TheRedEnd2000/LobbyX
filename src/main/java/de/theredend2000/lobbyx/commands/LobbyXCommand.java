package de.theredend2000.lobbyx.commands;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.tutorial.Tutorial;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyXCommand implements CommandExecutor, TabCompleter {

    private Main plugin;

    public LobbyXCommand(Main plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 1) {
                if (args[0].equalsIgnoreCase("tutorial")) {
                    Tutorial tutorial = new Tutorial(plugin);
                    tutorial.start(player);
                    return true;
                }else if(args[0].equalsIgnoreCase("help")){

                }else if(args[0].equalsIgnoreCase("manage")) {
                    String permission = plugin.getConfig().getString("Permissions.LobbyXCommandPermission");
                    assert permission != null;
                    if (player.hasPermission(permission)) {
                        plugin.getLobbyXMenuManager().createMainInventory(player);
                    } else
                        player.sendMessage(Util.getMessage(Util.getLocale(player), "NoPermissionMessage").replaceAll("%PERMISSION%", permission));
                }else if(args[0].equalsIgnoreCase("information")){
                    player.sendMessage("§3-------------------------------");
                    player.sendMessage("§6§lPlugin Information");
                    player.sendMessage("§7Plugin name: §5"+plugin.getDescription().getName());
                    player.sendMessage("§7Plugin version: §a"+plugin.getDescription().getVersion());
                    player.sendMessage("§7Plugin author: §aXMC-Plugins");
                    player.sendMessage("§7Plugin api version: §a"+plugin.getDescription().getAPIVersion());
                    player.sendMessage("§3-------------------------------");
                }else if(args[0].equalsIgnoreCase("reload")){
                    new BukkitRunnable() {
                        int count = 0;
                        @Override
                        public void run() {
                            if(count == 10){
                                plugin.reloadConfig();
                                player.sendMessage("§aConfig reloaded...");
                            }
                            if(count == 20){
                                plugin.reloadData();
                                player.sendMessage("§aDatabase reloaded...");
                            }
                            if(count == 30){
                                plugin.reloadGadgets();
                                player.sendMessage("§aGadgets reloaded...");
                            }
                            if(count == 40){
                                plugin.reloadNavigator();
                                player.sendMessage("§aNavigator reloaded...");
                                cancel();
                            }
                            count ++;
                        }
                    }.runTaskTimer(plugin,0,2L);
                }
            }
        }else
            sender.sendMessage(Util.getMessage("en","OnlyPlayerUse"));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1){
            String[] completes = {"help","manage","information","reload"};
            ArrayList<String> tabComplete = new ArrayList<>();
            Collections.addAll(tabComplete, completes);
            return tabComplete;
        }
        return null;
    }
}
