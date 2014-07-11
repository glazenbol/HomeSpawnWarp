package com.homespawnwarp.plugin;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.homespawnwarp.listener.JoinListener;
import com.homespawnwarp.listener.PlayerMoveListener;
import com.homespawnwarp.listener.RespawnListener;
import com.homespawnwarp.listener.TeleportListener;
import com.homespawnwarp.listener.TeleportWarmupCompleteListener;
import com.homespawnwarp.tool.CommandManager;
import com.homespawnwarp.tool.ConfigIO;
import com.homespawnwarp.tool.LocationIO;
import com.homespawnwarp.tool.MoneyMachine;
import com.homespawnwarp.tool.Teleportation;
import com.homespawnwarp.tool.Tools;

final public class HomeSpawnWarp extends JavaPlugin {

	public final static Logger logger = Logger.getLogger("Minecraft");
	public final static String emblem = "[HomeSpawnWarp]";

	public JavaPlugin plugin;

	public boolean useGeneralSpawn;
	public boolean useExactSpawn;
	public boolean useFireworkEffects;
	public boolean cancelWarmupsOnMove;// TODO unstaticify
	
	private CommandManager cManager;


	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
		ConfigIO.save("locations");
	}

	@Override
	public void onEnable() {
		// TODO Make commands initialize tru constructors
		cManager = new CommandManager(this);
		cManager.initCommands();
		
		
		setupWarmups();

		if (setupEconomy()) {
			logger.info(emblem + " Using vault!");
		} else {
			logger.warning(emblem
					+ " Vault not found. Economic features disabled!");
		}

		// LISTENERS!!!!!!!!!!!!!
		new TeleportListener(this,useFireworkEffects);

		if (useExactSpawn) {
			new RespawnListener(this);
			new JoinListener(this);
		}

		if (Teleportation.usesWarmup()) {
			new TeleportWarmupCompleteListener(this);

			if (cancelWarmupsOnMove) {
				new PlayerMoveListener(this);
			}
		}
		logger.info(emblem + " UseExactSpawn = " + useExactSpawn);
		logger.info(emblem + " UseGeneralSpawn = " + useGeneralSpawn);
		logger.info(emblem + " CancelWarmupsOnMove = " + cancelWarmupsOnMove);

		logger.info(emblem + " Enabled!");
	}

	@Override
	public void onLoad() {
		plugin = this;

		//Initialize tools
		LocationIO.init(this);
		Teleportation.init(this);
		ConfigIO.init(this);

		ConfigIO.saveDefault("messages");
		ConfigIO.saveDefault("locations");
		ConfigIO.saveDefault("config");

		Tools.getConfig().options().copyDefaults(true);
		Tools.getLocations().options().copyDefaults(true);
		Tools.getMessages().options().copyDefaults(true);

		ConfigIO.save("messages");
		ConfigIO.save("config");

		useExactSpawn = Tools.getConfig().getBoolean("useexactspawn");
		useGeneralSpawn = Tools.getConfig().getBoolean("usegeneralspawn");
		useFireworkEffects = Tools.getConfig().getBoolean("usefireworkeffects");
		cancelWarmupsOnMove = Tools.getConfig().getBoolean(
				"cancelwarmupsonmove");
	}

	private void setupWarmups() {
		Teleportation.setWarmups(
				Tools.getConfig().getDouble("warmups.home") * 1000, Tools
						.getConfig().getDouble("warmups.spawn") * 1000, Tools
						.getConfig().getDouble("warmups.warp") * 1000, Tools
						.getConfig().getDouble("warmups.warpto") * 1000);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "Command not implemented yet!");
		return false; // When forgetting something...
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			MoneyMachine.setEconomy(economyProvider.getProvider());

		}

		return (MoneyMachine.getEconomy() != null);
	}
}
