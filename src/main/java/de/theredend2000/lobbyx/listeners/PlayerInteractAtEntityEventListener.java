package de.theredend2000.lobbyx.listeners;

import com.google.protobuf.StringValue;
import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;

import javax.naming.InvalidNameException;
import java.io.File;
import java.util.Objects;

public class PlayerInteractAtEntityEventListener implements Listener {

    private Main plugin;


    public PlayerInteractAtEntityEventListener(Main plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event){
        Player player = event.getPlayer();
        if(event.getRightClicked() instanceof Player){
            Player rightClicked = (Player) event.getRightClicked();
            if(plugin.getLobbyWorlds().contains(player.getWorld()) && plugin.getLobbyWorlds().contains(rightClicked.getWorld()) && player.getItemInHand().getItemMeta() == null){
                interactWithPLayerInv(player,rightClicked);
            }
        }
    }

    private void interactWithPLayerInv(Player player,Player rightClicked){
        Inventory Playerinfo = Bukkit.createInventory(player, 54,Objects.requireNonNull(plugin.getConfig().getString("Inventory.InteractWithPlayerInventory")).replaceAll("&", "§"));
        int[] white = new int[]{10,12,14,16,19,21,22,23,24,25,28,30,31,32,33,34,37,39,41,43};
        int[] orange = new int[]{0,1,2,3,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,50,51,52};
        Playerinfo.setItem(4,new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname("§c").build());
        Playerinfo.setItem(49,new ItemBuilder(Material.RED_STAINED_GLASS_PANE).setDisplayname("§c").build());
        for (int i = 0; i < white.length; i++) {Playerinfo.setItem(white[i], new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < orange.length; i++) {Playerinfo.setItem(orange[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        Playerinfo.setItem(11,new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("Send friend invation").setSkullOwner(Main.getTexture("YTc5YTVjOTVlZTE3YWJmZWY0NWM4ZGMyMjQxODk5NjQ5NDRkNTYwZjE5YTQ0ZjE5ZjhhNDZhZWYzZmVlNDc1NiJ9fX0=")).setLocalizedName("addfriend").build());
        Playerinfo.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname(rightClicked.getName()).setOwner(rightClicked.getName()).setLocalizedName("Playerhead").build());
        Playerinfo.setItem(53,new ItemBuilder(Material.NETHERITE_SHOVEL).setDisplayname("§1Rank").setLocalizedName("Rank").build());//info: bei jmd der ranks vergeben kann soll hier auch der rank gesetzt werden können
        Playerinfo.setItem(15, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("Invite in Clan").setSkullOwner(Main.getTexture("NmE1MzYxYjUyZGFmNGYxYzVjNTQ4MGEzOWZhYWExMDg5NzU5NWZhNTc2M2Y3NTdiZGRhMzk1NjU4OGZlYzY3OCJ9fX0=")).setLocalizedName("inviteinclan").build());
        Playerinfo.setItem(42,new ItemBuilder(Material.DIAMOND).setDisplayname("Sozial-Info").setLocalizedName("Sozial").build());
        Playerinfo.setItem(38,new ItemBuilder(Material.WRITABLE_BOOK).setDisplayname("sendMassages").setLocalizedName("sendmassage").build());
        Playerinfo.setItem(40,new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eCoins§f: §5"+ (plugin.getCoinManager().allowCoinsSee(rightClicked) ? plugin.getCoinManager().getCoins(rightClicked) : "§4§lPRIVATE")).setSkullOwner(Main.getTexture("MjRhZjhkNTc1ZjBhNTI0ZWY2NmEyMTU3M2U4YTRhOGFjN2Q2NDBiMDBkOThlMmU5Mzc0ZTYwNDcwZTdjODZmMCJ9fX0=")).setLocalizedName("Coins").build());
        player.openInventory(Playerinfo);
    }
}
