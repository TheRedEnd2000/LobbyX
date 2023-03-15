package de.theredend2000.lobbyx.tutorial;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Tutorial {

    private Main plugin;
    private boolean isRunning;
    private int count;
    protected int taskID;

    public Tutorial(Main plugin){
        this.plugin = plugin;
    }


    public void start(Player player){
        if(isRunning){
            return;
        }
        isRunning = true;
        count = 0;
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                checkActions(player,count);
                count ++;
            }
        },0,20);
    }
    public void stop(Player player, boolean canceled){
        if(isRunning){
            Bukkit.getScheduler().cancelTask(taskID);
            isRunning = false;
            count = 0;
            if(canceled)
                player.sendMessage("§4§lTutorial canceled!");
            else
                player.sendMessage("§2§lTutorial finished!");
            plugin.getSetPlayerLobbyManager().setPlayerInLobby(player);
        }
    }

    private void checkActions(Player player,int count){
        if(count == 0){
            player.sendMessage("§6§lLobbyX Tutorial.\n§aPlease wait a moment.\n§7Type '§ccancel§7' to cancel the tutorial.");
        }
        if(count == 10){
            player.sendMessage("");
            player.sendMessage("§2§lHello "+player.getName());
            player.sendMessage("");
            player.sendMessage("§7§lYou will get a short explanation of the plugin.\n So take a moment to learn everything about the plugin.");
        }
        if(count == 20){
            player.sendMessage("");
            player.sendMessage("§7§lBefore we start, you should first choose your language to make the tutorial easy for you.");
        }
        if(count == 30){
            player.sendMessage("§2Select your language.");
            TextComponent c = new TextComponent("§9§lClick to language you want. §c(You can change it later)\n");
            TextComponent clickme = new TextComponent("§6§l[German] ");
            TextComponent clickme2 = new TextComponent("§3§l[English] ");
            TextComponent clickme3 = new TextComponent("§e§l[Spain]");

            clickme.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/setlang de"));
            clickme.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§aSelect German.")));
            c.addExtra(clickme);
            clickme2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/setlang en"));
            clickme2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§aSelect English.")));
            c.addExtra(clickme2);
            clickme3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/setlang sp"));
            clickme3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§aSelect Spain.")));
            c.addExtra(clickme3);
            player.spigot().sendMessage(c);
        }
        if(count == 40){
            player.sendMessage("");
            player.sendMessage(Util.getMessage(Util.getLocale(player),"TT-Section1"));
        }
        if(count == 50){
            player.sendMessage("");
            player.sendMessage(Util.getMessage(Util.getLocale(player),"TT-Section2"));
        }
        if(count == 60){
            player.sendMessage("");
            player.sendMessage(Util.getMessage(Util.getLocale(player),"TT-Section3"));
        }
        if(count == 70){
            player.sendMessage("");
            player.sendMessage(Util.getMessage(Util.getLocale(player),"TT-Section4"));
        }
        if(count == 80){
            player.sendMessage("");
            player.sendMessage(Util.getMessage(Util.getLocale(player),"TT-Section5"));
        }
        if(count == 85){
            stop(player,false);
        }
    }

}
