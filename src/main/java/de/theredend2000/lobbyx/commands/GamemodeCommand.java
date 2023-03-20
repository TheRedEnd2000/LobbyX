package de.theredend2000.lobbyx.commands;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GamemodeCommand implements CommandExecutor, TabCompleter {

    private Main plugin;

    public GamemodeCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            String permission = plugin.getConfig().getString("Permissions.GamemodeCommandPermission");
            assert permission != null;
            if(player.hasPermission(permission)){
                if(args.length == 1){
                    String gameMode = args[0];
                    if(gameMode.equalsIgnoreCase("s") || gameMode.equalsIgnoreCase("survival") || gameMode.equalsIgnoreCase("0")){
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NewGamemodePersonal").replaceAll("%GAMEMODE%", player.getGameMode().name()));
                    }else if(gameMode.equalsIgnoreCase("c") || gameMode.equalsIgnoreCase("creative") || gameMode.equalsIgnoreCase("1")){
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NewGamemodePersonal").replaceAll("%GAMEMODE%", player.getGameMode().name()));
                    }else if(gameMode.equalsIgnoreCase("a") || gameMode.equalsIgnoreCase("adventure") || gameMode.equalsIgnoreCase("2")){
                        player.setGameMode(GameMode.ADVENTURE);
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NewGamemodePersonal").replaceAll("%GAMEMODE%", player.getGameMode().name()));
                    }else if(gameMode.equalsIgnoreCase("sp") || gameMode.equalsIgnoreCase("spectator") || gameMode.equalsIgnoreCase("3")){
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NewGamemodePersonal").replaceAll("%GAMEMODE%", player.getGameMode().name()));
                    }else
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"GamemodeUsage"));
                }else if(args.length == 2){
                    Player getPlayer = Bukkit.getPlayer(args[1]);
                    if(getPlayer == null){
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"GamemodePlayerNotFound").replaceAll("%PLAYER%", args[1]));
                        return true;
                    }
                    String gameMode = args[0];
                    if(gameMode.equalsIgnoreCase("s") || gameMode.equalsIgnoreCase("survival") || gameMode.equalsIgnoreCase("0")){
                        getPlayer.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NewGamemodeOther").replaceAll("%GAMEMODE%", player.getGameMode().name()).replaceAll("%PLAYER%",getPlayer.getName()));
                    }else if(gameMode.equalsIgnoreCase("c") || gameMode.equalsIgnoreCase("creative") || gameMode.equalsIgnoreCase("1")){
                        getPlayer.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NewGamemodeOther").replaceAll("%GAMEMODE%", player.getGameMode().name()).replaceAll("%PLAYER%",getPlayer.getName()));
                    }else if(gameMode.equalsIgnoreCase("a") || gameMode.equalsIgnoreCase("adventure") || gameMode.equalsIgnoreCase("2")){
                        getPlayer.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NewGamemodeOther").replaceAll("%GAMEMODE%", player.getGameMode().name()).replaceAll("%PLAYER%",getPlayer.getName()));
                    }else if(gameMode.equalsIgnoreCase("sp") || gameMode.equalsIgnoreCase("spectator") || gameMode.equalsIgnoreCase("3")){
                        getPlayer.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NewGamemodeOther").replaceAll("%GAMEMODE%", player.getGameMode().name()).replaceAll("%PLAYER%",getPlayer.getName()));
                    }else
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"GamemodeUsage"));
                }else
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"GamemodeUsage"));
            }else
                player.sendMessage(Util.getMessage(Util.getLocale(player),"NoPermissionMessage").replaceAll("%PERMISSION%",permission));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        String permission = plugin.getConfig().getString("Permissions.GamemodeCommandPermission");
        assert permission != null;
        if(args.length == 1 && sender.hasPermission(permission)){
            ArrayList<String > tabComplete = new ArrayList<>();
            String[] completes = {"Survival","Creative","Adventure","Spectator"};
            Collections.addAll(tabComplete,completes);
            return tabComplete;
        }
        return null;
    }
}
