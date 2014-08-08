package com.homespawnwarp.util;

import org.bukkit.Location;
import org.bukkit.World;

import com.homespawnwarp.plugin.HomeSpawnWarp;

final public class LocationIO {

	private static HomeSpawnWarp plugin;

	public static void init(HomeSpawnWarp plugin) {
		LocationIO.plugin = plugin;
	}


	public static void write(final String path, final Location location) {

		Tools.getLocations()
				.set(path + ".world", location.getWorld().getName());
		Tools.getLocations().set(path + ".x", location.getX());
		Tools.getLocations().set(path + ".y", location.getY());
		Tools.getLocations().set(path + ".z", location.getZ());
		Tools.getLocations().set(path + ".yaw", location.getYaw());
		Tools.getLocations().set(path + ".pitch", location.getPitch());
		ConfigIO.save("Locations");

	}

	public static void write(final String path, final Location location,
			double customPrice) {

		write(path, location);

		Tools.getLocations().set(path + ".price", customPrice);
		ConfigIO.save("Locations");// TODO custom prices warps

	}
	

	public static boolean checkPriced(final String path) {
		return Tools.getLocations().contains(path + ".price");
	}
	
	public static double readPrice(final String path) {
		return Tools.getLocations().getDouble(path + ".price");
	}

	public static Location read(final String path) {

		if (ConfigIO.get("Locations").contains(path)) {
			World w = plugin.getServer().getWorld(
					Tools.getLocations().getString(path + ".world"));

			if (w != null) {
				return new Location(w, Tools.getLocations().getDouble(
						path + ".x"), Tools.getLocations().getDouble(
						path + ".y"), Tools.getLocations().getDouble(
						path + ".z"), (float) Tools.getLocations().getDouble(
						path + ".yaw"), (float) Tools.getLocations().getDouble(
						path + ".pitch"));
			} else {
				return null;
			}

		} else {
			return null;
		}
	}
}
