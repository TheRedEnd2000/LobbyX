package de.theredend2000.lobbyx.listeners.gadgetsListener;

import de.theredend2000.lobbyx.Main;
import org.bukkit.*;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

public class SheepCannon implements Listener {

    private Main plugin;
    private ArrayList<Sheep> sheeps;

    public SheepCannon(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
        sheeps = new ArrayList<>();
        updateSheeps();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.WHITE_WOOL) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Inv").equals("Sheep_Cannon")) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    Sheep sheep = player.getEyeLocation().getWorld().spawn(player.getEyeLocation(),Sheep.class);
                    sheep.setRotation(player.getLocation().getYaw(),player.getLocation().getPitch());
                    sheep.setVelocity(player.getEyeLocation().getDirection().multiply(1.3));
                    sheep.setCustomName("jeb_");
                    sheep.setCustomNameVisible(false);
                    sheeps.add(sheep);
                }
            }
        }
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Sheep){
            Sheep sheep = (Sheep) event.getEntity();
            if(sheeps.contains(sheep)){
                event.setCancelled(true);
            }
        }
    }

    private void updateSheeps(){
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Sheep sheep : sheeps){
                    if(sheep.isOnGround()){
                        sheep.remove();
                        sheep.getLocation().getWorld().spawnParticle(Particle.EXPLOSION_NORMAL,sheep.getLocation(),5,1,1,1,0);
                        Firework fw = sheep.getLocation().getWorld().spawn(sheep.getLocation(), Firework.class);
                        FireworkMeta meta = fw.getFireworkMeta();
                        meta.setPower(1);
                        meta.addEffects(FireworkEffect.builder().withColor(Color.BLUE).build(),FireworkEffect.builder().withColor(Color.RED).build());
                        fw.setFireworkMeta(meta);
                        sheeps.remove(sheep);
                    }
                }
            }
        }.runTaskTimer(plugin,0,10);
    }

}
