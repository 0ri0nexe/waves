package fr.orionexe.waves.tasks;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;

import fr.orionexe.waves.Plugin;

public class MultiGameCycle extends BukkitRunnable {

    private Plugin main; // instance principale
    private List<Player> players; // joueurs de la partie
    private List<Location> mobsSpawns;

    private int timer = 5; // timer avant que la game ne commence
    private boolean timerIsActive = true; // evite que la partie ne commence pendant le timer
    
    private int timeBeforeContinue = 0; // temps entre les vagues (0 quand la game commence)
    private boolean haveAlreadyStarted = false; // déclencheur des mobs

    public MultiGameCycle(Plugin main, List<Player> players, List<Location> mobsSpawns){
        this.main = main;
        this.players = players;
        this.mobsSpawns = mobsSpawns;
    }

    //envoie un message à tous les joueurs de la game
    private void messagePlayers(String message){
        for(Player pl: players){
            pl.sendMessage(message);
        }
    }

    @Override
    public void run() {
        if (timerIsActive){
            manageTimer();
        }
        else if (timeBeforeContinue == 0){
            if (!haveAlreadyStarted){
                Zombie zombies = (Zombie) main.getWorld().spawnEntity(getRandomSpawn(), EntityType.ZOMBIE);
                haveAlreadyStarted = true;
            }
        }
    }

    private Location getRandomSpawn(){
        Random r = new Random();
        return mobsSpawns.get(r.nextInt(mobsSpawns.size()));
    }

    public void manageTimer(){
        if (timer == 5){
            messagePlayers("§5Le jeu va démarer dans §c5 secondes§5 ! \nPréparez vous !");
        }
        if (timer == 3 || timer == 2 || timer == 1){
            messagePlayers("§c" + timer);
        }
        if (timer == 0){
            messagePlayers("§5Bonne chance !");
            timerIsActive = false;
        }
        timer--;
    }
}
