package de.theredend2000.lobbyx.commands;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DailyRewardCommand implements CommandExecutor {

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
        if(args.length == 0) {
            if(!plugin.getRewardManager().getAllowReward(player)) {
                player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyClaimed"));
                long current = System.currentTimeMillis();
                long release = plugin.getRewardManager().getTime(player);
                long millis = release - current;
                player.sendMessage(plugin.getRewardManager().getRemainingTime(millis,player));
                return true;
            }
            player.sendMessage(Util.getMessage(Util.getLocale(player),"ClaimReward"));
            plugin.getRewardManager().setReward(player);
            int coins = plugin.getConfig().getInt("Settings.DailyRewardCoinsAmount");
            plugin.getCoinManager().addCoins(player,coins);
            player.sendMessage(Util.getMessage(Util.getLocale(player),"CoinsLayout").replaceAll("%COINS_AMOUNT%", String.valueOf(coins)).replaceAll("%COINS_BALANCE%", String.valueOf(plugin.getCoinManager().getCoins(player))));
            return true;
        }
        return false;
    }
}
