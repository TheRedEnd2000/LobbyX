package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class RankManager {

    private Main plugin;

    public RankManager(Main plugin){
        this.plugin = plugin;
        createDefaultRanks();
        checkOpPlayers();
        checkPermissionPlayers();
    }

    public File getRankFile() {
        return new File(plugin.getDataFolder(), "ranks.yml");
    }

    public void createRank(String name, String displayName, String prefix, ChatColor color, boolean op){
        saveRank("Ranks."+name,displayName, prefix, color,op);
    }

    private void saveRank(String root, String displayName, String prefix, ChatColor color, boolean op){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        config.set(root + ".DisplayName", displayName);
        config.set(root+".Prefix", prefix);
        config.set(root + ".Color", color.toString());
        config.set(root + ".Op", op);
        try {
            config.save(getRankFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listRanks(Inventory inventory){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        for(String ranks : config.getConfigurationSection("Ranks.").getKeys(false)){
            if(getRankSize() <= 20) {
                String name = config.getString("Ranks." + ranks + ".DisplayName");
                String color = config.getString("Ranks." + ranks + ".Color");
                boolean op = config.getBoolean("Ranks." + ranks + ".Op");
                inventory.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("ZjY2YmM1MTljZDI2NjJiYmIwYmFjN2U2OWY4MDAyNjFhMTk4M2EzMmIzOWMxODlkM2M5OGJjMjk4YjUyNWJkZCJ9fX0=")).setDisplayname(color + name).setLore(op ? "§2§lThis Rank has Op" : "§cThis rank has §c§lNO §cOp", "§eClick to configure this rank.").setLocalizedName(ranks).build());
            }
        }
    }

    public int getRankSize(){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        int counter = 0;
        for(String ranks : config.getConfigurationSection("Ranks.").getKeys(false)){
            counter++;
        }
        return counter;
    }

    public void setRank(String name, Player player){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        if(existRank(name)) {
            config.set("Ranks_on_Player." + player.getUniqueId(), name);
            try {
                config.save(getRankFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
            player.sendMessage("§cERROR");
    }

    public boolean hasRank(Player player){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        return config.contains("Ranks_on_Player."+player.getUniqueId());
    }
    public String getRank(Player player){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        if(!hasRank(player))
            setRank("default",player);
        String rank = config.getString("Ranks_on_Player."+player.getUniqueId());
        String color = getColor(rank);
        String name = getName(rank);
        return color+name;
    }
    public String getColoredPrefix(Player player){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        if(!hasRank(player))
            setRank("default",player);
        String rank = config.getString("Ranks_on_Player."+player.getUniqueId());
        String color = getColor(rank);
        String prefix = getPrefix(rank);
        return color+prefix;
    }

    public String getBlankRank(Player player){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        if(!hasRank(player))
            setRank("default",player);
        return config.getString("Ranks_on_Player."+player.getUniqueId());
    }

    public void setRanksList(Player player){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        for(String ranks : config.getConfigurationSection("Ranks.").getKeys(false)){
            if(getBlankRank(player).equals(ranks)) {
                Scoreboard scoreboard = player.getScoreboard();
                Team team = scoreboard.getTeam(ranks);
                if(team == null){
                    team = scoreboard.registerNewTeam(ranks);
                }
                team.setPrefix(getColor(ranks)+getPrefix(ranks)+" §7| ");
                team.addEntry(player.getName());
                return;
            }
        }
    }

    public void resetRank(Player player){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        config.set("Ranks_on_Player." + player.getUniqueId(), getDefaultRank());
        try {
            config.save(getRankFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean existRank(String name){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        return config.contains("Ranks." + name);
    }

    public void deleteRank(String name){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        config.set("Ranks."+name, null);
        try {
            config.save(getRankFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName(String rank){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        return config.getString("Ranks."+rank+".DisplayName");
    }
    public String getPrefix(String rank){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        return config.getString("Ranks."+rank+".Prefix");
    }
    public String getColor(String rank){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        return config.getString("Ranks."+rank+".Color");
    }

    public boolean hasOp(String rank){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        return config.getBoolean("Ranks."+rank+".Op");
    }
    public boolean hasPermission(String rank, String permission){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        for(String permissions : config.getConfigurationSection("Ranks."+rank+".Permissions.").getKeys(false)){
            String finalPermission = config.getString("Ranks."+rank+".Permissions."+permissions);
            assert finalPermission != null;
            if(finalPermission.equals(permission)){
                return true;
            }
        }
        return false;
    }

    public void setRankName(String newName, String rank){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        config.set("Ranks."+rank+".DisplayName", newName);
        try {
            config.save(getRankFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addPermission(String permission, String rank){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        config.set("Ranks." + rank + ".Permissions."+permission, permission.replaceAll("_","."));
        try {
            config.save(getRankFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removePermission(String permission, String rank){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        config.set("Ranks." + rank + ".Permissions."+permission, null);
        try {
            config.save(getRankFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setRankPrefix(String newPrefix, String rank){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        config.set("Ranks."+rank+".Prefix", newPrefix);
        try {
            config.save(getRankFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setRankColor(String newColor, String rank){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        config.set("Ranks."+rank+".Color", newColor);
        try {
            config.save(getRankFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDefaultRanks(){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        if(!config.contains("Ranks.")) {
            createRank("owner", "Owner", "OWNER", ChatColor.DARK_RED, true);
            createRank("mod", "Moderator","MOD", ChatColor.GOLD, true);
            createRank("vip", "VIP", "VIP", ChatColor.DARK_PURPLE, false);
            createRank("default", "Player","Player", ChatColor.GRAY, false);
        }
    }

    public void changeOp(boolean op, String rank){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        config.set("Ranks."+rank+".Op", op);
        try {
            config.save(getRankFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkOpPlayers(){
        new BukkitRunnable() {
            @Override
            public void run() {
                FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
                for(Player player : Bukkit.getOnlinePlayers()){
                    for(String playerUUID : config.getConfigurationSection("Ranks_on_Player.").getKeys(false)){
                        if(player.getUniqueId().equals(UUID.fromString(playerUUID))){
                            String rank = getBlankRank(player);
                            if(hasOp(rank) && !player.isOp()){
                                setRank(getDefaultRank(),player);
                                return;
                            }
                            if(!hasOp(rank) && player.isOp()){
                                setRank(getOperatorRank(),player);
                                return;
                            }
                            player.setOp(hasOp(rank));
                        }
                    }
                }
            }
        }.runTaskTimer(plugin,0,10);
    }
    private void checkPermissionPlayers(){
        new BukkitRunnable() {
            @Override
            public void run() {
                FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
                for(Player player : Bukkit.getOnlinePlayers()){
                    for(String playerUUID : config.getConfigurationSection("Ranks_on_Player.").getKeys(false)){
                        if(player.getUniqueId().equals(UUID.fromString(playerUUID))){
                            String rank = getBlankRank(player);
                            for(String permissions : config.getConfigurationSection("Ranks."+rank+".Permissions.").getKeys(false)){
                                String permission = config.getString("Ranks."+rank+".Permissions."+permissions);
                                PermissionAttachment permissionAttachment = player.addAttachment(plugin);
                                assert permission != null;
                                if(!hasOp(rank)) {
                                    if(hasPermission(rank,permission)){
                                        permissionAttachment.setPermission(permission,true);
                                        player.sendMessage("added "+permission);
                                    }
                                }
                            }
                            player.setOp(hasOp(rank));
                        }
                    }
                }
            }
        }.runTaskTimer(plugin,0,10);
    }

    public String getDefaultRank(){
        return plugin.getConfig().getString("Settings.DefaultRank");
    }
    public String getOperatorRank(){
        return plugin.getConfig().getString("Settings.OperatorRank");
    }

}
