package de.theredend2000.lobbyx.commands;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SetLangCommand implements CommandExecutor, TabCompleter {

    private Main plugin;

    public SetLangCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 1){
                String fileName = args[0];
                if(fileName.equalsIgnoreCase("english") || fileName.equalsIgnoreCase("en")){
                    File file = new File(plugin.getDataFolder()+"/locales","en.yml");
                    Util.setLocale(player, file);
                    player.sendMessage(Util.getMessage(Util.getLocale(player), "LangSelectEnglish"));
                }else if(fileName.equalsIgnoreCase("german") || fileName.equalsIgnoreCase("de")){
                    File file = new File(plugin.getDataFolder()+"/locales","de.yml");
                    Util.setLocale(player, file);
                    player.sendMessage(Util.getMessage(Util.getLocale(player), "LangSelectEnglish"));
                }else if(fileName.equalsIgnoreCase("spain") || fileName.equalsIgnoreCase("sp")){
                    player.sendMessage("§cThis language has not yet been translated. Do you speak spanish? Then help us to translate this. Join the discord for more info. Discord: https://discord.gg/7x2fzYKucZ");
                    return true;
                    /*File file = new File(plugin.getDataFolder()+"/locales","sp.yml");
                    Util.setLocale(player, file);
                    player.sendMessage(Util.getMessage(Util.getLocale(player), "LangSelectEnglish"));*/
                }else
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"LanguagesNotFound"));
            }else
                player.sendMessage(Util.getMessage(Util.getLocale(player),"LanguagesUsage"));
        }else
            sender.sendMessage(Util.getMessage("en","OnlyPlayerUse"));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if(args.length == 0){
            player.sendMessage(Util.getMessage(Util.getLocale(player), "FriendCommandUsage"));
        }
        if(args.length == 1){
            String[] completes = {"en","de","sp","GERMAN","ENGLISH","SPAIN"};
            ArrayList<String> completeList = new ArrayList<>();
            Collections.addAll(completeList, completes);
            return completeList;
        }
        return null;
    }
}
