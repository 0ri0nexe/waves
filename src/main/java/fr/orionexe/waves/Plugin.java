package fr.orionexe.waves;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import fr.orionexe.waves.commands.ConstructTabCompleter;
import fr.orionexe.waves.commands.ManagementCommands;
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
	private static final Logger LOGGER=Logger.getLogger("waves");

	private World world = Bukkit.getWorld(getConfig().getString("world"));
	// fin multi

	private File arenaFile;
	private YamlConfiguration arenaConfig;
	private MultiArenaManager multiArenaManager = new MultiArenaManager();
	private List<String> allArenas; // inclus les arènes qui ne sont pas encore finies

	public World getWorld(){
		return this.world;
	}

	public MultiArenaManager getMultiArenaManager(){
		return multiArenaManager;
	}

	public List<MultiArena> getMultiArenas(){
		return multiArenaManager.getArenas();
	}

	// setter et getter d'enumerations

	public Arena getArenaByPlayer(Player pl){
		for (Arena ar : multiArenaManager.getArenas()){
			if (ar.getPlayers().contains(pl))
			return ar;
		}
		return null;
	}

	public Arena getArenaByName(String name){
		for (Arena ar : multiArenaManager.getArenas()){
			if (ar.getName().equalsIgnoreCase(name))
			return ar;
		}
		return null;
	}

	// coordonnées en location
	public Location coordsToLoc(String label){
		System.out.println(label);
		String[] parsedLoc = label.split("/");
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
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		//chargement de la config
		arenaConfig = YamlConfiguration.loadConfiguration(arenaFile);
		List<String> parameters = Arrays.asList("multi", "solo");
		arenaConfig.addDefault("arenas", "");
		for (String para : parameters){
			arenaConfig.addDefault("arenas." + para, true);
		}
	}

	public String locToCoords(Location loc){
		return loc.getX() + "/" + loc.getY() + "/" + loc.getZ();
	}

	public void onEnable(){

		saveDefaultConfig();
		loadArenaConfig();
		
		// charger les arenes
		world = Bukkit.getWorld(getConfig().getString("world"));
		
			ConfigurationSection arenaSection = arenaConfig.getConfigurationSection("arenas");
		try{	
			for (String str : arenaSection.getConfigurationSection("multi").getKeys(false)){
				String firstCoords = arenaSection.getString("multi." + str + ".first_coords");
				String secondCoords = arenaSection.getString("multi." + str + ".second_coords");
				String spawnString = arenaSection.getString("multi." + str + ".spawn");
				String lobbyString = arenaSection.getString("multi." + str + ".lobby");
				List<String> mobsSpawnsStrings = arenaSection.getStringList("multi." + str + ".mobs_points");
				String name = str;
				
				Location firstLoc = coordsToLoc(firstCoords);
				Location secondLoc = coordsToLoc(secondCoords);
				Location spawn = coordsToLoc(spawnString);
				Location lobby = coordsToLoc(lobbyString);
				List<Location> mobsSpawns = new ArrayList<>();
				for(int i = 0; i < mobsSpawnsStrings.size(); i++){
					mobsSpawns.add(coordsToLoc(mobsSpawnsStrings.get(i)));
				}
				MobsArea mobA = new MobsArea(mobsSpawns);
	
				MultiArena multiArena = new MultiArena(name, mobA, spawn, lobby, firstLoc, secondLoc);
				multiArenaManager.addArena(multiArena);
				allArenas.add(name);
			}
		}
		catch (NullPointerException e){
			LOGGER.info("arenas.yml --> \"arenas.multi\" n'existe pas");
		}

		// set les commandes
		getCommand("wmulti").setExecutor(new MultiLaunchCommands(this, multiArenaManager.getNextArena()));
		
		getCommand("wv").setExecutor(new ManagementCommands(arenaConfig, arenaFile, this));
		getCommand("wv").setTabCompleter(new ConstructTabCompleter(this));
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
