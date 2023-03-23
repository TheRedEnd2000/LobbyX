package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ColorUtils;
import de.theredend2000.lobbyx.util.DatetimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class ScoreboardManager {

    private Main plugin;
    public ScoreboardManager(Main plugin){
        this.plugin = plugin;
        updateScoreboardForAll();
    }

    private void updateScoreboardForAll(){
        int delay = plugin.getConfig().getInt("Scoreboard.UpdateDelay");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(plugin.getLobbyWorlds().contains(player.getWorld())) {
                        updateScoreboard(player);
                    }
                }
            }
        },0,delay);
    }

    private void updateScoreboard(Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("hub","hub");
        String title = plugin.getConfig().getString("Scoreboard.Title").replaceAll("&","§");
        objective.setDisplayName(title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        int onlineFriends = plugin.getFriendManager().getOnlineFriends(player);
        List<String> lines = plugin.getConfig().getStringList("Scoreboard.Lines");
        for(int i = lines.size()-1; i >= 0; i--){
            if(!lines.isEmpty()) {
                String currentLine = lines.get(0);
                String rank = plugin.getApi().getPrimaryRank(player);
                String nameColor = plugin.getApi().getNameColor(rank);
                objective.getScore(currentLine.replaceAll("%NAME_COLOR%",nameColor).replaceAll("%PLAYER%", player.getName()).replaceAll("%COINS%", String.valueOf(plugin.getCoinManager().getCoins(player))).replaceAll("%DATE%", plugin.getDatetimeUtils().getNowDate()).replaceAll("%TIME%", plugin.getDatetimeUtils().getNowTime()).replaceAll("%RANK%", rank).replaceAll("%ONLINE_FRIENDS%", String.valueOf(onlineFriends)).replaceAll("%CLAN%", getClan(player)).replaceAll("&", "§")).setScore(i);
                lines.remove(currentLine);
            }
        }
        player.setScoreboard(scoreboard);
    }

    private String getClan(Player player){
        if(plugin.yaml.contains("Clans")) {
            if (plugin.getClanManager().hasClan(player)) {
                return plugin.getClanManager().getClanName(player);
            }
            if (plugin.getClanManager().isInClan(player)) {
                return plugin.getClanManager().getClanNameAsMember(player);
            }
        }
        return "§cNo Clan";
    }

}
