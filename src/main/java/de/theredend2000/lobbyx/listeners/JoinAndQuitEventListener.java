package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.jumpandrun.JumpAndRun;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.othergadgets.RainbowArmor;
import de.theredend2000.lobbyx.util.Updater;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;

public class JoinAndQuitEventListener implements Listener {

    private Main plugin;

    public JoinAndQuitEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        plugin.getPlayerDataManager().checkPlayerData(player);
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            plugin.getSetPlayerLobbyManager().setPlayerInLobby(player);
            event.setJoinMessage(null);
            for(Player lobbyPlayers : player.getWorld().getPlayers()){
                plugin.getPlayerDataManager().setYaml(lobbyPlayers);
                if(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Join_Messages"))
                    lobbyPlayers.sendMessage(Util.getMessage(Util.getLocale(lobbyPlayers),"JoinMessage").replaceAll("%PLAYER%",player.getName()));
            }
            plugin.getPlayerDataManager().setYaml(player);
            if(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Join_Messages"))
                player.sendMessage(Util.getMessage(Util.getLocale(player),"JoinMessage").replaceAll("%PLAYER%",player.getName()));
            if(plugin.getConfig().getBoolean("Titles.WelcomeTitle.enabled"))
                player.sendTitle(plugin.getConfig().getString("Titles.WelcomeTitle.title").replaceAll("&","§").replaceAll("%PLAYER%",player.getName()),plugin.getConfig().getString("Titles.WelcomeTitle.subtitle").replaceAll("&","§").replaceAll("%PLAYER%",player.getName()),20,100,40);
            boolean updates = plugin.getConfig().getBoolean("Settings.CheckForUpdates");
            if(updates){
                if(!player.isOp()) return;
                Updater updater = new Updater(plugin);
                if(updater.isOutdated(player)) return;
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            event.setQuitMessage(null);
            for(Player lobbyPlayers : player.getWorld().getPlayers()){
                if(!lobbyPlayers.equals(player)) {
                    plugin.getPlayerDataManager().setYaml(lobbyPlayers);
                    if(plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.Leave_Messages"))
                        lobbyPlayers.sendMessage(Util.getMessage(Util.getLocale(lobbyPlayers), "QuitMessage").replaceAll("%PLAYER%",player.getName()));
                }
            }
        }
        if (JumpAndRun.getJumpAndRuns().containsKey(player.getUniqueId())) {
            JumpAndRun jumpAndRun = (JumpAndRun)JumpAndRun.getJumpAndRuns().get(player.getUniqueId());
            jumpAndRun.stop(player);
        }
    }

}
