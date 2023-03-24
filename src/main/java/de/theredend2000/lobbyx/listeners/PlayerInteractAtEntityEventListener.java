package de.theredend2000.lobbyx.listeners;

import com.google.protobuf.StringValue;
import de.theredend2000.lobbyx.Main;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

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
                playerInformationInventory(player,rightClicked);
            }
        }
    }

    private void playerInformationInventory(Player player,Player rightClicked){
        Inventory playerInformation = Bukkit.createInventory(player, 54,Objects.requireNonNull(plugin.getConfig().getString("Inventory.InteractWithPlayerInventory")).replaceAll("&", "§"));
        int[] white = new int[]{10,12,14,16,19,20,21,22,23,24,25,28,29,30,31,32,33,34,37,39,41,43};
        int[] orange = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53};
        for (int i = 0; i < white.length; i++) {playerInformation.setItem(white[i], new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        for (int i = 0; i < orange.length; i++) {playerInformation.setItem(orange[i], new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        playerInformation.setItem(11,new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§3Add Friend").setLore("§7Send "+rightClicked.getName()+" a friend invation.","","§eClick to send.").setSkullOwner(Main.getTexture("YTc5YTVjOTVlZTE3YWJmZWY0NWM4ZGMyMjQxODk5NjQ5NDRkNTYwZjE5YTQ0ZjE5ZjhhNDZhZWYzZmVlNDc1NiJ9fX0=")).setLocalizedName("playerInformation.addFriend").build());
        playerInformation.setItem(13, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§6"+rightClicked.getName()).setLore("","§7Rank: §6"+plugin.getApi().getPrimaryRank(rightClicked),"§7Joined first: §6"+plugin.yaml.getString("PlayerInformation."+player.getUniqueId()+".FirstJoined.Date")+"§f§l - §6"+plugin.yaml.getString("PlayerInformation."+player.getUniqueId()+".FirstJoined.Time"),"§7Ping: §6"+rightClicked.getPing()).setOwner(rightClicked.getName()).setLocalizedName(rightClicked.getName()).build());
        playerInformation.setItem(15, new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§9Invite in Clan").setLore("§7Send "+rightClicked.getName()+"§7 a clan invation to your clan.","","§eClick to send.").setSkullOwner(Main.getTexture("NmE1MzYxYjUyZGFmNGYxYzVjNTQ4MGEzOWZhYWExMDg5NzU5NWZhNTc2M2Y3NTdiZGRhMzk1NjU4OGZlYzY3OCJ9fX0=")).setLocalizedName("playerInformation.inviteClan").build());
        playerInformation.setItem(42,new ItemBuilder(Material.DIAMOND).setDisplayname("§2Social").setLore("§7Show the social of "+rightClicked.getName(),"","§eShow socials.").setLocalizedName("playerInformation.social").build());
        playerInformation.setItem(38,new ItemBuilder(Material.WRITABLE_BOOK).setDisplayname("§eMessage").setLore("§7Send "+rightClicked.getName()+"§7 an message.","","§eClick to enter.").setLocalizedName("playerInformation.message").build());
        playerInformation.setItem(40,new ItemBuilder(Material.PLAYER_HEAD).setDisplayname("§eCoins§f: §5"+ (plugin.getCoinManager().allowCoinsSee(rightClicked) ? plugin.getCoinManager().getCoins(rightClicked) : "§4§lPRIVATE")).setSkullOwner(Main.getTexture("MjRhZjhkNTc1ZjBhNTI0ZWY2NmEyMTU3M2U4YTRhOGFjN2Q2NDBiMDBkOThlMmU5Mzc0ZTYwNDcwZTdjODZmMCJ9fX0=")).setLocalizedName("Coins").build());
        player.openInventory(playerInformation);
    }

    @EventHandler
    public void InteractWithDailyVillager(PlayerInteractEntityEvent event){
        if(!(event.getRightClicked() instanceof Villager)) return;
        Villager daily = (Villager) event.getRightClicked();
        if(daily.getCustomName().equals("§6Daily")){
            event.setCancelled(true);
            Player player = event.getPlayer();
            createDailyInventory(player);
        }
    }

    public void createDailyInventory(Player player){
        Inventory inventory = Bukkit.createInventory(player,45, Objects.requireNonNull(Objects.requireNonNull(plugin.getConfig().getString("Inventory.DailyRewardInventoryTitle")).replaceAll("&","§")));
        int[] yellowGlass = new int[]{0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,37,38,39,40,41,42,43,44};
        for (int i = 0; i<yellowGlass.length;i++){inventory.setItem(yellowGlass[i], new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).setDisplayname("§c").build());}
        long current = System.currentTimeMillis();
        long releaseDaily = plugin.getRewardManager().getTimeDaily(player);
        long millisDaily = releaseDaily - current;
        long releaseWeekly = plugin.getRewardManager().getTimeWeekly(player);
        long millisWeekly = releaseWeekly - current;
        long releaseMonthly = plugin.getRewardManager().getTimeMonthly(player);
        long millisMonthly = releaseMonthly - current;
        boolean claimedDaily = !plugin.getRewardManager().getAllowReward(player,"daily");
        boolean claimedWeekly = !plugin.getRewardManager().getAllowReward(player,"weekly");
        boolean claimedMonthly = !plugin.getRewardManager().getAllowReward(player,"monthly");
        inventory.setItem(20, new ItemBuilder(claimedDaily ? Material.BUCKET : Material.WATER_BUCKET).setDisplayname("§6Daily Reward").setLore(claimedDaily ? "§7You have already claimed your daily reward." : "§7Your daily reward is ready to claim.", claimedDaily ? "§7Come back in: "+plugin.getRewardManager().getRemainingTime(millisDaily) : "§eClick to claim").setLocalizedName("daily.dailyReward").build());
        inventory.setItem(22, new ItemBuilder(claimedWeekly ? Material.BUCKET : Material.MILK_BUCKET).setDisplayname("§6Weekly Reward").setLore(claimedWeekly ? "§7You have already claimed your weekly reward." : "§7Your weekly reward is ready to claim.", claimedWeekly ? "§7Come back in: "+plugin.getRewardManager().getRemainingTime(millisWeekly) : "§eClick to claim").setLocalizedName("daily.weeklyReward").build());
        inventory.setItem(24, new ItemBuilder(claimedMonthly ? Material.BUCKET : Material.LAVA_BUCKET).setDisplayname("§6Monthly Reward").setLore(claimedMonthly ? "§7You have already claimed your monthly reward." : "§7Your monthly reward is ready to claim.", claimedMonthly ? "§7Come back in: "+plugin.getRewardManager().getRemainingTime(millisMonthly) : "§eClick to claim").setLocalizedName("daily.monthlyReward").build());
        inventory.setItem(40, new ItemBuilder(Material.BARRIER).setDisplayname("§cClose").setLocalizedName("daily.close").build());
        player.openInventory(inventory);
    }
}
