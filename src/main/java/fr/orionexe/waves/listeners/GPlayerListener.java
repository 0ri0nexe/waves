package fr.orionexe.waves.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.orionexe.waves.GKind;
import fr.orionexe.waves.GState;
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
    @EventHandler
    public void onDamage(EntityDamageEvent evt){
        if (main.getPlayers().contains(evt.getEntity())){
            if (!main.isState(GState.BETWEENWAWES) && !main.isState(GState.INWAWE)){
                evt.setCancelled(true);
                return;
            }
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
