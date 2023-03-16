package de.theredend2000.lobbyx.util;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class Updater implements Listener {

    private Main plugin;
    private int key = 107189;

    public Updater(Main plugin){
        this.plugin = plugin;
        isOutdated();
    }

    public boolean isOutdated(Player player) {
        try {
            HttpURLConnection c = (HttpURLConnection)new URL("https://api.spigotmc.org/legacy/update.php?resource="+key).openConnection();
            String newVersion = new BufferedReader(new InputStreamReader(c.getInputStream())).readLine();
            c.disconnect();
            String oldVersion = plugin.getDescription().getVersion();
            if(!newVersion.equals(oldVersion)) {
                player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("Prefix")).replaceAll("&","§")+"§cYou do not have the most updated version of §b§lLobby§4§lX§c.");
                player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("Prefix")).replaceAll("&","§")+"§cPlease chance the version: §4"+oldVersion+"§6 --> §2§l"+newVersion);
                return true;
            }
        }
        catch(Exception e) {
            player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("Prefix")).replaceAll("&","§")+"§4§lERROR: §cCould not make connection to SpigotMC.org");
            e.printStackTrace();
        }
        return false;
    }
    public boolean isOutdated() {
        try {
            HttpURLConnection c = (HttpURLConnection)new URL("https://api.spigotmc.org/legacy/update.php?resource="+key).openConnection();
            String newVersion = new BufferedReader(new InputStreamReader(c.getInputStream())).readLine();
            c.disconnect();
            String oldVersion = plugin.getDescription().getVersion();
            if(!newVersion.equals(oldVersion)) {
                Bukkit.getConsoleSender().sendMessage(Objects.requireNonNull(plugin.getConfig().getString("Prefix")).replaceAll("&","§")+"§cYou do not have the most updated version of §b§lLobby§4§lX§c.");
                Bukkit.getConsoleSender().sendMessage(Objects.requireNonNull(plugin.getConfig().getString("Prefix")).replaceAll("&","§")+"§cPlease chance the version: §4"+oldVersion+"§6 --> §2§l"+newVersion);
                return true;
            }
        }
        catch(Exception e) {
            Bukkit.getConsoleSender().sendMessage(Objects.requireNonNull(plugin.getConfig().getString("Prefix")).replaceAll("&","§")+"§4§lERROR: §cCould not make connection to SpigotMC.org");
            e.printStackTrace();
        }
        return false;
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.sendMessage("hi");
        boolean updates = plugin.getConfig().getBoolean("Settings.CheckForUpdates");
        if(updates){
            if(!player.isOp()) return;
            if(isOutdated(player)) return;
        }
    }

}
