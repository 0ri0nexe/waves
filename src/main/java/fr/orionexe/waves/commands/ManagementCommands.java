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

            if (args[0].equalsIgnoreCase("mcreatearena")){
                player.sendMessage("creer arene");
                if (args.length == 2){
                    List<String> parameters = Arrays.asList("lobby", "spawn", "mobs_points");
                    
                    ConfigurationSection arenas = arenaConfig.createSection("arenas.multi");
                    arenas.set(args[1] + "." + parameters.get(0), "0/0/0" );
                    arenas.set(args[1] + "." + parameters.get(1), "0/0/0" );
                    List<String> mobs_points = Arrays.asList();
                    arenas.set(args[1] + "." + parameters.get(2), mobs_points);

                    player.sendMessage("vous venez de créer l'arène §5" + args[1]);

                    try {
                        arenaConfig.save(arenaFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    player.sendMessage("Merci de respecter la forme /wv createmultiarena <Nom>");
                }
                return true;
            }

            //set les paramètres de l'arène
            if (args[0].equalsIgnoreCase("msetlobby")){
                
                player.sendMessage("vous venez de set le lobby de l'arène §5" + args[1]);
                arenaConfig.set("arenas.multi." + args[1] + ".lobby", main.locToCoords(playerLocation));
                try {
                    arenaConfig.save(arenaFile);
                } 
                catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("msetspawn")){
                if (main.getArenaByName(args[1]) != null){
                    player.sendMessage("vous venez de set le spawn de l'arène §5" + args[1]);
                    arenaConfig.set("arenas.multi." + args[1] + ".spawn", main.locToCoords(playerLocation));
                    try {
                        arenaConfig.save(arenaFile);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("msetmobspawn")){
                if (main.getArenaByName(args[1]) != null){
                    player.sendMessage("vous venez de set un spawn de monstre de l'arène §5" + args[1]);
                    List<String> mobs_points = arenaConfig.getStringList("arenas.multi." + args[1] + ".mobs_points");
                    mobs_points.add(main.locToCoords(playerLocation));
                    arenaConfig.set("arenas.multi." + args[1] + ".mobs_points", mobs_points);
                    try {
                        arenaConfig.save(arenaFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("msetloc1")){
                if (main.getArenaByName(args[1]) != null){
                    player.sendMessage("vous venez de set une limite de de l'arène §5" + args[1]);
                    arenaConfig.set("arenas.multi." + args[1] + ".first_coords", main.locToCoords(playerLocation));
                    try {
                        arenaConfig.save(arenaFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("msetloc2")){
                if (main.getArenaByName(args[1]) != null){
                    player.sendMessage("vous venez de set une limite de de l'arène §5" + args[1]);
                    arenaConfig.set("arenas.multi." + args[1] + ".second_coords", main.locToCoords(playerLocation));
                    try {
                        arenaConfig.save(arenaFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("list") && args.length == 1){
                String message = "Liste des arènes :\n multi:";
                for (MultiArena ar : main.getMultiArenas()){
                    message += "\n  -" + ar.getName();
                }

                player.sendMessage(message);
            }
        }
        return false;
    }
}