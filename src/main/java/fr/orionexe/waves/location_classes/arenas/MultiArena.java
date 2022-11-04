package fr.orionexe.waves.location_classes.arenas;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.orionexe.waves.location_classes.MobsArea;

public class MultiArena {
    private MobsArea mobSpawns;
    private Location spawn;
    private List<Player> players;
    private boolean isstarted = false;

    public MultiArena(MobsArea mobSpawns, Location spawn){
        this.mobSpawns = mobSpawns;
        this.spawn = spawn;
        this.players = new ArrayList<>();
        this.isstarted = false;
    }

    public MobsArea getMobsSpawns(){
        return mobSpawns;
    }

    public Location getSpawn(){
        return spawn;
    }

    public void addPlayer(Player pl){
        players.add(pl);
    }
    public List<Player> getPlayers(){
        return players;
    }

    public boolean isstarted(){
        return isstarted;
    }

    
}
