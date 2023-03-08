package de.theredend2000.lobbyx.commands.playercommand;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoinsCommand implements CommandExecutor {

    private Main plugin;

    public CoinsCommand(Main plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 0){
                int coins = plugin.getCoinManager().getCoins(player);
                player.sendMessage(Util.getMessage(Util.getLocale(player),"GetCoins").replaceAll("%COINS%", String.valueOf(coins)));
            }else if(args.length == 1){
                Player getCoinsPlayer = Bukkit.getPlayer(args[0]);
                if(getCoinsPlayer == null){
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerNotFound"));
                    return true;
                }
                int getPlayerCoins = plugin.getCoinManager().getCoins(getCoinsPlayer);
                player.sendMessage(Util.getMessage(Util.getLocale(player),"GetCoinsPlayer").replaceAll("%PLAYER%",getCoinsPlayer.getName()).replaceAll("%COINS%", String.valueOf(getPlayerCoins)));
            }else if(args.length == 3){
                String per = plugin.getConfig().getString("Permissions.CoinsCommandPermission");
                assert per != null;
                if(player.hasPermission(per)){
                    if(args[0].equalsIgnoreCase("add")){
                        Player getPlayer = Bukkit.getPlayer(args[1]);
                        if(getPlayer == null){
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerNotFound"));
                            return true;
                        }
                        try {
                            int coins = Integer.parseInt(args[2]);
                            plugin.getCoinManager().addCoins(getPlayer, coins);
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"AddCoinsPlayer").replaceAll("%PLAYER%",getPlayer.getName()).replaceAll("%COINS%", String.valueOf(coins)));
                        }catch (NumberFormatException e){
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"CoinCommandUsage"));
                        }
                    }else if(args[0].equalsIgnoreCase("remove")){
                        Player getPlayer = Bukkit.getPlayer(args[1]);
                        if(getPlayer == null){
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerNotFound"));
                            return true;
                        }
                        try {
                            int coins = Integer.parseInt(args[2]);
                            plugin.getCoinManager().removeCoins(getPlayer, coins);
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"RemoveCoinsPlayer").replaceAll("%PLAYER%",getPlayer.getName()).replaceAll("%COINS%", String.valueOf(coins)));
                        }catch (NumberFormatException e){
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"CoinCommandUsage"));
                        }
                    }else if(args[0].equalsIgnoreCase("set")){
                        Player getPlayer = Bukkit.getPlayer(args[1]);
                        if(getPlayer == null){
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerNotFound"));
                            return true;
                        }
                        try {
                            int coins = Integer.parseInt(args[2]);
                            plugin.getCoinManager().setCoins(getPlayer, coins);
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SetCoinsPlayer").replaceAll("%PLAYER%",getPlayer.getName()).replaceAll("%COINS%", String.valueOf(coins)));
                        }catch (NumberFormatException e){
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"CoinCommandUsage"));
                        }
                    }else if(args[0].equalsIgnoreCase("reset") && args[2].equalsIgnoreCase("confirm")){
                        Player getPlayer = Bukkit.getPlayer(args[1]);
                        if(getPlayer == null){
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerNotFound"));
                            return true;
                        }
                        plugin.getCoinManager().resetCoins(player);
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"ResetCoinsPlayer").replaceAll("%PLAYER%",getPlayer.getName()));
                    }else
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"CoinCommandUsage"));
                }else
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"NoPermissionMessage").replaceAll("%PERMISSION%",per));
            }else
                player.sendMessage(Util.getMessage(Util.getLocale(player),"CoinCommandUsage"));
        }else
            sender.sendMessage(Util.getMessage("en","OnlyPlayerUse"));
        return false;
    }
}
