package de.theredend2000.lobbyx.jumpandrun;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class PlayerMoveListener implements Listener {

    private Main plugin;
    private HashMap<String, Long> messagecooldown;

    public PlayerMoveListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
        messagecooldown = new HashMap<>();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if (JumpAndRun.getJumpAndRuns().containsKey(player.getUniqueId())) {
            JumpAndRun jumpAndRun = (JumpAndRun)JumpAndRun.getJumpAndRuns().get(player.getUniqueId());
            Block underBlock = player.getLocation().subtract(0.0, 1.0, 0.0).getBlock();
            if (underBlock.getLocation().equals(jumpAndRun.getNextLocation().getBlock().getLocation()) && underBlock.getType().equals(plugin.getMaterial("JumpAndRun.Blocks.NewBlock"))) {
                jumpAndRun.nextBlock(player, false);
            }

            if (player.getLocation().getY() < jumpAndRun.getCurrentLocation().getY()) {
                jumpAndRun.stop(player);
            }
        }
        if(!JumpAndRun.getJumpAndRuns().containsKey(player.getUniqueId())){
            if (plugin.getLobbyWorlds().contains(player.getWorld())) {
                if(plugin.getConfig().getBoolean("JumpAndRun.pressurePlateStart")){
                    if(player.getLocation().getBlock().getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE) || player.getLocation().getBlock().getType().equals(Material.HEAVY_WEIGHTED_PRESSURE_PLATE) || player.getLocation().getBlock().getType().equals(Material.STONE_PRESSURE_PLATE)){
                        if(plugin.getConfig().getBoolean("JumpAndRun.enabled")){
                            JumpAndRun jumpAndRun = new JumpAndRun(player,plugin);
                            jumpAndRun.start(player);
                            JumpAndRun.getJumpAndRuns().put(player.getUniqueId(), jumpAndRun);
                        }else {
                            if(messagecooldown.containsKey(player.getName())){
                                if(messagecooldown.get(player.getName()) > System.currentTimeMillis()){
                                    return;
                                }
                            }
                            messagecooldown.put(player.getName(),System.currentTimeMillis()+5000);
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "JnrDisabled"));
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if (JumpAndRun.getJumpAndRuns().containsKey(player.getUniqueId())) {
            JumpAndRun jumpAndRun = JumpAndRun.getJumpAndRuns().get(player.getUniqueId());
            jumpAndRun.stop(player);
        }
    }

}
