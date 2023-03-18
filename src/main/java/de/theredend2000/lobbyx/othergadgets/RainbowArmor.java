package de.theredend2000.lobbyx.othergadgets;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class RainbowArmor {

    private Main plugin;
    public HashMap<UUID,Float> hue;

    public RainbowArmor(Main plugin){
        this.plugin = plugin;
        hue = new HashMap<>();
        mainLoop().runTaskTimer(plugin,1,1);
    }

    private BukkitRunnable mainLoop() {
        return new BukkitRunnable() {
            @Override
            public void run() {
                hue.forEach((uuid,h) ->{
                    Player player = Bukkit.getPlayer(uuid);
                    if(player != null && player.isOnline()){
                        h = handleColor(h,0.005f);
                        setRainBowArmor(player,h,0.02f);
                        hue.put(uuid,h);
                    }
                });
            }
        };
    }

    public void setRainBowArmor(Player player, float hue, float gradientSpeed){
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        ItemStack leggins = new ItemStack(Material.LEATHER_LEGGINGS);
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

        org.bukkit.Color helmetColor = org.bukkit.Color.fromBGR(getRGB(hue).getRed(), getRGB(hue).getGreen(), getRGB(hue).getBlue());
        hue = handleColor(hue,gradientSpeed);
        org.bukkit.Color chestplateColor = org.bukkit.Color.fromBGR(getRGB(hue).getRed(), getRGB(hue).getGreen(), getRGB(hue).getBlue());
        hue = handleColor(hue,gradientSpeed);
        org.bukkit.Color legginsColor = org.bukkit.Color.fromBGR(getRGB(hue).getRed(), getRGB(hue).getGreen(), getRGB(hue).getBlue());
        hue = handleColor(hue,gradientSpeed);
        org.bukkit.Color bootsColor = org.bukkit.Color.fromBGR(getRGB(hue).getRed(), getRGB(hue).getGreen(), getRGB(hue).getBlue());

        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(helmetColor);
        helmet.setItemMeta(helmetMeta);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(chestplateColor);
        chestplate.setItemMeta(chestplateMeta);
        LeatherArmorMeta legginsMeta = (LeatherArmorMeta) leggins.getItemMeta();
        legginsMeta.setColor(legginsColor);
        leggins.setItemMeta(legginsMeta);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(bootsColor);
        boots.setItemMeta(bootsMeta);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggins);
        player.getInventory().setBoots(boots);
    }

    private float handleColor(float hue, float speed){
        hue += speed;
        if(hue >=1.0f)
            hue = 0.0f;
        return hue;
    }

    private Color getRGB(float hue){
        return Color.getHSBColor(hue,1f,1f);
    }

}
