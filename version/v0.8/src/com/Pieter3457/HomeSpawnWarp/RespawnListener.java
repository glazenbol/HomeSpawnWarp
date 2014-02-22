package com.Pieter3457.HomeSpawnWarp;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

	// MAKE LISTENER FAILPROOF

	HomeSpawnWarp plugin;

	public RespawnListener(HomeSpawnWarp hsw) {
		hsw.getServer().getPluginManager().registerEvents(this, hsw);
		plugin = hsw;
	}

	@EventHandler
	void onPlayerJoin(PlayerJoinEvent e) {
		if (!e.getPlayer().hasPlayedBefore()) {

			String[] s = plugin.getLocations().getConfigurationSection("spawn")
					.getKeys(false).toArray(new String[0]);

			Location l = plugin.locationIO.read("spawn." + s[0]);

			if (l != null) {

				if (!l.getChunk().isLoaded()) {
					l.getChunk().load();
				}
				e.getPlayer().teleport(l);
			}
		}
	}

	@EventHandler
	void onPlayerRespawn(PlayerRespawnEvent e) {
		if (!e.isBedSpawn()) {
			Player player = e.getPlayer();

			Location l = plugin.locationIO.read("homes." + player.getName()
					+ ".default");

			if (l != null) {

				if (!l.getChunk().isLoaded()) {
					l.getChunk().load();
				}
				e.setRespawnLocation(l);

			} else {
				if (plugin.useGeneralSpawn) {
					// Exact general spawn teleport
					String[] s = plugin.getLocations()
							.getConfigurationSection("spawn").getKeys(false)
							.toArray(new String[0]);

					l = plugin.locationIO.read("spawn." + s[0]);

					if (l != null) {

						if (!l.getChunk().isLoaded()) {
							l.getChunk().load();
						}
						e.setRespawnLocation(l);
					}

				} else {
					// Exact players current world spawn
					String s = e.getRespawnLocation().getWorld().getName();

					if (plugin.getLocations().contains("spawn." + s)) {

						l = plugin.locationIO.read("spawn." + s);

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
