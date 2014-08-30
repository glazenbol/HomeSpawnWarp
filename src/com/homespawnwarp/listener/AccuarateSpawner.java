package com.homespawnwarp.listener;

import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.homespawnwarp.HomeSpawnWarp;
import com.homespawnwarp.tp.TeleportTicket;
import com.homespawnwarp.tp.Teleportation;
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

			Location l = LocationIO.read("spawn."
					+ e.getPlayer().getWorld().getName());

			if (l != null) {
				Teleportation.teleportPlayer(new TeleportTicket(e.getPlayer(),
						null, l, false, 0, 0));
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

				// TPs to default home
				if (!l.getChunk().isLoaded()) {
					l.getChunk().load();
				}
				e.setRespawnLocation(l);

			} else {
				if (plugin.useGeneralSpawn) {
					// Exact general spawn teleport
					Set<String> s = ConfigIO.getLocations()
							.getConfigurationSection("spawn").getKeys(false);
					if (s.size() != 0) {

						l = LocationIO.read("spawn." + s.iterator().next());

						if (l != null) {

							if (!l.getChunk().isLoaded()) {
								l.getChunk().load();
							}
							e.setRespawnLocation(l);
						}
					}

				} else {
					// Exact players current world spawn
					String s = "spawn."
							+ e.getRespawnLocation().getWorld().getName();

					l = LocationIO.read(s);
					if (l != null) {
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
