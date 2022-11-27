package fr.orionexe.waves.location_classes.arenas;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.orionexe.waves.MultiArenaState;
import fr.orionexe.waves.location_classes.MobsArea;

public class MultiArena extends Arena{
    
    private MobsArea mobSpawns;
    private Location spawn;

    private MultiArenaState currentGState;

    public MultiArena(String name, MobsArea mobSpawns, Location spawn, Location lobby, Location fLocation, Location sLocation){
        super(name, lobby, fLocation, sLocation);
        this.mobSpawns = mobSpawns;
        this.spawn = spawn;
        this.isstarted = false;
        this.currentGState = MultiArenaState.WAITING;
    }

    public void addPlayer(Player pl){
        players.add(pl);
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
