package de.theredend2000.lobbyx.hologramms;

import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HologramManager extends Module {

    private Set<Hologram> holograms;

    public HologramManager(Main plugin) {
        super(plugin, ModuleType.HOLOGRAMS);
    }

    @Override
    public void onEnable() {
        holograms = new HashSet<>();
        loadHolograms();
    }

    @Override
    public void onDisable() {
        saveHolograms();
    }

    public void loadHolograms() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(getPlugin(), () -> {

            FileConfiguration config = getPlugin().yaml;

            if (config.contains("holograms")) {
                for (String key : config.getConfigurationSection("holograms").getKeys(false)) {
                    List<String> lines = config.getStringList("holograms." + key + ".lines");

                    Location loc = (Location) config.get("holograms." + key + ".location");
                    if (loc == null) continue;
                    deleteNearbyHolograms(loc);

                    Hologram holo = createHologram(key, loc);
                    holo.setLines(lines);
                }
            }
        }, 40L);
    }

    public void saveHolograms() {
        FileConfiguration config = getPlugin().yaml;
        holograms.forEach(hologram -> {
            config.set("holograms." + hologram.getName() + ".location", hologram.getLocation());
            List<String> lines = new ArrayList<String>();
            for (ArmorStand stand : hologram.getStands()) lines.add(stand.getCustomName());
            config.set("holograms." + hologram.getName() + ".lines", lines);
        });
        getPlugin().saveData();
        deleteAllHolograms();
    }

    public Set<Hologram> getHolograms() {
        return holograms;
    }

    public boolean hasHologram(String name) {
        return getHolograms().stream().anyMatch(hologram -> hologram.getName().equalsIgnoreCase(name));
    }

    public Hologram getHologram(String name) {
        return getHolograms().stream().filter(hologram -> hologram.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public Hologram createHologram(String name, Location location) {
        Hologram holo = new Hologram(name, location);
        holograms.add(holo);
        return holo;
    }

    public void deleteHologram(String name) {
        Hologram holo = getHologram(name);
        holo.remove();

        holograms.remove(holo);
        getPlugin().yaml.set("holograms." + name, null);
        getPlugin().saveData();
    }

    public void deleteAllHolograms() {
        holograms.forEach(Hologram::remove);
        holograms.clear();
    }

    public void deleteNearbyHolograms(Location location) {
        World world = location.getWorld();
        if (world == null) return;
        world.getNearbyEntities(location, 0, 20, 0).stream().filter(entity -> entity instanceof ArmorStand).forEach(Entity::remove);
    }

}
