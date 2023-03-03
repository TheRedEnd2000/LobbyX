package de.theredend2000.lobbyx.searchguis;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import de.theredend2000.lobbyx.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class SearchSignGUI {
    private PacketAdapter packetListener;
    private final Player p;
    private Sign sign;
    private final LeaveListener listener = new LeaveListener();
    private Main plugin;

    public SearchSignGUI(Player p, Main plugin) {
        this.p = p;
        this.plugin = plugin;
        int x_start = p.getLocation().getBlockX();
        int y_start = 255;
        int z_start = p.getLocation().getBlockZ();
        Material material = Material.getMaterial("WALL_SIGN");
        if (material == null) {
            material = Material.OAK_WALL_SIGN;
        }

        while(!p.getWorld().getBlockAt(x_start, y_start, z_start).getType().equals(Material.AIR) && !p.getWorld().getBlockAt(x_start, y_start, z_start).getType().equals(material)) {
            --y_start;
            if (y_start == 1) {
                return;
            }
        }

        p.getWorld().getBlockAt(x_start, y_start, z_start).setType(material);
        this.sign = (Sign)p.getWorld().getBlockAt(x_start, y_start, z_start).getState();
        this.sign.setEditable(true);
        this.sign.update();
        this.sign.setLine(1, "s");
        this.sign.setLine(2, "s1");
        this.sign.setLine(3, "s2");
        this.sign.update(false, false);
        PacketContainer openSign = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
        BlockPosition position = new BlockPosition(x_start, y_start, z_start);
        openSign.getBlockPositionModifier().write(0, position);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(p, openSign);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }, 3L);
        Bukkit.getPluginManager().registerEvents(this.listener, plugin);
        p.sendMessage("1");
        p.sendMessage(String.valueOf(p));
        this.registerSignUpdateListener();
    }

    private void registerSignUpdateListener() {
        final ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        p.sendMessage("a");
        this.sign.update();
        this.sign.setEditable(true);
        this.packetListener = new PacketAdapter(plugin, new PacketType[]{PacketType.Play.Client.UPDATE_SIGN}) {
            public void onPacketReceiving(PacketEvent event) {
                event.getPlayer().sendMessage(String.valueOf(event.getPacket()));
                p.sendMessage("h");
                if (event.getPlayer().equals(SearchSignGUI.this.p)) {
                    String input;
                    if (Bukkit.getVersion().contains("1.8")) {
                        input = ((WrappedChatComponent[])event.getPacket().getChatComponentArrays().read(0))[0].getJson().replaceAll("\"", "");
                    } else {
                        input = ((String[])event.getPacket().getStringArrays().read(0))[0];
                    }
                    p.sendMessage("2");

                    Bukkit.getScheduler().runTask(plugin, () -> {
                        manager.removePacketListener(this);
                        HandlerList.unregisterAll(SearchSignGUI.this.listener);
                        p.sendMessage(input);
                        //new BrowsingAuctionsMenu(SearchSignGUI.this.p, SearchSignGUI.this.category, 0, input.equals("") ? null : (AuctionMaster.auctionsHandler.auctions.isEmpty() ? null : input));
                        SearchSignGUI.this.sign.getBlock().setType(Material.AIR);
                    });
                }else
                    p.sendMessage("ne");
                super.onPacketReceiving(event);
            }
        };
        manager.addPacketListener(this.packetListener);
    }

    private class LeaveListener implements Listener {
        private LeaveListener() {
        }

        @EventHandler
        public void onLeave(PlayerQuitEvent e) {
            if (e.getPlayer().equals(SearchSignGUI.this.p)) {
                ProtocolLibrary.getProtocolManager().removePacketListener(SearchSignGUI.this.packetListener);
                HandlerList.unregisterAll(this);
                SearchSignGUI.this.sign.getBlock().setType(Material.AIR);
            }

        }
    }
}
