package de.theredend2000.lobbyx.jumpandrun;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Leaderboard {

    private static List<ArmorStand> armorStands = new ArrayList<>();
    private static final String HEADER = "§a§lLEADERBOARD";
    private static final String TITLE = "§5Top Score";
    private static final String FOOTER = "§bYourServer.net";
    private Location location;
    private Main plugin;

    public Leaderboard(Main plugin, Location location){
        this.plugin = plugin;
        this.location = location.add(0,1.3,0);
        createLeaderBoard();
        plugin.getServer().getScheduler().runTaskTimer(plugin,this::update,0,10L);
    }


    private void createLeaderBoard(){
        Location loc = location.clone();
        createArmorStand(HEADER, loc);
        loc = loc.subtract(0,0.25,0);
        createArmorStand(TITLE, loc);
        loc = loc.subtract(0,0.25,0);
        for(int i = 0; i < 10; i++){
            armorStands.add(createArmorStand("§7Loading...", loc));
            loc = loc.subtract(0,0.25,0);
        }
        createArmorStand(FOOTER, loc);
    }
    private void update(){
        int i = 0;
        if(plugin.yaml.getString("JumpAndRun.Points") != null) {
            for (String player : plugin.yaml.getConfigurationSection("JumpAndRun.Points").getKeys(false)) {
                int points = plugin.yaml.getInt("JumpAndRun.Points." + player);
                armorStands.get(i).setCustomName("§a" + (i + 1) + "#§b " + player + " §f- §6" + points);
                i++;
            }
        }
        for(; i < 10; i++){
            if(armorStands.get(i).getCustomName().equalsIgnoreCase("§7Loading..."))
                armorStands.get(i).setCustomName("§7Loading.");
            else if(armorStands.get(i).getCustomName().equalsIgnoreCase("§7Loading."))
                armorStands.get(i).setCustomName("§7Loading..");
            else if(armorStands.get(i).getCustomName().equalsIgnoreCase("§7Loading.."))
                armorStands.get(i).setCustomName("§7Loading...");
        }
        for(Player player : Bukkit.getOnlinePlayers()){
            if(plugin.getLobbyWorlds().contains(player.getWorld())){
                for(Entity entity : player.getNearbyEntities(100,100,100)){
                    if(entity.getType().equals(EntityType.ARMOR_STAND)){
                        ArmorStand armorStand = (ArmorStand) entity;
                        if(armorStand.getScoreboardTags().contains("jnr.lb")) {
                            if(!armorStands.contains(armorStand)) {
                                armorStands.add(armorStand);
                                player.sendMessage(armorStand.getUniqueId()+" added");
                            }
                            player.sendMessage(String.valueOf(armorStands.size()));
                            Bukkit.getConsoleSender().sendMessage(armorStand.getUniqueId());
                        }
                    }
                }
            }
        }
    }

    private ArmorStand createArmorStand(String name, Location location){
        ArmorStand armorStand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
        armorStand.setCustomName(name);
        armorStand.setCustomNameVisible(true);
        armorStand.addScoreboardTag("jnr.lb");
        return armorStand;
    }


}
