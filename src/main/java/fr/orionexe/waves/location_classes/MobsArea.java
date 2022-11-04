package fr.orionexe.waves.location_classes;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;

public class MobsArea {

    private List<Location> spawns;

    public MobsArea(List<Location> spawns){
        this.spawns = spawns;
    }

    public Location getRandomSpawn(){

        Random r = new Random();
        return spawns.get(r.nextInt(spawns.size()));
    }
}
