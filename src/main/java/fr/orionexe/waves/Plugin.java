package fr.orionexe.waves;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.orionexe.waves.commands.MultiLaunchCommands;
import fr.orionexe.waves.listeners.GPlayerListener;
import fr.orionexe.waves.location_classes.MobsArea;
import fr.orionexe.waves.location_classes.arenas.MultiArenaManager;
import fr.orionexe.waves.location_classes.arenas.Arena;
import fr.orionexe.waves.location_classes.arenas.MultiArena;

/*
* waves java plugin
*/
public class Plugin extends JavaPlugin
{

	private List<MultiArena> multiArenas= new ArrayList<>();

	private static final Logger LOGGER=Logger.getLogger("waves");
	private MultiArenaState currentState;

	private World world = Bukkit.getWorld(getConfig().getString("world"));
	// fin multi
	public Location multiEndLocation = new Location(world, getConfig().getInt("multi.end.x"), getConfig().getInt("multi.end.y"), getConfig().getInt("multi.end.z"));

	private File arenaFile;
	private YamlConfiguration arenaConfig;
	private MultiArenaManager multiArenaManager = new MultiArenaManager();

	public List<MultiArena> getMultiArenas(){
		return multiArenas;
	}

	public World getWorld(){
		return this.world;
	}

	public Location getMultiEndLocation(){
		return multiEndLocation;
	}


	public MultiArenaManager getArenaManager(){
		return multiArenaManager;
	}

	// setter et getter d'enumerations

	public Arena getArenaByPlayer(Player pl){
		
	}

	// coordonnées en location
	public Location coordsToLoc(String label){
		String[] parsedLoc = label.split(",");
		double x = Double.valueOf(parsedLoc[0]);
		double y = Double.valueOf(parsedLoc[1]);	
		double z = Double.valueOf(parsedLoc[2]);
		Location loc = new Location(world, x, y, z);
		return loc;
	}

	private void loadArenaConfig(){
		//creation du fichier arena.yml
		if (!getDataFolder().exists()){
			getDataFolder().mkdir();
		}
		arenaFile = new File(getDataFolder()+ File.separator + "arenas.yml");

		if (!arenaFile.exists()){
			try {
				arenaFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		//chargement de la config
		arenaConfig = YamlConfiguration.loadConfiguration(arenaFile);
	}

	public String locToCoords(Location loc){
		return loc.getX() + "," + loc.getY() + "," + loc.getZ();
	} 

	public void onEnable(){

		loadArenaConfig();
		// charger les arenes
		ConfigurationSection arenaSection = arenaConfig.getConfigurationSection("arenas");
		
		for (String str : arenaSection.getConfigurationSection("multi").getKeys(false)){

			String spawnString = arenaSection.getString("multi." + str + ".spawn");
			String lobbyString = arenaSection.getString("multi." + str + ".lobby");
			String mobsSpawnsStrings = arenaSection.getString("multi." + str + ".mobs_points");
			String name = str;

			Location spawn = coordsToLoc(spawnString);
			Location lobby = coordsToLoc(lobbyString);
			List<Location> mobsSpawns = new ArrayList<>();
			for(int i = 0; i < mobsSpawnsStrings.split(";").length; i++){
				mobsSpawns.add(coordsToLoc(mobsSpawnsStrings.split(";")[i]));
			}
			MobsArea mobA = new MobsArea(mobsSpawns);

			MultiArena multiArena = new MultiArena(name, mobA, spawn, lobby);
			multiArenaManager.addArena(multiArena);
		}

		// set la game en attente de joueurs

		// set la commande pour join le multi
		getCommand("wmulti").setExecutor(new MultiLaunchCommands(this, multiArenaManager.getNextArena()));
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
