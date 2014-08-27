package com.homespawnwarp.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.tp.Teleportation;
import com.homespawnwarp.tp.TeleportationType;
import com.homespawnwarp.util.ConfigIO;
import com.homespawnwarp.util.LocationIO;

public class AccuarateSpawner implements Listener {

	HomeSpawnWarp plugin;

	public AccuarateSpawner(HomeSpawnWarp plugin) {
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (!e.getPlayer().hasPlayedBefore()) {

			String[] s = ConfigIO.getLocations().getConfigurationSection("spawn")
					.getKeys(false).toArray(new String[0]);

			if (s.length != 0) {
				Location l = LocationIO.read("spawn." + s[0]);

				if (l != null) {

					Teleportation.teleportPlayer(e.getPlayer(), l,
							TeleportationType.SPAWN, 0, false, false, 0);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		if (!e.isBedSpawn()) {
			Player player = e.getPlayer();

			Location l = LocationIO.read("homes." + player.getUniqueId()
					+ ".default");

			if (l != null) {

				if (!l.getChunk().isLoaded()) {
					l.getChunk().load();
				}
				e.setRespawnLocation(l);

			} else {
				if (plugin.useGeneralSpawn) {
					// Exact general spawn teleport
					String[] s = ConfigIO.getLocations()
							.getConfigurationSection("spawn").getKeys(false)
							.toArray(new String[0]);
					if (s.length != 0) {
						l = LocationIO.read("spawn." + s[0]);

						if (l != null) {

							if (!l.getChunk().isLoaded()) {
								l.getChunk().load();
							}
							e.setRespawnLocation(l);
						}
					}

				} else {
					// Exact players current world spawn
					String s = e.getRespawnLocation().getWorld().getName();

					if (ConfigIO.getLocations().contains("spawn." + s)) {

						l = LocationIO.read("spawn." + s);

						if (!l.getChunk().isLoaded()) {
							l.getChunk().load();
						}
						e.setRespawnLocation(l);
					}
				}
			}
		}
	}
}
