package de.theredend2000.lobbyx.listeners.inventoryListeners;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;
import java.util.Objects;

public class GadgetsInventoryListener implements Listener {

    private Main plugin;

    public GadgetsInventoryListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onInventoryClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.GadgetsMainMenu")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "gadgets.main.close":
                            player.closeInventory();
                            break;
                        case "gadgets.main.heads":
                            plugin.getGadgetsMenuManager().createHeadsGadgetsInventory(player);
                            break;
                        case "gadgets.main.aheads":
                            plugin.getGadgetsMenuManager().createAHeadsGadgetsInventory(player);
                            break;
                        case"gadgets.main.specialItems":
                            if(player.isOp())
                                plugin.getGadgetsMenuManager().createItemGadgetsInventory(player);
                            else {
                                player.sendMessage("§cSpecial items are currently only available for operators. We ask for your understanding.");
                                player.closeInventory();
                            }
                            break;
                        case"gadgets.main.particle":
                            plugin.getGadgetsMenuManager().createParticleInventory(player);
                            break;
                        case"gadgets.main.armor":
                            plugin.getGadgetsMenuManager().createArmorInventory(player);
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.HeadInventory")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "gadgets.head.close":
                            player.closeInventory();
                            break;
                        case "gadgets.heads.reset":
                            if(player.getInventory().getHelmet() != null){
                                player.getInventory().setHelmet(null);
                                plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Head","null");
                                plugin.saveData();
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"ResetHead"));
                            }else{
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"FailedResetHead"));
                            }
                            break;
                        case "gadgets.heads.back":
                            plugin.getGadgetsMenuManager().createGadgetsInventory(player);
                            break;
                    }
                    for(String heads : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Heads").getKeys(false)){
                        if(event.getCurrentItem().getItemMeta().getLocalizedName().equals(heads)){
                            plugin.getPlayerDataManager().setYaml(player);
                            boolean isBought = plugin.getPlayerDataManager().playerDataYaml.getBoolean("Gadgets.Heads."+heads);
                            if(!isBought){
                                plugin.getGadgetsMenuManager().createGadgetsBugConfirm(player,heads);
                                return;
                            }
                            player.closeInventory();
                            String headName = plugin.gadgetsYaml.getString("Gadgets.Heads."+heads+".name");
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SelectHead").replaceAll("%HEAD%", headName.replaceAll("&","§")));
                            String headTexture = plugin.gadgetsYaml.getString("Gadgets.Heads."+heads+".texture");
                            player.getInventory().setHelmet(new ItemBuilder(Material.PLAYER_HEAD).setDisplayname(headName.replaceAll("&","§")).setSkullOwner(Main.getTexture(headTexture)).build());
                            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Head",heads);
                            plugin.saveData();
                        }
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.SpecialItemInventory")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem() != null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "gadgets.items.close":
                            player.closeInventory();
                            break;
                        case "gadgets.items.reset":
                            if(!plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Inv").equals("null")){
                                player.getInventory().setItem(5,null);
                                plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Inv","null");
                                plugin.saveData();
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"ResetGadget"));
                            }else{
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"FailedResetGadget"));
                            }
                            break;
                        case "gadgets.items.back":
                            plugin.getGadgetsMenuManager().createGadgetsInventory(player);
                            break;
                    }
                    for (String items : plugin.gadgetsYaml.getConfigurationSection("Gadgets.SpecialItems").getKeys(false)) {
                        if (event.getCurrentItem().getItemMeta().getLocalizedName().equals(items)) {
                            plugin.getPlayerDataManager().setYaml(player);
                            boolean isBought = plugin.getPlayerDataManager().playerDataYaml.getBoolean("Gadgets.SpecialItems." + items);
                            if (!isBought) {
                                plugin.getGadgetsMenuManager().createGadgetsBugConfirm(player, items);
                                return;
                            }
                            player.closeInventory();
                            ;
                            String itemName = plugin.gadgetsYaml.getString("Gadgets.SpecialItems." + items + ".name");
                            Material material = plugin.getGadgetsMaterial("Gadgets.SpecialItems." + items + ".item");
                            player.sendMessage(Util.getMessage(Util.getLocale(player), "SelectSpecialItems").replaceAll("%GADGET%", itemName.replaceAll("&", "§")));
                            player.getInventory().setItem(5, new ItemBuilder(material).setDisplayname(itemName.replaceAll("&", "§")).build());
                            plugin.yaml.set("Selected_Items." + player.getUniqueId() + ".Inv", items);
                            plugin.saveData();
                        }
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.ParticleInventory")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem() != null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "gadgets.particle.close":
                            player.closeInventory();
                            break;
                        case "gadgets.particle.reset":
                            if(!plugin.yaml.getString("Selected_Items."+player.getUniqueId()+".Particle").equals("null")){
                                plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Particle","null");
                                plugin.saveData();
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"ResetParticle"));
                            }else{
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"FailedResetParticle"));
                            }
                            break;
                        case "gadgets.particle.back":
                            plugin.getGadgetsMenuManager().createGadgetsInventory(player);
                            break;
                    }
                    for(String items : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Particle").getKeys(false)){
                        if(event.getCurrentItem().getItemMeta().getLocalizedName().equals(items)){
                            plugin.getPlayerDataManager().setYaml(player);
                            boolean isBought = plugin.getPlayerDataManager().playerDataYaml.getBoolean("Gadgets.Particle."+items);
                            if(!isBought){
                                plugin.getGadgetsMenuManager().createGadgetsBugConfirm(player,items);
                                return;
                            }
                            player.closeInventory();
                            String particleName = plugin.gadgetsYaml.getString("Gadgets.Particle."+items+".name");
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SelectParticleEffect").replaceAll("%PARTICLE%", particleName.replaceAll("&","§")));
                            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Particle",items);
                            plugin.saveData();
                        }
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.ArmorInventory")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem() != null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "gadgets.armor.close":
                            player.closeInventory();
                            break;
                        case "gadgets.armor.reset":
                            if(player.getInventory().getChestplate() != null){
                                player.getInventory().setArmorContents(null);
                                plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Head","null");
                                plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Chest","null");
                                plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Leggins","null");
                                plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Boots","null");
                                plugin.saveData();
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"ResetArmor"));
                            }else{
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"FailedResetArmor"));
                            }
                            break;
                        case "gadgets.armor.back":
                            plugin.getGadgetsMenuManager().createGadgetsInventory(player);
                            break;
                    }
                    for(String items : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Armor").getKeys(false)){
                        if(event.getCurrentItem().getItemMeta().getLocalizedName().equals(items)){
                            plugin.getPlayerDataManager().setYaml(player);
                            boolean isBought = plugin.getPlayerDataManager().playerDataYaml.getBoolean("Gadgets.Armor."+items);
                            if(!isBought){
                                plugin.getGadgetsMenuManager().createGadgetsBugConfirm(player,items);
                                return;
                            }
                            player.closeInventory();
                            if(items.equals("RainbowArmor")){
                                player.sendMessage("§cThis armor is currently disabled.");
                                return;
                            }
                            String armorName = plugin.gadgetsYaml.getString("Gadgets.Armor."+items+".name");
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SelectArmor").replaceAll("%ARMOR%", armorName.replaceAll("&","§")));
                            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Head",items);
                            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Chest",items);
                            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Leggins",items);
                            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Boots",items);
                            plugin.saveData();
                            Material helmetMaterial = plugin.getGadgetsMaterial("Gadgets.Armor."+items+".Helmet.material");
                            String helmetName = plugin.gadgetsYaml.getString("Gadgets.Armor."+items+".Helmet.name");
                            String helmetTexture = plugin.gadgetsYaml.getString("Gadgets.Armor."+items+".Helmet.texture");
                            int redHelmet = plugin.gadgetsYaml.getInt("Gadgets.Armor."+items+".Helmet.color.red");
                            int greenHelmet = plugin.gadgetsYaml.getInt("Gadgets.Armor."+items+".Helmet.color.green");
                            int blueHelmet = plugin.gadgetsYaml.getInt("Gadgets.Armor."+items+".Helmet.color.blue");
                            Color colorHelmet = Color.fromRGB(redHelmet, greenHelmet, blueHelmet);
                            if(helmetMaterial.equals(Material.PLAYER_HEAD))
                                player.getInventory().setHelmet(new ItemBuilder(helmetMaterial).setSkullOwner(Main.getTexture(helmetTexture)).setDisplayname(helmetName.replaceAll("&","§")).build());
                            else
                                player.getInventory().setHelmet(new ItemBuilder(helmetMaterial).setColor(helmetMaterial.equals(Material.LEATHER_HELMET) ? colorHelmet : null).setDisplayname(helmetName.replaceAll("&","§")).build());


                            Material chestplateMaterial = plugin.getGadgetsMaterial("Gadgets.Armor."+items+".Chestplate.material");
                            String chestplateName = plugin.gadgetsYaml.getString("Gadgets.Armor."+items+".Chestplate.name");
                            int redChestplate = plugin.gadgetsYaml.getInt("Gadgets.Armor."+items+".Chestplate.color.red");
                            int greenChestplate = plugin.gadgetsYaml.getInt("Gadgets.Armor."+items+".Chestplate.color.green");
                            int blueChestplate = plugin.gadgetsYaml.getInt("Gadgets.Armor."+items+".Chestplate.color.blue");
                            Color colorChestplate = Color.fromRGB(redChestplate, greenChestplate, blueChestplate);
                            player.getInventory().setChestplate(new ItemBuilder(chestplateMaterial).setColor(colorChestplate).setDisplayname(chestplateName.replaceAll("&","§")).build());

                            Material legginsMaterial = plugin.getGadgetsMaterial("Gadgets.Armor."+items+".Leggins.material");
                            String legginsName = plugin.gadgetsYaml.getString("Gadgets.Armor."+items+".Leggins.name");
                            int redLeggins = plugin.gadgetsYaml.getInt("Gadgets.Armor."+items+".Leggins.color.red");
                            int greenLeggins = plugin.gadgetsYaml.getInt("Gadgets.Armor."+items+".Leggins.color.green");
                            int blueLeggins = plugin.gadgetsYaml.getInt("Gadgets.Armor."+items+".Leggins.color.blue");
                            Color colorLeggins = Color.fromRGB(redLeggins, greenLeggins, blueLeggins);
                            player.getInventory().setLeggings(new ItemBuilder(legginsMaterial).setColor(colorLeggins).setDisplayname(legginsName.replaceAll("&","§")).build());

                            Material bootsMaterial = plugin.getGadgetsMaterial("Gadgets.Armor."+items+".Boots.material");
                            String bootsName = plugin.gadgetsYaml.getString("Gadgets.Armor."+items+".Boots.name");
                            int redBoots = plugin.gadgetsYaml.getInt("Gadgets.Armor."+items+".Boots.color.red");
                            int greenBoots = plugin.gadgetsYaml.getInt("Gadgets.Armor."+items+".Boots.color.green");
                            int blueBoots = plugin.gadgetsYaml.getInt("Gadgets.Armor."+items+".Boots.color.blue");
                            Color colorBoots = Color.fromRGB(redBoots, greenBoots, blueBoots);
                            player.getInventory().setBoots(new ItemBuilder(bootsMaterial).setColor(colorBoots).setDisplayname(bootsName.replaceAll("&","§")).build());
                        }
                    }
                }
            }
        }else if (event.getView().getTitle().equals("§7Confirm purchase?")){
            event.setCancelled(true);
            if (event.getCurrentItem() != null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "gadgets.buy.confirm":
                            plugin.getPlayerDataManager().setYaml(player);
                            for(String heads : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Heads").getKeys(false)){
                                String name = event.getInventory().getItem(13).getItemMeta().getLocalizedName();
                                if(heads.equals(name)){
                                    int coinCost = plugin.gadgetsYaml.getInt("Gadgets.Heads."+heads+".coins");
                                    if(plugin.getCoinManager().haveEnoughCoins(player, coinCost)) {
                                        plugin.getCoinManager().removeCoins(player, coinCost);
                                        plugin.getPlayerDataManager().playerDataYaml.set("Gadgets.Heads." + heads, true);
                                        plugin.getPlayerDataManager().save(player);
                                        player.sendMessage(Util.getMessage(Util.getLocale(player), "BuyNewGadget").replaceAll("%GADGET%", heads).replaceAll("%COINS%", String.valueOf(plugin.getCoinManager().getCoins(player))));
                                    }else
                                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NotEnoughCoins"));
                                }
                            }
                            for(String heads : plugin.gadgetsYaml.getConfigurationSection("Gadgets.AnimatedHeads").getKeys(false)){
                                String name = event.getInventory().getItem(13).getItemMeta().getLocalizedName();
                                if(heads.equals(name)){
                                    int coinCost = plugin.gadgetsYaml.getInt("Gadgets.AnimatedHeads."+heads+".coins");
                                    if(plugin.getCoinManager().haveEnoughCoins(player, coinCost)) {
                                        plugin.getCoinManager().removeCoins(player, coinCost);
                                        plugin.getPlayerDataManager().playerDataYaml.set("Gadgets.AnimatedHeads." + heads, true);
                                        plugin.getPlayerDataManager().save(player);
                                        player.sendMessage(Util.getMessage(Util.getLocale(player), "BuyNewGadget").replaceAll("%GADGET%", heads).replaceAll("%COINS%", String.valueOf(plugin.getCoinManager().getCoins(player))));
                                    }else
                                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NotEnoughCoins"));
                                }
                            }
                            for(String heads : plugin.gadgetsYaml.getConfigurationSection("Gadgets.SpecialItems").getKeys(false)){
                                String name = event.getInventory().getItem(13).getItemMeta().getLocalizedName();
                                if(heads.equals(name)){
                                    int coinCost = plugin.gadgetsYaml.getInt("Gadgets.SpecialItems."+heads+".coins");
                                    if(plugin.getCoinManager().haveEnoughCoins(player, coinCost)) {
                                        plugin.getCoinManager().removeCoins(player, coinCost);
                                        plugin.getPlayerDataManager().playerDataYaml.set("Gadgets.SpecialItems." + heads, true);
                                        plugin.getPlayerDataManager().save(player);
                                        player.sendMessage(Util.getMessage(Util.getLocale(player), "BuyNewGadget").replaceAll("%GADGET%", heads).replaceAll("%COINS%", String.valueOf(plugin.getCoinManager().getCoins(player))));
                                    }else
                                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NotEnoughCoins"));
                                }
                            }
                            for(String particle : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Particle").getKeys(false)){
                                String name = event.getInventory().getItem(13).getItemMeta().getLocalizedName();
                                if(particle.equals(name)){
                                    int coinCost = plugin.gadgetsYaml.getInt("Gadgets.Particle."+particle+".coins");
                                    if(plugin.getCoinManager().haveEnoughCoins(player, coinCost)) {
                                        plugin.getCoinManager().removeCoins(player, coinCost);
                                        plugin.getPlayerDataManager().playerDataYaml.set("Gadgets.Particle." + particle, true);
                                        plugin.getPlayerDataManager().save(player);
                                        player.sendMessage(Util.getMessage(Util.getLocale(player), "BuyNewParticle").replaceAll("%PARTICLE%", particle).replaceAll("%COINS%", String.valueOf(plugin.getCoinManager().getCoins(player))));
                                    }else
                                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NotEnoughCoins"));
                                }
                            }
                            for(String armor : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Armor").getKeys(false)){
                                String name = event.getInventory().getItem(13).getItemMeta().getLocalizedName();
                                if(armor.equals(name)){
                                    int coinCost = plugin.gadgetsYaml.getInt("Gadgets.Armor."+armor+".coins");
                                    if(plugin.getCoinManager().haveEnoughCoins(player, coinCost)) {
                                        plugin.getCoinManager().removeCoins(player, coinCost);
                                        plugin.getPlayerDataManager().playerDataYaml.set("Gadgets.Armor." + armor, true);
                                        plugin.getPlayerDataManager().save(player);
                                        player.sendMessage(Util.getMessage(Util.getLocale(player), "BuyNewArmor").replaceAll("%ARMOR%", armor).replaceAll("%COINS%", String.valueOf(plugin.getCoinManager().getCoins(player))));
                                    }else
                                        player.sendMessage(Util.getMessage(Util.getLocale(player),"NotEnoughCoins"));
                                }
                            }
                            player.closeInventory();
                            break;
                        case "gadgets.buy.cancel":
                            player.closeInventory();
                            break;
                    }
                }
            }
        }else if (event.getView().getTitle().equals(Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.AnimatedHeadsInventory")).replaceAll("&","§"))){
            event.setCancelled(true);
            if (event.getCurrentItem()!=null){
                if (event.getCurrentItem().getItemMeta().hasLocalizedName()){
                    switch(event.getCurrentItem().getItemMeta().getLocalizedName()){
                        case "gadgets.head.close":
                            player.closeInventory();
                            break;
                        case "gadgets.heads.reset":
                            if(player.getInventory().getHelmet() != null){
                                player.getInventory().setHelmet(null);
                                plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Head","null");
                                plugin.saveData();
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"ResetHead"));
                            }else{
                                player.closeInventory();
                                player.sendMessage(Util.getMessage(Util.getLocale(player),"FailedResetHead"));
                            }
                            break;
                        case "gadgets.heads.back":
                            plugin.getGadgetsMenuManager().createGadgetsInventory(player);
                            break;
                    }
                    for(String heads : plugin.gadgetsYaml.getConfigurationSection("Gadgets.AnimatedHeads").getKeys(false)){
                        if(event.getCurrentItem().getItemMeta().getLocalizedName().equals(heads)){
                            plugin.getPlayerDataManager().setYaml(player);
                            boolean isBought = plugin.getPlayerDataManager().playerDataYaml.getBoolean("Gadgets.AnimatedHeads."+heads);
                            if(!isBought){
                                plugin.getGadgetsMenuManager().createGadgetsBugConfirm(player,heads);
                                return;
                            }
                            player.closeInventory();
                            String headName = plugin.gadgetsYaml.getString("Gadgets.AnimatedHeads."+heads+".name");
                            player.sendMessage(Util.getMessage(Util.getLocale(player),"SelectHead").replaceAll("%HEAD%", headName.replaceAll("&","§")));
                            String headTexture = plugin.gadgetsYaml.getString("Gadgets.AnimatedHeads."+heads+".texture");
                            player.getInventory().setHelmet(new ItemBuilder(Material.PLAYER_HEAD).setDisplayname(headName.replaceAll("&","§")).setSkullOwner(Main.getTexture(headTexture)).build());
                            plugin.yaml.set("Selected_Items."+player.getUniqueId()+".Head",heads);
                            plugin.saveData();
                        }
                    }
                }
            }
        }
    }

}
