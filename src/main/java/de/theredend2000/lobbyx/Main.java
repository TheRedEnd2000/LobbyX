package de.theredend2000.lobbyx;

import de.theredend2000.lobbyx.animatedHeads.Allay_Die;
import de.theredend2000.lobbyx.commands.LobbyXCommand;
import de.theredend2000.lobbyx.commands.playercommand.*;
import de.theredend2000.lobbyx.hologramms.Hologram;
import de.theredend2000.lobbyx.jumpandrun.JnrCommand;
import de.theredend2000.lobbyx.jumpandrun.JumpAndRun;
import de.theredend2000.lobbyx.jumpandrun.PlayerMoveListener;
import de.theredend2000.lobbyx.listeners.*;
import de.theredend2000.lobbyx.listeners.gadgetsListener.PaintballListener;
import de.theredend2000.lobbyx.listeners.inventoryListeners.GadgetsInventoryListener;
import de.theredend2000.lobbyx.listeners.inventoryListeners.LobbxListener;
import de.theredend2000.lobbyx.listeners.inventoryListeners.ProfileListener;
import de.theredend2000.lobbyx.listeners.itemListeners.*;
import de.theredend2000.lobbyx.managers.*;
import de.theredend2000.lobbyx.messages.LanguageListeners;
import de.theredend2000.lobbyx.messages.Util;
import de.theredend2000.lobbyx.util.Broadcaster;
import de.theredend2000.lobbyx.util.DatetimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public final class Main extends JavaPlugin {
    private ArrayList<World> lobbyWorlds;
    private ArrayList<Player> buildPlayers;

    public YamlConfiguration yaml;
    public YamlConfiguration gadgetsYaml;
    public YamlConfiguration navigatorYaml;
    private LobbyXMenuManager lobbyXMenuManager;
    private SetPlayerLobbyManager setPlayerLobbyManager;
    private TablistManager tablistManager;
    private ProfileMenuManager profileMenuManager;
    private ClanManager clanManager;
    private RewardManager rewardManager;
    private RankManager rankManager;
    private DatetimeUtils datetimeUtils;
    private GadgetsMenuManager gadgetsMenuManager;
    private LobbySelectorManager lobbySelectorManager;
    private NavigatorMenuManager navigatorMenuManager;
    private CoinManager coinManager;
    private PlayerDataManager playerDataManager;
    private FriendManager friendManager;
    public File data = new File("plugins/LobbyX", "database.yml");
    public File gadgetData;
    public File navigatorData;


    @Override
    public void onEnable() {
            saveDefaultConfig();
            this.yaml = YamlConfiguration.loadConfiguration(this.data);
            this.saveData();
            createGadgetsYaml();
            this.gadgetsYaml = YamlConfiguration.loadConfiguration(this.gadgetData);
            saveGadgets();
            createNavigatorYaml();
            this.navigatorYaml = YamlConfiguration.loadConfiguration(this.navigatorData);
            saveNavigator();
            datetimeUtils = new DatetimeUtils();
            initManagers();
            Util.loadMessages();

            initLists();
            initCommand();
            initListeners();
            initAHeads();
            addLobbyWorlds();
    }

    @Override
    public void onDisable() {
        Iterator var1 = JumpAndRun.getJumpAndRuns().values().iterator();

        while(var1.hasNext()) {
            JumpAndRun jumpAndRun = (JumpAndRun)var1.next();
            jumpAndRun.getCurrentLocation().getBlock().setType(Material.AIR);
            jumpAndRun.getNextLocation().getBlock().setType(Material.AIR);
        }
        yaml.set("WorldCreator",null);
        saveData();
    }

    private void createGadgetsYaml(){
        gadgetData = new File(getDataFolder(),"gadgets.yml");
        try {
            if(!gadgetData.exists()){
                InputStream in = getResource("gadgets.yml");
                assert in != null;
                Files.copy(in,gadgetData.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void createNavigatorYaml(){
        navigatorData = new File(getDataFolder(),"navigator.yml");
        try {
            if(!navigatorData.exists()){
                InputStream in = getResource("navigator.yml");
                assert in != null;
                Files.copy(in,navigatorData.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initLists(){
        lobbyWorlds = new ArrayList<>();
        buildPlayers = new ArrayList<>();
    }

    public void addLobbyWorlds(){
        lobbyWorlds.clear();
        for (World world : Bukkit.getWorlds()) {
            if (getConfig().getConfigurationSection("Lobby_Worlds").getKeys(false).contains(world.getName())) {
                lobbyWorlds.add(world);
                setLobbyWeather(world);
            }
        }
    }

    private void setLobbyWeather(World world){
        String weatherType = getConfig().getString("Settings.DefaultWeather").toUpperCase();
        switch (weatherType) {
            case ("CLEAR"):
                world.setStorm(false);
                world.setThundering(false);
                break;
            case ("RAIN"):
                world.setStorm(true);
                world.setThundering(false);
                break;
            case ("THUNDER"):
                world.setStorm(true);
                world.setThundering(true);
                break;
        }
    }

    private void initCommand(){
        getCommand("lobby").setExecutor(new LobbyCommand(this));
        getCommand("lobbyx").setExecutor(new LobbyXCommand(this));
        getCommand("build").setExecutor(new BuildCommand(this));
        getCommand("friend").setExecutor(new FriendCommand(this));
        getCommand("jnr").setExecutor(new JnrCommand(this));
        getCommand("setLang").setExecutor(new SetLangCommand(this));
        getCommand("clan").setExecutor(new ClanCommands(this));
        getCommand("music").setExecutor(new MusicCommand());
        getCommand("daily").setExecutor(new DailyRewardCommand(this));
        getCommand("coins").setExecutor(new CoinsCommand(this));
        getCommand("hologram").setExecutor(new hologramCommand());
    }

    private void initListeners(){
        new LanguageListeners(this);
        new BlockBreakEventListener(this);
        new BlockPlaceEventListener(this);
        new FoodLevelChangeEventListener(this);
        new PlayerHiderListener(this);
        new JoinAndQuitEventListener(this);
        new EntityExplodeEventListener(this);
        new ProfileListeners(this);
        new EntityDamageEventListener(this);
        new HangingBreakByEntityEventListener(this);
        new LeavesDecayEventListener(this);
        new PlayerArmorStandManipulateEventListener(this);
        new PlayerBucketChangeEventListeners(this);
        new PlayerItemDropEventListener(this);
        new PlayerFishEventListener(this);
        new PlayerInteractEventListener(this);
        new ProfileListener(this);
        new LobbxListener(this);
        new PlayerMoveListener(this);
        new PlayerItemConsumeListener(this);
        new PlayerItemDamageEventListener(this);
        new PlayerPickupItemEventListener(this);
        new PlayerSwapHandItemsEventListener(this);
        new WeatherChangeEventListener(this);
        new InventoryClickEventListener(this);
        new GadgetsListener(this);
        new PlayerChangeWorldEventListener(this);
        new LobbySelectorListener(this);
        new GadgetsInventoryListener(this);
        new SpecialItemsListener(this);
        new NavigatorListener(this);
        new PaintballListener(this);
        new PlayerInteractAtEntityEventListener(this);
        new PlayerChatEventListener(this);
    }
    private void initManagers(){
        new Util(this);
        lobbyXMenuManager = new LobbyXMenuManager(this);
        setPlayerLobbyManager = new SetPlayerLobbyManager(this);
        tablistManager = new TablistManager(this);
        profileMenuManager = new ProfileMenuManager(this);
        clanManager = new ClanManager(this);
        gadgetsMenuManager = new GadgetsMenuManager(this);
        lobbySelectorManager = new LobbySelectorManager(this);
        navigatorMenuManager = new NavigatorMenuManager(this);
        rewardManager = new RewardManager(this);
        coinManager = new CoinManager(this);
        playerDataManager = new PlayerDataManager(this);
        friendManager = new FriendManager(this);
        rankManager = new RankManager(this);
        new Broadcaster(this).startBroadcast();
        new ScoreboardManager(this);
    }
    private void initAHeads(){
        new Allay_Die(this);
    }

    public static final String BACK_SKULL_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6L"
            + "y90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzY"
            + "jJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==";


    public void saveData() {
        try {
            this.yaml.save(this.data);
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }
    public void saveGadgets() {
        try {
            this.gadgetsYaml.save(this.gadgetData);
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }
    public void saveNavigator() {
        try {
            this.navigatorYaml.save(this.navigatorData);
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    public ArrayList<World> getLobbyWorlds() {
        return lobbyWorlds;
    }

    public ArrayList<Player> getBuildPlayers() {
        return buildPlayers;
    }

    public LobbyXMenuManager getLobbyXMenuManager() {
        return lobbyXMenuManager;
    }

    public SetPlayerLobbyManager getSetPlayerLobbyManager() {
        return setPlayerLobbyManager;
    }

    public TablistManager getTablistManager() {
        return tablistManager;
    }

    public ProfileMenuManager getProfileMenuManager() {
        return profileMenuManager;
    }

    public Material getMaterial(String materialString) {
        try {
            Material material = Material.getMaterial(Objects.requireNonNull(getConfig().getString(materialString)));
            if (material == null) {
                return Material.BARRIER;
            }
            return material;
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("§4Material Error:");
            return Material.STONE;
        }
    }
    public Material getGadgetsMaterial(String materialString) {
        try {
            Material material = Material.getMaterial(Objects.requireNonNull(gadgetsYaml.getString(materialString)));
            if (material == null) {
                return Material.BARRIER;
            }
            return material;
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("§4Material Error:");
            return Material.STONE;
        }
    }
    public Material getNavigatorMaterial(String materialString) {
        try {
            Material material = Material.getMaterial(Objects.requireNonNull(navigatorYaml.getString(materialString)));
            if (material == null) {
                return Material.BARRIER;
            }
            return material;
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("§4Material Error:");
            return Material.STONE;
        }
    }
    public static String getTexture(String texture){
        String prefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";
        texture = prefix+texture;
        return texture;
    }
    public DatetimeUtils getDatetimeUtils() {
        return datetimeUtils;
    }
    public ClanManager getClanManager() {
        return clanManager;
    }
    public GadgetsMenuManager getGadgetsMenuManager() {
        return gadgetsMenuManager;
    }
    public LobbySelectorManager getLobbySelectorManager() {
        return lobbySelectorManager;
    }
    public NavigatorMenuManager getNavigatorMenuManager() {
        return navigatorMenuManager;
    }
    public RewardManager getRewardManager() {
        return rewardManager;
    }
    public CoinManager getCoinManager() {
        return coinManager;
    }
    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }
    public FriendManager getFriendManager() {
        return friendManager;
    }
    public RankManager getRankManager() {
        return rankManager;
    }
}
