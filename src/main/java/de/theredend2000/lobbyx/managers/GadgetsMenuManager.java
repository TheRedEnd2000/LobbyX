package de.theredend2000.lobbyx.managers;

import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.listeners.itemListeners.GadgetsListener;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public class GadgetsMenuManager {

    private Main plugin;

    public GadgetsMenuManager(Main plugin){
        this.plugin = plugin;
    }

    public void createGadgetsInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.GadgetsMainMenu")).replaceAll("&","§"));
        int[] orangeglass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,50,51,52,53};
        for (int i = 0; i<orangeglass.length;i++){inventory.setItem(orangeglass[i], new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(49,new ItemBuilder(Material.BARRIER).setDisplayname("§4Close").setLocalizedName("gadgets.main.close").build());
        inventory.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setLore("§7Heads for everyone. ","§7Look for the most suitable head for you.","","§eClick to browse.").setSkullOwner(Main.getTexture("N2RlNDFlODE1YTQ5MmM5MmQ4YTQ1YmU1NGRhOWVhNjdlNmMyOTliZWNlMTM4NmMxZGZhOWMxNjk5ZDA5ZTA4NCJ9fX0=")).setDisplayname("§5Heads").setLocalizedName("gadgets.main.heads").build());
        inventory.setItem(10, new ItemBuilder(Material.FISHING_ROD).setLore("§7Fun with everything. ","§7Look for you favorite ones.","","§eClick to browse.").setDisplayname("§9Special Items").setLocalizedName("gadgets.main.specialItems").build());
        inventory.setItem(16, new ItemBuilder(Material.PLAYER_HEAD).setLore("§7Heads but animated. ","§7What could be cooler than animated heads.","","§eClick to browse.").setSkullOwner(Main.getTexture("OGFkMmI0ZTQ4OTU5NDk4YzNiZmIyZDE0OTg3NGVkYmY5YTBjZmJhZGNlYjU0YWQyNTlhYTQwNzA2ZGRhMWI2YyJ9fX0=")).setDisplayname("§6Animated Heads").setLocalizedName("gadgets.main.aheads").build());
        inventory.setItem(37, new ItemBuilder(Material.FIREWORK_ROCKET).setLore("§7Particles of all kinds. ","§7Select your favorite one or try out everyone.","","§eClick to browse.").setDisplayname("§6Particle Effects").setLocalizedName("gadgets.main.particle").build());
        inventory.setItem(40, new ItemBuilder(Material.IRON_CHESTPLATE).setLore("§7Cool armor for everyone. ","§7From colorful to stylish, ","§7everything is included.","","§eClick to browse.").setDisplayname("§3Armor").setLocalizedName("gadgets.main.armor").build());
        inventory.setItem(43, new ItemBuilder(Material.BARRIER).setLore("§cThis feature is coming soon.").setDisplayname("§4Coming Soon").setLocalizedName("gadgets.main.soon").build());
        player.openInventory(inventory);
    }

    public void createHeadsGadgetsInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.HeadInventory")).replaceAll("&","§"));
        int[] yellowglass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,46,47,48,51,52,53};
        for (int i = 0; i<yellowglass.length;i++){inventory.setItem(yellowglass[i], new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(49,new ItemBuilder(Material.BARRIER).setDisplayname("§4Close").setLocalizedName("gadgets.head.close").build());
        inventory.setItem(50, new ItemBuilder(Material.RED_STAINED_GLASS).setDisplayname("§eReset Head").setLocalizedName("gadgets.heads.reset").build());
        inventory.setItem(45,new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.BACK_SKULL_TEXTURE).setDisplayname("§eBack").setLocalizedName("gadgets.heads.back").build());
        for(String heads : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Heads").getKeys(false)){
            String headTexture = plugin.gadgetsYaml.getString("Gadgets.Heads."+heads+".texture");
            String headName = plugin.gadgetsYaml.getString("Gadgets.Heads."+heads+".name");
            int coinCost = plugin.gadgetsYaml.getInt("Gadgets.Heads."+heads+".coins");
            plugin.getPlayerDataManager().setYaml(player);
            boolean isBought = plugin.getPlayerDataManager().playerDataYaml.getBoolean("Gadgets.Heads."+heads);
            inventory.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture(headTexture)).setLore(isBought ? "§a§l✓ Unlocked" : "§7Costs: §6"+coinCost).setDisplayname(headName.replaceAll("&","§")).setLocalizedName(heads).withGlow(plugin.yaml.getString("Selected_Items." + player.getUniqueId() + ".Head").equals(heads)).build());
        }
        player.openInventory(inventory);
    }
    public void createAHeadsGadgetsInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.AnimatedHeadsInventory")).replaceAll("&","§"));
        int[] yellowglass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,46,47,48,51,52,53};
        for (int i = 0; i<yellowglass.length;i++){inventory.setItem(yellowglass[i], new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(49,new ItemBuilder(Material.BARRIER).setDisplayname("§4Close").setLocalizedName("gadgets.head.close").build());
        inventory.setItem(50, new ItemBuilder(Material.RED_STAINED_GLASS).setDisplayname("§eReset Head").setLocalizedName("gadgets.heads.reset").build());
        inventory.setItem(45,new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.BACK_SKULL_TEXTURE).setDisplayname("§eBack").setLocalizedName("gadgets.heads.back").build());
        for(String heads : plugin.gadgetsYaml.getConfigurationSection("Gadgets.AnimatedHeads").getKeys(false)){
            String headTexture = plugin.gadgetsYaml.getString("Gadgets.AnimatedHeads."+heads+".texture");
            String headName = plugin.gadgetsYaml.getString("Gadgets.AnimatedHeads."+heads+".name");
            int coinCost = plugin.gadgetsYaml.getInt("Gadgets.AnimatedHeads."+heads+".coins");
            plugin.getPlayerDataManager().setYaml(player);
            boolean isBought = plugin.getPlayerDataManager().playerDataYaml.getBoolean("Gadgets.AnimatedHeads."+heads);
            inventory.addItem(new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.getTexture(headTexture)).setLore(isBought ? "§a§l✓ Unlocked" : "§7Costs: §6"+coinCost).setDisplayname(headName.replaceAll("&","§")).setLocalizedName(heads).withGlow(plugin.yaml.getString("Selected_Items." + player.getUniqueId() + ".Head").equals(heads)).build());
        }
        player.openInventory(inventory);
    }

    public void createItemGadgetsInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.SpecialItemInventory")).replaceAll("&","§"));
        int[] yellowglass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,46,47,48,51,52,53};
        for (int i = 0; i<yellowglass.length;i++){inventory.setItem(yellowglass[i], new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(49,new ItemBuilder(Material.BARRIER).setDisplayname("§4Close").setLocalizedName("gadgets.items.close").build());
        inventory.setItem(50, new ItemBuilder(Material.RED_STAINED_GLASS).setDisplayname("§eReset Item").setLocalizedName("gadgets.items.reset").build());
        inventory.setItem(45,new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.BACK_SKULL_TEXTURE).setDisplayname("§eBack").setLocalizedName("gadgets.items.back").build());
        for(String items : plugin.gadgetsYaml.getConfigurationSection("Gadgets.SpecialItems").getKeys(false)){
            String itemName = plugin.gadgetsYaml.getString("Gadgets.SpecialItems."+items+".name");
            String material = "Gadgets.SpecialItems."+items+".item";
            int coinCost = plugin.gadgetsYaml.getInt("Gadgets.SpecialItems."+items+".coins");
            plugin.getPlayerDataManager().setYaml(player);
            boolean isBought = plugin.getPlayerDataManager().playerDataYaml.getBoolean("Gadgets.SpecialItems."+items);
            inventory.addItem(new ItemBuilder(plugin.getGadgetsMaterial(material)).setLore(isBought ? "§a§l✓ Unlocked" : "§7Costs: §6"+coinCost).setDisplayname(itemName.replaceAll("&","§")).setLocalizedName(items).withGlow(plugin.yaml.getString("Selected_Items." + player.getUniqueId() + ".Inv").equals(items)).build());
        }
        player.openInventory(inventory);
    }

    public void createParticleInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.ParticleInventory")).replaceAll("&","§"));
        int[] redGlass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,46,47,48,51,52,53};
        for (int i = 0; i<redGlass.length;i++){inventory.setItem(redGlass[i], new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(49,new ItemBuilder(Material.BARRIER).setDisplayname("§4Close").setLocalizedName("gadgets.particle.close").build());
        inventory.setItem(50, new ItemBuilder(Material.RED_STAINED_GLASS).setDisplayname("§eReset Particle").setLocalizedName("gadgets.particle.reset").build());
        inventory.setItem(45,new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.BACK_SKULL_TEXTURE).setDisplayname("§eBack").setLocalizedName("gadgets.particle.back").build());
        for(String items : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Particle").getKeys(false)){
            String itemName = plugin.gadgetsYaml.getString("Gadgets.Particle."+items+".name");
            String material = "Gadgets.Particle."+items+".material";
            int coinCost = plugin.gadgetsYaml.getInt("Gadgets.Particle."+items+".coins");
            plugin.getPlayerDataManager().setYaml(player);
            boolean isBought = plugin.getPlayerDataManager().playerDataYaml.getBoolean("Gadgets.Particle."+items);
            inventory.addItem(new ItemBuilder(plugin.getGadgetsMaterial(material)).setLore(isBought ? "§a§l✓ Unlocked" : "§7Costs: §6"+coinCost).setDisplayname("§eParticle: "+itemName.replaceAll("&","§")).setLocalizedName(items).withGlow(plugin.yaml.getString("Selected_Items." + player.getUniqueId() + ".Particle").equals(items)).build());
        }
        player.openInventory(inventory);
    }

    public void createArmorInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,54, Objects.requireNonNull(plugin.getConfig().getString("Inventory.GadgetsInventory.ArmorInventory")).replaceAll("&","§"));
        int[] redGlass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,46,47,48,51,52,53};
        for (int i = 0; i<redGlass.length;i++){inventory.setItem(redGlass[i], new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        inventory.setItem(49,new ItemBuilder(Material.BARRIER).setDisplayname("§4Close").setLocalizedName("gadgets.armor.close").build());
        inventory.setItem(50, new ItemBuilder(Material.RED_STAINED_GLASS).setDisplayname("§eReset Armor").setLocalizedName("gadgets.armor.reset").build());
        inventory.setItem(45,new ItemBuilder(Material.PLAYER_HEAD).setSkullOwner(Main.BACK_SKULL_TEXTURE).setDisplayname("§eBack").setLocalizedName("gadgets.armor.back").build());
        for(String armors : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Armor").getKeys(false)){
            String armorName = plugin.gadgetsYaml.getString("Gadgets.Armor."+armors+".name");
            Material material = plugin.getGadgetsMaterial("Gadgets.Armor."+armors+".item");
            int coinCost = plugin.gadgetsYaml.getInt("Gadgets.Armor."+armors+".coins");
            int redColor = plugin.gadgetsYaml.getInt("Gadgets.Armor."+armors+".color.red");
            int greenColor = plugin.gadgetsYaml.getInt("Gadgets.Armor."+armors+".color.green");
            int blueColor = plugin.gadgetsYaml.getInt("Gadgets.Armor."+armors+".color.blue");
            Color color = Color.fromRGB(redColor, greenColor, blueColor);
            String texture = plugin.gadgetsYaml.getString("Gadgets.Armor."+armors+".texture");
            plugin.getPlayerDataManager().setYaml(player);
            boolean isBought = plugin.getPlayerDataManager().playerDataYaml.getBoolean("Gadgets.Armor."+armors);
            if(material.equals(Material.PLAYER_HEAD))
                inventory.addItem(new ItemBuilder(material).setSkullOwner(Main.getTexture(texture)).setLore(isBought ? "§a§l✓ Unlocked" : "§7Costs: §6"+coinCost).setDisplayname(armorName.replaceAll("&","§")).setLocalizedName(armors).withGlow(plugin.yaml.getString("Selected_Items." + player.getUniqueId() + ".Chest").equals(armors)).setDisplayname(armorName.replaceAll("&","§")).build());
            else
                inventory.addItem(new ItemBuilder(material).setColor(material.equals(Material.LEATHER_HELMET) ? color : null).setLore(isBought ? "§a§l✓ Unlocked" : "§7Costs: §6"+coinCost).setDisplayname(armorName.replaceAll("&","§")).setLocalizedName(armors).withGlow(plugin.yaml.getString("Selected_Items." + player.getUniqueId() + ".Chest").equals(armors)).setDisplayname(armorName.replaceAll("&","§")).build());
        }
        player.openInventory(inventory);
    }
    public void createGadgetsBugConfirm(Player player, String item){
        Inventory inventory = Bukkit.createInventory(player,27, "§7Confirm purchase?");
        inventory.setItem(11,new ItemBuilder(Material.LIME_CONCRETE).setDisplayname("§2Confirm").setLocalizedName("gadgets.buy.confirm").build());
        for(String heads : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Heads").getKeys(false)){
            if(heads.equals(item)){
                String headName = plugin.gadgetsYaml.getString("Gadgets.Heads."+item+".name");
                String headTexture = plugin.gadgetsYaml.getString("Gadgets.Heads."+item+".texture");
                int coinCost = plugin.gadgetsYaml.getInt("Gadgets.Heads."+item+".coins");
                inventory.setItem(13,new ItemBuilder(Material.PLAYER_HEAD).setLore("§7Costs: §6"+coinCost).setDisplayname(headName.replaceAll("&","§")).setSkullOwner(Main.getTexture(headTexture)).setLocalizedName(item).build());
            }
        }
        for(String heads : plugin.gadgetsYaml.getConfigurationSection("Gadgets.AnimatedHeads").getKeys(false)){
            if(heads.equals(item)){
                String headName = plugin.gadgetsYaml.getString("Gadgets.AnimatedHeads."+item+".name");
                String headTexture = plugin.gadgetsYaml.getString("Gadgets.AnimatedHeads."+item+".texture");
                int coinCost = plugin.gadgetsYaml.getInt("Gadgets.AnimatedHeads."+item+".coins");
                inventory.setItem(13,new ItemBuilder(Material.PLAYER_HEAD).setLore("§7Costs: §6"+coinCost).setDisplayname(headName.replaceAll("&","§")).setSkullOwner(Main.getTexture(headTexture)).setLocalizedName(item).build());
            }
        }
        for(String items : plugin.gadgetsYaml.getConfigurationSection("Gadgets.SpecialItems").getKeys(false)){
            if(items.equals(item)){
                String itemName = plugin.gadgetsYaml.getString("Gadgets.SpecialItems."+item+".name");
                Material itemMaterial = plugin.getGadgetsMaterial("Gadgets.SpecialItems."+items+".item");
                int coinCost = plugin.gadgetsYaml.getInt("Gadgets.SpecialItems."+item+".coins");
                inventory.setItem(13,new ItemBuilder(itemMaterial).setLore("§7Costs: §6"+coinCost).setDisplayname(itemName.replaceAll("&","§")).setLocalizedName(item).build());
            }
        }
        for(String particles : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Particle").getKeys(false)){
            if(particles.equals(item)){
                String itemName = plugin.gadgetsYaml.getString("Gadgets.Particle."+item+".name");
                Material itemMaterial = plugin.getGadgetsMaterial("Gadgets.Particle."+item+".material");
                int coinCost = plugin.gadgetsYaml.getInt("Gadgets.Particle."+item+".coins");
                inventory.setItem(13,new ItemBuilder(itemMaterial).setLore("§7Costs: §6"+coinCost).setDisplayname("§eParticle: "+itemName.replaceAll("&","§")).setLocalizedName(item).build());
            }
        }
        for(String armor : plugin.gadgetsYaml.getConfigurationSection("Gadgets.Armor").getKeys(false)){
            if(armor.equals(item)){
                String itemName = plugin.gadgetsYaml.getString("Gadgets.Armor."+item+".name");;
                Material itemMaterial = plugin.getGadgetsMaterial("Gadgets.Armor."+item+".item");
                int coinCost = plugin.gadgetsYaml.getInt("Gadgets.Armor."+item+".coins");
                Color color = plugin.gadgetsYaml.getColor("Gadgets.Armor."+item+".color");
                String texture = plugin.gadgetsYaml.getString("Gadgets.Armor."+item+".texture");
                if(itemMaterial.equals(Material.PLAYER_HEAD))
                    inventory.setItem(13,new ItemBuilder(itemMaterial).setLore("§7Costs: §6"+coinCost).setSkullOwner(Main.getTexture(texture)).setDisplayname(itemName.replaceAll("&","§")).setLocalizedName(item).build());
                else
                    inventory.setItem(13,new ItemBuilder(itemMaterial).setLore("§7Costs: §6"+coinCost).setColor(color).setDisplayname(itemName.replaceAll("&","§")).setLocalizedName(item).build());
            }
        }
        inventory.setItem(15,new ItemBuilder(Material.RED_CONCRETE).setDisplayname("§4Cancel").setLocalizedName("gadgets.buy.cancel").build());
        player.openInventory(inventory);
    }

}
