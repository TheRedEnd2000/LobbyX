package de.theredend2000.lobbyx.commands;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DailyRewardCommand implements CommandExecutor, TabCompleter {

    private Main plugin;

    public DailyRewardCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Util.getMessage("en","OnlyPlayerUse"));
            return true;
        }
        Player player = (Player) sender;
        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("daily")){
                if(!plugin.getRewardManager().getAllowReward(player, "daily")) {
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyClaimed"));
                    long current = System.currentTimeMillis();
                    long release = plugin.getRewardManager().getTimeDaily(player);
                    long millis = release - current;
                    player.sendMessage(plugin.getRewardManager().getRemainingTime(millis,player));
                    return true;
                }
                player.sendMessage(Util.getMessage(Util.getLocale(player),"ClaimReward"));
                plugin.getRewardManager().setRewardDaily(player);
                int coins = plugin.getConfig().getInt("Settings.DailyRewardCoinsAmount");
                plugin.getCoinManager().addCoins(player,coins);
                player.sendMessage(Util.getMessage(Util.getLocale(player),"CoinsLayout").replaceAll("%COINS_AMOUNT%", String.valueOf(coins)).replaceAll("%COINS_BALANCE%", String.valueOf(plugin.getCoinManager().getCoins(player))));
            }else if(args[0].equalsIgnoreCase("weekly")){
                if(!plugin.getRewardManager().getAllowReward(player, "weekly")) {
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyClaimed"));
                    long current = System.currentTimeMillis();
                    long release = plugin.getRewardManager().getTimeWeekly(player);
                    long millis = release - current;
                    player.sendMessage(plugin.getRewardManager().getRemainingTime(millis,player));
                    return true;
                }
                player.sendMessage(Util.getMessage(Util.getLocale(player),"ClaimReward"));
                plugin.getRewardManager().setRewardWeekly(player);
                int coins = plugin.getConfig().getInt("Settings.WeeklyRewardCoinsAmount");
                plugin.getCoinManager().addCoins(player,coins);
                player.sendMessage(Util.getMessage(Util.getLocale(player),"CoinsLayout").replaceAll("%COINS_AMOUNT%", String.valueOf(coins)).replaceAll("%COINS_BALANCE%", String.valueOf(plugin.getCoinManager().getCoins(player))));
            }else if(args[0].equalsIgnoreCase("monthly")){
                if(!plugin.getRewardManager().getAllowReward(player, "monthly")) {
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyClaimed"));
                    long current = System.currentTimeMillis();
                    long release = plugin.getRewardManager().getTimeMonthly(player);
                    long millis = release - current;
                    player.sendMessage(plugin.getRewardManager().getRemainingTime(millis,player));
                    return true;
                }
                player.sendMessage(Util.getMessage(Util.getLocale(player),"ClaimReward"));
                plugin.getRewardManager().setRewardMonthly(player);
                int coins = plugin.getConfig().getInt("Settings.MonthlyRewardCoinsAmount");
                plugin.getCoinManager().addCoins(player,coins);
                player.sendMessage(Util.getMessage(Util.getLocale(player),"CoinsLayout").replaceAll("%COINS_AMOUNT%", String.valueOf(coins)).replaceAll("%COINS_BALANCE%", String.valueOf(plugin.getCoinManager().getCoins(player))));
            }else
                player.sendMessage(Util.getMessage(Util.getLocale(player),"RewardUsage"));
            return true;
        }else
            player.sendMessage(Util.getMessage(Util.getLocale(player),"RewardUsage"));
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1){
            String[] completes = {"daily","weekly","monthly"};
            ArrayList<String> completeList = new ArrayList<>();
            Collections.addAll(completeList, completes);
            return completeList;
        }
        return null;
    }

}
