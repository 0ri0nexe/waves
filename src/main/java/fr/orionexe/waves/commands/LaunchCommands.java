package fr.orionexe.waves.commands;

import java.util.ArrayList;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.orionexe.waves.GState;
import fr.orionexe.waves.Plugin;
import fr.orionexe.waves.tasks.AutoStartTask;

public class LaunchCommands implements CommandExecutor {

    private Plugin main;
    private Location lobby;
    private ArrayList<Location> spawns;

    public LaunchCommands(Plugin main, Location lobby, ArrayList<Location> spawns){
        this.main = main;
        this.lobby = lobby;
        this.spawns = spawns;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            if (cmd.getName().equalsIgnoreCase("wmulti")){
                Player player = (Player)sender;
                player.sendMessage("bienvenu dans le jeu !");

                player.teleport(lobby);

                if (!main.isState(GState.WAITING)){
                    player.sendMessage("Le jeu a déja commencé !");
                    return true;
                }

                if (!main.getPlayers().contains(player)){
                    for (Player pl: main.getPlayers()){
                        pl.sendMessage(pl.getName() + "a rejoint" + main.getPlayers().size() +"joueurs en jeu !");
                    }
                    main.getPlayers().add(player);
                }
                player.setGameMode(GameMode.ADVENTURE);

                if (main.isState(GState.WAITING) && main.getPlayers().size() == 1){
                    AutoStartTask start = new AutoStartTask(main, main.getPlayers(), spawns);
                    start.runTaskTimer(main, 0, 20);
                    main.setState(GState.STARTING);
                }

                return true;
            }
        }
        return false;
    }

}
