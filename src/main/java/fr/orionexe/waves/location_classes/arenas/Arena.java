package fr.orionexe.waves.location_classes.arenas;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Arena {
    protected String name;
    protected Location lobby;
    protected boolean isstarted;
    protected List<Player> players = new ArrayList<>();

    protected Location firstCoords;
    protected Location secondCoords;

    protected Arena(String name, Location lobby, Location fLocation, Location sLocation){
        this.name = name;
        this.lobby = lobby;
        this.firstCoords = fLocation;
        this.secondCoords = sLocation;
    }

    public void messagePlayers(String message){
        for (Player pl : players){
            pl.sendMessage(message);
        }
    }

    public String getName(){
        return name;
    }

    public Location getLobby(){
        return lobby;
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

    public Arena asArena(){
        return new Arena(name, lobby, firstCoords, secondCoords);
    }

}
