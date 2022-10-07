package fr.orionexe.waves.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.orionexe.waves.GState;
import fr.orionexe.waves.Plugin;

public class AutoStartTask extends BukkitRunnable {
    
    private int timer = 10;
    private Plugin main;

    public AutoStartTask(Plugin main){
        this.main = main;
    }

    @Override
    public void run() {

        for (Player player: main.getPlayers()){
            player.setLevel(timer);
        }

        if (timer == 0){
            Bukkit.broadcastMessage("Le jeu commence !");
            main.setState(GState.PLAYING);
            cancel();
        }
        if (timer != 0){
        Bukkit.broadcastMessage("Le jeu va commencer dans " + timer);
        }
        timer--;
        
    }
    
}
