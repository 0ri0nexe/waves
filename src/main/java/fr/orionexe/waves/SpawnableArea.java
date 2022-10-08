package fr.orionexe.waves;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Location;

/*
 * les zones de spawns pour 1 arène et permet d'y téléporter aléatoirement les joueurs
 */

public class SpawnableArea{
    // liste des spawns dispos
    ArrayList<Location> spawns = new ArrayList<Location>();

    // constructeur
    public SpawnableArea(ArrayList<Location> spawns) {
        this.spawns = spawns;
    }

    // donner un spawn random parmis ceux dispo
    public Location getRandomSpawn(){
        Random r = new Random();
        Location spawn = spawns.get(r.nextInt(spawns.size()));
        return spawn;
    }
}

