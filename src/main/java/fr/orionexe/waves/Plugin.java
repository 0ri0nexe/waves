package fr.orionexe.waves;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * waves java plugin
 */
public class Plugin extends JavaPlugin
{
	private static final Logger LOGGER=Logger.getLogger("waves");
	private GState currentState;

	public void onEnable()
	{
		LOGGER.info("[WAVES - Bucher Plugin] - Plugin en cours d'execution");
	}

	public void onDisable()
	{
		LOGGER.info("[WAVES - Bucher Plugin] - Plugin arrete");
	}
	public void setState(GState newState){
		this.currentState = newState;
	}

}
