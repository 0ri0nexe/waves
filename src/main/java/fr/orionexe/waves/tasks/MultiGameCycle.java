package fr.orionexe.waves.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import fr.orionexe.waves.Plugin;
import fr.orionexe.waves.location_classes.arenas.MultiArena;

public class MultiGameCycle extends BukkitRunnable {

    private Plugin main; // instance principale
    private MultiArena arena; //l'arène de jeu

    private int timer = 5; // timer avant que la game ne commence
    
    private MultiGState currentState;

    private List<Entity> monsters;


    public MultiGameCycle(Plugin main, MultiArena arena){
        this.main = main;
        this.arena = arena;
        this.currentState = MultiGState.STARTING;
    }
    
    public void setState(MultiGState newState){
        this.currentState = newState;
    }

    public boolean isState(MultiGState anotherState){
        if (currentState == anotherState)
        return true;
        else 
        return false;
    }

    // envoie un message à tous les joueurs de la game
    private void messagePlayers(String message){
        for(Player pl: arena.getPlayers()){
            pl.sendMessage(message);
        }
    }

    private List<Entity> getMobsToSpawn(){
        List<Entity> monsters = new ArrayList<>();
        Zombie zombie = (Zombie) main.getWorld().spawnEntity(arena.getMobsSpawns().getRandomSpawn(), EntityType.ZOMBIE);
        monsters.add(zombie);
        return monsters;
    }

    @Override
    public void run() {

        if (isState(MultiGState.STARTING)){
            manageTimer();
        }

        if (isState(MultiGState.PLAYING)){
            playTime();
        }
    }

    public void manageTimer(){
        setState(MultiGState.STARTING);
        if (timer == 5){
            messagePlayers("§5Le jeu va démarer dans §c5 secondes§5 ! \nPréparez vous !");
        }
        if (timer == 3 || timer == 2 || timer == 1){
            messagePlayers("§c" + timer);
        }
        if (timer == 0){
            messagePlayers("§5Bonne chance !");
            setState(MultiGState.PLAYING);
            monsters = getMobsToSpawn();
        }
        timer--;
    }

    public boolean checkWin(){
        boolean monstersCleared = true;
        for(Entity monster : monsters){
            if (!monster.isDead()){
                monstersCleared = false;
            }
        }
        return monstersCleared;
    }

    private void playTime(){
        
        if (checkWin()){
            monsters.clear();
            arena.messagePlayers("tous les monstres ont été éliminé !");
            setState(MultiGState.BETWEEN);
        }
    }

}
