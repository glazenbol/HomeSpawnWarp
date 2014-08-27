package com.homespawnwarp;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.homespawnwarp.cmd.TeleportCommand;
import com.homespawnwarp.listener.AccuarateSpawner;
import com.homespawnwarp.listener.OnPlayerMoveWarmupCanceller;
import com.homespawnwarp.tp.Teleportation;
import com.homespawnwarp.util.CommandManager;
import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.EconomyManager;
import com.homespawnwarp.util.LocationIO;

final public class HomeSpawnWarp extends JavaPlugin {

	public final Logger logger = Logger.getLogger("Minecraft");
	public final String emblem = "[HomeSpawnWarp]";

	public JavaPlugin plugin;

	public boolean useGeneralSpawn;
	public boolean useExactSpawn;
	public boolean useFireworkEffects;
	public boolean cancelWarmupsOnMove;

	private CommandManager cManager;

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
		ConfigIO.save("locations");
	}

	@Override
	public void onLoad() {
		plugin = this;

		ConfigIO.init(this);

		ConfigIO.saveDefault("messages");
		ConfigIO.saveDefault("locations");
		ConfigIO.saveDefault("config");

		ConfigIO.getConfig().options().copyDefaults(true);
		ConfigIO.getLocations().options().copyDefaults(true);
		ConfigIO.getMessages().options().copyDefaults(true);

		ConfigIO.save("messages");
		ConfigIO.save("config");

		useExactSpawn = ConfigIO.getConfig().getBoolean("useexactspawn");
		useGeneralSpawn = ConfigIO.getConfig().getBoolean("usegeneralspawn");
		useFireworkEffects = ConfigIO.getConfig().getBoolean(
				"usefireworkeffects");
		cancelWarmupsOnMove = ConfigIO.getConfig().getBoolean(
				"cancelwarmupsonmove");

		LocationIO.init(this);
		Teleportation.init(this, useFireworkEffects);
	}

	@Override
	public void onEnable() {
		cManager = new CommandManager(this);
		cManager.initCommands();

		if (setupEconomy()) {
			logger.info(emblem + " Using vault!");
		} else {
			logger.warning(emblem
					+ " Vault not found. Economic features disabled!");
		}

		// LISTENERS!!!!!!!!!!!!!

		if (useExactSpawn) {
			new AccuarateSpawner(this);
		}

		if (TeleportCommand.usingWarmups) {
			if (cancelWarmupsOnMove) {
				new OnPlayerMoveWarmupCanceller(this);
			}
		}
		logger.info(emblem + " UseExactSpawn = " + useExactSpawn);
		logger.info(emblem + " UseGeneralSpawn = " + useGeneralSpawn);
		logger.info(emblem + " UseFireworkEffects = " + useFireworkEffects);
		logger.info(emblem + " CancelWarmupsOnMove = " + cancelWarmupsOnMove);

		logger.info(emblem + " Enabled!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "Command not implemented yet! :P");
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
			EconomyManager.setEconomy(economyProvider.getProvider());

		}

		return (EconomyManager.getEconomy() != null);
	}
}
