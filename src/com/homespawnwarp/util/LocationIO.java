package com.homespawnwarp.util;

import org.bukkit.Location;
import org.bukkit.World;

final public class LocationIO extends Tool{
	
	public static void write(final String path, final Location location) {//TODO iuud's
		

		Tools.getLocations()
				.set(path + ".world", location.getWorld().getName());
		Tools.getLocations().set(path + ".x", location.getX());
		Tools.getLocations().set(path + ".y", location.getY());
		Tools.getLocations().set(path + ".z", location.getZ());
		Tools.getLocations().set(path + ".yaw", location.getYaw());
		Tools.getLocations().set(path + ".pitch", location.getPitch());
		ConfigIO.save("Locations");

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
