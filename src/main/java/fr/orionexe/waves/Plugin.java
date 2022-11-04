package fr.orionexe.waves;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.orionexe.waves.commands.MultiLaunchCommands;
import fr.orionexe.waves.listeners.GPlayerListener;

/*
* waves java plugin
*/
public class Plugin extends JavaPlugin
{
	private static final Logger LOGGER=Logger.getLogger("waves");
	private GState currentState;
	private GKind currentKind;
	private List<Player> players = new ArrayList<>();
	private World world = Bukkit.getWorld(getConfig().getString("world"));
	// fin multi
	public Location multiEndLocation = new Location(world, getConfig().getInt("multi.end.x"), getConfig().getInt("multi.end.y"), getConfig().getInt("multi.end.z"));

	public World getWorld(){
		return this.world;
	}

	public Location getMultiEndLocation(){
		return multiEndLocation;
	}

	// setter et getter d'enumerations
	public void setState(GState newState){
		this.currentState = newState;
	}

	public boolean isState(GState state){
		return this.currentState == state;
	}
	
	public void setKind(GKind kind){
		currentKind = kind;
	}
	public boolean isKind(GKind kind){
		return currentKind == kind;
	}

	public List<Player> getPlayers(){
		return players;
	}

	public void onEnable(){
		saveDefaultConfig();


		// multi
			// lobby
		Location multiLobby = new Location(world, getConfig().getInt("multi.lobby.x"), getConfig().getInt("multi.lobby.y"), getConfig().getInt("multi.lobby.z"));
			// spawn
		Location multiSpawn = new Location(world, getConfig().getInt("multi.spawnable_point.x"), getConfig().getInt("multi.spawnable_point.y"), getConfig().getInt("multi.spawnable_point.z"));
			// spawns des mobs
		List<Location> multiMobsSpawns = new ArrayList<Location>();
		int multiMobsY = getConfig().getInt("multi.end.y");
		Location multiMobPoint1 = new Location(world, getConfig().getInt("multi.mobs_points.x1"), multiMobsY, getConfig().getInt("multi.mobs_points.z1"));
		Location multiMobPoint2 = new Location(world, getConfig().getInt("multi.mobs_points.x2"), multiMobsY, getConfig().getInt("multi.mobs_points.z2"));
		Location multiMobPoint3 = new Location(world, getConfig().getInt("multi.mobs_points.x3"), multiMobsY, getConfig().getInt("multi.mobs_points.z3"));
		multiMobsSpawns.add(multiMobPoint1);
		multiMobsSpawns.add(multiMobPoint2);
		multiMobsSpawns.add(multiMobPoint3);
			// fin

		// solo
			// lobby
		Location soloLobby;
			// spawns
		ArrayList<Location> soloSpawns = new ArrayList<Location>();
		
		// set les spawns des mobs
		

		// set la game en attente de joueurs
		setState(GState.WAITING);
		// set la commande pour join le multi
		getCommand("wmulti").setExecutor(new MultiLaunchCommands(this, multiLobby, multiSpawn, multiMobsSpawns));
		// message à l'execution
		LOGGER.info("[WAVES - Bucher Plugin] - Plugin en cours d'execution");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new GPlayerListener(this), this);
	}

	public void onDisable(){
		LOGGER.info("[WAVES - Bucher Plugin] - Plugin arrete");
	}

	public void checkWin(){
		
	}
}
