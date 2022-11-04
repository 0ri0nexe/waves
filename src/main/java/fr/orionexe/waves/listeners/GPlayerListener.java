package fr.orionexe.waves.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.orionexe.waves.GKind;
import fr.orionexe.waves.MultiArenaState;
import fr.orionexe.waves.Plugin;

public class GPlayerListener implements Listener{

    private Plugin main;

    public GPlayerListener(Plugin main){
        this.main = main;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent evt){

        Player quitter = (Player)evt.getPlayer();

        if (main.getArenaByPlayer(quitter).getPlayers().contains(evt.getPlayer())){
            main.getArenaByPlayer(quitter).getPlayers().remove(quitter);
        }
    }
    @EventHandler
    public void onDamage(EntityDamageEvent evt){
        if (evt.getEntity() instanceof Player)
        return;
        Player victim = (Player)evt.getEntity();


        if (main.getArenaByPlayer(victim) == null || !main.getArenaByPlayer(victim).isstarted()){
            evt.setCancelled(true);
            return;
        }
        
    }
    @EventHandler
    public void onRespawn(EntityResurrectEvent evt){
        Entity entity = evt.getEntity();
        entity.sendMessage("test");
        if (entity instanceof Player){
            Player player = (Player)entity;
            if (main.getPlayers().contains(entity)){
                if (main.isKind(GKind.MULTI)){
                    player.teleport(main.getMultiEndLocation());
                    player.sendMessage("nul mdr");
                    main.getPlayers().remove(player);
                }
            }
        }
    }
}
