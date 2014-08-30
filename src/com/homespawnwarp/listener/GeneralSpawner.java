package com.homespawnwarp.listener;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.LocationIO;

public class GeneralSpawner implements Listener {

	HomeSpawnWarp plugin;

	public GeneralSpawner(HomeSpawnWarp plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Set<String> s = ConfigIO.getLocations()
				.getConfigurationSection("spawn").getKeys(false);
		if (s.size() != 0) {

			Location l = LocationIO.read("spawn." + s.iterator().next());

			if (l != null) {

				if (!l.getChunk().isLoaded()) {
					l.getChunk().load();
				}
				e.setRespawnLocation(l);
			}
		}
	}
}
