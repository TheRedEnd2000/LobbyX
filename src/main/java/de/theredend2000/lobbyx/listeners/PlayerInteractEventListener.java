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
                if (event.getClickedBlock().getType() == Material.CHEST | event.getClickedBlock().getType() == Material.ENDER_CHEST | event.getClickedBlock().getType() == Material.TRAPPED_CHEST | event.getClickedBlock().getType() == Material.CRAFTING_TABLE | event.getClickedBlock().getType() == Material.FURNACE | event.getClickedBlock().getType() == Material.ENDER_CHEST | event.getClickedBlock().getType() == Material.ENCHANTING_TABLE | event.getClickedBlock().getType() == Material.ANVIL | event.getClickedBlock().getType() == Material.BLUE_BED | event.getClickedBlock().getType() == Material.BLACK_BED | event.getClickedBlock().getType() == Material.BROWN_BED | event.getClickedBlock().getType() == Material.CYAN_BED | event.getClickedBlock().getType() == Material.GRAY_BED | event.getClickedBlock().getType() == Material.GREEN_BED | event.getClickedBlock().getType() == Material.LIGHT_BLUE_BED | event.getClickedBlock().getType() == Material.LIGHT_GRAY_BED | event.getClickedBlock().getType() == Material.LIME_BED | event.getClickedBlock().getType() == Material.MAGENTA_BED | event.getClickedBlock().getType() == Material.ORANGE_BED | event.getClickedBlock().getType() == Material.PINK_BED | event.getClickedBlock().getType() == Material.PURPLE_BED | event.getClickedBlock().getType() == Material.RED_BED | event.getClickedBlock().getType() == Material.WHITE_BED | event.getClickedBlock().getType() == Material.YELLOW_BED | event.getClickedBlock().getType() == Material.JUKEBOX | event.getClickedBlock().getType() == Material.BEACON | event.getClickedBlock().getType() == Material.DISPENSER | event.getClickedBlock().getType() == Material.LEVER | event.getClickedBlock().getType() == Material.STONE_BUTTON | event.getClickedBlock().getType() == Material.ACACIA_BUTTON | event.getClickedBlock().getType() == Material.BIRCH_BUTTON | event.getClickedBlock().getType() == Material.DARK_OAK_BUTTON | event.getClickedBlock().getType() == Material.JUNGLE_BUTTON | event.getClickedBlock().getType() == Material.OAK_BUTTON | event.getClickedBlock().getType() == Material.SPRUCE_BUTTON | event.getClickedBlock().getType() == Material.DAYLIGHT_DETECTOR | event.getClickedBlock().getType() == Material.HOPPER | event.getClickedBlock().getType() == Material.DROPPER | event.getClickedBlock().getType() == Material.BREWING_STAND | event.getClickedBlock().getType() == Material.COMPARATOR | event.getClickedBlock().getType() == Material.REPEATER | event.getClickedBlock().getType() == Material.DRAGON_EGG | event.getClickedBlock().getType() == Material.NOTE_BLOCK | event.getClickedBlock().getType() == Material.FLOWER_POT) {
                    if (!plugin.getBuildPlayers().contains(player)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
