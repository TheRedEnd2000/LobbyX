package de.theredend2000.lobbyx.heads;

import de.theredend2000.lobbyx.Main;
import org.bukkit.inventory.ItemStack;

public enum CustomHeads {

    GERMAN_HEAD("NWU3ODk5YjQ4MDY4NTg2OTdlMjgzZjA4NGQ5MTczZmU0ODc4ODY0NTM3NzQ2MjZiMjRiZDhjZmVjYzc3YjNmIn19fQ==","german"),
    ENGLISH_HEAD("ODgzMWM3M2Y1NDY4ZTg4OGMzMDE5ZTI4NDdlNDQyZGZhYTg4ODk4ZDUwY2NmMDFmZDJmOTE0YWY1NDRkNTM2OCJ9fX0=","english");

    private ItemStack item;
    private String idTag;
    private String prefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";
    private CustomHeads(String texture,String id){
        item = Main.createCustomSkull(prefix + texture, id);
        idTag = id;
    }

    public ItemStack getItemStack(){
        return item;
    }
    public String getName(){
        return idTag;
    }

}
