package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class RewardManager {

    private final Main plugin;

    public RewardManager(Main plugin){
        this.plugin = plugin;
        teleportAllVillagers();
    }

    public boolean getAllowReward(Player player,String type) {
        long current = System.currentTimeMillis();
        if(type.equalsIgnoreCase("daily")){
            long millis = getTimeDaily(player);
            return current > millis;
        }
        if(type.equalsIgnoreCase("weekly")){
            long millis = getTimeWeekly(player);
            return current > millis;
        }
        if(type.equalsIgnoreCase("monthly")){
            long millis = getTimeMonthly(player);
            return current > millis;
        }
        return false;
    }

    public File getRewardFile() {
        return new File(plugin.getDataFolder(), "rewards.yml");
    }

    public void setRewardDaily(Player player) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getRewardFile());
        long toSet = System.currentTimeMillis()+86400000;
        cfg.set(player.getUniqueId() + ".millisDaily", toSet);
        try {
            cfg.save(getRewardFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setRewardWeekly(Player player) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getRewardFile());
        long toSet = System.currentTimeMillis()+604800000;
        cfg.set(player.getUniqueId() + ".millisWeekly", toSet);
        try {
            cfg.save(getRewardFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setRewardMonthly(Player player) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getRewardFile());
        long toSet = System.currentTimeMillis()+ 2592000000L;
        cfg.set(player.getUniqueId() + ".millisMonthly", toSet);
        try {
            cfg.save(getRewardFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getTimeDaily(Player player) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getRewardFile());
        return cfg.getLong(player.getUniqueId() + ".millisDaily");
    }
    public long getTimeWeekly(Player player) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getRewardFile());
        return cfg.getLong(player.getUniqueId() + ".millisWeekly");
    }
    public long getTimeMonthly(Player player) {
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(getRewardFile());
        return cfg.getLong(player.getUniqueId() + ".millisMonthly");
    }

    public String getRemainingTime(long millis, Player player) {
        long seconds = millis/1000;
        long minutes = 0;
        while(seconds > 60) {
            seconds-=60;
            minutes++;
        }
        long hours = 0;
        while(minutes > 60) {
            minutes-=60;
            hours++;
        }
        long days = 0;
        while(hours > 24) {
            hours-=24;
            days++;
        }
        return Util.getMessage(Util.getLocale(player),"RewardTimeLayOut").replaceAll("%DAYS%", String.valueOf(days)).replaceAll("%HOURS%", String.valueOf(hours)).replaceAll("%MINUTES%", String.valueOf(minutes)).replaceAll("%SECONDS%", String.valueOf(seconds));
    }
    public String getRemainingTime(long millis) {
        long seconds = millis/1000;
        long minutes = 0;
        while(seconds > 60) {
            seconds-=60;
            minutes++;
        }
        long hours = 0;
        while(minutes > 60) {
            minutes-=60;
            hours++;
        }
        long days = 0;
        while(hours > 24) {
            hours-=24;
            days++;
        }
        return "§6"+days+"d "+hours+"h "+minutes+"m "+seconds+"s";
    }

    public void spawnDailyRewardVillager(Location location){
        if(plugin.yaml.contains("DailyRewardVillager."+location.getWorld().getName())){
            for(Entity entity : location.getWorld().getEntities()){
                if(entity instanceof Villager){
                    Villager villager = (Villager) entity;
                    if(villager.getCustomName().equals("§6Daily")){
                        villager.remove();
                    }
                }
            }
        }
        Villager daily = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        daily.setCustomName("§6Daily");
        daily.setCustomNameVisible(true);
        daily.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,Integer.MAX_VALUE,500));
        daily.setSilent(true);
        daily.setBreed(false);
        new ConfigLocationUtil(plugin,location,"DailyRewardVillager."+location.getWorld().getName()).saveLocation();
    }

    public void removeDailyRewardVillager(Location location){
        if(plugin.yaml.contains("DailyRewardVillager."+location.getWorld().getName())){
            for(Entity entity : location.getWorld().getEntities()){
                if(entity instanceof Villager){
                    Villager villager = (Villager) entity;
                    if(villager.getCustomName().equals("§6Daily")){
                        villager.remove();
                    }
                }
            }
        }
    }

    private void teleportAllVillagers(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for(World world : Bukkit.getWorlds()){
                    if(plugin.yaml.contains("DailyRewardVillager."+world.getName())){
                        for(Entity entity : world.getEntities()){
                            if(entity instanceof Villager){
                                Villager villager = (Villager) entity;
                                if(villager.getCustomName().equals("§6Daily")){
                                    ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin,"DailyRewardVillager."+world.getName());
                                    if(locationUtil.loadLocation() != null){
                                        villager.teleport(locationUtil.loadLocation());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin,0,1);
    }

}
