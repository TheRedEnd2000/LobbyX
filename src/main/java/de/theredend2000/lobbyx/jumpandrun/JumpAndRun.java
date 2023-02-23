package de.theredend2000.lobbyx.jumpandrun;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class JumpAndRun {
    private static Map<UUID, JumpAndRun> jumpAndRuns = new HashMap();
    private static Map<UUID,Integer> jumpAndRunPoints = new HashMap<>();
    private Player player;
    private Location spawnLocation;
    private Location currentLocation;
    private Location nextLocation;
    private Random random;
    private boolean finished;
    private Main plugin;

    public JumpAndRun(Player player, Main plugin) {
        this.player = player;
        this.spawnLocation = player.getLocation();
        this.random = new Random();
        this.plugin = plugin;

        sendActionbar();
    }

    public void start(Player player) {

        double x = (double)this.random.nextInt(3);
        double y = (double)(this.random.nextInt( 10));
        double z = (double)this.random.nextInt(3);

        Location newLocation = new Location(this.spawnLocation.getWorld(), this.spawnLocation.getX() + x, this.spawnLocation.getY() + y+10, this.spawnLocation.getZ() + z);

        this.addBlock(newLocation);
        this.currentLocation = newLocation;
        player.teleport(this.currentLocation.add(0.0, 1.0, 0.0));
        this.currentLocation.subtract(0.0, 1.0, 0.0);
        this.nextBlock(player, true);

    }

    public void nextBlock(Player player, boolean isStarted) {
        if (!this.finished) {
            if (!isStarted) {
                player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("JumpAndRun.Sounds.NewBlockSound")), 1.0F, 1.0F);
                int points = jumpAndRunPoints.get(player.getUniqueId());
                points += 1;
                jumpAndRunPoints.remove(player.getUniqueId());
                jumpAndRunPoints.put(player.getUniqueId(),points);
                this.removeBlock(this.currentLocation);
            } else {
                jumpAndRunPoints.put(player.getUniqueId(), 0);
                player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("JumpAndRun.Sounds.StartSound")), 1.0F, 1.0F);
                player.sendMessage(Util.getMessage(Util.getLocale(player), "StartJnr"));
            }

            this.currentLocation = player.getLocation();
            if (plugin.getConfig().getBoolean("fis")) {
                this.finished = true;

                this.setBlock(this.currentLocation, Material.QUARTZ_BLOCK, 0, 1, 0, false);
            } else {
                int radius = this.random.nextInt(1) + this.random.nextInt(1) + 2 + 2;
                double angle = Math.random() * Math.PI * 2.0;
                double x = Math.cos(angle) * (double)radius;
                double y = (double)this.random.nextInt(2);
                if (radius >= 5) {
                    y = 0.0;
                }

                double z = Math.sin(angle) * (double)radius;
                this.nextLocation = new Location(this.currentLocation.getWorld(), this.currentLocation.getX() + x, this.currentLocation.getY() + y, this.currentLocation.getZ() + z);
                if(nextLocation.getBlock().getType() == Material.AIR) {
                    this.currentLocation.subtract(0.0, 1.0, 0.0).getBlock().setType(plugin.getMaterial("JumpAndRun.Blocks.StandingBlock"));
                    this.nextLocation.subtract(0.0, 1.0, 0.0).getBlock().setType(plugin.getMaterial("JumpAndRun.Blocks.NewBlock"));
                    return;
                }else{
                    int counter = 0;
                    if(counter == 10){
                        stop(player);
                        player.sendMessage("§cThere was an error. §4§l(NO-FREE-BLOCK-FOUND) \n§cPlease contact an admin.");
                        nextLocation.getBlock().setType(Material.AIR);
                        return;
                    }
                    nextBlock(player, true);
                    counter++;
                    player.sendMessage(String.valueOf(counter));
                }
            }
        }
    }

    public void stop(Player player) {
        this.currentLocation.getBlock().setType(Material.AIR);
        this.nextLocation.getBlock().setType(Material.AIR);
        jumpAndRuns.remove(player.getUniqueId());
        player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("JumpAndRun.Sounds.StopSound")), 1.0F, 1.0F);
        player.sendMessage(Util.getMessage(Util.getLocale(player), "StopJnr").replaceAll("%JUMP_AND_RUN_POINTS%", jumpAndRunPoints.get(player.getUniqueId()).toString()));
        if(jumpAndRunPoints.get(player.getUniqueId()) > plugin.yaml.getInt("JumpAndRun.Points."+player.getName())){
            plugin.yaml.set("JumpAndRun.Points."+player.getName(), jumpAndRunPoints.get(player.getUniqueId()));
            plugin.saveData();
        }
        jumpAndRunPoints.remove(player.getUniqueId());
    }

    private void setBlock(Location location, Material material, int x, int y, int z, boolean add) {
        if (add) {
            location.add((double)x, (double)y, (double)z).getBlock().setType(material);
        } else {
            location.subtract((double)x, (double)y, (double)z).getBlock().setType(material);
        }
    }
    private void sendActionbar(){
        for(Player players : Bukkit.getOnlinePlayers()){
            new BukkitRunnable() {
                @Override
                public void run() {
                    if(jumpAndRuns.containsKey(players.getUniqueId())){
                        if(plugin.getConfig().getBoolean("JumpAndRun.Actionbar.enabled"))
                            players.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(plugin.getConfig().getString("JumpAndRun.Actionbar.ActionbarText").replaceAll("%JUMP_AND_RUN_POINTS%", jumpAndRunPoints.get(players.getUniqueId()).toString()).replaceAll("&","§")));
                    }
                }
            }.runTaskTimer(plugin,0,10);
        }
    }

    private void addBlock(Location location) {
        location.subtract(0.0, 1.0, 0.0).getBlock().setType(plugin.getMaterial("JumpAndRun.Blocks.StandingBlock"));
    }

    private void removeBlock(Location location) {
        location.getBlock().setType(Material.AIR);
    }

    public static Map<UUID, JumpAndRun> getJumpAndRuns() {
        return jumpAndRuns;
    }

    public Location getCurrentLocation() {
        return this.currentLocation;
    }

    public Location getNextLocation() {
        return this.nextLocation;
    }
}