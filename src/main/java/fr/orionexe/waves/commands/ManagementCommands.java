package fr.orionexe.waves.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
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
        Location playerLocation = player.getLocation();

        if (label.equalsIgnoreCase("wv")){

            if (args[0].equalsIgnoreCase("multi"))

                if (args[1].equalsIgnoreCase("createarena")){
                    player.sendMessage("creer arene");
                        
                    ConfigurationSection arenas = arenaConfig.createSection("arenas.multi");
                    arenas.set(args[2] + "." + "lobby", "0/0/0" );
                    arenas.set(args[2] + "." + "spawn", "0/0/0" );
                    List<String> mobs_points = Arrays.asList();
                    arenas.set(args[2] + "." + "mobs_points", mobs_points);
                    arenas.set(args[2] + "." + "loc2", "0/0/0");
                    arenas.set(args[2] + "." + "loc1", "0/0/0");

                    player.sendMessage("vous venez de créer l'arène §5" + args[1]);

                    try {
                        arenaConfig.save(arenaFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return true;
                }

                //set les paramètres de l'arène
                if (args[1].equalsIgnoreCase("setlobby")){
                    
                    player.sendMessage("vous venez de set le lobby de l'arène §5" + args[2]);
                    arenaConfig.set("arenas.multi." + args[2] + ".lobby", main.locToCoords(playerLocation));
                    try {
                        arenaConfig.save(arenaFile);
                    } 
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }

                if (args[1].equalsIgnoreCase("setspawn")){

                    player.sendMessage("vous venez de set le spawn de l'arène §5" + args[2]);
                    arenaConfig.set("arenas.multi." + args[2] + ".spawn", main.locToCoords(playerLocation));
                    try {
                        arenaConfig.save(arenaFile);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }

                if (args[1].equalsIgnoreCase("setmobspawn")){

                    player.sendMessage("vous venez de set un spawn de monstre de l'arène §5" + args[2]);
                    List<String> mobs_points = arenaConfig.getStringList("arenas.multi." + args[2] + ".mobs_points");
                    mobs_points.add(main.locToCoords(playerLocation));
                    arenaConfig.set("arenas.multi." + args[2] + ".mobs_points", mobs_points);
                    try {
                        arenaConfig.save(arenaFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return true;
                }

                if (args[1].equalsIgnoreCase("setloc1")){

                    player.sendMessage("vous venez de set une limite de de l'arène §5" + args[2]);
                    arenaConfig.set("arenas.multi." + args[2] + ".loc1", main.locToCoords(playerLocation));
                    try {
                        arenaConfig.save(arenaFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return true;
                }

                if (args[1].equalsIgnoreCase("setloc2")){

                    player.sendMessage("vous venez de set une limite de de l'arène §5" + args[2]);
                    arenaConfig.set("arenas.multi." + args[2] + ".loc2", main.locToCoords(playerLocation));
                    try {
                        arenaConfig.save(arenaFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return true;
                }

                if (args[1].equalsIgnoreCase("list") && args.length == 2){
                    String message = "Liste des arènes :\n multi:";
                    for (String ar : main.getAllMultiArenas()){
                        message += "\n  -" + ar;
                    }

                    player.sendMessage(message);
                }
            }
        return false;
    }
}