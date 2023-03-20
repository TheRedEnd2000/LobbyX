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
                    helpMessages(player);
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

    public void helpMessages(Player player){
        player.sendMessage("");
        player.sendMessage("§b-=-=-=-=-=-=-§6§lHELP§b-=-=-=-=-=-=-");
        player.sendMessage("");
        player.sendMessage("§5------§2§lCommands§5------");
        player.sendMessage("§e§l/lobbyx §f§l- §bConfigure the hole plugin or get information of it. §4[ADMIN]");
        player.sendMessage("§e§l/build §f§l- §bSets you in creative and gives you the permission to edit the lobbys. §4[ADMIN]");
        player.sendMessage("§e§l/coins §f§l- §bShows your coins or manage the coins of others. §2[EVERYONE] §6§l+ §4[ADMIN]");
        player.sendMessage("§e§l/daily §f§l- §bClaim your daily coins to get as rich as possible. §2[EVERYONE]");
        player.sendMessage("§e§l/clan §f§l- §bCreate a clan and invite friends. Manage everything or play with the settings. §2[EVERYONE]");
        player.sendMessage("§e§l/friend §f§l- §bAdd friends to your friendship an manage it. §2[EVERYONE]");
        player.sendMessage("§e§l/lobby §f§l- §bTeleports you to the lobby. §2[EVERYONE]");
        player.sendMessage("§e§l/msg §f§l- §bText other players. §2[EVERYONE]");
        player.sendMessage("§e§l/language §f§l- §bChange your language to English, Deutsch or Español. §2[EVERYONE]");
        player.sendMessage("");
        player.sendMessage("§5------§2§lPermissions§5------");
        player.sendMessage("§e§l"+plugin.getConfig().getString("Permissions.LobbyXCommandPermission")+" §f§l- §bPermission for the /lobbyx command.");
        player.sendMessage("§e§l"+plugin.getConfig().getString("Permissions.BuildCommandPermission")+" §f§l- §bPermission for the /build command.");
        player.sendMessage("§e§l"+plugin.getConfig().getString("Permissions.CreateNewLobbyWorld")+" §f§l- §bPermission for the world creator in the lobby selector.");
        player.sendMessage("§e§l"+plugin.getConfig().getString("Permissions.CoinsCommandPermission")+" §f§l- §bPermission for the /coins command to change coins.");
        player.sendMessage("§e§l"+plugin.getConfig().getString("Permissions.MaintenanceModsPermission")+" §f§l- §bPermission for the world edit menu in the lobby selector.");
        player.sendMessage("§e§l"+plugin.getConfig().getString("Permissions.GamemodeCommandPermission")+" §f§l- §bPermission for the /gamemode command.");
        player.sendMessage("");
        player.sendMessage("§b-=-=-=-=-=-=-§6§lHELP§b-=-=-=-=-=-=-");
    }
}
