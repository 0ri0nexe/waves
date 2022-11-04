package fr.orionexe.waves.commands;

import java.util.List;

/*
 * commande /wmulti
 */

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.orionexe.waves.GKind;
import fr.orionexe.waves.GState;
import fr.orionexe.waves.Plugin;
import fr.orionexe.waves.tasks.AutoStartTask;

public class MultiLaunchCommands implements CommandExecutor {

    private Plugin main;
    private Location lobby;
    private Location inGameSpawn;
    private List<Location> mobsSpawns;

    public MultiLaunchCommands(Plugin main, Location lobby, Location inGameSpawn, List<Location> mobsSpawns){
        this.main = main;
        this.lobby = lobby;
        this.inGameSpawn = inGameSpawn;
        this.mobsSpawns = mobsSpawns;
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
                    AutoStartTask start = new AutoStartTask(main, main.getPlayers(), inGameSpawn, mobsSpawns);
                    start.runTaskTimer(main, 0, 20);
                    main.setState(GState.STARTING);
                    main.setKind(GKind.MULTI);
                }

                return true;
            }
        }
        return false;
    }

}
