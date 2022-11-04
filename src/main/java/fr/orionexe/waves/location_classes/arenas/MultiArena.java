package fr.orionexe.waves.location_classes.arenas;

import org.bukkit.Location;

import fr.orionexe.waves.MultiArenaState;
import fr.orionexe.waves.location_classes.MobsArea;

public class MultiArena extends Arena{
    
    private MobsArea mobSpawns;
    private Location spawn;

    private MultiArenaState currentGState = MultiArenaState.WAITING;

    public MultiArena(String name, MobsArea mobSpawns, Location spawn, Location lobby){
        super(name, lobby);
        this.mobSpawns = mobSpawns;
        this.spawn = spawn;
        this.isstarted = false;
    }

    public void setState(MultiArenaState newState){
		this.currentGState = newState;
	}

    public boolean isState(MultiArenaState state){
		return this.currentGState == state;
	}

    public MobsArea getMobsSpawns(){
        return mobSpawns;
    }

    public Location getSpawn(){
        return spawn;
    }
}
