package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.jumpandrun.JumpAndRun;
import de.theredend2000.lobbyx.messages.Util;
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
                lobbyPlayers.sendMessage(Util.getMessage(Util.getLocale(lobbyPlayers),"JoinMessage").replaceAll("%PLAYER%",player.getName()));
            }
            player.sendMessage(Util.getMessage(Util.getLocale(player),"JoinMessage").replaceAll("%PLAYER%",player.getName()));
            if(plugin.getConfig().getBoolean("Titles.WelcomeTitle.enabled"))
                player.sendTitle(plugin.getConfig().getString("Titles.WelcomeTitle.title").replaceAll("&","§").replaceAll("%PLAYER%",player.getName()),plugin.getConfig().getString("Titles.WelcomeTitle.subtitle").replaceAll("&","§").replaceAll("%PLAYER%",player.getName()),20,100,40);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            event.setQuitMessage(null);
            for(Player lobbyPlayers : player.getWorld().getPlayers()){
                if(!lobbyPlayers.equals(player)) {
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
