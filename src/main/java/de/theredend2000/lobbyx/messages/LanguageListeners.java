package de.theredend2000.lobbyx.messages;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.UUID;

public class LanguageListeners implements Listener {

    private Main plugin;

    public LanguageListeners(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if(plugin.yaml.get(uuid.toString()) == null){
            Util.setLocale(player, new File(plugin.getDataFolder()+"/locales", "en.yml"));
            plugin.yaml.set(uuid.toString(),"en");
            plugin.saveData();
            return;
        }
        String localeFileName = plugin.getConfig().getString(uuid.toString());
        File langFile = new File(plugin.getDataFolder()+"/locales",localeFileName+".yml");
        Util.setLocale(player, langFile);
        plugin.getSetPlayerLobbyManager().setPlayerInLobby(player);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Util.removePlayer(event.getPlayer());
    }

}
