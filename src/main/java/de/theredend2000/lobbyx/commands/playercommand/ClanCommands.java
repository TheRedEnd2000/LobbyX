package de.theredend2000.lobbyx.commands.playercommand;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ClanCommands implements CommandExecutor {

    private Main plugin;
    private HashMap<Player, Player> clanRequest;
    private HashMap<Player, Integer> clanRequestTime;

    public ClanCommands(Main plugin){
        this.plugin = plugin;
        clanRequest = new HashMap<>();
        clanRequestTime = new HashMap<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(plugin.getLobbyWorlds().contains(player.getWorld())){
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("create")){
                        if(!plugin.getClanManager().hasClan(player)){
                            if(plugin.yaml.contains("Clans")) {
                                for (String clanOwner : plugin.yaml.getConfigurationSection("Clans.").getKeys(false)) {
                                    if(plugin.getClanManager().isAlreadyInClan(UUID.fromString(clanOwner),player,"s")){
                                        return true;
                                    }
                                }
                            }
                            String clanName = args[1];
                            plugin.getClanManager().createClan(clanName,player);
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"CreatedClan").replaceAll("%CLAN_NAME%",clanName));
                        }else
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"HasAlreadyCreatedClan"));
                    }else if(args[0].equalsIgnoreCase("invite")){
                        if(plugin.getClanManager().hasClan(player)){
                            Player receiver = Bukkit.getPlayer(args[1]);
                            if(receiver != null){
                                String clanName = plugin.getClanManager().getClanName(player);
                                if(plugin.getClanManager().isAlreadyInClan(player.getUniqueId(),receiver,clanName)){
                                    player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyInYourClan").replaceAll("%PLAYER_NAME%",receiver.getName()));
                                    return true;
                                }
                                if(!plugin.getClanManager().hasClan(receiver)) {
                                    plugin.getClanManager().inviteToClan(player, receiver, clanName);
                                    clanRequestTime.put(player,300);
                                    clanRequest.put(player, receiver);
                                }else
                                    player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyInClan").replaceAll("%PLAYER_NAME%",receiver.getName()));
                            }else
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerNotFound"));
                        }else
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"HasNoClan"));
                    }else if(args[0].equalsIgnoreCase("join")){
                        Player inviter = Bukkit.getPlayer(args[1]);
                        if(inviter != null){
                            if(!plugin.getClanManager().hasClan(player)){
                                String clanName = plugin.getClanManager().getClanName(inviter);
                                new ConfigLocationUtil(plugin,"Clans."+inviter.getUniqueId()+"."+clanName+".Member."+player.getName()).joinClan(player);
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"JoinClan").replaceAll("%CLAN_NAME%",clanName).replaceAll("%PLAYER_NAME%",player.getName()));
                                inviter.sendMessage(Util.getMessage(Util.getLocale(player),"JoinClanMessageInviter").replaceAll("%CLAN_NAME%",clanName).replaceAll("%PLAYER_NAME%",player.getName()));
                                clanRequestTime.remove(inviter);
                                clanRequest.remove(inviter);
                            }else
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyInClanPersonal"));
                        }else
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerNotFound"));
                    }else if(args[0].equalsIgnoreCase("deny")){
                        Player inviter = Bukkit.getPlayer(args[1]);
                        if(inviter != null){
                            if(!plugin.getClanManager().hasClan(player)){
                                String clanName = plugin.getClanManager().getClanName(inviter);
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"DenyClan").replaceAll("%CLAN_NAME%",clanName).replaceAll("%PLAYER_NAME%",player.getName()));
                                inviter.sendMessage(Util.getMessage(Util.getLocale(player),"DenyClanMessageInviter").replaceAll("%CLAN_NAME%",clanName).replaceAll("%PLAYER_NAME%",player.getName()));
                                clanRequestTime.remove(inviter);
                                clanRequest.remove(inviter);
                            }else
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyInClanPersonal"));
                        }else
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"PlayerNotFound"));
                    }else
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"ClanCommandUsage"));
                }else if(args.length == 1){
                    if(args[0].equalsIgnoreCase("manage")){
                        if(plugin.getClanManager().hasClan(player)){
                            player.sendMessage("OpenInv");
                        }else
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"HasNoClan"));
                    }else
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"ClanCommandUsage"));
                }else
                    player.sendMessage(Util.getMessage(Util.getLocale(player),"ClanCommandUsage"));
            }
        }else
            sender.sendMessage(Util.getMessage("en","OnlyPlayerUse"));
        return false;
    }
}
