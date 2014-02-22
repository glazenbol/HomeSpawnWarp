package com.homespawnwarp.main;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.homespawnwarp.cmd.DelHomeCommand;
import com.homespawnwarp.cmd.DelWarpCommand;
import com.homespawnwarp.cmd.HomeCommand;
import com.homespawnwarp.cmd.HomeListCommand;
import com.homespawnwarp.cmd.SetHomeCommand;
import com.homespawnwarp.cmd.SetSpawnCommand;
import com.homespawnwarp.cmd.SetWarpCommand;
import com.homespawnwarp.cmd.SpawnCommand;
import com.homespawnwarp.cmd.WarpAcceptCommand;
import com.homespawnwarp.cmd.WarpCommand;
import com.homespawnwarp.cmd.WarpListCommand;
import com.homespawnwarp.cmd.WarpToCommand;
import com.homespawnwarp.listener.JoinListener;
import com.homespawnwarp.listener.PlayerMoveListener;
import com.homespawnwarp.listener.RespawnListener;
import com.homespawnwarp.listener.TimerCompleteListener;
import com.homespawnwarp.tool.ConfigIO;
import com.homespawnwarp.tool.Teleportation;
import com.homespawnwarp.tool.Tools;

final public class HomeSpawnWarp extends JavaPlugin { // TODO FIX VAULT ECONOMY

	final static Logger logger = Logger.getLogger("Minecraft");
	final static String emblem = "[HomeSpawnWarp]";

	public Economy economy;

	public static JavaPlugin hsw;

	public static boolean useGeneralSpawn;
	public static boolean leightWeightMode;
	public static boolean useExactSpawn;
	public static boolean cancelWarmupsOnMove;

	private final SetHomeCommand setHomeCommand = new SetHomeCommand(this,
			"HomeSpawnWarp.sethome", true, false);
	private final HomeCommand homeCommand = new HomeCommand(this,
			"HomeSpawnWarp.home", true, false);
	private final HomeListCommand homeListCommand = new HomeListCommand(this,
			"HomeSpawnWarp.homelist", true, false);
	private final DelHomeCommand delHomeCommand = new DelHomeCommand(this,
			"HomeSpawnWarp.delhome", true, false);
	private final SetSpawnCommand setSpawnCommand = new SetSpawnCommand(this,
			"HomeSpawnWarp.setspawn", false, false);
	private final SpawnCommand spawnCommand = new SpawnCommand(this,
			"HomeSpawnWarp.spawn", true, false);
	private final SetWarpCommand setWarpCommand = new SetWarpCommand(this,
			"HomeSpawnWarp.setwarp", false, false);
	private final WarpCommand warpCommand = new WarpCommand(this,
			"HomeSpawnWarp.warp", true, false);
	private final WarpListCommand warpListCommand = new WarpListCommand(this,
			"HomeSpawnWarp.warplist", true, true);
	private final DelWarpCommand delWarpCommand = new DelWarpCommand(this,
			"HomeSpawnWarp.delwarp", false, true);
	private final WarpToCommand warpToCommand = new WarpToCommand(this,
			"HomeSpawnWarp.warpto", true, false);
	private final WarpAcceptCommand warpAcceptCommand = new WarpAcceptCommand(
			this, "HomeSpawnWarp.warpaccept", true, false);

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
		ConfigIO.save("locations");
	}

	@Override
	public void onEnable() {
		if (setupEconomy()) {
			logger.info(emblem + " Using vault!");
		} else {
			logger.warning(emblem
					+ " Vault not found. Economic features disabled!");
		}

		getCommand("sethome").setExecutor(setHomeCommand);
		getCommand("home").setExecutor(homeCommand);
		getCommand("homelist").setExecutor(homeListCommand);
		getCommand("delhome").setExecutor(delHomeCommand);
		getCommand("setspawn").setExecutor(setSpawnCommand);
		getCommand("spawn").setExecutor(spawnCommand);
		getCommand("setwarp").setExecutor(setWarpCommand);
		getCommand("warp").setExecutor(warpCommand);
		getCommand("warplist").setExecutor(warpListCommand);
		getCommand("delwarp").setExecutor(delWarpCommand);
		getCommand("warpto").setExecutor(warpToCommand);
		getCommand("warpaccept").setExecutor(warpAcceptCommand);

		if (useExactSpawn) {
			new RespawnListener(this);
			new JoinListener(this);
		}

		if (Teleportation.usesWarmup()) {
			new TimerCompleteListener(this);

			if (cancelWarmupsOnMove) {
				new PlayerMoveListener(this);
			}
		}

		logger.info(emblem + " LeightWeightMode = " + leightWeightMode);
		logger.info(emblem + " UseExactSpawn = " + useExactSpawn);
		logger.info(emblem + " UseGeneralSpawn = " + useGeneralSpawn);
		logger.info(emblem + " CancelWarmupsOnMove = " + cancelWarmupsOnMove);

		logger.info(emblem + " Enabled!");
	}

	@Override
	public void onLoad() {
		ConfigIO.init(this);
		hsw = this;
		
		ConfigIO.saveDefault("messages");
		ConfigIO.saveDefault("locations");
		ConfigIO.saveDefault("config");
		
		Tools.getConfig().options().copyDefaults(true);
		Tools.getLocations().options().copyDefaults(true);
		Tools.getMessages().options().copyDefaults(true);
		
		ConfigIO.save("messages");
		ConfigIO.save("config");
		
		leightWeightMode = Tools.getConfig().getBoolean("leightweigtmode");
		useExactSpawn = Tools.getConfig().getBoolean("useexactspawn");
		useGeneralSpawn = Tools.getConfig().getBoolean("usegeneralspawn");
		cancelWarmupsOnMove = Tools.getConfig().getBoolean(
				"cancelwarmupsonmove");

		setupPrices();
		setupWarmups();

		setHomeCommand.group1 = Tools.getConfig().getInt("group1");
		setHomeCommand.group2 = Tools.getConfig().getInt("group2");
		setHomeCommand.group3 = Tools.getConfig().getInt("group3");
		setHomeCommand.group4 = Tools.getConfig().getInt("group4");
		setHomeCommand.group5 = Tools.getConfig().getInt("group5");
	}

	private void setupWarmups() {
		Teleportation.setWarmups(
				Tools.getConfig().getDouble("warmups.home") * 1000, Tools
						.getConfig().getDouble("warmups.spawn") * 1000, Tools
						.getConfig().getDouble("warmups.warp") * 1000, Tools
						.getConfig().getDouble("warmups.request") * 1000);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "Command not implemented yet!");
		return false;
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer()
				.getServicesManager().getRegistration(
						net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	private void setupPrices() {
		ConfigurationSection cs = Tools.getConfig().getConfigurationSection(
				"prices");

		if (cs.contains("sethome")) {
			setHomeCommand.setPrice(cs.getDouble("sethome"));
		}
		if (cs.contains("home")) {
			homeCommand.setPrice(cs.getDouble("home"));
		}
		if (cs.contains("homelist")) {
			homeListCommand.setPrice(cs.getDouble("homelist"));
		}
		if (cs.contains("setspawn")) {
			setSpawnCommand.setPrice(cs.getDouble("setspawn"));
		}
		if (cs.contains("spawn")) {
			spawnCommand.setPrice(cs.getDouble("spawn"));
		}
		if (cs.contains("setwarp")) {
			setWarpCommand.setPrice(cs.getDouble("setwarp"));
		}
		if (cs.contains("warp")) {
			warpCommand.setPrice(cs.getDouble("warp"));
		}
		if (cs.contains("warplist")) {
			warpListCommand.setPrice(cs.getDouble("warplist"));
		}
		if (cs.contains("delwarp")) {
			delWarpCommand.setPrice(cs.getDouble("delwarp"));
		}
		if (cs.contains("warpto")) {
			delWarpCommand.setPrice(cs.getDouble("warpto"));
		}
	}

}
