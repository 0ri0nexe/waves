package fr.orionexe.waves.tasks;

import java.util.List;

import org.bukkit.entity.Player;

import fr.orionexe.waves.MultiArenaState;
import fr.orionexe.waves.Plugin;
import fr.orionexe.waves.location_classes.arenas.MultiArena;

public class MultiAutoStartTask extends AutoStartTask {

    private MultiArena arena;
    
    // public AutoStartTask(Plugin main, SoloArena arena){
    //     this.solo = true;
    //     this.main = main;
    //     this.soloArena = arena;
    //     this.player = soloArena.getPlayers();
    // }

    public MultiAutoStartTask(Plugin main, MultiArena arena){
        super(main, arena);
        this.arena = (MultiArena) arena;
    }

    @Override
    public void run() {

        for (Player player: arena.getPlayers()){
            player.setLevel(timer);
        }
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
