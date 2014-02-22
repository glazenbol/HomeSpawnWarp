package com.Pieter3457.HomeSpawnWarp;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class HomeSpawnWarp extends JavaPlugin {
	
	final LocationIO locationIO = new LocationIO(this);
	
	final Logger logger = Logger.getLogger("Minecraft");
	final String emblem = "[HomeSpawnWarp]";
	
	boolean useGeneralSpawn;
	boolean leightWeightMode;
	boolean useExactSpawn;
	
	final SetHomeCommand setHomeCommand = new SetHomeCommand(this, "HomeSpawnWarp.sethome", true, false);
	final HomeCommand homeCommand = new HomeCommand(this, "HomeSpawnWarp.home", true, false, new ItemStack(Material.DIRT, 15), 0);
	final HomeListCommand homeListCommand = new HomeListCommand(this, "HomeSpawnWarp.homelist", true, false);
	final DelHomeCommand delHomeCommand = new DelHomeCommand(this, "HomeSpawnWarp.delhome", true, false);
	final SetSpawnCommand setSpawnCommand = new SetSpawnCommand(this, "HomeSpawnWarp.setspawn", false, false);
	final SpawnCommand spawnCommand = new SpawnCommand(this, "HomeSpawnWarp.spawn", true, false, null, 0);
	final SetWarpCommand setWarpCommand = new SetWarpCommand(this, "HomeSpawnWarp.setwarp", false, false);
	final WarpCommand warpCommand = new WarpCommand(this, "HomeSpawnWarp.warp", true, false, null, 0);
	final WarpListCommand warpListCommand = new WarpListCommand(this, "HomeSpawnWarp.warplist", true, true);
	final DelWarpCommand delWarpCommand = new DelWarpCommand(this, "HomeSpawnWarp.delwarp", false, true);

	FileConfiguration getLocations();
	FileConfiguration getMessages();
	java.io.File getLocations()File;
	java.io.File getMessages()File;

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
		saveLocations();
	}

	@Override
	public void onEnable() {
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

		if (useExactSpawn) {
			new RespawnListener(this);
		}
		
		logger.log(Level.INFO, emblem + " Enabled!");
	}

	@Override
	public void onLoad() {
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		reloadMessages();
		reloadLocations();
		
		getMessages().options().copyDefaults(true);
		getLocations().options().copyDefaults(true);
		
		saveLocations();
		saveMessages();

		leightWeightMode = getConfig().getBoolean("leightWeigtMode");
		useExactSpawn = getConfig().getBoolean("useExactSpawn");
		useGeneralSpawn = getConfig().getBoolean("useGeneralSpawn");
		
		logger.info(emblem + " LeightWeightMode = " + leightWeightMode);
		logger.info(emblem + " UseExactSpawn = " + useExactSpawn);
		logger.info(emblem + " UseGeneralSpawn = " + useGeneralSpawn);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		sender.sendMessage(ChatColor.AQUA + "Command not implemented yet!");
		return false;
	}

	void reloadMessages() {

		if (getMessages()File == null) {
			getMessages()File = new java.io.File(getDataFolder(), "getMessages().yml");
		}
		getMessages() = YamlConfiguration.loadConfiguration(getMessages()File);

		// Look for defaults in the jar
		InputStream defConfigStream = getResource("getMessages().yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			getMessages().setDefaults(defConfig);
		}
	}

	void reloadLocations() {

		if (getLocations()File == null) {
			getLocations()File = new java.io.File(getDataFolder(), "getLocations().yml");
		}
		getLocations() = YamlConfiguration.loadConfiguration(getLocations()File);

		// Look for defaults in the jar
		InputStream defConfigStream = getResource("getLocations().yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			getLocations().setDefaults(defConfig);
		}
	}

	void saveLocations() {
		
		if (getLocations() == null || getLocations()File == null) {
			return;
		}
		
		try {
			getLocations().save(getLocations()File);
		} catch (IOException ex) {
			logger.severe("Could not save config to " + getLocations()File
					+ ex.getStackTrace());
		}
	}
	
	void saveMessages() {
		
		if (getMessages() == null || getMessages()File == null) {
			return;
		}
		
		try {
			getMessages().save(getMessages()File);
		} catch (IOException ex) {
			logger.severe("Could not save config to " + getMessages()File
					+ ex.getStackTrace());
		}
	}
}
