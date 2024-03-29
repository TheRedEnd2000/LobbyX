package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class ParticleManager {

    private Main plugin;
    private int i = 0;

    public ParticleManager(Main plugin){
        this.plugin = plugin;
        spawnParticle();
    }

    private void spawnParticle(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(plugin.getLobbyWorlds().contains(player.getWorld()) && !plugin.getBuildPlayers().contains(player)){
                        for(String particles : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Particle").getKeys(false)) {
                            if (plugin.yaml.getString("Selected_Items." + player.getUniqueId() + ".Particle") != null) {
                                if (Objects.requireNonNull(plugin.yaml.getString("Selected_Items." + player.getUniqueId() + ".Particle")).equals(particles)) {
                                    Particle particle = plugin.getParticle(plugin.gadgetsYaml.getString("Gadgets.Particle." + particles + ".particle"));
                                    int count = plugin.gadgetsYaml.getInt("Gadgets.Particle."+particles+".count");
                                    String location = plugin.gadgetsYaml.getString("Gadgets.Particle." + particles + ".location");
                                    switch (location){
                                        case "EYE":
                                            double angel = (i*Math.PI / 100);
                                            double x = 0.5 * Math.cos(angel);
                                            double z = 0.5 * Math.sin(angel);
                                            double y = 0.5;
                                            player.getEyeLocation().getWorld().spawnParticle(particle, player.getEyeLocation().add(x, y, z), count,0,0,0,0);
                                            i = i + 20;
                                            break;
                                        case "FEED":
                                            player.getLocation().getWorld().spawnParticle(particle, player.getLocation(), count,0.15,0.15,0.15,0);
                                            break;
                                        case "BODY":
                                            player.getLocation().getWorld().spawnParticle(particle, player.getLocation().add(0,0.5,0), count,0.2,0.5,0.2,0);
                                            break;
                                        default:
                                            player.getLocation().getWorld().spawnParticle(particle, player.getLocation(), count,0.15,0.15,0.15,0);
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }.runTaskTimer(plugin,0,5);
    }

}
