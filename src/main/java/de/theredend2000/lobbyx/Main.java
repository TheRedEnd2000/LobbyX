package de.theredend2000.lobbyx;

import de.theredend2000.lobbyx.commands.LobbyXCommand;
import de.theredend2000.lobbyx.commands.playercommand.BuildCommand;
import de.theredend2000.lobbyx.commands.playercommand.HubCommand;
import de.theredend2000.lobbyx.commands.playercommand.LobbyCommand;
import de.theredend2000.lobbyx.listeners.BlockBreakEventListener;
import de.theredend2000.lobbyx.listeners.BlockPlaceEventListener;
import de.theredend2000.lobbyx.listeners.FoodLevelChangeEventListener;
import de.theredend2000.lobbyx.listeners.itemListeners.PlayerHiderListener;
import de.theredend2000.lobbyx.managers.LobbyXMenuManager;
import de.theredend2000.lobbyx.managers.SetPlayerLobbyManager;
import de.theredend2000.lobbyx.messages.LanguageListeners;
import de.theredend2000.lobbyx.messages.Util;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public final class Main extends JavaPlugin {
    private ArrayList<World> lobbyWorlds;
    private ArrayList<Player> buildPlayers;

    public YamlConfiguration yaml;
    private LobbyXMenuManager lobbyXMenuManager;
    private SetPlayerLobbyManager setPlayerLobbyManager;
    public File data = new File("plugins/LobbyX", "database.yml");


    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.yaml = YamlConfiguration.loadConfiguration(this.data);
        this.saveData();
        initManagers();
        Util.loadMessages();

        initLists();
        initCommand();
        initListeners();
        addLobbyWorlds();
    }

    @Override
    public void onDisable() {

    }

    private void initLists(){
        lobbyWorlds = new ArrayList<>();
        buildPlayers = new ArrayList<>();
    }

    private void addLobbyWorlds(){
        for (World world : Bukkit.getWorlds()) {
            if (getConfig().getStringList("Lobby_Worlds").contains(world.getName())) {
                lobbyWorlds.add(world);
            }
        }
    }

    private void initCommand(){
        getCommand("lobby").setExecutor(new LobbyCommand(this));
        getCommand("lobbyx").setExecutor(new LobbyXCommand(this));
        getCommand("hub").setExecutor(new HubCommand(this));
        getCommand("build").setExecutor(new BuildCommand(this));
    }

    private void initListeners(){
        new LanguageListeners(this);
        new BlockBreakEventListener(this);
        new BlockPlaceEventListener(this);
        new FoodLevelChangeEventListener(this);
        new PlayerHiderListener(this);
    }
    private void initManagers(){
        new Util(this);
        lobbyXMenuManager = new LobbyXMenuManager(this);
        setPlayerLobbyManager = new SetPlayerLobbyManager(this);
    }


    public void saveData() {
        try {
            this.yaml.save(this.data);
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
}
