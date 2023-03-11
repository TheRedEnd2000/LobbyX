package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class RankManager {

    private Main plugin;

    public RankManager(Main plugin){
        this.plugin = plugin;
        createDefaultRanks();
    }

    public File getRankFile() {
        return new File(plugin.getDataFolder(), "ranks.yml");
    }

    public void createRank(String name, String displayName, ChatColor color, boolean op){
        saveRank(displayName,"Ranks."+name, color,op);
    }

    private void saveRank(String displayName, String root, ChatColor color, boolean op){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        config.set(root + ".DisplayName", displayName);
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
            String name = config.getString("Ranks."+ranks+".DisplayName");
            String color = config.getString("Ranks."+ranks+".Color");
            boolean op = config.getBoolean("Ranks."+ranks+".Op");
            inventory.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("ZjY2YmM1MTljZDI2NjJiYmIwYmFjN2U2OWY4MDAyNjFhMTk4M2EzMmIzOWMxODlkM2M5OGJjMjk4YjUyNWJkZCJ9fX0=")).setDisplayname(color+name).setLore(op ? "§2§lThis Rank has Op" : "§cThis rank has §c§lNO §cOp","§eClick to configure this rank.").build());
        }
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
    public String getColor(String rank){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        return config.getString("Ranks."+rank+".Color");
    }

    public boolean hasOp(String rank){
        FileConfiguration config = YamlConfiguration.loadConfiguration(getRankFile());
        return config.getBoolean("Ranks."+rank+".Op");
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
            createRank("owner", "Owner", ChatColor.DARK_RED, true);
            createRank("mod", "Moderator", ChatColor.GOLD, true);
            createRank("vip", "VIP", ChatColor.DARK_PURPLE, false);
            createRank("default", "Spieler", ChatColor.GRAY, false);
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

    public String getDefaultRank(){
        return plugin.getConfig().getString("Settings.DefaultRank");
    }

}
