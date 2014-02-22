package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.Location;
import org.bukkit.World;

public class LocationIO {

	HomeSpawnWarp plugin;

	public LocationIO(HomeSpawnWarp plugin) {
		this.plugin = plugin;
	}

	public void write(String path, Location location) {

		plugin.getLocations().set(path + ".world", location.getWorld().getName());
		plugin.getLocations().set(path + ".x", location.getX());
		plugin.getLocations().set(path + ".y", location.getY());
		plugin.getLocations().set(path + ".z", location.getZ());
		plugin.getLocations().set(path + ".yaw", location.getYaw());
		plugin.getLocations().set(path + ".pitch", location.getPitch());
	}

	public Location read(String path) {

		if (plugin.getLocations().contains(path)) {
			World w = plugin.getServer().getWorld(
					plugin.getLocations().getString(path + ".world"));
			if (w != null) {
				return new Location(w, plugin.getLocations().getDouble(path + ".x"),
						plugin.getLocations().getDouble(path + ".y"),
						plugin.getLocations().getDouble(path + ".z"),
						(float) plugin.getLocations().getDouble(path + ".yaw"),
						(float) plugin.getLocations().getDouble(path + ".pitch"));
			} else {
				return null;
			}

		} else {
			return null;
		}
	}
}
