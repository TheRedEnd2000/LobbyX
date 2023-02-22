package de.theredend2000.lobbyx.messages;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Util {

    private static Main plugin;

    private static Map<Player, File> localSettings;
    private static Map<File, Map<String, String>> messages;

    public Util(Main plugin){
        Util.plugin = plugin;
        messages = new HashMap<>();
        localSettings = new HashMap<>();
    }

    public static String getMessage(String locale, String messName){
        File file = new File(plugin.getDataFolder()+ "/locales", locale+".yml");
        return messages.get(file).get(messName);
    }

    public static String getMessage(File file, String messName){
        return messages.get(file).get(messName);
    }

    public static File getLocale(Player p){
        return localSettings.get(p);
    }
    public static void setLocale(Player p, File file){
        localSettings.remove(p);
        if(!file.exists()){
            File locale = new File(plugin.getDataFolder()+ "/locales","en.yml");
            localSettings.put(p,locale);
        }else{
            localSettings.put(p, file);
        }
    }

    public static void removePlayer(Player p){
        localSettings.remove(p);
    }

    public static void loadMessages(){
        File langFolder = new File(plugin.getDataFolder()+ "/locales");
        if(!langFolder.exists()){
            langFolder.mkdirs();
        }
        File enFile = new File(langFolder,"en.yml");
        try {
            if(!enFile.exists()){
                InputStream in = plugin.getResource("en.yml");
                assert in != null;
                Files.copy(in,enFile.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        File deFile = new File(langFolder,"de.yml");
        try {
            if(!deFile.exists()){
                InputStream in = plugin.getResource("de.yml");
                assert in != null;
                Files.copy(in,deFile.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (File file : Objects.requireNonNull(langFolder.listFiles())){
            Map<String, String> localesMessages = new HashMap<>();
            FileConfiguration lang = YamlConfiguration.loadConfiguration(file);
            for(String key : lang.getKeys(false)){
                for(String messName : lang.getConfigurationSection(key).getKeys(false)){
                    String message = Objects.requireNonNull(plugin.getConfig().getString("Prefix")).replaceAll("&","§")+ChatColor.translateAlternateColorCodes('&' , Objects.requireNonNull(lang.getString(key + "." + messName)));
                    localesMessages.put(messName, message);
                }
            }
            messages.put(file,localesMessages);
            System.out.println(file.getName()+ " loaded");
        }
        for(Player player : Bukkit.getOnlinePlayers()){
            UUID uuid = player.getUniqueId();
            if(plugin.yaml.get("Languages."+uuid) == null){
                Util.setLocale(player, new File(plugin.getDataFolder()+"/locales", "en.yml"));
                plugin.yaml.set("Languages."+uuid,"en");
                plugin.saveData();
                return;
            }
            String localeFileName = plugin.getConfig().getString("Languages."+uuid);
            File langFile = new File(plugin.getDataFolder()+"/locales",localeFileName+".yml");
            Util.setLocale(player, langFile);
        }

    }


}
