package de.theredend2000.lobbyx.commands;

import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class MusicCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length == 1){
                String song = args[0];
                if(!(song.equals("Faded") || song.equals("AllStar") || song.equals("Levels") || song.equals("Imagine") || song.equals("Love") || song.equals("Happy") || song.equals("Animals"))){
                    player.sendMessage("§7Song: §aFaded, AllStar, Levels, Imagine, Love, Happy, Animals §cMuss so geschrieben werden.");
                    return true;
                }
                if(Bukkit.getPluginManager().isPluginEnabled("NoteBlockAPI")){
                    Song playingsong = NBSDecoder.parse(new File("plugins/Lobbyx/"+song+".nbs"));
                    if(playingsong != null){
                        EntitySongPlayer esp2 = new EntitySongPlayer(playingsong);
                        esp2.setEntity(player);
                        esp2.setDistance(16);
                        esp2.addPlayer(player);
                        esp2.setPlaying(true);
                        player.sendMessage("§aNow Playing: §6"+args[0]);
                    }else
                        player.sendMessage("§cError.");
                }
            }else
                player.sendMessage("§c/music <Song>");
        }
        return false;
    }
}
