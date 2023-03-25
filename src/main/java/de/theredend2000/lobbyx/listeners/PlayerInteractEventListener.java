package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractEventListener implements Listener {

    private Main plugin;

    public PlayerInteractEventListener(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (plugin.getLobbyWorlds().contains(p.getWorld())) {
            if (e.getRightClicked().getType() == EntityType.ITEM_FRAME && !plugin.getBuildPlayers().contains(p)) {
                e.setCancelled(true);
            }
            if (p.getItemInHand().getType() == Material.NAME_TAG && !plugin.getBuildPlayers().contains(p)) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (plugin.getLobbyWorlds().contains(player.getWorld())) {
            if (event.getAction() == Action.PHYSICAL) {
                if (!plugin.getBuildPlayers().contains(player)) {
                    event.setCancelled(true);
                }
            }
            if (event.getAction() == Action.LEFT_CLICK_BLOCK | event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(!plugin.getBuildPlayers().contains(player)) {
                    if (plugin.getConfig().getBoolean("IntractableItems.RedStoneItems")) {
                        if (event.getClickedBlock().getType() == Material.DAYLIGHT_DETECTOR | event.getClickedBlock().getType() == Material.LEVER | event.getClickedBlock().getType() == Material.COMPARATOR | event.getClickedBlock().getType() == Material.REPEATER | event.getClickedBlock().getType() == Material.DROPPER | event.getClickedBlock().getType() == Material.HOPPER | event.getClickedBlock().getType() == Material.REDSTONE) {
                            event.setCancelled(true);
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Doors")) {
                        if (event.getClickedBlock().getType() == Material.OAK_DOOR | event.getClickedBlock().getType() == Material.IRON_DOOR | event.getClickedBlock().getType() == Material.WARPED_DOOR | event.getClickedBlock().getType() == Material.SPRUCE_DOOR | event.getClickedBlock().getType() == Material.BIRCH_DOOR | event.getClickedBlock().getType() == Material.JUNGLE_DOOR | event.getClickedBlock().getType() == Material.ACACIA_DOOR | event.getClickedBlock().getType() == Material.DARK_OAK_DOOR | event.getClickedBlock().getType() == Material.MANGROVE_DOOR | event.getClickedBlock().getType() == Material.CRIMSON_DOOR) {
                            event.setCancelled(true);
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Buttons")) {
                        if (event.getClickedBlock().getType() == Material.STONE_BUTTON | event.getClickedBlock().getType() == Material.MANGROVE_BUTTON | event.getClickedBlock().getType() == Material.DARK_OAK_BUTTON | event.getClickedBlock().getType() == Material.POLISHED_BLACKSTONE_BUTTON | event.getClickedBlock().getType() == Material.OAK_BUTTON | event.getClickedBlock().getType() == Material.SPRUCE_BUTTON | event.getClickedBlock().getType() == Material.BIRCH_BUTTON | event.getClickedBlock().getType() == Material.JUNGLE_BUTTON | event.getClickedBlock().getType() == Material.ACACIA_BUTTON | event.getClickedBlock().getType() == Material.CRIMSON_BUTTON | event.getClickedBlock().getType() == Material.WARPED_BUTTON) {
                            event.setCancelled(true);
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Furnaces")) {
                        if (event.getClickedBlock().getType() == Material.FURNACE | event.getClickedBlock().getType() == Material.SMOKER | event.getClickedBlock().getType() == Material.BLAST_FURNACE) {
                            event.setCancelled(true);
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.WorkingStations")) {
                        if (event.getClickedBlock().getType() == Material.LODESTONE | event.getClickedBlock().getType() == Material.BELL | event.getClickedBlock().getType() == Material.STONECUTTER | event.getClickedBlock().getType() == Material.CARTOGRAPHY_TABLE | event.getClickedBlock().getType() == Material.GRINDSTONE | event.getClickedBlock().getType() == Material.SMOKER | event.getClickedBlock().getType() == Material.BARREL | event.getClickedBlock().getType() == Material.SMITHING_TABLE | event.getClickedBlock().getType() == Material.LOOM) {
                            event.setCancelled(true);
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Boats")) {
                        if (event.getClickedBlock().getType() == Material.OAK_BOAT | event.getClickedBlock().getType() == Material.OAK_CHEST_BOAT | event.getClickedBlock().getType() == Material.SPRUCE_BOAT | event.getClickedBlock().getType() == Material.CARTOGRAPHY_TABLE | event.getClickedBlock().getType() == Material.GRINDSTONE | event.getClickedBlock().getType() == Material.SMOKER | event.getClickedBlock().getType() == Material.BARREL | event.getClickedBlock().getType() == Material.SMITHING_TABLE | event.getClickedBlock().getType() == Material.LOOM) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
