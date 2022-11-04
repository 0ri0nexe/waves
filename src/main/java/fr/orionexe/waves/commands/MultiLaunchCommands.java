package fr.orionexe.waves.commands;
/*
 * commande /wmulti
 */

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.orionexe.waves.GKind;
import fr.orionexe.waves.MultiArenaState;
import fr.orionexe.waves.Plugin;
import fr.orionexe.waves.location_classes.arenas.MultiArena;
import fr.orionexe.waves.tasks.MultiAutoStartTask;

public class MultiLaunchCommands implements CommandExecutor {

    private Plugin main;
    private MultiArena arena;

    public MultiLaunchCommands(Plugin main, MultiArena arena){
        this.main = main;
        this.arena = arena;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player){
            if (cmd.getName().equalsIgnoreCase("wmulti")){
                Player player = (Player)sender;
                player.sendMessage("bienvenu dans le jeu !");

                player.teleport(arena.getLobby());

                if (!arena.isState(MultiArenaState.WAITING)){
                    player.sendMessage("Le jeu a déja commencé !");
                    return true;
                }

                if (!arena.getPlayers().contains(player)){
                    for (Player pl: arena.getPlayers()){
                        pl.sendMessage(pl.getName() + "a rejoint" + arena.getPlayers().size() +"joueurs en jeu !");
                    }
                    arena.getPlayers().add(player);
                }
                player.setGameMode(GameMode.ADVENTURE);

                if (arena.isState(MultiArenaState.WAITING) && arena.getPlayers().size() == 1){
                    MultiAutoStartTask start = new MultiAutoStartTask(main, arena);
                    start.runTaskTimer(main, 0, 20);
                    arena.setState(MultiArenaState.STARTING);
                }

                return true;
            }
        }
        return false;
    }

}
