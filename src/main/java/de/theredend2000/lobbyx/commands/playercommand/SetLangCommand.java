package de.theredend2000.lobbyx.commands.playercommand;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class SetLangCommand implements CommandExecutor {

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
                    player.sendMessage("In Arbeit");
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
}
