package de.theredend2000.lobbyx.hologramms;

import de.theredend2000.lobbyx.Main;

public abstract class Module {

    private Main plugin;
    private ModuleType moduleType;
    public Module(Main plugin, ModuleType type) {
        this.plugin = plugin;
        this.moduleType = type;
    }
    public Main getPlugin() {
        return plugin;
    }

    public abstract void onEnable();

    public abstract void onDisable();

}