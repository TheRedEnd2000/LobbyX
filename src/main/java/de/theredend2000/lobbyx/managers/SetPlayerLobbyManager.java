package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ConfigLocationUtil;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;

public class SetPlayerLobbyManager {

    private Main plugin;

    public SetPlayerLobbyManager(Main plugin){
        this.plugin = plugin;
    }

    public void setPlayerInLobby(Player player){
        if(plugin.getLobbyWorlds().contains(player.getWorld())){
            if(plugin.yaml.getBoolean("Settings."+player.getUniqueId()+".PlayerHidden")){
                for(Player lobbyPlayer : player.getWorld().getPlayers()){
                    player.hidePlayer(lobbyPlayer);
                    if(plugin.yaml.getBoolean("Settings."+lobbyPlayer.getUniqueId()+".PlayerHidden")){
                        lobbyPlayer.hidePlayer(player);
                    }
                }
            }
            player.setFoodLevel(20);
            //HEAL
            setItems(player);
            checkConfig(player);
            setGamemode(player);
            updateLobbyInventory();

            if(!plugin.getLobbyWorlds().isEmpty()) {
                new BukkitRunnable() {
                    int worldID = 0;
                    @Override
                    public void run() {
                        if(plugin.getLobbyWorlds().contains(player.getWorld()) && !plugin.getConfig().getBoolean("Lobby_Worlds."+player.getWorld().getName()+".maintenance")){
                            ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin, "Locations.Lobby." + player.getWorld().getName());
                            if (locationUtil.loadLocation() != null) {
                                player.teleport(locationUtil.loadLocation());
                            } else {
                                for (Player admins : Bukkit.getOnlinePlayers()) {
                                    if (admins.isOp()) {
                                        admins.sendMessage("§cThe Lobby for the world " + player.getWorld().getName() + " is not set. Please do this!");
                                    }
                                }
                            }
                            cancel();
                            return;
                        }else{
                            if(plugin.getLobbyWorlds().size() <= worldID){
                                cancel();
                                if(player.isOp())
                                    player.sendMessage(Util.getMessage(Util.getLocale(player),"NoFreeWorldsFound"));
                                else
                                    player.kickPlayer(plugin.getConfig().getString("Prefix").replaceAll("&","§")+"§cThere are no free lobbys!");
                                return;
                            }
                            World newWorld = plugin.getLobbyWorlds().get(worldID);
                            if(!plugin.getConfig().getBoolean("Lobby_Worlds."+newWorld.getName()+".maintenance")){
                                cancel();
                                ConfigLocationUtil locationUtil = new ConfigLocationUtil(plugin,"Locations.Lobby."+newWorld.getName());
                                if(locationUtil.loadLocation() != null){
                                    player.teleport(locationUtil.loadLocation());
                                    player.sendMessage(Util.getMessage(Util.getLocale(player),"WorldSetInMaintenance"));
                                }else
                                    player.sendMessage(Util.getMessage(Util.getLocale(player),"LobbyInNotSet"));
                            }else
                                worldID++;
                        }
                    }
                }.runTaskTimer(plugin,0,1);
            }

        }
    }

    public void setItems(Player player) {
        player.getInventory().clear();
        if(plugin.getConfig().getBoolean("Items.Gadgets.enabled"))
            player.getInventory().setItem(plugin.getConfig().getInt("Items.Gadgets.slot"),new ItemBuilder(plugin.getMaterial("Items.Gadgets.material")).setDisplayname(plugin.getConfig().getString("Items.Gadgets.displayname").replaceAll("&","§")).setLocalizedName("lobbyx.gadgets").build());
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwner(player.getName());
        skullMeta.setDisplayName(plugin.getConfig().getString("Items.Profile.displayname").replaceAll("&","§"));
        skullMeta.setLocalizedName("lobbyx.profile");
        playerHead.setItemMeta(skullMeta);
        if(plugin.getConfig().getBoolean("Items.Profile.enabled"))
            player.getInventory().setItem(plugin.getConfig().getInt("Items.Profile.slot"),playerHead);
        setPlayerHider(player);
        if(plugin.getConfig().getBoolean("Items.Teleporter.enabled"))
            player.getInventory().setItem(plugin.getConfig().getInt("Items.Teleporter.slot"),new ItemBuilder(plugin.getMaterial("Items.Teleporter.material")).setDisplayname(plugin.getConfig().getString("Items.Teleporter.displayname").replaceAll("&","§")).setLocalizedName("lobbyx.teleporter").build());
        if(plugin.getConfig().getBoolean("Items.Selector.enabled"))
            player.getInventory().setItem(plugin.getConfig().getInt("Items.Selector.slot"),new ItemBuilder(plugin.getMaterial("Items.Selector.material")).setDisplayname(plugin.getConfig().getString("Items.Selector.displayname").replaceAll("&","§")).setLocalizedName("lobbyx.lobbyselector").build());
        setPlayerSelectedItems(player);
    }

    public void setPlayerHider(Player player){
        if(plugin.yaml.getBoolean("Settings."+player.getUniqueId()+".PlayerHidden")){
            if(plugin.getConfig().getBoolean("Items.Hider_OFF.enabled"))
                player.getInventory().setItem(plugin.getConfig().getInt("Items.Hider_OFF.slot"),new ItemBuilder(plugin.getMaterial("Items.Hider_OFF.material")).setDisplayname(plugin.getConfig().getString("Items.Hider_OFF.displayname").replaceAll("&","§")).setLocalizedName("lobbyx.player_hider").build());
        }else {
            if(plugin.getConfig().getBoolean("Items.Hider_ON.enabled"))
                player.getInventory().setItem(plugin.getConfig().getInt("Items.Hider_ON.slot"),new ItemBuilder(plugin.getMaterial("Items.Hider_ON.material")).setDisplayname(plugin.getConfig().getString("Items.Hider_ON.displayname").replaceAll("&","§")).setLocalizedName("lobbyx.player_hider").build());
        }
    }

    public void setPlayerSelectedItems(Player player){
        for(String items : plugin.gadgetsYaml.getConfigurationSection("Gadgets.SpecialItems").getKeys(false)){
            String checkPath = plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Inv");
            if(items.equals(checkPath)){
                String gadgetName = plugin.gadgetsYaml.getString("Gadgets.SpecialItems."+items+".name");
                Material material = plugin.getGadgetsMaterial("Gadgets.SpecialItems."+items+".item");
                player.getInventory().setItem(5, new ItemBuilder(material).setDisplayname(gadgetName.replaceAll("&","§")).build());
            }
        }
        for(String heads : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Heads").getKeys(false)){
            String checkPath = plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Head");
            if(heads.equals(checkPath)){
                String headName = plugin.gadgetsYaml.getString("Gadgets.Heads."+heads+".name");
                String headTexture = plugin.gadgetsYaml.getString("Gadgets.Heads."+heads+".texture");
                player.getInventory().setHelmet(new ItemBuilder(Material.PLAYER_HEAD).setDisplayname(headName.replaceAll("&","§")).setSkullOwner(Main.getTexture(headTexture)).build());
            }
        }
        for(String heads : plugin.gadgetsYaml.getConfigurationSection("Gadgets.AnimatedHeads").getKeys(false)){
            String checkPath = plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Head");
            if(heads.equals(checkPath)){
                String headName = plugin.gadgetsYaml.getString("Gadgets.AnimatedHeads."+heads+".name");
                String headTexture = plugin.gadgetsYaml.getString("Gadgets.AnimatedHeads."+heads+".texture");
                player.getInventory().setHelmet(new ItemBuilder(Material.PLAYER_HEAD).setDisplayname(headName.replaceAll("&","§")).setSkullOwner(Main.getTexture(headTexture)).build());
            }
        }
        for(String items : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Armor").getKeys(false)){
            String checkPath = plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Chest");
            if(items.equals(checkPath)){
                Material helmetMaterial = plugin.getGadgetsMaterial("Gadgets.Armor."+items+".Helmet.material");
                String helmetName = plugin.gadgetsYaml.getString("Gadgets.Armor."+items+".Helmet.name");
                String helmetTexture = plugin.gadgetsYaml.getString("Gadgets.Armor."+items+".Helmet.texture");
                Color helmetColor = plugin.gadgetsYaml.getColor("Gadgets.Armor."+items+".Helmet.color");
                player.getInventory().setHelmet(new ItemBuilder(helmetMaterial).setColor(helmetColor).setSkullOwner(helmetTexture != null ? Main.getTexture(helmetTexture) : null).setDisplayname(helmetName.replaceAll("&","§")).build());

                Material chestplateMaterial = plugin.getGadgetsMaterial("Gadgets.Armor."+items+".Chestplate.material");
                String chestplateName = plugin.gadgetsYaml.getString("Gadgets.Armor."+items+".Chestplate.name");
                Color chestplateColor = plugin.gadgetsYaml.getColor("Gadgets.Armor."+items+".Chestplate.color");
                player.getInventory().setChestplate(new ItemBuilder(chestplateMaterial).setColor(chestplateColor).setDisplayname(chestplateName.replaceAll("&","§")).build());

                Material legginsMaterial = plugin.getGadgetsMaterial("Gadgets.Armor."+items+".Leggins.material");
                String legginsName = plugin.gadgetsYaml.getString("Gadgets.Armor."+items+".Leggins.name");
                Color legginsColor = plugin.gadgetsYaml.getColor("Gadgets.Armor."+items+".Leggins.color");
                player.getInventory().setLeggings(new ItemBuilder(legginsMaterial).setColor(legginsColor).setDisplayname(legginsName.replaceAll("&","§")).build());

                Material bootsMaterial = plugin.getGadgetsMaterial("Gadgets.Armor."+items+".Boots.material");
                String bootsName = plugin.gadgetsYaml.getString("Gadgets.Armor."+items+".Boots.name");
                Color bootsColor = plugin.gadgetsYaml.getColor("Gadgets.Armor."+items+".Boots.color");
                player.getInventory().setBoots(new ItemBuilder(bootsMaterial).setColor(bootsColor).setDisplayname(bootsName.replaceAll("&","§")).build());
            }
        }
    }

    private void checkConfig(Player player){
        if(!plugin.yaml.contains("Settings."+player.getUniqueId()+".FriendSort")){
            plugin.yaml.set("Settings."+player.getUniqueId()+".FriendSort","All");
        }
        if(!plugin.yaml.contains("Selected_Items."+player.getUniqueId())){
            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Inv","null");
            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Particle","null");
            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Head","null");
            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Chest","null");
            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Leggins","null");
            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Boots","null");
        }
        plugin.getPlayerDataManager().setYaml(player);
        if(!plugin.getPlayerDataManager().playerDataYaml.contains("Settings")){
            plugin.getPlayerDataManager().playerDataYaml.set("Settings.Chat_Messages",true);
            plugin.getPlayerDataManager().playerDataYaml.set("Settings.Msg_Messages","EVERYONE");
            plugin.getPlayerDataManager().playerDataYaml.set("Settings.FriendRequests",true);
            plugin.getPlayerDataManager().playerDataYaml.set("Settings.ClanRequests",true);
            plugin.getPlayerDataManager().playerDataYaml.set("Settings.Join_Messages",true);
            plugin.getPlayerDataManager().playerDataYaml.set("Settings.Leave_Messages",true);
            plugin.getPlayerDataManager().playerDataYaml.set("Settings.CoinsAPI",true);
            plugin.getPlayerDataManager().save(player);
        }
        plugin.saveData();
    }

    private void setGamemode(Player player){
        String weatherType = plugin.getConfig().getString("Settings.DefaultGamemode").toUpperCase();
        switch (weatherType) {
            case ("SURVIVAL"):
                player.setGameMode(GameMode.SURVIVAL);
                break;
            case ("SPECTATOR"):
                player.setGameMode(GameMode.SPECTATOR);
                break;
            case ("CREATIVE"):
                player.setGameMode(GameMode.CREATIVE);
                break;
            case ("ADVENTURE"):
                player.setGameMode(GameMode.ADVENTURE);
                break;
        }
    }

    public void updateLobbyInventory(){
        for(Player lobbyPlayer : Bukkit.getOnlinePlayers()){
            if(lobbyPlayer.getOpenInventory().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.LobbySelectorInventoryTitle")).replaceAll("&","§"))){
                plugin.getLobbySelectorManager().createLobbySelector(lobbyPlayer);
            }
        }
    }

}
