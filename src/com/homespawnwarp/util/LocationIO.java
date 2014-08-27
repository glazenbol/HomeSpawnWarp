package com.homespawnwarp.util;

import org.bukkit.Location;
import org.bukkit.World;

import com.homespawnwarp.HomeSpawnWarp;

final public class LocationIO {

	private static HomeSpawnWarp plugin;

	public static void init(HomeSpawnWarp plugin) {
		LocationIO.plugin = plugin;
	}


	public static void write(final String path, final Location location) {

		ConfigIO.getLocations()
				.set(path + ".world", location.getWorld().getName());
		ConfigIO.getLocations().set(path + ".x", location.getX());
		ConfigIO.getLocations().set(path + ".y", location.getY());
		ConfigIO.getLocations().set(path + ".z", location.getZ());
		ConfigIO.getLocations().set(path + ".yaw", location.getYaw());
		ConfigIO.getLocations().set(path + ".pitch", location.getPitch());
		ConfigIO.save("Locations");

	}

	public static void write(final String path, final Location location,
			double customPrice) {

		write(path, location);

		ConfigIO.getLocations().set(path + ".price", customPrice);
		ConfigIO.save("Locations");

	}
	

	public static boolean checkPriced(final String path) {
		return ConfigIO.getLocations().contains(path+ ".price");
	}
	
	public static double readPrice(final String path) {
		return ConfigIO.getLocations().getDouble(path + ".price");
	}

	public static Location read(final String path) {

		if (ConfigIO.get("Locations").contains(path)) {
			World w = plugin.getServer().getWorld(
					ConfigIO.getLocations().getString(path + ".world"));

			if (w != null) {
				return new Location(w, ConfigIO.getLocations().getDouble(
						path + ".x"), ConfigIO.getLocations().getDouble(
						path + ".y"), ConfigIO.getLocations().getDouble(
						path + ".z"), (float) ConfigIO.getLocations().getDouble(
						path + ".yaw"), (float) ConfigIO.getLocations().getDouble(
						path + ".pitch"));
			} else {
				return null;
			}

		} else {
			return null;
		}
	}
}
