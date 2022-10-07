package fr.orionexe.waves;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.orionexe.waves.commands.LaunchCommands;

/*
 * waves java plugin
 */
public class Plugin extends JavaPlugin
{
	private static final Logger LOGGER=Logger.getLogger("waves");
	private GState currentState;
	private List<Player> players = new ArrayList<>();

	public void setState(GState newState){
		this.currentState = newState;
	}

	public boolean isState(GState state){
		return this.currentState == state;
	}

	public List<Player> getPlayers(){
		return players;
	}

	public void onEnable(){
		saveDefaultConfig();
		Location soloSpawn = new Location(Bukkit.getWorld(getConfig().getString("world")), getConfig().getInt("solo.spawn.x"), getConfig().getInt("solo.spawn.y"), getConfig().getInt("solo.spawn.z"));
		LOGGER.info("[WAVES - Bucher Plugin] - Plugin en cours d'execution");
		setState(GState.WAITING);
		getCommand("wsolo").setExecutor(new LaunchCommands(this, soloSpawn));
	}

	public void onDisable(){
		LOGGER.info("[WAVES - Bucher Plugin] - Plugin arrete");
	}
}
