package com.homespawnwarp.tool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.homespawnwarp.main.HomeSpawnWarp;

public final class ConfigIO {

	private final static HashMap<String, FileConfiguration> configs = new HashMap<String, FileConfiguration>();
	private final static HashMap<String, File> configFiles = new HashMap<String, File>();
	private static JavaPlugin main;

	// TODO Make add default messages when missing

	private ConfigIO() {
		// initial constructor
	}
	
	// GETTING

	public static void init(JavaPlugin main) {
		ConfigIO.main = main;
	}

	public static FileConfiguration get(String configName) {

		configName = configName.toLowerCase();

		if (!configs.containsKey(configName)) {
			load(configName);
		}
		return configs.get(configName);
	}

	// LOADING
	public static void load(String configName) {

		configName = configName.toLowerCase();

		if (!configFiles.containsKey(configName)) {
			configFiles.put(configName, HomeSpawnWarp.hsw.getDataFolder());
		}

		configs.put(configName, YamlConfiguration.loadConfiguration(configFiles
				.get(configName)));
		InputStream defConfigStream = main.getResource(configName + ".yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			configs.get(configName).setDefaults(defConfig);
			/* configs.get(configName).options().copyDefaults(true);
			save(configName);*/

		}
	}

	// SAVING DEFAULT
	public static void saveDefault(String configName) {

		configName = configName.toLowerCase();

		if (!configFiles.containsKey(configName)) {
			configFiles.put(configName,
					new File(HomeSpawnWarp.hsw.getDataFolder(), configName
							+ ".yml"));
		}

		if (!configFiles.get(configName).exists()) {
			HomeSpawnWarp.hsw.saveResource(configName + ".yml", false);
		}
	}

	// SAVING
	public static void save(String configName) {

		configName = configName.toLowerCase();

		if (!configs.containsKey(configName)
				|| !configFiles.containsKey(configName)) {
			return;
		}

		try {
			configs.get(configName).save(configFiles.get(configName));
		} catch (IOException ex) {
		}
	}

	public static Set<String> getCreatedConfigNames() {
		return configs.keySet();
	}
}
