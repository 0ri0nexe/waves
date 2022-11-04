package fr.orionexe.waves.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.orionexe.waves.MultiArenaState;
import fr.orionexe.waves.Plugin;
import fr.orionexe.waves.location_classes.arenas.Arena;

public class AutoStartTask extends BukkitRunnable {

    protected int timer = 10;
    protected Arena arena;
    protected Plugin main;

    protected AutoStartTask(Plugin main, Arena arena){
        this.arena = arena;
    }

    private void textPlayers(String message){
        for (Player pl: arena.getPlayers()){
            pl.sendMessage(message);
        }
    }

    @Override
    public void run() {

        if (timer == 0){
            textPlayers("Le jeu commence !");
            // if (this.solo){
            //     for (Player pl: main.getPlayers()){
            //         pl.teleport(new SpawnableArea(soloSpawns).getRandomSpawn());
            //     }
            // }
            afterTimer();
            cancel();
        }
        if (timer != 0){
            textPlayers("La partie commence dans : " + timer);
        }
        timer--; 

    }

    protected void afterTimer(){

    }
    
}
