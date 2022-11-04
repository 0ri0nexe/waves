package fr.orionexe.waves.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import fr.orionexe.waves.Plugin;
import fr.orionexe.waves.location_classes.arenas.MultiArena;

public class ManagementCommands implements CommandExecutor{

    private Plugin main;
    private YamlConfiguration arenaConfig;
    private File arenaFile;

    public ManagementCommands(YamlConfiguration arenaConfig, File arenaFile, Plugin main){
        this.main = main;
        this.arenaConfig = arenaConfig;
        this.arenaFile = arenaFile;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if (!(sender instanceof Player))
            return true;
        Player player = (Player) sender;

        if (label.equalsIgnoreCase("wv")){

            if (args[0].equalsIgnoreCase("createmultiarena")){
                if (args.length == 2){
                    String arenaName = args[1];
                    player.sendMessage("vous venez de créer l'arène §5" + arenaName);
                    arenaConfig.set("arenas.multi", arenaName);
                    try {
                        arenaConfig.save(arenaFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    player.sendMessage("Merci de respecter la forme /wv createmultiarena <Nom>");
                }
            }
            if (args[0].equalsIgnoreCase("list") && args.length == 1){
                String message = "Liste des arènes :\n multi:";
                for (MultiArena ar : main.getMultiArenas()){
                    message += "\n  -" + ar.getName();
                }

                player.sendMessage(message);
            }
            return true;

        }

        return false;
    }
    
}
