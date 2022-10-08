package fr.orionexe.waves.tasks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.orionexe.waves.GState;
import fr.orionexe.waves.Plugin;
import fr.orionexe.waves.SpawnableArea;

public class AutoStartTask extends BukkitRunnable {
    
    private int timer = 10;
    private Plugin main;
    private List<Player> players;
    private ArrayList<Location> spawns;

    public AutoStartTask(Plugin main, List<Player> players, ArrayList<Location> spawns){
        this.main = main;
        this.players = players;
    }

    private void textPlayers(String message){
        for (Player pl: players){
            pl.sendMessage(message);
        }
    }

    @Override
    public void run() {

        for (Player player: main.getPlayers()){
            player.setLevel(timer);
        }

        if (timer == 0){
            textPlayers("Le jeu commence !");
            main.setState(GState.PLAYING);

            for (Player pl: main.getPlayers()){
                pl.teleport(new SpawnableArea(spawns).getRandomSpawn());
            }
            cancel();
        }
        if (timer != 0){
            textPlayers("Le jeu va commencer dans " + timer);
        }
        timer--;        
    }
    
}
