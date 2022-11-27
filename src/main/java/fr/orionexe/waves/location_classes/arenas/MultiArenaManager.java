package fr.orionexe.waves.location_classes.arenas;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class MultiArenaManager {

    private List<MultiArena> arenas = new ArrayList<>();

    public void addArena(MultiArena ar){
        arenas.add(ar);
    }

    public void messagePlayers(List<Player> players, String message){
        for (Player pl : players){
            pl.sendMessage(message);
        }
    }

    public void joinArena(List<Player> players){
        MultiArena nextArena = getNextArena();
        if (nextArena != null){
            for (int i = 0; i < players.size(); i++ ){
                Player pl = players.get(i);
                nextArena.addPlayer(pl);
                pl.teleport(nextArena.getSpawn());
            }
        }
        else {
            messagePlayers(players, "aucune arène dispo, réessayez plus tard");
        }
    }
    public MultiArena getNextArena() {

        for(MultiArena ar : arenas){
            if (!ar.isstarted()){
                return ar;
            }
        }
        return null;
    }

    public List<MultiArena> getArenas(){
        return arenas;
    }

    public Arena playerArena(Player pl){
        for (Arena ar : arenas){
            if (ar.getPlayers().contains(pl)){
                return (Arena) ar;
            }
        }
        return null;
    }
}
