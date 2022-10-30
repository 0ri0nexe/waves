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
    private ArrayList<Location> soloSpawns;
    private Location multiSpawn;
    private boolean solo = false;
    private boolean multi = false;

    public AutoStartTask(Plugin main, List<Player> players, ArrayList<Location> soloSpawns){
        this.main = main;
        this.players = players;
        this.soloSpawns = soloSpawns;
        this.solo = true;
    }

    public AutoStartTask(Plugin main, List<Player> players, Location multiSpawn){
        this.main = main;
        this.players = players;
        this.multiSpawn = multiSpawn;
        this.multi = true;
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

            if (this.solo){
                for (Player pl: main.getPlayers()){
                    pl.teleport(new SpawnableArea(soloSpawns).getRandomSpawn());
                }
            }
            else if (this.multi){
                for (Player pl: main.getPlayers()){
                    pl.teleport(multiSpawn);
                }
                MultiGameCycle cycle = new MultiGameCycle(main, players);
                cycle.runTaskTimer(main, 0, 20);
            }

            cancel();
        }
        if (timer != 0){
            textPlayers("Le jeu va commencer dans " + timer);
        }
        timer--;        
    }
    
}
