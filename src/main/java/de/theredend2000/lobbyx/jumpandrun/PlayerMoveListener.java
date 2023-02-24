package de.theredend2000.lobbyx.jumpandrun;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private Main plugin;

    public PlayerMoveListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
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
            if(!JumpAndRun.getJumpAndRuns().containsKey(player.getUniqueId())){
                if (plugin.getLobbyWorlds().contains(player.getWorld())) {
                    if(player.getLocation().getBlock().getType().equals(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)){
                        jumpAndRun.start(player);
                        JumpAndRun.getJumpAndRuns().put(player.getUniqueId(), jumpAndRun);
                    }
                }
            }
        }
    }

}
