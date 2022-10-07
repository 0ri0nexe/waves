package fr.orionexe.waves.commands;

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
    private Location spawn;

    public LaunchCommands(Plugin main, Location spawn){
        this.main = main;
        this.spawn = spawn;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            if (cmd.getName().equalsIgnoreCase("wsolo")){
                Player player = (Player)sender;
                player.sendMessage("bienvenu dans le jeu !");

                player.teleport(spawn);

                if (!main.isState(GState.WAITING)){
                    player.sendMessage("Le jeu a déja commencé !");
                    return true;
                }

                if (!main.getPlayers().contains(player)){
                    main.getPlayers().add(player);
                }
                player.setGameMode(GameMode.ADVENTURE);

                if (main.isState(GState.WAITING) && main.getPlayers().size() == 1){
                    AutoStartTask start = new AutoStartTask(main);
                    start.runTaskTimer(main, 0, 20);
                    main.setState(GState.STARTING);
                }

                return true;
            }
        }
        return false;
    }
    
}
