package fr.orionexe.waves.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.orionexe.waves.MultiArenaState;
import fr.orionexe.waves.Plugin;
import fr.orionexe.waves.location_classes.arenas.Arena;

public class GPlayerListener implements Listener{

    private Plugin main;


    public GPlayerListener(Plugin main){
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent evt){

        Player quitter = (Player)evt.getPlayer();

        if (main.getArenaByPlayer(quitter) != null){
            main.getArenaByPlayer(quitter).getPlayers().remove(quitter);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent evt){
        if (!(evt.getEntity() instanceof Player))
        return;
        Player victim = (Player)evt.getEntity();

        if (main.getArenaByPlayer(victim) == null || !main.getArenaByPlayer(victim).isstarted()){
            evt.setCancelled(true);
            return;
        }

        Entity entity = evt.getEntity();
        if (entity instanceof Player){
            Player player = (Player)entity;
            if (evt.getDamage() >= player.getHealth()){
            
                if (main.getArenaByPlayer(player) != null & !main.getArenaByPlayer(player).isstarted()){

                    Arena ar = main.getArenaByPlayer(player);

                    player.teleport(ar.getLobby());
                    player.sendMessage("nul mdr");
                    ar.getPlayers().remove(player);
                }
            }
        }
    }
}
