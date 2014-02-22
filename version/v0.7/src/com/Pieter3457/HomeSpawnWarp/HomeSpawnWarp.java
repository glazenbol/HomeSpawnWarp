package com.Pieter3457.HomeSpawnWarp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class HomeSpawnWarp extends JavaPlugin {
	final Logger logger = Logger.getLogger("Minecraft");
	boolean useGeneralSpawn;
	boolean leightWeightMode;
	boolean useExactSpawn;
	HomeSpawnWarp plugin;
	FileConfiguration homes;
	FileConfiguration warps;
	FileConfiguration spawn;
	java.io.File homesFile;
	java.io.File warpsFile;
	java.io.File spawnFile;

	public HomeSpawnWarp() {

	}

	@Override
	public void onDisable() {
		HandlerList.unregisterAll(plugin);
		saveHomes();
		saveWarps();
		saveSpawn();
		logger.info("[HomeSpawnWarp] version " + getDescription().getVersion()
				+ " has been disabled!");
	}

	@Override
	public void onEnable() {
		logger.info("[HomeSpawnWarp] version " + getDescription().getVersion()
				+ " has been enabled!");
		if (useExactSpawn) {
			new RespawnListener(this);
		}
	}

	@Override
	public void onLoad() {

		logger.info("[HomeSpawnWarp] version " + getDescription().getVersion()
				+ " enabling...");
		getConfig().options().copyDefaults(true);
		saveConfig();

		getWarps();
		getHomes();
		getSpawn();

		// Get config set shit!
		leightWeightMode = getConfig().getBoolean("leightWeigtMode");
		useExactSpawn = getConfig().getBoolean("useExactSpawn");
		useGeneralSpawn = getConfig().getBoolean("useGeneralSpawn");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd,
			String commandLabel, String[] args) {
		switch (commandLabel) {

		case "sethome":
			if (isPlayer(sender)) {
				if (hasPerm(sender, "HomeSpawnWarp.sethome", true)) {
					sethome(sender);
					if (!leightWeightMode)
						saveHomes();
				}
			}
			return true;

		case "home":
			if (isPlayer(sender)) {
				if (hasPerm(sender, "HomeSpawnWarp.home", true)) {
					home(sender);
				}
			}
			return true;

		case "setspawn":
			if (isPlayer(sender)) {
				if (hasPerm(sender, "HomeSpawnWarp.setwarp", false)) {
					setspawn(sender);
					if (!leightWeightMode)
						saveSpawn();
				}
			}
			return true;

		case "spawn":
			if (isPlayer(sender)) {
				if (hasPerm(sender, "HomeSpawnWarp.spawn", true)) {
					spawn(sender);
				}
			}
			return true;

		case "warp":
			// NOTE: extra permission inside
			if (isPlayer(sender)) {
				if (hasPerm(sender, "HomeSpawnWarp.setwarp", true)) {
					warp(sender, args);
				}
			}
			return true;

		case "setwarp":
			if (isPlayer(sender)) {
				if (hasPerm(sender, "HomeSpawnWarp.setwarp", false)) {
					setwarp(sender, args);
					if (!leightWeightMode)
						saveWarps();
				}
			}
			return true;

		case "setprivatewarp":
			if (isPlayer(sender)) {
				if (hasPerm(sender, "HomeSpawnWarp.setprivatewarp", true)) {
					setprivatewarp(sender, args);
					if (!leightWeightMode)
						saveWarps();
				}
			}
			return true;

		case "delwarp":
			/** Too complex to check permissions here */
			delwarp(sender, args);
			if (!leightWeightMode)
				saveWarps();
			return true;

		case "warplist":
			if (hasPerm(sender, "HomeSpawnWarp.warplist", true)) {
				warplist(sender, args);
			}
			return true;
		default:
			sender.sendMessage(ChatColor.AQUA + "Command not implemented yet!");
			return false;

		}
	}

	void sethome(CommandSender sender) {
		Player player = (Player) sender;
		Location currentLocation = player.getLocation();
		homes.set(player.getName() + ".world", currentLocation.getWorld()
				.getName());
		homes.set(player.getName() + ".x", currentLocation.getX());
		homes.set(player.getName() + ".y", currentLocation.getY());
		homes.set(player.getName() + ".z", currentLocation.getZ());
		homes.set(player.getName() + ".yaw", currentLocation.getYaw());
		homes.set(player.getName() + ".pitch", currentLocation.getPitch());
		player.sendMessage(getConfig().getString("getMessages().set-home"));

	}

	void home(CommandSender sender) {
		Player player = (Player) sender;
		if (homes.getString(player.getName() + ".world") != null) {
			String worldName = homes.getString(player.getName() + ".world");
			Location location = new Location(getServer().getWorld(worldName),
					homes.getDouble(player.getName() + ".x"),
					homes.getDouble(player.getName() + ".y"),
					homes.getDouble(player.getName() + ".z"),
					(float) homes.getDouble(player.getName() + ".yaw"),
					(float) homes.getDouble(player.getName() + ".pitch"));
			player.sendMessage(getConfig().getString(
					"getMessages().teleport-to-home"));
			location.getChunk().load();
			player.teleport(location);
		} else
			player.sendMessage(getConfig().getString("getMessages().no-home-set"));
	}

	void setspawn(CommandSender sender) {
		String[] string = getConfig().getKeys(false).toArray(new String[0]);
		if (useGeneralSpawn) {
			for (int i = 0; i < string.length; i++) {
				spawn.set(string[i], null);
			}
		}

		Player player = (Player) sender;

		Location l = player.getLocation();
		
		String s = l.getWorld().getName();

		l.getWorld().setSpawnLocation((int) l.getX(), (int) l.getY(),
				(int) l.getZ());
		
		spawn.set(s + ".x", l.getX());
		spawn.set(s + ".y", l.getY());
		spawn.set(s + ".z", l.getZ());
		spawn.set(s + ".yaw", l.getYaw());
		spawn.set(s + ".pitch", l.getPitch());

		player.sendMessage(getConfig().getString("getMessages().set-spawn"));
	}

	void spawn(CommandSender sender) {

		Player player = (Player) sender;
		player.sendMessage(getConfig().getString("getMessages().teleport-to-spawn"));

		if (useGeneralSpawn) {

			// Exact general spawn teleport

			String[] s = spawn.getKeys(false).toArray(new String[0]);

			if (s.length != 0) {
				String string = s[0];
				Location l = new Location(getServer().getWorld(string),
						(float) spawn.getDouble(string + ".x"),
						(float) spawn.getDouble(string + ".y"),
						(float) spawn.getDouble(string + ".z"),
						(float) spawn.getDouble(string + ".yaw"),
						(float) spawn.getDouble(string + ".pitch"));
				l.getChunk().load();
				player.teleport(l);
			} else {
				player.sendMessage(getConfig().getString(
						"getMessages().no-spawn-set"));
			}

		} else {

			// Exact players current world spawn
			String s = player.getWorld().getName();
			if (spawn.contains(s)) {
				Location l = new Location(player.getWorld(),
						(float) spawn.getDouble(s + ".x"),
						(float) spawn.getDouble(s + ".y"),
						(float) spawn.getDouble(s + ".z"),
						(float) spawn.getDouble(s + ".yaw"),
						(float) spawn.getDouble(s + ".pitch"));
				l.getChunk().load();
				player.teleport(l);
			} else {
				player.sendMessage(getConfig().getString(
						"getMessages().no-spawn-set"));
			}
		}
	}

	void warp(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length != 0) {
			if (warps.getString("public." + args[0]) != null) {
				String worldName = warps.getString("public." + args[0]
						+ ".world");
				Location location = new Location(getServer()
						.getWorld(worldName), warps.getDouble("public."
						+ args[0] + ".x"), warps.getDouble("public." + args[0]
						+ ".y"), warps.getDouble("public." + args[0] + ".z"),
						(float) warps.getDouble("public." + args[0] + ".yaw"),
						(float) warps.getDouble("public." + args[0] + ".pitch"));
				player.sendMessage(getConfig().getString(
						"getMessages().teleport-to-warp"));
				location.getChunk().load();
				player.teleport(location);
			} else {

				if (warps.getString(player.getName() + "." + args[0]) != null) {
					String worldName = warps.getString(player.getName() + "."
							+ args[0] + ".world");
					Location location = new Location(getServer().getWorld(
							worldName), warps.getDouble(player.getName() + "."
							+ args[0] + ".x"), warps.getDouble(player.getName()
							+ "." + args[0] + ".y"), warps.getDouble(player
							.getName() + "." + args[0] + ".z"),
							(float) warps.getDouble(player.getName() + "."
									+ args[0] + ".yaw"),
							(float) warps.getDouble(player.getName() + "."
									+ args[0] + ".pitch"));
					player.sendMessage(getConfig().getString(
							"getMessages().teleport-to-private-warp"));
					location.getChunk().load();
					player.teleport(location);
				} else
					player.sendMessage(getConfig().getString(
							"getMessages().wrong-warpname"));

			}
		} else {
			if (sender.hasPermission("HomeSpawnWarp.warplist")
					|| !sender.isPermissionSet("HomeSpawnWarp.warplist"))
				warplist(player, args);
			else
				player.sendMessage(getConfig().getString(
						"getMessages().too-few-arguments"));
		}

	}

	void setwarp(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length != 0) {
			Location currentLocation = player.getLocation();
			warps.set("public." + args[0] + ".world", currentLocation
					.getWorld().getName());
			warps.set("public." + args[0] + ".x", currentLocation.getX());
			warps.set("public." + args[0] + ".y", currentLocation.getY());
			warps.set("public." + args[0] + ".z", currentLocation.getZ());
			warps.set("public." + args[0] + ".yaw", currentLocation.getYaw());
			warps.set("public." + args[0] + ".pitch",
					currentLocation.getPitch());
			player.sendMessage(getConfig().getString("getMessages().set-warp"));
		} else
			player.sendMessage(getConfig().getString(
					"getMessages().too-few-arguments"));
	}

	void setprivatewarp(CommandSender sender, String[] args) {
		Player player = (Player) sender;
		if (args.length != 0) {
			if (warps.getString("public." + args[0]) == null) {
				Location currentLocation = player.getLocation();
				warps.set(player.getName() + "." + args[0] + ".world",
						currentLocation.getWorld().getName());
				warps.set(player.getName() + "." + args[0] + ".x",
						currentLocation.getX());
				warps.set(player.getName() + "." + args[0] + ".y",
						currentLocation.getY());
				warps.set(player.getName() + "." + args[0] + ".z",
						currentLocation.getZ());
				warps.set(player.getName() + "." + args[0] + ".yaw",
						currentLocation.getYaw());
				warps.set(player.getName() + "." + args[0] + ".pitch",
						currentLocation.getPitch());
				player.sendMessage(getConfig().getString(
						"getMessages().set-warp-private"));
			} else
				player.sendMessage(getConfig().getString(
						"getMessages().warp-is-already"));
		} else
			player.sendMessage(getConfig().getString(
					"getMessages().too-few-arguments"));
	}

	void delwarp(CommandSender sender, String[] args) {
		if (sender.hasPermission("HomeSpawnWarp.setwarp")) {
			if (args.length != 0) {
				if (warps.getString("public." + args[0]) != null) {
					warps.set("public." + args[0], null);
					sender.sendMessage(getConfig().getString(
							"getMessages().warp-deleted-public"));
				} else {
					if (warps.getString(sender.getName() + "." + args[0]) != null) {
						warps.set(sender.getName() + "." + args[0], null);
						sender.sendMessage(getConfig().getString(
								"getMessages().warp-deleted-private"));
					} else
						sender.sendMessage(getConfig().getString(
								"getMessages().wrong-warpname"));
				}

			} else
				sender.sendMessage(getConfig().getString(
						"getMessages().too-few-arguments"));
		} else {
			if (sender.hasPermission("HomeSpawnWarp.setprivatewarp")
					|| !sender.isPermissionSet("HomeSpawnWarp.setprivatewarp")) {
				if (args.length != 0) {
					if (warps.getString(sender.getName() + "." + args[0]) != null) {
						warps.set(sender.getName() + "." + args[0], null);
						sender.sendMessage(getConfig().getString(
								"getMessages().warp-deleted-private"));
					} else
						sender.sendMessage(getConfig().getString(
								"getMessages().wrong-warpname"));
				} else
					sender.sendMessage(getConfig().getString(
							"getMessages().too-few-arguments"));
			} else
				sender.sendMessage(getConfig().getString(
						"getMessages().no-permission"));
		}
	}

	void warplist(CommandSender sender, String[] args) {

		// Public
		sender.sendMessage(getConfig().getString("getMessages().warpspublic"));
		if (warps.contains("public")) {
			String[] w = warps.getConfigurationSection("public").getKeys(false)
					.toArray(new String[0]);
			if (w.length == 0) {
				sender.sendMessage(getConfig().getString("getMessages().none"));
			} else {
				String fw = Arrays.toString(w);
				sender.sendMessage(ChatColor.DARK_GREEN + fw);
			}

		} else
			sender.sendMessage(getConfig().getString("getMessages().none"));

		// Private
		if (warps.contains(sender.getName())) {
			String[] w = warps.getConfigurationSection(sender.getName())
					.getKeys(false).toArray(new String[0]);
			if (w.length != 0) {
				String fw = Arrays.toString(w);
				sender.sendMessage(getConfig().getString(
						"getMessages().warpsprivate"));
				sender.sendMessage(ChatColor.DARK_GREEN + fw);
			}
		}
	}

	boolean hasPerm(CommandSender sender, String permission, boolean isDefault) {
		if (isDefault) {
			if (sender.hasPermission(permission)
					|| !sender.isPermissionSet(permission)) {
				return true;
			} else {
				sender.sendMessage(getConfig().getString(
						"getMessages().no-permission"));
				return false;
			}
		} else {
			if (sender.hasPermission(permission)) {
				return true;
			} else {
				sender.sendMessage(getConfig().getString(
						"getMessages().no-permission"));
				return false;
			}
		}
	}

	boolean isPlayer(CommandSender sender) {
		if (sender instanceof Player) {
			return true;
		} else {
			sender.sendMessage(getConfig().getString("getMessages().cannot-perform"));
			return false;
		}
	}

	boolean hasItem(Player player, ItemStack item, int amount) {
		Inventory i = player.getInventory();
		if (i.contains(item, amount)) {
			return true;
		} else {
			player.sendMessage(getConfig().getString(
					"getMessages().not-enough-items"));
			return false;
		}
	}

	FileConfiguration getWarps() {
		if (warps == null) {
			reloadWarps();
		}
		return warps;
	}

	FileConfiguration getHomes() {
		if (homes == null) {
			reloadHomes();
		}
		return homes;
	}

	FileConfiguration getSpawn() {
		if (spawn == null) {
			reloadSpawn();
		}
		return spawn;
	}

	void reloadWarps() {

		if (warpsFile == null) {
			warpsFile = new java.io.File(getDataFolder(), "warps.yml");
		}
		warps = YamlConfiguration.loadConfiguration(warpsFile);

		// Look for defaults in the jar
		InputStream defConfigStream = getResource("warps.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			warps.setDefaults(defConfig);
		}
	}

	void reloadHomes() {

		if (homesFile == null) {
			homesFile = new java.io.File(getDataFolder(), "homes.yml");
		}
		homes = YamlConfiguration.loadConfiguration(homesFile);

		// Look for defaults in the jar
		InputStream defConfigStream = getResource("homes.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			warps.setDefaults(defConfig);
		}
	}

	void reloadSpawn() {

		if (spawnFile == null) {
			spawnFile = new java.io.File(getDataFolder(), "spawn.yml");
		}
		spawn = YamlConfiguration.loadConfiguration(spawnFile);

		// Look for defaults in the jar
		InputStream defConfigStream = getResource("spawn.yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			warps.setDefaults(defConfig);
		}
	}

	void saveWarps() {
		if (warps == null || warpsFile == null) {
			return;
		}
		try {
			getWarps().save(warpsFile);
		} catch (IOException ex) {
			logger.severe("Could not save config to " + warpsFile
					+ ex.getStackTrace());
		}
	}

	void saveHomes() {
		if (homes == null || homesFile == null) {
			return;
		}
		try {
			getHomes().save(homesFile);
		} catch (IOException ex) {
			logger.severe("Could not save config to " + homesFile
					+ ex.getStackTrace());
		}
	}

	void saveSpawn() {
		if (spawn == null || spawnFile == null) {
			return;
		}
		try {
			getSpawn().save(spawnFile);
		} catch (IOException ex) {
			logger.severe("Could not save config to " + spawnFile
					+ ex.getStackTrace());
		}
	}
}
