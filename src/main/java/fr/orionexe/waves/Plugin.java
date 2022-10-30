package fr.orionexe.waves;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.orionexe.waves.commands.MultiLaunchCommands;

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
		World world = Bukkit.getWorld(getConfig().getString("world"));
		// lobbys
		Location multiLobby = new Location(world, getConfig().getInt("multi.spawn.x"), getConfig().getInt("multi.spawn.y"), getConfig().getInt("multi.spawn.z"));
		
		// liste des spawns au lancement
			//multi
		Location multiSpawn = new Location(world, getConfig().getInt("multi.spawnable_point.x"), getConfig().getInt("multi.spawnable_point.y"), getConfig().getInt("multi.spawnable_point.z"));
			//solo
		ArrayList<Location> spawns = new ArrayList<Location>();


		// set la game en attente de joueurs
		setState(GState.WAITING);
		// set la commande pour join le multi
		getCommand("wmulti").setExecutor(new MultiLaunchCommands(this, multiLobby, multiSpawn));
		// message à l'execution
		LOGGER.info("[WAVES - Bucher Plugin] - Plugin en cours d'execution");
	}

	public void onDisable(){
		LOGGER.info("[WAVES - Bucher Plugin] - Plugin arrete");
	}
}
