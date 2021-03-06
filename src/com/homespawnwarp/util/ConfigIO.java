package com.homespawnwarp.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.homespawnwarp.HomeSpawnWarp;

public final class ConfigIO {

	private final static HashMap<String, FileConfiguration> configs = new HashMap<String, FileConfiguration>();
	private final static HashMap<String, File> configFiles = new HashMap<String, File>();
	private static HomeSpawnWarp plugin;

	public static void init(HomeSpawnWarp plugin) {
		ConfigIO.plugin = plugin;
	}

	// GETTING

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
			configFiles.put(configName, plugin.getDataFolder());
		}

		configs.put(configName, YamlConfiguration.loadConfiguration(configFiles
				.get(configName)));

		Reader defaultConfig;
		try {
			defaultConfig = new InputStreamReader(plugin.getResource(configName
					+ ".yml"), "UTF8");

			if (defaultConfig != null) {
				YamlConfiguration defConfig = YamlConfiguration
						.loadConfiguration(defaultConfig);
				configs.get(configName).setDefaults(defConfig);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

	}

	// SAVING DEFAULT
	public static void saveDefault(String configName) {

		configName = configName.toLowerCase();

		if (!configFiles.containsKey(configName)) {
			configFiles.put(configName, new File(plugin.getDataFolder(),
					configName + ".yml"));
		}

		if (!configFiles.get(configName).exists()) {
			plugin.saveResource(configName + ".yml", false);
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
	
	public static FileConfiguration getLocations() {
		return ConfigIO.get("Locations");
	}

	public static FileConfiguration getConfig() {
		return ConfigIO.get("Config");
	}

	public static FileConfiguration getMessages() {
		return ConfigIO.get("Messages");
	}
}
