package de.theredend2000.lobbyx.listeners.gadgetsListener;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class LightningStaff implements Listener {

    private Main plugin;

    public LightningStaff(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();
            if (snowball.getShooter() != null && snowball.getShooter() instanceof Player && snowball.getCustomName() != null) {
                Player shooter = (Player) snowball.getShooter();
                if (snowball.getCustomName().equalsIgnoreCase("lighningstaff."+shooter.getName())) {
                    LightningStrike strike = snowball.getLocation().getWorld().spawn(snowball.getLocation(), LightningStrike.class);
                    snowball.remove();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta != null && plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Inv").equals("Lightning_Staff")) {
            String name = item.getItemMeta().getDisplayName();
            if (name.equals(plugin.gadgetsYaml.getString("Gadgets.SpecialItems.Lightning_Staff.name").replaceAll("&", "§"))) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    Snowball snowball = player.launchProjectile(Snowball.class);
                    snowball.setShooter(player);
                    snowball.setCustomName("lighningstaff." + player.getName());
                    snowball.setItem(new ItemStack(Material.NETHER_STAR));
                }
            }
        }
    }

}
