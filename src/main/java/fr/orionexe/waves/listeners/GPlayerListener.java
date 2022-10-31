package fr.orionexe.waves.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.orionexe.waves.Plugin;

public class GPlayerListener implements Listener{

    private Plugin main;

    public GPlayerListener(Plugin main){
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent evt){
        if (main.getPlayers().contains(evt.getPlayer())){
            main.getPlayers().remove(evt.getPlayer());
        }
    }
}
