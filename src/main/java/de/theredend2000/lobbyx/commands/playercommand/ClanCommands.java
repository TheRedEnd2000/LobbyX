package de.theredend2000.lobbyx.commands.playercommand;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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

        checkClanRequestTimeOut();
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
                                    for(String clanNames : plugin.yaml.getConfigurationSection("Clans."+clanOwner+".").getKeys(false)) {
                                        if (plugin.getClanManager().isAlreadyInClan(UUID.fromString(clanOwner), player.getName(), clanNames)) {
                                            player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyInClanPersonal"));
                                            return true;
                                        }
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
                                if(player.equals(receiver)){
                                    player.sendMessage(Util.getMessage(Util.getLocale(player),"NotInviteYourself"));
                                    return true;
                                }
                                String clanName = plugin.getClanManager().getClanName(player);
                                if(plugin.getClanManager().isAlreadyInClan(player.getUniqueId(),receiver.getName(),clanName)){
                                    player.sendMessage(Util.getMessage(Util.getLocale(player),"AlreadyInYourClan").replaceAll("%PLAYER_NAME%",receiver.getName()));
                                    return true;
                                }
                                if(clanRequest.containsKey(player)){
                                    player.sendMessage(Util.getMessage(Util.getLocale(player), "AlreadySendInvitation").replaceAll("%PLAYER_NAME%", receiver.getName()));
                                    return true;
                                }
                                if(!plugin.getClanManager().hasClan(receiver)) {
                                    plugin.getClanManager().inviteToClan(player, receiver, clanName);
                                    clanRequestTime.put(player,10);
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
                            if(!clanRequest.containsKey(inviter) && !clanRequest.containsValue(player)){
                                player.sendMessage(Util.getMessage(Util.getLocale(player), "NoClanInvitation"));
                                return true;
                            }
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
                            if(!clanRequest.containsKey(inviter) && !clanRequest.containsValue(player)){
                                player.sendMessage(Util.getMessage(Util.getLocale(player), "NoClanInvitation"));
                                return true;
                            }
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
                    }else if(args[0].equalsIgnoreCase("kick")){
                        String leaver = args[1];
                        if(plugin.getClanManager().hasClan(player)){
                            String clanName = plugin.getClanManager().getClanName(player);
                            plugin.getClanManager().kickPlayer(player,leaver, clanName);
                        }else
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"HasNoClan"));
                    }else if(args[0].equalsIgnoreCase("leave")){
                        String leaver = args[1];

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

    private void checkClanRequestTimeOut(){
        for(Player player : Bukkit.getServer().getOnlinePlayers()){
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(plugin.getLobbyWorlds().contains(player.getWorld())){
                        if(clanRequest.containsKey(player)){
                            if(clanRequestTime.get(player) == 0){
                                clanRequest.remove(player);
                                clanRequestTime.remove(player);
                                player.sendMessage(Util.getMessage(Util.getLocale(player), "ClanInvationExpired"));
                                return;
                            }
                            int time = clanRequestTime.get(player);
                            time -=1;
                            clanRequestTime.remove(player);
                            clanRequestTime.put(player,time);
                        }
                    }
                }
            }.runTaskTimer(plugin,0,20);
        }
    }

}
