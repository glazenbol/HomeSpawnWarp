package com.homespawnwarp.plugin;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
import com.homespawnwarp.listener.TeleportListener;
import com.homespawnwarp.listener.TeleportWarmupCompleteListener;
import com.homespawnwarp.tool.ConfigIO;
import com.homespawnwarp.tool.MoneyMachine;
import com.homespawnwarp.tool.Teleportation;
import com.homespawnwarp.tool.Tools;

final public class HomeSpawnWarp extends JavaPlugin {

	public final static Logger logger = Logger.getLogger("Minecraft");
	public final static String emblem = "[HomeSpawnWarp]";

	public static JavaPlugin plugin;

	public boolean useGeneralSpawn;
	public boolean useExactSpawn;
	public boolean useFireworkEffects;
	public boolean cancelWarmupsOnMove;// TODO unstaticify

	public SetHomeCommand setHomeCommand;
	public HomeCommand homeCommand;
	public HomeListCommand homeListCommand;
	public DelHomeCommand delHomeCommand;
	public SetSpawnCommand setSpawnCommand;
	public SpawnCommand spawnCommand;
	public SetWarpCommand setWarpCommand;
	public WarpCommand warpCommand;
	public WarpListCommand warpListCommand;
	public DelWarpCommand delWarpCommand;
	public WarpToCommand warpToCommand;
	public WarpAcceptCommand warpAcceptCommand;

	private void initCommands(HomeSpawnWarp hsw) {
		setHomeCommand = new SetHomeCommand(hsw, "HomeSpawnWarp.sethome", true,
				false);
		homeCommand = new HomeCommand(hsw, "HomeSpawnWarp.home", true, false);
		homeListCommand = new HomeListCommand(hsw, "HomeSpawnWarp.homelist",
				true, false);
		delHomeCommand = new DelHomeCommand(hsw, "HomeSpawnWarp.delhome", true,
				false);
		setSpawnCommand = new SetSpawnCommand(hsw, "HomeSpawnWarp.setspawn",
				false, false);
		spawnCommand = new SpawnCommand(hsw, "HomeSpawnWarp.spawn", true, false);
		setWarpCommand = new SetWarpCommand(hsw, "HomeSpawnWarp.setwarp",
				false, false);
		warpCommand = new WarpCommand(hsw, "HomeSpawnWarp.warp", true, false);
		warpListCommand = new WarpListCommand(hsw, "HomeSpawnWarp.warplist",
				true, true);
		delWarpCommand = new DelWarpCommand(hsw, "HomeSpawnWarp.delwarp",
				false, true);
		warpToCommand = new WarpToCommand(hsw, "HomeSpawnWarp.warpto", true,
				false);
		warpAcceptCommand = new WarpAcceptCommand(hsw,
				"HomeSpawnWarp.warpaccept", true, false);

		plugin.getCommand("sethome").setExecutor(setHomeCommand);
		plugin.getCommand("home").setExecutor(homeCommand);
		plugin.getCommand("homelist").setExecutor(homeListCommand);
		plugin.getCommand("delhome").setExecutor(delHomeCommand);
		plugin.getCommand("setspawn").setExecutor(setSpawnCommand);
		plugin.getCommand("spawn").setExecutor(spawnCommand);
		plugin.getCommand("setwarp").setExecutor(setWarpCommand);
		plugin.getCommand("warp").setExecutor(warpCommand);
		plugin.getCommand("warplist").setExecutor(warpListCommand);
		plugin.getCommand("delwarp").setExecutor(delWarpCommand);
		plugin.getCommand("warpto").setExecutor(warpToCommand);
		plugin.getCommand("warpaccept").setExecutor(warpAcceptCommand);
	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
		ConfigIO.save("locations");
	}

	@Override
	public void onEnable() {
		// TODO Make commands initialize tru constructors
		initCommands(this);
		setupPrices();
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
						.getConfig().getDouble("warmups.request") * 1000);
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

	private void setupPrices() {

		homeCommand.setupPrices();
		spawnCommand.setupPrices();
		warpCommand.setupPrices();
		warpToCommand.setupPrices();

	}
}
