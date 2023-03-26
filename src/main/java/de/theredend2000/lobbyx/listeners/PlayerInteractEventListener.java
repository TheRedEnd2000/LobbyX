package de.theredend2000.lobbyx.listeners;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.FlowerPot;

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
            if(!plugin.getBuildPlayers().contains(p)){
                if (e.getRightClicked().getType() == EntityType.ITEM_FRAME && !plugin.getBuildPlayers().contains(p)) {
                    e.setCancelled(true);
                }
                if (p.getItemInHand().getType() == Material.NAME_TAG && !plugin.getBuildPlayers().contains(p)) {
                    e.setCancelled(true);
                }
                if (plugin.getConfig().getBoolean("IntractableItems.Minecarts")) {
                    if (e.getRightClicked().getType() == EntityType.MINECART | e.getRightClicked().getType() == EntityType.MINECART_CHEST | e.getRightClicked().getType() == EntityType.MINECART_HOPPER) {
                        e.setCancelled(true);
                    }
                }
                if (plugin.getConfig().getBoolean("IntractableItems.Boats")) {
                    if (e.getRightClicked().getType() == EntityType.BOAT) {
                        e.setCancelled(true);
                    }
                    if (plugin.getServer().getVersion().contains("1.19")) {
                        if (e.getRightClicked().getType() == EntityType.CHEST_BOAT) {
                            e.setCancelled(true);
                        }
                    }
                }
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
                        if (event.getClickedBlock().getType() == Material.DAYLIGHT_DETECTOR | event.getClickedBlock().getType() == Material.LEVER | event.getClickedBlock().getType() == Material.COMPARATOR | event.getClickedBlock().getType() == Material.REPEATER | event.getClickedBlock().getType() == Material.DROPPER | event.getClickedBlock().getType() == Material.DISPENSER | event.getClickedBlock().getType() == Material.HOPPER | event.getClickedBlock().getType() == Material.REDSTONE_WIRE) {
                            event.setCancelled(true);
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Doors")) {
                        if (event.getClickedBlock().getType() == Material.OAK_DOOR | event.getClickedBlock().getType() == Material.IRON_DOOR | event.getClickedBlock().getType() == Material.SPRUCE_DOOR | event.getClickedBlock().getType() == Material.BIRCH_DOOR | event.getClickedBlock().getType() == Material.JUNGLE_DOOR | event.getClickedBlock().getType() == Material.ACACIA_DOOR | event.getClickedBlock().getType() == Material.DARK_OAK_DOOR) {
                            event.setCancelled(true);
                            if (plugin.getServer().getVersion().contains("1.16") || plugin.getServer().getVersion().contains("1.17") || plugin.getServer().getVersion().contains("1.18") || plugin.getServer().getVersion().contains("1.19")) {
                                if (event.getClickedBlock().getType() == Material.CRIMSON_DOOR | event.getClickedBlock().getType() == Material.WARPED_DOOR) {
                                    event.setCancelled(true);
                                    if (plugin.getServer().getVersion().contains("1.19")) {
                                        if (event.getClickedBlock().getType() == Material.MANGROVE_DOOR) {
                                            event.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Buttons")) {
                        if (event.getClickedBlock().getType() == Material.STONE_BUTTON | event.getClickedBlock().getType() == Material.DARK_OAK_BUTTON | event.getClickedBlock().getType() == Material.OAK_BUTTON | event.getClickedBlock().getType() == Material.SPRUCE_BUTTON | event.getClickedBlock().getType() == Material.BIRCH_BUTTON | event.getClickedBlock().getType() == Material.JUNGLE_BUTTON | event.getClickedBlock().getType() == Material.ACACIA_BUTTON) {
                            event.setCancelled(true);
                        }
                        if(plugin.getServer().getVersion().contains("1.19")){
                            if(event.getClickedBlock().getType() == Material.MANGROVE_BUTTON){
                                event.setCancelled(true);
                            }
                        }
                        if(plugin.getServer().getVersion().contains("1.16") || plugin.getServer().getVersion().contains("1.17") || plugin.getServer().getVersion().contains("1.18") || plugin.getServer().getVersion().contains("1.19")){
                            if(event.getClickedBlock().getType() == Material.POLISHED_BLACKSTONE_BUTTON | event.getClickedBlock().getType() == Material.CRIMSON_BUTTON | event.getClickedBlock().getType() == Material.WARPED_BUTTON){
                                event.setCancelled(true);
                            }
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Furnaces")) {
                        if (event.getClickedBlock().getType() == Material.FURNACE | event.getClickedBlock().getType() == Material.SMOKER | event.getClickedBlock().getType() == Material.BLAST_FURNACE) {
                            event.setCancelled(true);
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.WorkingStations")) {
                        if (event.getClickedBlock().getType() == Material.LODESTONE | event.getClickedBlock().getType() == Material.BELL | event.getClickedBlock().getType() == Material.STONECUTTER | event.getClickedBlock().getType() == Material.CARTOGRAPHY_TABLE | event.getClickedBlock().getType() == Material.GRINDSTONE | event.getClickedBlock().getType() == Material.BARREL | event.getClickedBlock().getType() == Material.SMITHING_TABLE | event.getClickedBlock().getType() == Material.LOOM) {
                            event.setCancelled(true);
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Chests")) {
                        if (event.getClickedBlock().getType() == Material.CHEST | event.getClickedBlock().getType() == Material.TRAPPED_CHEST | event.getClickedBlock().getType() == Material.ENDER_CHEST) {
                            event.setCancelled(true);
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Fences_Gates")) {
                        if (event.getClickedBlock().getType() == Material.OAK_FENCE_GATE | event.getClickedBlock().getType() == Material.SPRUCE_FENCE_GATE | event.getClickedBlock().getType() == Material.BIRCH_FENCE_GATE | event.getClickedBlock().getType() == Material.JUNGLE_FENCE_GATE | event.getClickedBlock().getType() == Material.ACACIA_FENCE_GATE | event.getClickedBlock().getType() == Material.DARK_OAK_FENCE_GATE) {
                            event.setCancelled(true);
                            if (plugin.getServer().getVersion().contains("1.19")) {
                                if (event.getClickedBlock().getType() == Material.MANGROVE_FENCE_GATE) {
                                    event.setCancelled(true);
                                }
                            }
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Trapdoors")) {
                        if (event.getClickedBlock().getType() == Material.OAK_TRAPDOOR | event.getClickedBlock().getType() == Material.SPRUCE_TRAPDOOR | event.getClickedBlock().getType() == Material.BIRCH_TRAPDOOR | event.getClickedBlock().getType() == Material.JUNGLE_TRAPDOOR | event.getClickedBlock().getType() == Material.ACACIA_TRAPDOOR | event.getClickedBlock().getType() == Material.DARK_OAK_TRAPDOOR) {
                            event.setCancelled(true);
                        }
                        if (plugin.getServer().getVersion().contains("1.16") || plugin.getServer().getVersion().contains("1.17") || plugin.getServer().getVersion().contains("1.18") || plugin.getServer().getVersion().contains("1.19")) {
                            if (event.getClickedBlock().getType() == Material.CRIMSON_TRAPDOOR | event.getClickedBlock().getType() == Material.WARPED_TRAPDOOR) {
                                event.setCancelled(true);
                                if (plugin.getServer().getVersion().contains("1.19")) {
                                    if (event.getClickedBlock().getType() == Material.MANGROVE_TRAPDOOR) {
                                        event.setCancelled(true);
                                    }
                                }
                            }
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Candles")) {
                        if (plugin.getServer().getVersion().contains("1.17") || plugin.getServer().getVersion().contains("1.18") || plugin.getServer().getVersion().contains("1.19")) {
                            if (event.getClickedBlock().getType() == Material.CANDLE | event.getClickedBlock().getType() == Material.WHITE_CANDLE | event.getClickedBlock().getType() == Material.ORANGE_CANDLE | event.getClickedBlock().getType() == Material.MAGENTA_CANDLE | event.getClickedBlock().getType() == Material.LIGHT_BLUE_CANDLE | event.getClickedBlock().getType() == Material.YELLOW_CANDLE | event.getClickedBlock().getType() == Material.LIME_CANDLE | event.getClickedBlock().getType() == Material.PINK_CANDLE | event.getClickedBlock().getType() == Material.GRAY_CANDLE | event.getClickedBlock().getType() == Material.LIGHT_GRAY_CANDLE | event.getClickedBlock().getType() == Material.CYAN_CANDLE | event.getClickedBlock().getType() == Material.PURPLE_CANDLE | event.getClickedBlock().getType() == Material.BLUE_CANDLE | event.getClickedBlock().getType() == Material.BROWN_CANDLE | event.getClickedBlock().getType() == Material.GREEN_CANDLE | event.getClickedBlock().getType() == Material.RED_CANDLE | event.getClickedBlock().getType() == Material.BLACK_CANDLE) {
                                event.setCancelled(true);
                            }
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Other")) {
                        if (event.getClickedBlock().getType() == Material.BEACON | event.getClickedBlock().getType() == Material.ENCHANTING_TABLE | event.getClickedBlock().getType() == Material.NOTE_BLOCK | event.getClickedBlock().getType() == Material.JUKEBOX | event.getClickedBlock().getType() == Material.BREWING_STAND | event.getClickedBlock().getType() == Material.CAULDRON | event.getClickedBlock().getType() == Material.FLOWER_POT) {
                            event.setCancelled(true);
                            if (plugin.getServer().getVersion().contains("1.13") || plugin.getServer().getVersion().contains("1.14") || plugin.getServer().getVersion().contains("1.15") || plugin.getServer().getVersion().contains("1.16") || plugin.getServer().getVersion().contains("1.17") || plugin.getServer().getVersion().contains("1.18") || plugin.getServer().getVersion().contains("1.19")) {
                                if (event.getClickedBlock().getType() == Material.BEE_NEST | event.getClickedBlock().getType() == Material.BEEHIVE) {
                                    event.setCancelled(true);
                                    if (plugin.getServer().getVersion().contains("1.16") || plugin.getServer().getVersion().contains("1.17") || plugin.getServer().getVersion().contains("1.18") || plugin.getServer().getVersion().contains("1.19")) {
                                        if (event.getClickedBlock().getType() == Material.RESPAWN_ANCHOR) {
                                            event.setCancelled(true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Anvils")) {
                        if (event.getClickedBlock().getType() == Material.ANVIL | event.getClickedBlock().getType() == Material.DAMAGED_ANVIL | event.getClickedBlock().getType() == Material.CHIPPED_ANVIL) {
                            event.setCancelled(true);
                        }
                    }
                    if (plugin.getConfig().getBoolean("IntractableItems.Shulkers")) {
                        if (event.getClickedBlock().getType() == Material.SHULKER_BOX | event.getClickedBlock().getType() == Material.DAMAGED_ANVIL | event.getClickedBlock().getType() == Material.CHIPPED_ANVIL) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}

