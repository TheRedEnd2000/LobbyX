package de.theredend2000.lobbyx.listeners.itemListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class LobbySelectorListener implements Listener {

    private Main plugin;
    private ArrayList<Player> worldCreatorPlayers;

    public LobbySelectorListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
        worldCreatorPlayers = new ArrayList<>();
    }

    @EventHandler
    public void onInteractWithGadgetsEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
                if(event.getItem() != null && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasLocalizedName()){
                    if(Objects.requireNonNull(event.getItem().getItemMeta()).getLocalizedName().equals("lobbyx.lobbyselector")) {
                        if (plugin.getConfig().getBoolean("LobbySelector.enabled")) {
                            plugin.getLobbySelectorManager().createLobbySelector(player);
                        } else
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "LobbySelectorDisabled"));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.LobbySelectorInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    if(event.getCurrentItem().getItemMeta().getLocalizedName().equals("ls.create")){
                        plugin.getLobbySelectorManager().createWorldCreatorInventory(player);
                        return;
                    }
                    if(event.getAction().equals(InventoryAction.PICKUP_HALF) && player.hasPermission(Objects.requireNonNull(plugin.getConfig().getString("Permissions.MaintenanceModsPermission")))){
                        plugin.getLobbySelectorManager().createManageWorldSelector(player,event.getCurrentItem().getItemMeta().getLocalizedName());
                        return;
                    }
                    World world = Bukkit.getWorld(event.getCurrentItem().getItemMeta().getLocalizedName());
                    if(world == null){
                        player.sendMessage("§4§lThere was an error. Please try again.");
                        player.closeInventory();
                        return;
                    }
                    if(plugin.getConfig().getBoolean("Lobby_Worlds."+world.getName()+".maintenance")){
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"WorldInMaintenance"));
                        player.closeInventory();
                        return;
                    }
                    if(world.getName().equalsIgnoreCase(player.getWorld().getName())){
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"LobbySelectorAlreadyInThisWorld"));
                        player.closeInventory();
                        return;
                    }
                    ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin,"Locations.Lobby."+world.getName());
                    if(locationUtil.loadLocation() != null){
                        player.teleport(locationUtil.loadLocation());
                    }else{
                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NoLobbySpawn"));
                        return;
                    }
                    plugin.getSetPlayerLobbyManager().setPlayerInLobby(player);
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.WorldCreatorInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "wc.back":
                            plugin.getLobbySelectorManager().createLobbySelector(player);
                            plugin.yaml.set("WorldCreator."+player.getName(),null);
                            plugin.saveData();
                            break;
                        case "wc.normal":
                            plugin.yaml.set("WorldCreator."+player.getName()+".Type","FLAT");
                            plugin.saveData();
                            plugin.getLobbySelectorManager().createWorldCreatorInventory(player);
                            break;
                        case "wc.flat":
                            plugin.yaml.set("WorldCreator."+player.getName()+".Type","AMPLIFIED");
                            plugin.saveData();
                            plugin.getLobbySelectorManager().createWorldCreatorInventory(player);
                            break;
                        case "wc.amplified":
                            plugin.yaml.set("WorldCreator."+player.getName()+".Type","NORMAL");
                            plugin.saveData();
                            plugin.getLobbySelectorManager().createWorldCreatorInventory(player);
                            break;
                        case "wc.name":
                            player.closeInventory();
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"EnterWorldNameInChat"));
                            worldCreatorPlayers.add(player);
                            break;
                        case "wc.create":
                            if(plugin.yaml.getString("WorldCreator."+player.getName()+".Name").equals("null")){
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"NoWorldName"));
                                return;
                            }
                            String name = plugin.yaml.getString("WorldCreator."+player.getName()+".Name");
                            for(World worlds : Bukkit.getWorlds()) {
                                if (worlds.getName().equals(name)) {
                                    player.sendMessage(Util.getMessage(Util.getLocale(player), "WorldAlreadyExists").replaceAll("%WORLD_NAME%", name));
                                    return;
                                }
                            }
                            player.closeInventory();
                            assert name != null;
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"CreatingWorld").replaceAll("%WORLD_NAME%",name));
                            WorldCreator worldCreator = new WorldCreator(name);
                            switch (Objects.requireNonNull(plugin.yaml.getString("WorldCreator." + player.getName() + ".Type"))){
                                case "NORMAL":
                                    worldCreator.type(WorldType.NORMAL);
                                    break;
                                case "FLAT":
                                    worldCreator.type(WorldType.FLAT);
                                    break;
                                case "AMPLIFIED":
                                    worldCreator.type(WorldType.AMPLIFIED);
                                    break;
                                default:
                                    worldCreator.type(WorldType.NORMAL);
                                    break;
                            }
                            worldCreator.createWorld();
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"FinishCreatingWorld").replaceAll("%WORLD_NAME%",name));
                            plugin.yaml.set("WorldCreator."+player.getUniqueId(),null);
                            plugin.saveData();
                            plugin.getConfig().set("Lobby_Worlds."+name+".maintenance", false);
                            plugin.saveConfig();
                            plugin.addLobbyWorlds();
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.WorldManageInventoryTitle")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    String worldName = event.getInventory().getItem(4).getItemMeta().getLocalizedName();
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "wm.back":
                            plugin.getLobbySelectorManager().createLobbySelector(player);
                            break;
                        case "wm.maintenance":
                            plugin.getConfig().set("Lobby_Worlds."+worldName+".maintenance", !plugin.getConfig().getBoolean("Lobby_Worlds."+worldName+".maintenance"));
                            plugin.saveConfig();
                            plugin.getLobbySelectorManager().createManageWorldSelector(player,worldName);
                            if(plugin.getConfig().getBoolean("Lobby_Worlds."+worldName+".maintenance")){
                                World currentWorld = Bukkit.getWorld(worldName);
                                for(Player players : currentWorld.getPlayers()){
                                    new BukkitRunnable() {
                                        int worldID = 0;
                                        @Override
                                        public void run() {
                                            if(plugin.getLobbyWorlds().size() <= worldID){
                                                cancel();
                                                player.sendMessage(Util.getMessage(Util.getLocale(player),"NoFreeWorldsFound"));
                                                return;
                                            }
                                            World newWorld = plugin.getLobbyWorlds().get(worldID);
                                            if(!plugin.getConfig().getBoolean("Lobby_Worlds."+newWorld.getName()+".maintenance")){
                                                cancel();
                                                ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin,"Locations.Lobby."+newWorld.getName());
                                                if(locationUtil.loadLocation() != null){
                                                    players.teleport(locationUtil.loadLocation());
                                                    players.sendMessage(Util.getMessage(Util.getLocale(players),"WorldSetInMaintenance"));
                                                }else
                                                    players.sendMessage(Util.getMessage(Util.getLocale(players),"LobbyInNotSet"));
                                            }else
                                                worldID++;
                                        }
                                    }.runTaskTimer(plugin,0,1);
                                }
                            }
                            break;
                        case "wm.teleport":
                            player.teleport(Bukkit.getWorld(worldName).getSpawnLocation());
                            break;
                        case "wm.delete":
                            if(!player.getName().equals("-")){
                                player.sendMessage("§4This feature is coming soon.");
                                return;
                            }
                            plugin.getLobbySelectorManager().createDeleteLobby(player,worldName);
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals("§cConfirm deletion.")){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    String worldName = event.getInventory().getItem(13).getItemMeta().getLocalizedName();
                    switch (event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "cl.confirm":
                            for(Player players : Bukkit.getWorld(worldName).getPlayers()){
                                players.kickPlayer(plugin.getConfig().getString("Prefix").replaceAll("&","§")+"§cThere was an world error.\nPlease rejoin!");
                            }
                            player.sendMessage("Deleted");
                            break;
                        case "cl.cancel":
                            player.closeInventory();
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"CancelWorldDeletion"));
                            break;
                    }
                }
            }
        }
    }


    @EventHandler
    public void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        if(worldCreatorPlayers.contains(player)){
            event.setCancelled(true);
            if(event.getMessage().equalsIgnoreCase("cancel")){
                player.sendMessage(Util.getMessage(Util.getLocale(player),"CanceledAction"));
                plugin.getLobbySelectorManager().createWorldCreatorInventory(player);
                return;
            }
            if(event.getMessage().contains(".") || event.getMessage().contains(" ")){
                player.sendMessage(Util.getMessage(Util.getLocale(player),"CantContain"));
                plugin.getLobbySelectorManager().createWorldCreatorInventory(player);
                return;
            }
            plugin.yaml.set("WorldCreator."+player.getName()+".Name",event.getMessage());
            plugin.saveData();
            player.sendMessage(Util.getMessage(Util.getLocale(player),"NewWorldName"));
            worldCreatorPlayers.remove(player);
            plugin.getLobbySelectorManager().createWorldCreatorInventory(player);
        }
    }

}
