package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class ClanManager {

    private Main plugin;

    public ClanManager(Main plugin){
        this.plugin = plugin;
    }

    public void createClan(String clanName, Player clanOwner){
        new ConfigLocationUtil(plugin,"Clans."+clanOwner.getUniqueId()+"."+clanName).createClan(clanOwner, clanName);
    }

    public boolean hasClan(Player player){
        if(plugin.yaml.contains("Clans."+player.getUniqueId())){
            return true;
        }else
            return false;
    }
    public void leaveClan(UUID ownerUUID, Player leaver, String clanName){
        plugin.yaml.set("Clans."+ownerUUID+"."+clanName+".Member."+leaver.getName(), null);
        plugin.saveData();
    }

    public void inviteToClan(Player inviter, Player receiver, String clanName){
        inviter.sendMessage(Util.getMessage(Util.getLocale(inviter),"InviterMessage").replaceAll("%PLAYER_INVITER%",inviter.getName()).replaceAll("%PLAYER_RECEIVER%", receiver.getName()).replaceAll("%CLAN_NAME%",clanName));
        receiver.sendMessage("§4§l-=-=-=-=-=-=-=-=-=-=-=-=-");
        receiver.sendMessage(Util.getMessage(Util.getLocale(receiver),"ReceiverMessage").replaceAll("%PLAYER_INVITER%",inviter.getName()).replaceAll("%PLAYER_RECEIVER%", receiver.getName()).replaceAll("%CLAN_NAME%",clanName));
        TextComponent c = new TextComponent("");
        TextComponent clickme = new TextComponent("§7|----------- §a§l[Join] §5§l----- ");
        TextComponent clickme2 = new TextComponent("§c§l[Deny] §7-----------|");

        clickme.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/clan join "+inviter.getName()));
        clickme.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§aAccept clan request.")));
        c.addExtra(clickme);
        clickme2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/clan deny "+inviter.getName()));
        clickme2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText("§cDeny clan request.")));
        c.addExtra(clickme2);
        receiver.spigot().sendMessage(c);
        receiver.sendMessage("§4§l-=-=-=-=-=-=-=-=-=-=-=-=-");
    }

    public boolean acceptClanRequests(Player player){
        plugin.getPlayerDataManager().setYaml(player);
        return plugin.getPlayerDataManager().playerDataYaml.getBoolean("Settings.ClanRequests");
    }

    public String getClanName(Player player){
        String clanName = null;
        for(String clans : plugin.yaml.getConfigurationSection("Clans."+player.getUniqueId()).getKeys(false)){
            clanName = clans;
        }
        return clanName;
    }
    public String getClanNameAsMember(Player player){
        for(String ownerUUID : plugin.yaml.getConfigurationSection("Clans.").getKeys(false)){
            for(String clanName : plugin.yaml.getConfigurationSection("Clans."+ownerUUID).getKeys(false)){
                if(plugin.yaml.contains("Clans."+ownerUUID+"."+clanName+".Member."+player.getName())){
                    return clanName;
                }
            }
        }
        return "§cNo Clan";
    }
    public boolean isInClan(Player player){
        for(String ownerUUID : plugin.yaml.getConfigurationSection("Clans.").getKeys(false)){
            if(player.getUniqueId().equals(ownerUUID)){
                return true;
            }
            for(String clanName : plugin.yaml.getConfigurationSection("Clans."+ownerUUID).getKeys(false)){
                if(plugin.yaml.contains("Clans."+ownerUUID+"."+clanName+".Member."+player.getName())){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAlreadyInClan(UUID ownerUUID, String player, String clanName){
        if(plugin.yaml.contains("Clans."+ownerUUID+"."+clanName+".Member."+player)){
            return true;
        }else
            return false;
    }
    public void kickPlayer(Player player, String leaver, String clanName){
        if(isAlreadyInClan(player.getUniqueId(),leaver,clanName)){
            plugin.yaml.set("Clans."+player.getUniqueId()+"."+clanName+".Member."+leaver,null);
            plugin.saveData();
            player.sendMessage(Util.getMessage(Util.getLocale(player),"KickPlayerSenderMessage").replaceAll("%PLAYER_NAME%",leaver).replaceAll("%CLAN_NAME%",clanName));
            Player leaverPlayer = Bukkit.getPlayer(leaver);
            if(leaverPlayer != null)
                leaverPlayer.sendMessage(Util.getMessage(Util.getLocale(leaverPlayer),"KickPlayerReceiver").replaceAll("%PLAYER_NAME%",player.getName()).replaceAll("%CLAN_NAME%",clanName));
        }else
            player.sendMessage(Util.getMessage(Util.getLocale(player),"NotInYourClan").replaceAll("%PLAYER_NAME%",leaver).replaceAll("%CLAN_NAME%",clanName));
    }

}
