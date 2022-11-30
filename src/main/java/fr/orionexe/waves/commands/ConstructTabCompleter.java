package fr.orionexe.waves.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import fr.orionexe.waves.Plugin;
import fr.orionexe.waves.location_classes.arenas.MultiArena;

public class ConstructTabCompleter implements TabCompleter{

    private Plugin main;

    public ConstructTabCompleter(Plugin main){
        this.main = main;
    }

    private List<String> getValidsStrings(List<String> list, String ActualArg){
        List<String> solutions = new ArrayList<>();
        for(String test : list){
            if (test.startsWith(ActualArg)){
                solutions.add(test);
            }
        }
        return solutions;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {        

        List<String> firstArgsList = new ArrayList<>();
        firstArgsList.add("solo");
        firstArgsList.add("multi");

        List<String> secondArgsList = new ArrayList<>();

        secondArgsList.add("setlobby");
        secondArgsList.add("setspawn");
        secondArgsList.add("setmobspawn");
        secondArgsList.add("setloc1");
        secondArgsList.add("setloc2");
        
        // ce ont les paramètres qui nécéssitent de lister les arènes
        List<String> parametersArenasListable = secondArgsList;
        
        secondArgsList.add("list");
        secondArgsList.add("createarena");

        List<String> multiArenasNamesList = main.getAllMultiArenas();

        if (cmd.getName().equalsIgnoreCase("wv")){

            if (args.length == 1){
                return firstArgsList;
            }

            if (args[0].equalsIgnoreCase("multi")){
                if (args.length == 2) {
                return getValidsStrings(secondArgsList, args[1]);
                }
                else if (args.length == 3 & parametersArenasListable.contains(args[1])) {
                    return getValidsStrings(multiArenasNamesList, args[2]);
                }
            }

            
        }

        else if (args[0].equalsIgnoreCase("solo")){
            return secondArgsList;
        }
            // set la liste des arènes pour le solo
        return null;
    }
    
}
