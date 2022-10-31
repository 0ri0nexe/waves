package fr.orionexe.waves.tasks;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.orionexe.waves.Plugin;

public class MultiGameCycle extends BukkitRunnable {

    private Plugin main;
    private List<Player> players;
    private int timer = 5;

    public MultiGameCycle(Plugin main, List<Player> players){
        this.main = main;
        this.players = players;
    }

    private void messagePlayers(String message){
        for(Player pl: players){
            pl.sendMessage(message);
        }
    }

    @Override
    public void run() {
        
        if (timer == 5){
            messagePlayers("§5Le jeu démarre dans §c5 secondes§5 ! \nPréparez vous !");
        }
        if (timer == 3 || timer == 2 || timer == 1){
            messagePlayers("§c" + timer);
        }
        if (timer == 0){
            messagePlayers("§5-Bonne chance !");
        }
        timer--;
    }
}
