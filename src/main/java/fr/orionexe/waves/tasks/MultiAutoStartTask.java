package fr.orionexe.waves.tasks;

import java.util.List;

import org.bukkit.entity.Player;

import fr.orionexe.waves.MultiArenaState;
import fr.orionexe.waves.Plugin;
import fr.orionexe.waves.location_classes.arenas.MultiArena;

public class MultiAutoStartTask extends AutoStartTask {

    private MultiArena arena;

    public MultiAutoStartTask(Plugin main, MultiArena arena){
        super(main, arena);
        this.arena = (MultiArena) arena;
    }
        
    @Override
    protected void afterTimer(){
        arena.setState(MultiArenaState.BETWEENWAWES);
        for (Player pl: arena.getPlayers()){
            pl.teleport(arena.getSpawn());
        }
        MultiGameCycle cycle = new MultiGameCycle(main, arena);
        cycle.runTaskTimer(main, 0, 20);
    }
    
}
