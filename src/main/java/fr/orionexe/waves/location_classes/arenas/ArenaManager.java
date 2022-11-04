package fr.orionexe.waves.location_classes.arenas;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class ArenaManager {

    private List<MultiArena> arenas = new ArrayList<>();

    public void addArena(MultiArena ar){
        arenas.add(ar);
    }
    public void joinArena(List<Player> players){
        
    }

}
