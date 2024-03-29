package de.theredend2000.lobbyx.listeners.gadgetsListener;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.xutils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Random;

public class PaintballListener implements Listener {

    private Main plugin;

    public PaintballListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Snowball) {
            Snowball snowball = (Snowball) event.getEntity();
            if (snowball.getShooter() != null && snowball.getShooter() instanceof Player && snowball.getCustomName() != null) {
                Player shooter = (Player) snowball.getShooter();
                if (snowball.getCustomName().equalsIgnoreCase("paintball."+shooter.getName())) {
                    Random random = new Random();
                    Material[] materials = { Material.DIAMOND_BLOCK, Material.GOLD_BLOCK, Material.LAPIS_BLOCK, Material.EMERALD_BLOCK};
                    int randomIndex = random.nextInt(materials.length);
                    Material randomMaterial = materials[randomIndex];
                    Location impactPoint = snowball.getLocation().getBlock().getLocation().add(0,-1,0);
                    if(!impactPoint.getBlock().getType().equals(Material.AIR))
                        shooter.sendBlockChange(impactPoint, randomMaterial,(byte)0);
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
        if (meta != null && plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Inv").equals("Paint_Ball")) {
            String name = item.getItemMeta().getDisplayName();
            if (name.equals(plugin.gadgetsYaml.getString("Gadgets.SpecialItems.Paint_Ball.name").replaceAll("&", "§"))) {
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if(plugin.getGadgetsCooldown().containsKey(player)){
                        if(plugin.getGadgetsCooldown().get(player) > System.currentTimeMillis()){
                            return;
                        }
                    }
                    plugin.getGadgetsCooldown().put(player,System.currentTimeMillis()+ plugin.getGadgetsCooldownTime());
                    Snowball snowball = player.launchProjectile(Snowball.class);
                    snowball.setShooter(player);
                    snowball.setCustomName("paintball." + player.getName());
                    snowball.setItem(new ItemStack(Material.GHAST_TEAR));
                }
            }
        }
    }

}
