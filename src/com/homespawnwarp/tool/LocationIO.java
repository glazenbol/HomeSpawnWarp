package com.homespawnwarp.tool;

import org.bukkit.Location;
import org.bukkit.World;

import com.homespawnwarp.plugin.HomeSpawnWarp;

final public class LocationIO {

	public static void write(final String path, final Location location) {

		Tools.getLocations()
				.set(path + ".world", location.getWorld().getName());
		Tools.getLocations().set(path + ".x", location.getX());
		Tools.getLocations().set(path + ".y", location.getY());
		Tools.getLocations().set(path + ".z", location.getZ());
		Tools.getLocations().set(path + ".yaw", location.getYaw());
		Tools.getLocations().set(path + ".pitch", location.getPitch());

		if (!HomeSpawnWarp.leightWeightMode) {
			ConfigIO.save("Locations");
		}
	}

	public static Location read(final String path) {

		if (ConfigIO.get("Locations").contains(path)) {
			World w = HomeSpawnWarp.plugin.getServer().getWorld(
					Tools.getLocations().getString(path + ".world")); // Cuz
																			// there
																			// is
																			// no
																			// other
																			// way
																			// to
																			// do
																			// this,
																			// I
																			// mean,
																			// you
																			// can't
																			// get
																			// the
																			// server
																			// statically
			if (w != null) {
				return new Location(w, Tools.getLocations().getDouble(
						path + ".x"), Tools.getLocations().getDouble(
						path + ".y"), Tools.getLocations().getDouble(
						path + ".z"), (float) Tools.getLocations()
						.getDouble(path + ".yaw"), (float) Tools.getLocations().getDouble(path + ".pitch"));
			} else {
				return null;
			}

		} else {
			return null;
		}
	}
}
