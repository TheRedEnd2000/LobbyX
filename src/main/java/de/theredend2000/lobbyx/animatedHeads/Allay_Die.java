package de.theredend2000.lobbyx.animatedHeads;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Allay_Die {

    private Main plugin;

    public Allay_Die(Main plugin){
        this.plugin = plugin;

        generateHead();
    }

    private void generateHead(){
        for(Player player : Bukkit.getOnlinePlayers()){
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(plugin.getLobbyWorlds().contains(player.getWorld())){
                        String displayname = plugin.gadgetsYaml.getString("Gadgets."+player.getUniqueId()+".AnimatedHeads.Allay_Die.name");
                        if(plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Head").equals("Allay_Die")){
                            player.getInventory().setHelmet(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("OWUzN2U1MGY0NmFmZDRmZDc5NmM2ZWQxMGMzNTk1YWNiYzhhNGM5ZWE5NjNkOGU3YTY1Njc1ODBlOTUwOWFjOCJ9fX0=")).setDisplayname(displayname).build());
                            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Head","Allay_Die-2");
                            plugin.saveData();
                        }else if(plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Head").equals("Allay_Die-2")){
                            player.getInventory().setHelmet(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("MzQxZmQyMWJiMzNiZjY2OGQ4OTBlMDgwMWYzYWJiMzAwZTA2MzVlY2VkZjA1NTc5MjQxM2ZiNjlkNTU1NDg1MCJ9fX0=")).setDisplayname(displayname).build());
                            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Head","Allay_Die-3");
                            plugin.saveData();;
                        }else if(plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Head").equals("Allay_Die-3")){
                            player.getInventory().setHelmet(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture("YzRlYTUzYWEwNTM4NjFjOGIzNzg1MTg5MWRlOGM2ZTczY2ZiMTJlMmQ4MzExYjAwMjFjZDY2ZTc3YTBmY2UyOSJ9fX0=")).setDisplayname(displayname).build());
                            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Head","Allay_Die");
                            plugin.saveData();
                        }
                    }
                }
            }.runTaskTimer(plugin, 0, 20);
        }
    }


}
