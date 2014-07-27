package com.homespawnwarp.util;

import org.bukkit.configuration.file.FileConfiguration;

public class Tools {

	public static FileConfiguration getLocations() {
		return ConfigIO.get("Locations");
	}

	public static FileConfiguration getConfig() {
		return ConfigIO.get("Config");
	}

	public static FileConfiguration getMessages() {
		return ConfigIO.get("Messages");
	}

	public static String getMessage(String path) {
		return ConfigIO.get("Messages").getString(path);
	}
}
